package fxmain;

import client.ClientMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class endPageController {

    @FXML
    private Main main;
    private ClientMain cMain;


    @FXML
    private Button exitButton;

    @FXML
    void exitAction(ActionEvent event) {

    }


    public void setMain(Main main){
        this.main = main;
    }


    public void setcMain(ClientMain cMain) {
        this.cMain = cMain;
    }

}