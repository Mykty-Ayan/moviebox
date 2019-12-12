package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="loginField"
    private TextField loginField; // Value injected by FXMLLoader

    @FXML // fx:id="passwordField"
    private PasswordField passwordField; // Value injected by FXMLLoader

    @FXML // fx:id="signButton"
    private Button signButton; // Value injected by FXMLLoader

    @FXML // fx:id="nonAccBut"
    private Button nonAccBut; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }
    @FXML
    void register(ActionEvent event) {
        nonAccBut.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/Registration.fxml"));
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
    void sign(ActionEvent event) {
        if(loginField.getText().equals("admin") && passwordField.getText().equals("admin")){
            signButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/AdminPage.fxml"));
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
        else {
            Statement st = null;
            try {
                st = DatabaseHandler.getDbConnection().createStatement();
                ResultSet rs = st.executeQuery(" SELECT * FROM accounts WHERE username = \"" + loginField.getText() + "\" AND password= \"" + passwordField.getText() + "\"");
                if (rs.next()) {
                    Main.acc = new Account(
                            rs.getInt("idusers"),
                            rs.getString("firstname"),
                            rs.getString("lastname"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("gender")
                    );
                    signButton.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/sample/MainPage.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Access Denied");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong password or login");
                    alert.showAndWait();
                    //System.out.println(alert.getResult().getText());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
