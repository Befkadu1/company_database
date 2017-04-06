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
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Befkadu Degefa
 */



public class EditDepartmentController implements Initializable
{
    
    @FXML
    private TextField departmentAddress;
    @FXML
    private TextField departmentName;
    @FXML
    private TextField departmentDescription;
    
    private String department_address;
    private String department_name;
    private String department_description;
    private String department_id;
    
    @FXML
    private void Cancel_ButtonAction(ActionEvent event) throws IOException
    {
        Parent root2 = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root2);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sigma Database");
    }
    
    @FXML
    private void EditDepartmentInfo_ButtonAction(ActionEvent event) throws IOException
    {
        
        try
        {
            FXMLDocumentController.dc = new DbConnection();
            Connection connection = FXMLDocumentController.dc.Connect();

            department_id = FXMLDocumentController.departmentSelection.getDepartment_id();
            department_address = departmentAddress.getText();
            department_name = departmentName.getText();
            department_description = departmentDescription.getText();
            
            System.out.println("Department_id edit " + department_id);

            // the mysql update statement
            String query = "UPDATE department SET address=?, department_name=?, description=? WHERE department_id = " + department_id;

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, department_address);
            preparedStmt.setString(2, department_name);
            preparedStmt.setString(3, department_description);

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
    private void addDepartmentButtonAction(ActionEvent event) throws IOException
    {
        try
        {
            FXMLDocumentController.dc = new DbConnection();
            Connection connection  = FXMLDocumentController.dc.Connect();

            String department_address = departmentAddress.getText();
            String department_name = departmentName.getText();
            String department_description = departmentDescription.getText();


            // the mysql insert statement
      String query = " insert into department (address,department_name,description)"
        + " values (?, ?,?)";
      
      // create the mysql insert preparedstatement
      PreparedStatement preparedStmt = connection.prepareStatement(query);
      preparedStmt.setString (1, department_address);
      preparedStmt.setString (2, department_name);
      preparedStmt.setString (3, department_description);

      
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
        stage.setTitle("Employee Window");
    }
    /**
     * Initializes the controller class.
     */
  
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        if(FXMLDocumentController.departmentSelection !=null)
        {
        departmentAddress.setText(FXMLDocumentController.departmentSelection.getAddress());
        departmentDescription.setText(FXMLDocumentController.departmentSelection.getDescription());
        departmentName.setText(FXMLDocumentController.departmentSelection.getDepartment_name());

        }
    }    
    
}
