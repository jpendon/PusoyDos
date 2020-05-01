package sample.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;


public class Controller {

    public static Node loadScene(Controller c) throws IOException {
        URL resource = Controller.class.getResource("scene/menuScene.fxml");
        FXMLLoader loader = new FXMLLoader(resource);
        loader.setControllerFactory(param -> c);
        loader.load();
        return loader.getRoot();
    }

    public void startButtonOnClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loadGameScene.fxml"));
        Parent parent = fxmlLoader.load();
        parent.setStyle("-fx-background-color: #009900;");

        LoadGameScene controller = fxmlLoader.getController();

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        controller.setStage(window);



        window.setScene(new Scene(parent, 1280, 720));
        window.show();

    }
}
