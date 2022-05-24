package gui.controller;

import collection.DAO;
import collection.ServerDAO;
import dragon.*;
import exceptions.NonOKExitException;
import exceptions.ServerUnreachableException;
import gui.controller.context.Context;
import io.Properties;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import net.codes.ExitCode;
import net.codes.Result;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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

    private List<Dragon> tableItems;

    private DAO serverDao;

    private static final String HOST = "localhost";
    private static final int PORT = 4444;

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

    private Column[] columns = {
            new Column("id", null),
            new Column("name", null),
            new Column("coordinates", col -> {
                Column x = new Column("x",
                        column -> column.setCellValueFactory(callback -> new SimpleStringProperty(callback.getValue().getCoordinates().getX().toString())));
                Column y = new Column("y",
                        column -> column.setCellValueFactory(callback -> new SimpleStringProperty(callback.getValue().getCoordinates().getY().toString())));

                col.getColumns().addAll(x.composeColumn(), y.composeColumn());

            }),
            new Column("creationDate", null),
            new Column("age", null),
            new Column("color", col -> col.setCellValueFactory(callback ->
                    new SimpleStringProperty(callback.getValue().getColor() == null ?
                            "null" :
                            callback.getValue().getColor().getDescription()))),

            new Column("type", col -> col.setCellValueFactory(callback -> new SimpleStringProperty(callback.getValue().getType().getDescription()))),
            new Column("character",
                    col -> col.setCellValueFactory(callback -> new SimpleStringProperty(callback.getValue().getCharacter() == null ?
                            "null" :
                            callback.getValue().getCharacter().getDescription()))),

            new Column("cave", col -> {
                Column depth = new Column("depth", c -> c.setCellValueFactory(callback -> new SimpleStringProperty(String.valueOf(callback.getValue().getCave().getDepth()))));
                Column nTreasures = new Column("number of treasures", c -> c.setCellValueFactory(callback ->
                        new SimpleStringProperty(callback.getValue().getCave().getNumberOfTreasures() == null ?
                                "null" :
                                callback.getValue().getCave().getNumberOfTreasures().toString())));

                col.getColumns().addAll(depth.composeColumn(), nTreasures.composeColumn());
            }),
            new Column("creator name", col -> col.setCellValueFactory(callback -> new SimpleStringProperty(callback.getValue().getCreatorName())))
    };

    private final String[] filterProperties = {"name", "creator name", "id"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            serverDao = new ServerDAO(HOST, PORT);
        } catch (IOException ignored) {}

        buttonError.setText("");

        // load cols
        Arrays.stream(columns).forEachOrdered(column -> table.getColumns().add(column.composeColumn()));
        table.getColumns().forEach(col -> col.setPrefWidth(120));

        // fill table
        tableItems = getData();
        table.setItems(FXCollections.observableList(tableItems));

        propertyBox.getItems().addAll(filterProperties);
        propertyBox.setOnAction(e -> filterTyped(null));
    }

    public void reloadTable(List<Dragon> items) {
        table.setItems(FXCollections.observableList(items));
        table.refresh();
    }

    public void addElement(Dragon dragon) {
        tableItems.add(dragon);
        reloadTable(tableItems);
    }

    private List<Dragon> getData() {
        return serverDao.getAll();
    }

    private static class Column {
        String label;
        Consumer<TableColumn<Dragon, String>> additionalCode;

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

            return out;
        }
    }

    public void logout(ActionEvent event) throws IOException {
        switchScene(event, "/gui/signInWindow.fxml");
    }

    public void visualization(ActionEvent event) throws IOException {
        Dragon selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            buttonError.setText(Context.locale.getMsg("no_item_selected"));
            return;
        }
        VisualizationWindowController controller =
                openSubStage(event,"/gui/visualizationWindow.fxml", c ->  {
                    c.setElement(selected);
                    c.paint();
                });
    }

    public void add(ActionEvent event) throws IOException {
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
        Dragon selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            buttonError.setText(Context.locale.getMsg("no_item_selected"));
            return;
        }
        if (!selectedItem.getCreatorName().equals(Context.user.login)) {
            buttonError.setText("%s: %s".formatted(Context.locale.getMsg("INVALID_INPUT"), Context.locale.getMsg("NO_RIGHTS_TO_MODIFY")));
            return;
        }
        buttonError.setText("");
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
        Dragon selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            buttonError.setText(Context.locale.getMsg("no_item_selected"));
            return;
        }
        if (!selectedItem.getCreatorName().equals(Context.user.login)) {
            buttonError.setText("%s: %s".formatted(Context.locale.getMsg("INVALID_INPUT"), Context.locale.getMsg("NO_RIGHTS_TO_MODIFY")));
            return;
        }
        buttonError.setText("");
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

    public void filterTyped(KeyEvent event) {
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

}
