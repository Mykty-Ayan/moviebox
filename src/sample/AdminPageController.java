package sample;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AdminPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField titleField;

    @FXML
    private TextField ratingField;

    @FXML
    private TextArea reviewArea;

    @FXML
    private TextField yearField;

    @FXML
    private ImageView poster;

    @FXML
    private Button uploadButton;

    @FXML
    private ChoiceBox<String> choiceGenre;
    private FileInputStream fis;
    private File file;


    @FXML
    void browse(MouseEvent event) throws MalformedURLException {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Choose Ad Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image File", "*.jpg", "*.png")
        );
        file = fileChooser.showOpenDialog(new Stage());
        if(file!=null){
            Image image = new Image(file.toURI().toURL().toString());
            poster.setImage(image);
            try {
                fis= new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    void logout(ActionEvent event) {
        Main.acc=new Account();
        yearField.getScene().getWindow().hide();
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
    void upload(ActionEvent event) {
        try{
            if(titleField.getText().isEmpty()||reviewArea.getText().isEmpty()||ratingField.getText().isEmpty()||yearField.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Access Denied");
                alert.setHeaderText(null);
                alert.setContentText("Fields cannot be empty");
                alert.showAndWait();
            }
            PreparedStatement pst = DatabaseHandler.getDbConnection().prepareStatement(" INSERT INTO films (title, genre, rating, year, review, poster) VALUES (?, ?, ?, ?, ?, ?)");
            pst.setString(1, titleField.getText());
            pst.setString(2, choiceGenre.getValue());
            pst.setString(3, ratingField.getText());
            pst.setString(4, yearField.getText());
            pst.setString(5, reviewArea.getText());
            pst.setBinaryStream(6,  fis, (int )file.length());
            pst.execute();
            fis.close();
            Alert information = new Alert(Alert.AlertType.INFORMATION);
            information.setTitle("Successful");
            information.setHeaderText(null);
            information.setContentText("Film is added!");
            information.showAndWait();

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    void initialize() {
        choiceGenre.getItems().setAll("Horror","Fantasy","Sci-Fi","Drama","Action","Comedy","100 по Java от Олжас агая");

    }
}
