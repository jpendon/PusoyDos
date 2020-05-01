package sample.scene;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.model.*;

import java.io.File;
import java.io.IOException;
import java.lang.management.PlatformLoggingMXBean;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;

public class ComputerActionScene implements Initializable {

    private GameFile m_gameFile;
    private String computerAction;
    private Vector<Card> cardsUsed;
    private Stage stage;

    @FXML
    private FlowPane cardsUsedPane;

    @FXML
    private Label computerActionPane;

    @FXML
    public Label roundInfoPane;

    @FXML
    public Button continueButton;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Platform.runLater(()->
        {
            // Update cardsUsedPane
            ArrayList<StackPane> paneList = new ArrayList<>();

            for (Card card: this.cardsUsed)
            {
                String url = "resources/" + card.GetCardName().toLowerCase() + ".png";
                Image image = new Image(new File(url).toURI().toString());

                ImageView iv = new ImageView(image);
                iv.setFitHeight(250);
                iv.setFitWidth(170);

                StackPane pane = new StackPane(iv);
                pane.setStyle("-fx-background-color: white;");


                paneList.add(pane);
            }

            this.cardsUsedPane.getChildren().clear();
            this.cardsUsedPane.setHgap(10);
            this.cardsUsedPane.getChildren().addAll(paneList);

            // Update computerActionPane
            this.computerActionPane.setText(computerAction);
            this.computerActionPane.setFont(new Font(20));
            // Update Round Information

        });
    }

    public void continueButtonOnClick(ActionEvent event) throws IOException
    {
        // Load Game Scene to Continue the game
        FXMLLoader loader= new FXMLLoader(getClass().getResource("BaseScene.fxml"));
        Parent parent = loader.load();
        parent.setStyle("-fx-background-color: #009900;");

        BaseScene controller = loader.getController();
        controller.setGameFile(this.m_gameFile);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(parent, 1280, 720));
        controller.setStage(window);
        window.show();
    }

    public void setGameFile(GameFile gameFile) { this.m_gameFile = gameFile; }

    public void setComputerAction(String computerAction) { this.computerAction = computerAction; }

    public void setCardsUsed(Vector<Card> cardsUsed) { this.cardsUsed = cardsUsed; }

    public void setStage(Stage stage) { this.stage = stage;}


}
