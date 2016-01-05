package fxmain;

import client.ClientMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

/**
 * Created by GIASAHMED on 09-Dec-15.
 */
public class FirstPageController {
    private Main main;
    private ClientMain cMain;
    @FXML
    private Button nextButton;
    @FXML
    void nextButtonAction(ActionEvent event)
    {
        try {
           if(main!=null)
                main.namePageshow();
            if(cMain!=null)
                cMain.namePageshow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMain(Main main)
    {
        this.main = main;
    }

    public void setcMain(ClientMain cMain) {
        this.cMain = cMain;
    }
}
