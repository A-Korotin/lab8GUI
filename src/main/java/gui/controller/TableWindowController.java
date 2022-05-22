package gui.controller;

import dragon.*;
import gui.controller.context.Context;
import io.Properties;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;

public class TableWindowController extends Controller implements Initializable {
    @FXML
    private Button logoutButton;
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
                            "null":
                            callback.getValue().getColor().getDescription()))),

            new Column("type", col -> col.setCellValueFactory(callback -> new SimpleStringProperty(callback.getValue().getType().getDescription()))),
            new Column("character",
                    col -> col.setCellValueFactory(callback -> new SimpleStringProperty(callback.getValue().getCharacter() == null ?
                            "null":
                            callback.getValue().getCharacter().getDescription()))),

            new Column("cave", col -> {
                Column depth = new Column("depth", c -> c.setCellValueFactory(callback -> new SimpleStringProperty(String.valueOf(callback.getValue().getCave().getDepth()))));
                Column nTreasures = new Column("number of treasures", c -> c.setCellValueFactory(callback ->
                        new SimpleStringProperty(callback.getValue().getCave().getNumberOfTreasures() == null ?
                                "null":
                                callback.getValue().getCave().getNumberOfTreasures().toString())));

                col.getColumns().addAll(depth.composeColumn(), nTreasures.composeColumn());
            }),
            new Column("creator name", col -> col.setCellValueFactory(callback -> new SimpleStringProperty(callback.getValue().getCreatorName())))
    };

    private final String[] filterProperties = {"name", "creator name"};
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // load cols
        Arrays.stream(columns).forEachOrdered(column -> table.getColumns().add(column.composeColumn()));
        table.getColumns().forEach(col -> col.setPrefWidth(120));

        // fill table
        tableItems = getData();
        table.setItems(FXCollections.observableList(tableItems));

        propertyBox.getItems().addAll(filterProperties);
    }

    public void reloadTable(List<Dragon> items) {
        //TODO test
        table.getItems().clear();
        table.getItems().addAll(items);
    }

    public void addElement(Dragon dragon) {
        // FIXME: 22.05.2022 doubles element
        table.getItems().add(dragon);
        tableItems.add(dragon);
    }

    private List<Dragon> getData() {
        List<Dragon> out = new ArrayList<>();
        Dragon dragon = new Dragon(1, "hello", new Coordinates(1F, 1), LocalDate.now(),
                12L, Color.BLACK, DragonType.FIRE, DragonCharacter.GOOD, new DragonCave(1, 214));
        dragon.setCreatorName("qwerty");
        Dragon dragon1 = new Dragon(2, "hello world", new Coordinates(2F, 10), LocalDate.now(),
                1L, Color.BLACK, DragonType.FIRE, DragonCharacter.CHAOTIC_EVIL, new DragonCave(10000, 1));
        dragon1.setCreatorName("admin");

        Dragon dragon2 = new Dragon(3, "John", new Coordinates(14214F, -21414), LocalDate.now(),
                4214124L, Color.BLUE, DragonType.AIR, DragonCharacter.GOOD, new DragonCave(4214, null));
        dragon2.setCreatorName("qwerty");
        Dragon dragon3 = new Dragon(4, "Henry", new Coordinates(-123F, 444444444), LocalDate.now(),
                4214124421L, Color.WHITE, DragonType.UNDERGROUND, DragonCharacter.FICKLE, new DragonCave(-41, 4214124));
        dragon3.setCreatorName("qwerty");
        out.add(dragon);
        out.add(dragon1);
        out.add(dragon2);
        out.add(dragon3);
        return out;
        // FIXME: 18.05.2022 database loading
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
            if(additionalCode != null)
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
        switchScene(event, "/gui/visualizationWindow.fxml");
    }

    public void add(ActionEvent event) throws IOException {
        RequestWindowController controller = openNewWindow("/gui/request.fxml");
        //switchScene(event, "/gui/request.fxml");
        Properties properties = controller.getProperties();
        if(properties == null)
            return;
        addElement(new Dragon(-1, properties));
    }

    public void update(ActionEvent event) throws IOException {
        switchScene(event, "/gui/request.fxml");
    }

    public void remove(ActionEvent event) throws IOException {
        switchScene(event, "/gui/request.fxml");
    }

}
