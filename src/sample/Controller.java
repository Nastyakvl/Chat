package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller {
    @FXML
    private Button button_1;

    @FXML
    public void onClick()
    {
        button_1.setText("Hello!");
    }
}
