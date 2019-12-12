package sample;

import java.io.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView imgF;

    @FXML
    private TableView<Film> ta;

    @FXML
    private TableColumn<Film, String> titleT;

    @FXML
    private TableColumn<Film, Integer> yearT;

    @FXML
    private TableColumn<Film, String> genreT;

    @FXML
    private TableColumn<Film, Double> ratingT;

    @FXML
    private TextArea textF;

    @FXML
    private TextArea titleF;
    @FXML
    private Button basketButton;

    @FXML
    private Button buyButton;
    private Image image;
    private Film film= new Film();
    final ObservableList<Film> data = FXCollections.observableArrayList();
    @FXML
    void buy(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) {
        Main.acc=new Account();
        basketButton.getScene().getWindow().hide();
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
    void basket(ActionEvent event) {

        basketButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/Basket.fxml"));
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
    void show(MouseEvent event) {
        ResultSet rs=null;
        film = (Film) ta.getSelectionModel().getSelectedItem();
        String query  = "Select title, review, poster from films where id = ?";
        try {
            PreparedStatement pst = DatabaseHandler.getDbConnection().prepareStatement(query);
            pst.setInt(1, film.getId());
            rs=pst.executeQuery();
            if(rs.next()){
                InputStream is = rs.getBinaryStream(3);
                OutputStream os = new FileOutputStream(new File("photo.jpg"));
                byte[] content = new byte[1024];
                int size=0;
                while((size=is.read(content))!=-1){
                    os.write(content, 0, size);
                }
                image = new Image("file:photo.jpg");
                imgF.setImage(image);
                titleF.setText(rs.getString(1));
                textF.setText(rs.getString(2));
                /*is.close();
                os.close();*/
                pst.close();
            }
        } catch (Exception e){
            //e.printStackTrace();
        }
    }
    @FXML
    void initialize() {
        try {
            Main.acc = new Account();
            Statement st = DatabaseHandler.getDbConnection().createStatement();
            ResultSet rs=st.executeQuery("SELECT * FROM films");
            while (rs.next()){
                data.add(new Film(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getDouble("rating"),
                        rs.getInt("year"),
                        rs.getString("review")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        titleT.setCellValueFactory(new PropertyValueFactory<Film, String>("title"));
        ratingT.setCellValueFactory(new PropertyValueFactory<Film, Double>("rating"));
        genreT.setCellValueFactory(new PropertyValueFactory<Film, String>("genre"));
        yearT.setCellValueFactory(new PropertyValueFactory<Film, Integer>("year"));
        ta.setItems(data);
    }
}
