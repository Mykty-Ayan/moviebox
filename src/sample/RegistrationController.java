package sample;

import java.beans.EventHandler;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField NameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registrationButton;

    @FXML
    private TextField loginField;

    @FXML
    private TextField SurnameField;

    @FXML
    private CheckBox choiceMale;

    @FXML
    private CheckBox choiceFemale;

    @FXML
    private Button backB;

    @FXML
    void back(ActionEvent event) {
        backB.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/Autentification.fxml"));
        try {
            loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        Parent root =loader.getRoot();
        Stage stage=new Stage();
        stage.setScene(new Scene(root) );
        stage.show();
    }
    @FXML
    void register(ActionEvent event) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        String gender= "";
        if(choiceFemale.isSelected()) gender="Female";
        else if(choiceMale.isSelected()) gender = "Male";
        dbHandler.signUpUser(NameField.getText(), SurnameField.getText(),
                loginField.getText(), gender, passwordField.getText());
        back(null);
    }

    @FXML
    void initialize() {
        choiceMale.setSelected(true);
        choiceMale.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                choiceFemale.setSelected(!newValue);
            }
        });

        choiceFemale.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                choiceMale.setSelected(!newValue);
            }
        });
    }
}
