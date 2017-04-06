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
import java.util.ArrayList;
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
public class EditEmployeeController implements Initializable
{

    @FXML
    private TextField employeessn;

    @FXML
    private TextField employeeLastName;
    @FXML
    private TextField employeeFirstName;
    @FXML
    private TextField employeeSalary;
    @FXML
    private TextField employeeemail;
    @FXML
    private TextField employeePhone;
    @FXML
    private TextField employeeDepartment;

    @FXML
    private Label errorMessage;

    private String employee_ssn;
    private String employee_firstName;
    private String employee_lastName;
    private String employee_salary;
    private String employee_phone;
    private String employee_email;
    private String employee_department_id;
    private String employee_id;

    /**
     * Initializes the controller class.
     */
    @FXML
    private void addEmployeeButtonAction(ActionEvent event) throws IOException
    {
        employee_ssn = employeessn.getText();
        employee_firstName = employeeFirstName.getText();
        employee_lastName = employeeLastName.getText();
        employee_salary = employeeSalary.getText();
        employee_phone = employeePhone.getText();
        employee_email = employeeemail.getText();
        employee_department_id = employeeDepartment.getText();

        try
        {
            /**
             * Verify the SSN first, check if the new employee is already exist in the database or not
             */
            FXMLDocumentController.dc = new DbConnection();
            Connection connection = FXMLDocumentController.dc.Connect();
            //2. Create a statement
            Statement statement = connection.createStatement();

            //3. Execute SQL query
            ResultSet resultSet = statement.executeQuery("select * from employee");
            boolean check = true;
            while (resultSet.next())
            {
                if (employee_ssn.equals(resultSet.getString(5)))  //SSN is found on column 5 of the employee table in the db
                {
                    System.out.println("This person exists in the database");
                    errorMessage.setText("This person exists in the database");
                    check = false;

                }
            }
            if (check)
            {
                // the mysql insert statement
                String query = "insert into employee (ssn, first_name,last_name,salary,email,phone,employee_department_id)"
                        + " values (?, ?,?,?,?,?,?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, employee_ssn);
                preparedStmt.setString(2, employee_firstName);
                preparedStmt.setString(3, employee_lastName);
                preparedStmt.setString(4, employee_salary);
                preparedStmt.setString(5, employee_phone);
                preparedStmt.setString(6, employee_email);
                preparedStmt.setString(7, employee_department_id);

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
    private void updateEmployeeButtonAction(ActionEvent event) throws IOException
    {
        try
        {
            FXMLDocumentController.dc = new DbConnection();
            Connection connection = FXMLDocumentController.dc.Connect();

            employee_id = FXMLDocumentController.employeeSelection.getEmployee_id();
            employee_ssn = employeessn.getText();
            employee_firstName = employeeFirstName.getText();
            employee_lastName = employeeLastName.getText();
            employee_salary = employeeSalary.getText();
            employee_phone = employeePhone.getText();
            employee_email = employeeemail.getText();
            employee_department_id = employeeDepartment.getText();

            // the mysql insert statement
            String query = "UPDATE employee SET ssn=?, first_name=?, last_name=?, salary=? , phone=?, email=?,employee_department_id = ? WHERE employee_id =" + employee_id;

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, employee_ssn);
            preparedStmt.setString(2, employee_firstName);
            preparedStmt.setString(3, employee_lastName);
            preparedStmt.setString(4, employee_salary);
            preparedStmt.setString(5, employee_phone);
            preparedStmt.setString(6, employee_email);
            preparedStmt.setString(7, employee_department_id);

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
            System.out.println(ex.getMessage());
            System.out.println("updateEmployeeButtonAction error");
        }
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        if (FXMLDocumentController.employeeSelection != null)
        {
            employeessn.setText(FXMLDocumentController.employeeSelection.getssn());
            employeeFirstName.setText(FXMLDocumentController.employeeSelection.getFirst_name());
            employeeLastName.setText(FXMLDocumentController.employeeSelection.getLast_name());
            employeeSalary.setText(FXMLDocumentController.employeeSelection.getSalary());
            employeePhone.setText(FXMLDocumentController.employeeSelection.getPhone());
            employeeemail.setText(FXMLDocumentController.employeeSelection.getemail());
            employeeDepartment.setText(FXMLDocumentController.employeeSelection.getDepartment_id());

        }
    }

}
