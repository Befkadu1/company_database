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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Befkadu Degefa
 */

public class EditConsultantController implements Initializable
{
    @FXML
    private TextField consultantssn;
    
    @FXML
    private TextField consultantLastName;
    @FXML
    private TextField consultantFirstName;
    @FXML
    private TextField consultantCharge;
    @FXML
    private TextField consultantemail;
    @FXML
    private TextField consultantPhone;
    @FXML
    private TextField consultantDepartment;
    
    @FXML
    private Label errorMessage;
    
    private String consultant_firstName ;
    private String consultant_lastName;
    private String consultant_charge;
    private String consultant_phone ;
    private String consultant_email;
    private String consultant_department_id;
    private String consultant_id;
    private String consultant_ssn;

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
    
    
    @FXML
    private void addConsultantButtonAction(ActionEvent event) throws IOException
    {
        consultant_ssn = consultantssn.getText();
        consultant_firstName = consultantFirstName.getText();
        consultant_lastName = consultantLastName.getText();
        consultant_charge = consultantCharge.getText();
        consultant_phone = consultantPhone.getText();
        consultant_email = consultantemail.getText();
        consultant_department_id = consultantDepartment.getText();
        try
        {
            FXMLDocumentController.dc = new DbConnection();
            Connection connection  = FXMLDocumentController.dc.Connect();
            
            //2. Create a statement
            Statement statement = connection.createStatement();

            //3. Execute SQL query
            ResultSet resultSet = statement.executeQuery("select * from consultant");
            boolean check = true;
            
            //to check if the consultant's ssn registered or not
            while (resultSet.next())
            {
                if (consultant_ssn.equals(resultSet.getString(5)))  //SSN is found on column 5 of the consultant table in the db
                {
                    System.out.println("This person exists in the database");
                    errorMessage.setText("This person exists in the database");
                    check = false;  //if the consultant exist check will be false 

                }
            }
            if (check)
            {
            

            // the mysql insert statement
      String query = " insert into consultant (first_name,last_name,charge_perhr,ssn,phone,email, consultant_department_id)"
        + " values (?, ?,?,?,?,?,?)";
      
      // create the mysql insert preparedstatement
      PreparedStatement preparedStmt = connection.prepareStatement(query);
      preparedStmt.setString (1, consultant_firstName);
      preparedStmt.setString (2, consultant_lastName);
      preparedStmt.setString (3, consultant_charge);
      preparedStmt.setString (4, consultant_ssn);
      preparedStmt.setString (5, consultant_phone);
      preparedStmt.setString (6, consultant_email);
      preparedStmt.setString (7, consultant_department_id);
      
      // execute the preparedstatement
      preparedStmt.execute();
      
      connection.close();
      
      Parent root2 = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root2);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sigma Database");

            }
        } catch (SQLException ex)
        {
            Logger.getLogger(EditProjectController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }    
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        if(FXMLDocumentController.consultantSelection !=null)
        {
            consultantssn.setText(FXMLDocumentController.consultantSelection.getssn());
            consultantFirstName.setText(FXMLDocumentController.consultantSelection.getFirst_name());
            consultantLastName.setText(FXMLDocumentController.consultantSelection.getLast_name());
            consultantCharge.setText(FXMLDocumentController.consultantSelection.getCharge_perhr());
            consultantPhone.setText(FXMLDocumentController.consultantSelection.getPhone());
            consultantemail.setText(FXMLDocumentController.consultantSelection.getemail());
            consultantDepartment.setText(FXMLDocumentController.consultantSelection.getDepartment_id());

        }
    }  
    
    @FXML
    private void updateConsultantButtonAction(ActionEvent event) throws IOException
    {
        
        try
        {
            FXMLDocumentController.dc = new DbConnection();
            Connection connection = FXMLDocumentController.dc.Connect();
            consultant_id = FXMLDocumentController.consultantSelection.getConsultant_id();
            consultant_ssn = consultantssn.getText();
            consultant_firstName = consultantFirstName.getText();
            consultant_lastName = consultantLastName.getText();
            consultant_charge = consultantCharge.getText();
            consultant_phone = consultantPhone.getText();
            consultant_email = consultantemail.getText();
            consultant_department_id = consultantDepartment.getText();

            System.out.println("id " + consultant_id);
            System.out.println("ssn" + consultant_ssn);

            // the mysql insert statement
            String query = "UPDATE consultant SET ssn=?, first_name=?, last_name=?, charge_perhr=? , phone=?, email=?,consultant_department_id = ? WHERE consultant_id =" + consultant_id;

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, consultant_ssn);
            preparedStmt.setString(2, consultant_firstName);
            preparedStmt.setString(3, consultant_lastName);
            preparedStmt.setString(4, consultant_charge);
            preparedStmt.setString(5, consultant_phone);
            preparedStmt.setString(6, consultant_email);
            preparedStmt.setString(7, consultant_department_id);

            // execute the preparedstatement
            preparedStmt.execute();

            connection.close();
            Parent root2 = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root2);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sigma Database");

        } catch (SQLException ex)
        {
            Logger.getLogger(EditProjectController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
