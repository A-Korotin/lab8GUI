package gui.controller;

import gui.controller.context.Context;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import net.codes.EventCode;
import net.codes.ExitCode;


import java.net.URL;
import java.util.ResourceBundle;

public class ErrorWindowController extends Controller implements Initializable{
    @FXML
    private Text errorMessage;
    @FXML
    private Text errorInfo;
    @FXML
    private Button closeButton;
    @FXML
    private Button waitButton;

    private String buttonPressed = "exit";

    @Override
    protected void localize() {
        closeButton.setText(Context.locale.getMsg("close"));
        waitButton.setText(Context.locale.getMsg("wait"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessage.setText("");
        errorInfo.setText("");
    }

    public void displayMsg(ExitCode exitCode, EventCode eventCode) {
        errorMessage.setText(Context.locale.getMsg(exitCode));
        errorInfo.setText(Context.locale.getMsg(eventCode));
    }
    public void displayMsg(ExitCode exitCode) {
        errorMessage.setText(Context.locale.getMsg(exitCode));
    }

    public void closePressed(ActionEvent event) {
        buttonPressed = "close";
        errorMessage.getScene().getWindow().hide();
    }

    public void waitPressed(ActionEvent event) {
        buttonPressed = "wait";
        errorMessage.getScene().getWindow().hide();
    }

    public String getButtonPressed() {
        return buttonPressed;
    }

}
