package sample.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;


public class Controller {

/**/
/*
Controller::StartButtonOnClick(ActionEvent event)

NAME

    Controller::StartButtonOnClick - Function associated with the start button

SYNOPSIS

    void StartButtonOnClick(ActionEvent event);
        event           -> ActionEvent object

DESCRIPTION

    The function prepares and displays the LoadGameScene controller

RETURNS

AUTHOR

    Jay Pendon

DATE

    5:16am 4/2/2020
*/
/**/
    public void StartButtonOnClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoadGameScene.fxml"));
        Parent parent = fxmlLoader.load();
        parent.setStyle("-fx-background-color: #009900;");

        LoadGameScene controller = fxmlLoader.getController();

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        controller.SetStage(window);

        window.setScene(new Scene(parent, 1280, 720));
        window.show();

    }
/*void Controller::StartButtonOnClick(ActionEvent event);*/

}
