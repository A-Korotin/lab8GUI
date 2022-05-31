package gui.controller;

import collection.DAO;
import collection.ServerDAO;
import dragon.*;
import exceptions.ServerUnreachableException;
import gui.controller.context.Context;
import io.Properties;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import net.codes.ExitCode;
import net.codes.Result;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class TableWindowController extends Controller implements Initializable {
    @FXML
    private Button logoutButton;
    @FXML
    private Text buttonError;
    @FXML
    private Button visualizationButton;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button removeButton;
    @FXML
    private Text helloText;
    @FXML
    private ChoiceBox<String> propertyBox;
    @FXML
    private TextField filterField;
    @FXML
    private TableView<Dragon> table;
    @FXML
    private ImageView loading;

    private List<Dragon> tableItems = new ArrayList<>();

    private DAO serverDao;

    private Thread worker;

    private static final String HOST = "localhost";
    private static final int PORT = 4444;

    private final List<Column> columns = new ArrayList<>();

    private final String[] filterProperties = {"name", "creator name", "id"};

    @Override
    protected void localize() {
        logoutButton.setText(Context.locale.getMsg("logout"));
        visualizationButton.setText(Context.locale.getMsg("visualization"));
        addButton.setText(Context.locale.getMsg("add"));
        updateButton.setText(Context.locale.getMsg("update"));
        removeButton.setText(Context.locale.getMsg("remove"));
        helloText.setText("%s, %s!".formatted(Context.locale.getMsg("hello"), Context.user.login));
        filterField.setPromptText(Context.locale.getMsg("filter"));
        propertyBox.setValue(Context.locale.getMsg("filter"));
    }


    private void runWorker() {
        Runnable task = () -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(20_000); //todo busy-waiting
                } catch (InterruptedException ignored){
                    break;
                }
                try {
                    tableItems = serverDao.getAll();
                }  catch (ServerUnreachableException ignored) {
                    Runnable errorWindow = () -> {
                        try {
                            // pass nay node to get window from it
                            openSubStage(table, "/gui/errorWindow.fxml", c->{});
                        } catch (IOException e) {e.printStackTrace();}
                    };
                    // run error window in javafx thread
                    Platform.runLater(errorWindow);
                }
            }
        };
        worker = new Thread(task);
        worker.setDaemon(true);
        worker.start();
    }

    {
        Column idColumn = new Column("id");

        Column nameColumn = new Column("name");

        Column coordinatesColumn = new Column("coordinates", c -> {
        });
        {
            Column xCoord = new Column("x", c ->
                    c.setCellValueFactory(callback -> new SimpleStringProperty(callback.getValue().getCoordinates().getX().toString())));

            Column yCoord = new Column("y", c ->
                    c.setCellValueFactory(callback -> new SimpleStringProperty(callback.getValue().getCoordinates().getY().toString())));

            coordinatesColumn.addChildren(xCoord, yCoord);
        }

        Column creationDateColumn = new Column("creation date", c ->
                c.setCellValueFactory(callback -> new SimpleStringProperty(Context.locale.formatDate(callback.getValue().getCreationDate()))));

        Column ageColumn = new Column("age");

        Column colorColumn = new Column("color", c -> c.setCellValueFactory(callback ->
                new SimpleStringProperty(callback.getValue().getColor() == null ?
                        "null" :
                        callback.getValue().getColor().getDescription())));

        Column typeColumn = new Column("type", c ->
                c.setCellValueFactory(callback -> new SimpleStringProperty(callback.getValue().getType().getDescription())));

        Column characterColumn = new Column("character", c ->
                c.setCellValueFactory(callback -> new SimpleStringProperty(callback.getValue().getCharacter() == null ?
                        "null" :
                        callback.getValue().getCharacter().getDescription())));

        Column caveColumn = new Column("cave", c -> {
        });
        {
            Column depth = new Column("depth", c ->
                    c.setCellValueFactory(callback -> new SimpleStringProperty(String.valueOf(callback.getValue().getCave().getDepth()))));

            Column nTreasures = new Column("number of treasures", c ->
                    c.setCellValueFactory(callback -> new SimpleStringProperty(callback.getValue().getCave().getNumberOfTreasures() == null ?
                            "null" :
                            callback.getValue().getCave().getNumberOfTreasures().toString())));

            caveColumn.addChildren(depth, nTreasures);
        }

        Column creatorNameColumn = new Column("creator name", c ->
                c.setCellValueFactory(callback -> new SimpleStringProperty(callback.getValue().getCreatorName())));

        columns.add(idColumn);
        columns.add(nameColumn);
        columns.add(coordinatesColumn);
        columns.add(creationDateColumn);
        columns.add(ageColumn);
        columns.add(colorColumn);
        columns.add(typeColumn);
        columns.add(characterColumn);
        columns.add(caveColumn);
        columns.add(creatorNameColumn);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            serverDao = new ServerDAO(HOST, PORT);
        } catch (IOException ignored) {
        }

        buttonError.setText("");

        // load cols
        columns.forEach(column -> table.getColumns().add(column.composeColumn()));
        table.getColumns().forEach(col -> col.setPrefWidth(120));

        propertyBox.getItems().addAll(filterProperties);
        propertyBox.setOnAction(e -> filterTyped());

        // blocking part, execute in runtime
        new Thread(() -> {
            tableItems = serverDao.getAll();
            table.setItems(FXCollections.observableList(tableItems));
            this.loading.setImage(null);
        }).start();

        runWorker();
    }

    public void reloadTable(List<Dragon> items) {
        table.setItems(FXCollections.observableList(items));
        table.refresh();
    }

    public void addElement(Dragon dragon) {
        tableItems.add(dragon);
        reloadTable(tableItems);
    }

    public void logout(ActionEvent event) throws IOException {
        worker.interrupt();
        switchScene(event, "/gui/signInWindow.fxml");
    }

    private Dragon checkSelectedItem() {
        Dragon selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem == null)
            buttonError.setText(Context.locale.getMsg("no_item_selected"));
        return selectedItem;
    }

    private boolean wrongUser(Dragon selectedItem) {
        boolean notAllowed = !selectedItem.getCreatorName().equals(Context.user.login);
        if (notAllowed)
            buttonError.setText("%s: %s".formatted(Context.locale.getMsg("INVALID_INPUT"), Context.locale.getMsg("NO_RIGHTS_TO_MODIFY")));
        else
            buttonError.setText("");

        return notAllowed;
    }

    public void visualization(ActionEvent event) throws IOException {
        Dragon selected;
        if ((selected = checkSelectedItem()) == null)
            return;

        VisualizationWindowController controller =
                openSubStage(event, "/gui/visualizationWindow.fxml", c -> {
                    c.setElement(selected);
                    c.paint();
                });
    }

    public void add(ActionEvent event) throws IOException {
        buttonError.setText("");
        RequestWindowController controller = openSubStage(event, "/gui/request.fxml");
        Properties properties = controller.getProperties();
        if (properties == null)
            return;

        Result result;
        try {
            result = serverDao.create(properties);
        } catch (ServerUnreachableException e) {
            ErrorWindowController errorController = openSubStage(event, "/gui/errorWindow.fxml");
            handleErrorWindow(errorController.getButtonPressed(), table);
            return;
        }
        if (result.exitCode != ExitCode.OK) {
            buttonError.setText("%s: %s".formatted(Context.locale.getMsg(result.exitCode), Context.locale.getMsg(result.eventCodes.get(0))));
            return;
        }
        int id = Integer.parseInt(result.info);
        addElement(new Dragon(id, properties));
    }

    public void update(ActionEvent event) throws IOException {
        Dragon selectedItem;
        if ((selectedItem = checkSelectedItem()) == null)
            return;

        if (wrongUser(selectedItem))
            return;

        RequestWindowController controller = openSubStage(event, "/gui/request.fxml");
        Properties properties = controller.getProperties();
        if (properties == null)
            return;

        int idToUpdate = selectedItem.getId();

        Result result;
        try {
            result = serverDao.update(idToUpdate, properties);
        } catch (ServerUnreachableException e) {
            ErrorWindowController errorController = openSubStage(event, "/gui/errorWindow.fxml");
            handleErrorWindow(errorController.getButtonPressed(), table);
            return;
        }

        if (result.exitCode != ExitCode.OK) {
            buttonError.setText("%s: %s".formatted(Context.locale.getMsg(result.exitCode), Context.locale.getMsg(result.eventCodes.get(0))));
            return;
        }

        tableItems.forEach(dragon -> {
            if (dragon.equals(selectedItem)) {
                dragon.update(properties);
            }
        });
        table.refresh();
    }

    public void remove(ActionEvent event) throws IOException {
        Dragon selectedItem;
        if ((selectedItem = checkSelectedItem()) == null)
            return;

        if (wrongUser(selectedItem))
            return;

        int idToDelete = selectedItem.getId();

        Result result;
        try {
            result = serverDao.delete(idToDelete);
        } catch (ServerUnreachableException e) {
            ErrorWindowController errorController = openSubStage(event, "/gui/errorWindow.fxml");
            handleErrorWindow(errorController.getButtonPressed(), table);
            return;
        }

        if (result.exitCode != ExitCode.OK) {
            buttonError.setText("%s: %s".formatted(Context.locale.getMsg(result.exitCode), Context.locale.getMsg(result.eventCodes.get(0))));
            return;
        }
        tableItems.removeIf(d -> d.equals(selectedItem));
        table.refresh();
    }

    public void filterTyped() {
        String filter = filterField.getText().trim();
        if (filter.isEmpty()) {
            reloadTable(tableItems);
            return;
        }

        String filterProperty = propertyBox.getValue();

        // no property selected
        if (filterProperty.equals(Context.locale.getMsg("filter")))
            return;

        List<Dragon> filteredTable = filterByProperty(filterProperty, filter);

        reloadTable(filteredTable);
    }

    private List<Dragon> filterByProperty(String property, String subSequence) {

        return switch (property) {
            case "name" -> tableItems.stream().filter(d -> d.getName().toLowerCase().contains(subSequence.toLowerCase())).collect(Collectors.toList());
            case "creator name" -> tableItems.stream().filter(d -> d.getCreatorName().toLowerCase().contains(subSequence.toLowerCase())).collect(Collectors.toList());
            case "id" -> tableItems.stream().filter(d -> d.getId().toString().equals(subSequence)).collect(Collectors.toList());
            default -> throw new RuntimeException(""); // never happens
        };
    }

    private static class Column {
        private final String label;
        private Consumer<TableColumn<Dragon, String>> additionalCode;
        private final List<Column> children = new ArrayList<>();

        public Column(String label) {
            this.label = label;
        }

        public Column(String label, Consumer<TableColumn<Dragon, String>> additionalCode) {
            this.label = label;
            this.additionalCode = additionalCode;
        }

        public TableColumn<Dragon, String> composeColumn() {
            TableColumn<Dragon, String> out = new TableColumn<>(label);
            if (additionalCode != null)
                additionalCode.accept(out);
            else
                out.setCellValueFactory(new PropertyValueFactory<>(label));

            children.forEach(column -> out.getColumns().add(column.composeColumn()));

            return out;
        }

        public void addChildren(Column... columns) {
            children.addAll(List.of(columns));
        }
    }
}
