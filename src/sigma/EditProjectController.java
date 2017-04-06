/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sigma;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static sigma.FXMLDocumentController.dc;
import static sigma.FXMLDocumentController.obListconsultant;
import static sigma.FXMLDocumentController.projectSelection;

/**
 * FXML Controller class
 *
 * @author Befkadu Degefa
 */
public class EditProjectController implements Initializable
{

    @FXML
    public  TextField projectNameTextField;

    @FXML
    public TextField projectDescriptionTextField;
    
    String pro_id;

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private void Cancel_ButtonAction(ActionEvent event) throws IOException
    {
        Parent root2 = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root2);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sigma Database");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        if(FXMLDocumentController.projectSelection !=null)
        {
        projectNameTextField.setText(FXMLDocumentController.projectSelection.getProject_name());
        projectDescriptionTextField.setText(FXMLDocumentController.projectSelection.getDescription());

        }

    }

    @FXML
    private void addProject_ButtonAction(ActionEvent event) throws IOException
    {

        try
        {
            FXMLDocumentController.dc = new DbConnection();
            Connection connection = FXMLDocumentController.dc.Connect();

            String pro_name = projectNameTextField.getText();
            String pro_description = projectDescriptionTextField.getText();

            // the mysql insert statement
            String query = " insert into project (project_name, description)"
                    + " values (?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, pro_name);
            preparedStmt.setString(2, pro_description);

            // execute the preparedstatement
            preparedStmt.execute();

            connection.close();

        } catch (SQLException ex)
        {
            Logger.getLogger(EditProjectController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Parent root2 = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root2);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sigma Database");
    }

    @FXML
    private void EditProject_ButtonAction(ActionEvent event) throws IOException
    {
        
        try
        {
            FXMLDocumentController.dc = new DbConnection();
            Connection connection = FXMLDocumentController.dc.Connect();

            pro_id = FXMLDocumentController.projectSelection.getProject_id();
            String pro_name = projectNameTextField.getText();
            String pro_description = projectDescriptionTextField.getText();
            System.out.println("Pro_id edit " + pro_id);

            // the mysql insert statement
            String query = " UPDATE project SET project_name=?, description=? WHERE project_id = " + pro_id;

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, pro_name);
            preparedStmt.setString(2, pro_description);

            // execute the preparedstatement
            preparedStmt.execute();

            connection.close();

        } catch (SQLException ex)
        {
            Logger.getLogger(EditProjectController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Parent root2 = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root2);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sigma Database");
    }
}
