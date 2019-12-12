package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DatabaseHandler{

    public static Connection getDbConnection() throws ClassNotFoundException, SQLException{
        Connection dbConnection;
        String connectionString = "jdbc:mysql://localhost:3306/consumers";

        //Class.forName("com.mysql.jdbc.Driver");
        dbConnection=DriverManager.getConnection(connectionString, "root", "***Ayan1234");
        return dbConnection;

    }

    public static void signUpUser(String firstName, String lastName, String userName, String gender, String password){

        try {
            Statement st = getDbConnection().createStatement();
            st.executeUpdate("INSERT into accounts (firstname, lastname, username, password,gender) values (\""+firstName +"\",\"" + lastName+"\",\""+userName+"\",\""+password+"\",\""+gender+"\""+")");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    }
