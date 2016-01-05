package fxmain;

/**
 * Created by asus on 12/22/2015.
 */

import client.ClientMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class nameController {

    private Main main;
    private ClientMain cMain;

    @FXML
    private TextField nameInput;

    @FXML
    private Button nextButton;

    @FXML
    void nextAction(ActionEvent event) {
        try {
            if(main!=null){
                main.playGamePage();
            }
            if(cMain!=null){
                cMain.playGamePage();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setcMain(ClientMain cMain) {
        this.cMain=cMain;
    }

    public String returnName(){
            return nameInput.getText();
    }

}
