/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sigma;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author Befkadu Degefa
 */
public class FXMLDocumentController implements Initializable
{

    @FXML
    private TableView<DepartmentClass> departmentTableView;

    @FXML
    private TableColumn<DepartmentClass, String> departmentID;
    @FXML
    private TableColumn<DepartmentClass, String> departmentAddress;
    @FXML
    private TableColumn<DepartmentClass, String> departmentName;
    @FXML
    private TableColumn<DepartmentClass, String> departmentDescription;

    @FXML
    private TableView<ProjectClass> projectTableView;

    @FXML
    private TableColumn<ProjectClass, String> projectID;

    @FXML
    private TableColumn<ProjectClass, String> projectName;
    @FXML
    private TableColumn<ProjectClass, String> projectdescription;

    @FXML
    private TableView<ConsultantClass> consultantTableView;

    @FXML
    private TableView<EmployeeClass> employeeTableView;

    @FXML
    private TableColumn<EmployeeClass, String> employeeID;

    @FXML
    private TableColumn<EmployeeClass, String> employeeFirstName;
    @FXML
    private TableColumn<EmployeeClass, String> employeeLastName;
    @FXML
    private TableColumn<EmployeeClass, String> employeeSalary;
    @FXML
    private TableColumn<EmployeeClass, String> employeessn;
    @FXML
    private TableColumn<EmployeeClass, String> employeePhone;
    @FXML
    private TableColumn<EmployeeClass, String> employeeEmail;

    @FXML
    private TableColumn<ConsultantClass, String> consultantID;

    @FXML
    private TableColumn<ConsultantClass, String> consultantFirstName;
    @FXML
    private TableColumn<ConsultantClass, String> consultantLastName;
    @FXML
    private TableColumn<ConsultantClass, String> chargePerhr;
    @FXML
    private TableColumn<ConsultantClass, String> consultantssn;
    @FXML
    private TableColumn<ConsultantClass, String> consultantPhone;
    @FXML
    private TableColumn<ConsultantClass, String> consultantEmail;

    public static ObservableList<ProjectClass> obListproject;  //for edit window
    public static ObservableList<DepartmentClass> obListdepartment;//for edit window
    public static ObservableList<ConsultantClass> obListconsultant;//for edit window
    public static ObservableList<EmployeeClass> obListemployee;

    public static DbConnection dc;

    public static ProjectClass projectSelection; //A string to show when the user select a project
    public static DepartmentClass departmentSelection; //A string to show when the user select a department
    public static ConsultantClass consultantSelection; //A string to show when the user select a consultant
    public static EmployeeClass employeeSelection; //A string to show when the user select an employee

    @FXML
    private Label errorMessageLabel;
    @FXML
    private TextField searchEmployee;
    @FXML
    private TextField searchConsultant;

    //To search a specific consultant
    
    @FXML
    public void HighestEarnerFromProjectButtonAction(ActionEvent event) throws IOException
    {
        projectSelection = projectTableView.getSelectionModel().getSelectedItem();
        if (projectSelection != null)
        {

            String project_name = projectSelection.getProject_name();
            String project_id = projectSelection.getProject_id();

            try
            {
                // create the mysql database connection
                dc = new DbConnection();
                Connection conn = dc.Connect();

                Statement statement = conn.createStatement();
                System.out.println("project name " + project_name);

                //Consultant will be visible when a specific project selected
                ResultSet resultSetConsultant = statement.executeQuery("select consultant_id, first_name,last_name, charge_perhr,ssn, phone,email, consultant_department_id from consultant join ( project join consultant_works_on on "
                        + "project_id =project_project_id ) on consultant_id = consultant_consultant_id where  project_id = " + project_id + " order by charge_perhr desc limit 1");
                obListconsultant = FXCollections.observableArrayList();
                while (resultSetConsultant.next())
                {
                    //get String from db
                    obListconsultant.add(new ConsultantClass(resultSetConsultant.getString(1), resultSetConsultant.getString(2),
                            resultSetConsultant.getString(3), resultSetConsultant.getString(4), resultSetConsultant.getString(5),
                            resultSetConsultant.getString(6), resultSetConsultant.getString(7), resultSetConsultant.getString(8)));

                }

                //Employees will be visible when a specific project selected
                ResultSet resultSetEmployee = statement.executeQuery("select employee_id, first_name,last_name, salary,ssn, phone,email,employee_department_id from employee join (project join employee_works_on on "
                        + "project_id = project_project_id) on employee_id =employee_employee_id where project_id =  " + project_id + " order by salary desc limit 1" );
                obListemployee = FXCollections.observableArrayList();
                while (resultSetEmployee.next())
                {
                    obListemployee.add(new EmployeeClass(resultSetEmployee.getString(1), resultSetEmployee.getString(2),
                            resultSetEmployee.getString(3), resultSetEmployee.getString(4), resultSetEmployee.getString(5),
                            resultSetEmployee.getString(6), resultSetEmployee.getString(7), resultSetEmployee.getString(8)));

                }

                conn.close();

                //Setting employee TableView
                employeeID.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
                employeeFirstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
                employeeLastName.setCellValueFactory(new PropertyValueFactory<>("last_name"));
                employeeSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
                employeessn.setCellValueFactory(new PropertyValueFactory<>("ssn"));
                employeePhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
                employeeEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

                employeeTableView.setItems(null);
                employeeTableView.setItems(obListemployee);

                //Setting the consultant TableView
                consultantID.setCellValueFactory(new PropertyValueFactory<>("consultant_id"));
                consultantFirstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
                consultantLastName.setCellValueFactory(new PropertyValueFactory<>("last_name"));
                chargePerhr.setCellValueFactory(new PropertyValueFactory<>("charge_perhr"));
                consultantssn.setCellValueFactory(new PropertyValueFactory<>("ssn"));
                consultantPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
                consultantEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

                consultantTableView.setItems(null);
                consultantTableView.setItems(obListconsultant);

            } catch (Exception e)
            {
                System.err.println("Got an exception! ");
                System.err.println(e.getMessage());
            }
            

        }
        else
            errorMessageLabel.setText("Select the project");

    }
    
    @FXML
    public void HighestEarnerFromDepartmentButtonAction(ActionEvent event) throws IOException
    {
        
        //departmentSelection will be active When a specific department is selected
        departmentSelection = departmentTableView.getSelectionModel().getSelectedItem();
        if (departmentSelection == null)
        {
            errorMessageLabel.setText("Select the department");
        } else
        {
            String department_id = departmentSelection.getDepartment_id();

            try
            {
                // create the mysql database connection
                dc = new DbConnection();
                Connection conn = dc.Connect();

                Statement statement = conn.createStatement();

                //Consultants will be visible when a specific department selected
                ResultSet resultSetConsultant = statement.executeQuery("select consultant_id, first_name,last_name, charge_perhr,ssn, phone,email, consultant_department_id from (consultant join department on "
                        + "department_id = consultant_department_id) where department_id =  " + department_id + " order by charge_perhr desc limit 1");

                obListconsultant.clear();

                while (resultSetConsultant.next())
                {
                    //get String from db
                    obListconsultant.add(new ConsultantClass(resultSetConsultant.getString(1), resultSetConsultant.getString(2),
                            resultSetConsultant.getString(3), resultSetConsultant.getString(4), resultSetConsultant.getString(5),
                            resultSetConsultant.getString(6), resultSetConsultant.getString(7), resultSetConsultant.getString(8)));

                }

                //Employees will be visible when a specific department selected
                ResultSet resultSetEmployee = statement.executeQuery("select employee_id, first_name,last_name, salary,ssn, phone,email,employee_department_id from (employee join department on "
                        + "department_id = employee_department_id) where department_id =  " + department_id + " order by salary desc limit 1");
                obListemployee = FXCollections.observableArrayList();
                while (resultSetEmployee.next())
                {

                    //get String from db
                    obListemployee.add(new EmployeeClass(resultSetEmployee.getString(1), resultSetEmployee.getString(2),
                            resultSetEmployee.getString(3), resultSetEmployee.getString(4), resultSetEmployee.getString(5),
                            resultSetEmployee.getString(6), resultSetEmployee.getString(7), resultSetEmployee.getString(8)));

                }

                conn.close();

                //Setting employee Table View
                employeeID.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
                employeeFirstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
                employeeLastName.setCellValueFactory(new PropertyValueFactory<>("last_name"));
                employeeSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
                employeessn.setCellValueFactory(new PropertyValueFactory<>("ssn"));
                employeePhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
                employeeEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

                employeeTableView.setItems(null);
                employeeTableView.setItems(obListemployee);

                //Setting the consultant table
                consultantID.setCellValueFactory(new PropertyValueFactory<>("consultant_id"));
                consultantFirstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
                consultantLastName.setCellValueFactory(new PropertyValueFactory<>("last_name"));
                chargePerhr.setCellValueFactory(new PropertyValueFactory<>("charge_perhr"));
                consultantssn.setCellValueFactory(new PropertyValueFactory<>("ssn"));
                consultantPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
                consultantEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

                consultantTableView.setItems(null);
                consultantTableView.setItems(obListconsultant);

            } catch (Exception e)
            {
                System.err.println("Got an exception! ");
                System.err.println(e.getMessage());
            }

        }

    }
    
    @FXML
    public void SearchConsultantButtonAction(ActionEvent event) throws IOException
    {
        try
        {
            if(searchConsultant.getText().isEmpty())
            {
                errorMessageLabel.setText("Enter the consultant's name");
            }
            else
            {
            // create the mysql database connection
            dc = new DbConnection();
            Connection conn = dc.Connect();

            Statement statement = conn.createStatement();
            obListconsultant.clear(); //to clear the consultant table 
            
            //query to get the specific consultant in the database
            ResultSet resultSetConsultant = statement.executeQuery("SELECT * FROM consultant WHERE last_name LIKE '" + searchConsultant.getText() + "%'" + " OR first_name LIKE '" + searchConsultant.getText() + "%'");

            
            //4. process the result set
            while (resultSetConsultant.next())
            {
                //get String from db
                obListconsultant.add(new ConsultantClass(resultSetConsultant.getString(1), resultSetConsultant.getString(2),
                        resultSetConsultant.getString(3), resultSetConsultant.getString(4), resultSetConsultant.getString(5),
                        resultSetConsultant.getString(6), resultSetConsultant.getString(7), resultSetConsultant.getString(8)));

            }
            //Setting the consultant table
            consultantID.setCellValueFactory(new PropertyValueFactory<>("consultant_id"));
            consultantFirstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
            consultantLastName.setCellValueFactory(new PropertyValueFactory<>("last_name"));
            chargePerhr.setCellValueFactory(new PropertyValueFactory<>("charge_perhr"));
            consultantssn.setCellValueFactory(new PropertyValueFactory<>("ssn"));
            consultantPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            consultantEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

            consultantTableView.setItems(null);
            consultantTableView.setItems(obListconsultant);
            }

        } catch (SQLException ex)
        {
            System.out.println("Failure in the SearchConsultantButtonAction method");
        }

    }

    @FXML
    public void SearchEmployeeButtonAction(ActionEvent event) throws IOException
    {
        try
        {
            if(searchEmployee.getText().isEmpty())
            {
                errorMessageLabel.setText("Enter the employee's name");
            }
            else
            {
            // create the mysql database connection
            dc = new DbConnection();
            Connection conn = dc.Connect();

            Statement statement = conn.createStatement();

            //query in to search for a specific employee
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employee WHERE last_name LIKE '" + searchEmployee.getText() + "%'"
                    + " OR first_name LIKE '" + searchEmployee.getText() + "%'");

            obListemployee.clear();
            //4. process the result set
            while (resultSet.next())
            {

                obListemployee.add(new EmployeeClass(resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7), resultSet.getString(8)));
                
                //Setting employee Table View
                employeeID.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
                employeeFirstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
                employeeLastName.setCellValueFactory(new PropertyValueFactory<>("last_name"));
                employeeSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
                employeessn.setCellValueFactory(new PropertyValueFactory<>("ssn"));
                employeePhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
                employeeEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

                employeeTableView.setItems(null);
                employeeTableView.setItems(obListemployee);

            }
            }
            
        } catch (SQLException ex)
        {
            System.out.println("Failure in SearchEmployeeButtonAction button");
        }

    }

    @FXML
    public final void getOnMouseClickedOnDepartmentConsultantView()
    {

        //departmentSelection will be active When a specific department is selected
        departmentSelection = departmentTableView.getSelectionModel().getSelectedItem();
        if (departmentSelection == null)
        {
            //System.out.println("getOnMouse check" + departmentSelection.getDepartment_id());
        } else
        {
            String department_id = departmentSelection.getDepartment_id();

            try
            {
                // create the mysql database connection
                dc = new DbConnection();
                Connection conn = dc.Connect();

                Statement statement = conn.createStatement();

                //Consultants will be visible when a specific department selected
                ResultSet resultSetConsultant = statement.executeQuery("select * from (consultant join department on "
                        + "department_id = consultant_department_id) where department_id =  " + department_id);

                obListconsultant.clear();

                while (resultSetConsultant.next())
                {
                    //get String from db
                    obListconsultant.add(new ConsultantClass(resultSetConsultant.getString(1), resultSetConsultant.getString(2),
                            resultSetConsultant.getString(3), resultSetConsultant.getString(4), resultSetConsultant.getString(5),
                            resultSetConsultant.getString(6), resultSetConsultant.getString(7), resultSetConsultant.getString(8)));

                }

                //Employees will be visible when a specific department selected
                ResultSet resultSetEmployee = statement.executeQuery("select * from (employee join department on "
                        + "department_id = employee_department_id) where department_id =  " + department_id);
                obListemployee = FXCollections.observableArrayList();
                while (resultSetEmployee.next())
                {

                    //get String from db
                    obListemployee.add(new EmployeeClass(resultSetEmployee.getString(1), resultSetEmployee.getString(2),
                            resultSetEmployee.getString(3), resultSetEmployee.getString(4), resultSetEmployee.getString(5),
                            resultSetEmployee.getString(6), resultSetEmployee.getString(7), resultSetEmployee.getString(8)));

                }

                

                //Setting employee Table View
                employeeID.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
                employeeFirstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
                employeeLastName.setCellValueFactory(new PropertyValueFactory<>("last_name"));
                employeeSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
                employeessn.setCellValueFactory(new PropertyValueFactory<>("ssn"));
                employeePhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
                employeeEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

                employeeTableView.setItems(null);
                employeeTableView.setItems(obListemployee);

                //Setting the consultant table
                consultantID.setCellValueFactory(new PropertyValueFactory<>("consultant_id"));
                consultantFirstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
                consultantLastName.setCellValueFactory(new PropertyValueFactory<>("last_name"));
                chargePerhr.setCellValueFactory(new PropertyValueFactory<>("charge_perhr"));
                consultantssn.setCellValueFactory(new PropertyValueFactory<>("ssn"));
                consultantPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
                consultantEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

                consultantTableView.setItems(null);
                consultantTableView.setItems(obListconsultant);
                
                conn.close();

            } catch (Exception e)
            {
                System.err.println("Got an exception! ");
                System.err.println(e.getMessage());
            }

        }

    }

    @FXML
    public final void getOnMouseClickedOnProjectsView()
    {
        projectSelection = projectTableView.getSelectionModel().getSelectedItem();
        if (projectSelection != null)
        {

            String project_name = projectSelection.getProject_name();
            String project_id = projectSelection.getProject_id();

            try
            {
                // create the mysql database connection
                dc = new DbConnection();
                Connection conn = dc.Connect();

                Statement statement = conn.createStatement();
                System.out.println("project name " + project_name);

                //Consultant will be visible when a specific project selected
                ResultSet resultSetConsultant = statement.executeQuery("select * from consultant join ( project join consultant_works_on on "
                        + "project_id =project_project_id ) on consultant_id = consultant_consultant_id where  project_id = " + project_id);
                obListconsultant = FXCollections.observableArrayList();
                while (resultSetConsultant.next())
                {
                    //result from db
                    obListconsultant.add(new ConsultantClass(resultSetConsultant.getString(1), resultSetConsultant.getString(2),
                            resultSetConsultant.getString(3), resultSetConsultant.getString(4), resultSetConsultant.getString(5),
                            resultSetConsultant.getString(6), resultSetConsultant.getString(7), resultSetConsultant.getString(8)));

                }

                //Employees will be visible when a specific project selected
                ResultSet resultSetEmployee = statement.executeQuery("select * from employee join (project join employee_works_on on "
                        + "project_id = project_project_id) on employee_id =employee_employee_id where project_id =  " + project_id);
                obListemployee = FXCollections.observableArrayList();
                while (resultSetEmployee.next())
                {
                    obListemployee.add(new EmployeeClass(resultSetEmployee.getString(1), resultSetEmployee.getString(2),
                            resultSetEmployee.getString(3), resultSetEmployee.getString(4), resultSetEmployee.getString(5),
                            resultSetEmployee.getString(6), resultSetEmployee.getString(7), resultSetEmployee.getString(8)));

                }

                conn.close();

                //Setting employee TableView
                employeeID.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
                employeeFirstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
                employeeLastName.setCellValueFactory(new PropertyValueFactory<>("last_name"));
                employeeSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
                employeessn.setCellValueFactory(new PropertyValueFactory<>("ssn"));
                employeePhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
                employeeEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

                employeeTableView.setItems(null);
                employeeTableView.setItems(obListemployee);

                //Setting the consultant TableView
                consultantID.setCellValueFactory(new PropertyValueFactory<>("consultant_id"));
                consultantFirstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
                consultantLastName.setCellValueFactory(new PropertyValueFactory<>("last_name"));
                chargePerhr.setCellValueFactory(new PropertyValueFactory<>("charge_perhr"));
                consultantssn.setCellValueFactory(new PropertyValueFactory<>("ssn"));
                consultantPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
                consultantEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

                consultantTableView.setItems(null);
                consultantTableView.setItems(obListconsultant);

            } catch (Exception e)
            {
                System.err.println("Got an exception! ");
                System.err.println(e.getMessage());
            }

        }

    }

    @FXML
    private void addEmployeeButtonAction(ActionEvent event) throws IOException
    {
        Parent root2 = FXMLLoader.load(getClass().getResource("EditEmployee.fxml"));
        Scene scene = new Scene(root2);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Add Employee");
    }

    @FXML
    private void editEmployeeButtonAction(ActionEvent event) throws IOException
    {
        employeeSelection = employeeTableView.getSelectionModel().getSelectedItem();
        if (employeeSelection != null)
        {

            Parent root2 = FXMLLoader.load(getClass().getResource("EditEmployee.fxml"));
            Scene scene = new Scene(root2);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Sigma Database");
        } else
        {
            errorMessageLabel.setText("Select the employee to edit");
        }
    }

    @FXML
    private void editConsultantButtonAction(ActionEvent event) throws IOException
    {
        consultantSelection = consultantTableView.getSelectionModel().getSelectedItem();
        if (consultantSelection != null)
        {
            Parent root2 = FXMLLoader.load(getClass().getResource("EditConsultant.fxml"));
            Scene scene = new Scene(root2);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Sigma Database");
        } else
        {
            errorMessageLabel.setText("Select a specific consultant to edit");
        }
    }

    @FXML
    private void deleteEmployeeButtonAction(ActionEvent event) throws IOException
    {
        employeeSelection = employeeTableView.getSelectionModel().getSelectedItem();

        try
        {
            if (employeeSelection != null)
            {
                int n = Integer.valueOf(employeeSelection.getEmployee_id());

                // create the mysql database connection
                dc = new DbConnection();
                Connection conn = dc.Connect();

                Statement statement = conn.createStatement();
                String query = "delete from employee where employee_id= " + n;
                //PreparedStatement preparedStmt = conn.prepareStatement(query);

                int rowsAffected = statement.executeUpdate(query);
                System.out.println("rows affected " + rowsAffected);
                
                // To update the employee TableView after the delete
                obListemployee.clear();
                
                ResultSet resultEmployee = statement.executeQuery("select * from employee");
                
                while(resultEmployee.next())
                {
                    obListemployee.add(new EmployeeClass(resultEmployee.getString(1), resultEmployee.getString(2),
                        resultEmployee.getString(3), resultEmployee.getString(4), resultEmployee.getString(5),
                        resultEmployee.getString(6), resultEmployee.getString(7), resultEmployee.getString(8)));
                }

                conn.close();

                //Setting employee TableView columns
        employeeID.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        employeeFirstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        employeeLastName.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        employeeSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        employeessn.setCellValueFactory(new PropertyValueFactory<>("ssn"));
        employeePhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        employeeEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        employeeTableView.setItems(null);
        employeeTableView.setItems(obListemployee);
            } else
            {
                errorMessageLabel.setText("Select employee to delete");
            }
        } catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

    }

    @FXML
    private void addProjectButtonAction(ActionEvent event) throws IOException
    {

        obListproject.clear();
//        if (projectSelection == null)
//        {

            Parent root2 = FXMLLoader.load(getClass().getResource("EditProject.fxml"));
            Scene scene = new Scene(root2);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Add Project");
//        } else
//        {
//            errorMessageLabel.setText("You can't select ");
//            //projectSelection= projectTableView.getSelectionModel().getSelectedItem();
//            Parent root2 = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
//            Scene scene = new Scene(root2);
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            stage.setScene(scene);
//            stage.setTitle("Sigma Database");
//            System.out.println("not null");
//
//        }
    }
    
    @FXML
    private void showAllEmployeesButtonAction(ActionEvent event) throws IOException
    {
        obListemployee.clear();
         try
        {

            dc = new DbConnection();
            Connection connection = dc.Connect();

            //2. Create a statement
            Statement statement = connection.createStatement();

            //3. Execute SQL query
            ResultSet resultSet = statement.executeQuery("select * from employee");

            //4. process the result set
            while (resultSet.next())
            {

                obListemployee.add(new EmployeeClass(resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7), resultSet.getString(8)));

            }


        } catch (SQLException ex)
        {
            System.out.println("error");
        }
        //Setting employee Table View
        employeeID.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        employeeFirstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        employeeLastName.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        employeeSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        employeessn.setCellValueFactory(new PropertyValueFactory<>("ssn"));
        employeePhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        employeeEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        employeeTableView.setItems(null);
        employeeTableView.setItems(obListemployee);
        
    }

    @FXML
    private void showAllConsultantButtonAction(ActionEvent event) throws IOException
    {
        obListconsultant.clear();
        try
        {

            dc = new DbConnection();
            Connection connection = dc.Connect();

            //2. Create a statement
            Statement statement = connection.createStatement();
        //3. Execute SQL query for project table
            ResultSet resultSetConsultant = statement.executeQuery("select * from consultant");

            while (resultSetConsultant.next())
            {
                //get consultants data from db
                obListconsultant.add(new ConsultantClass(resultSetConsultant.getString(1), resultSetConsultant.getString(2),
                        resultSetConsultant.getString(3), resultSetConsultant.getString(4), resultSetConsultant.getString(5),
                        resultSetConsultant.getString(6), resultSetConsultant.getString(7), resultSetConsultant.getString(8)));

            }
             } catch (SQLException ex)
        {
            System.out.println("error");
        }
       
        //Setting the consultant table
        consultantID.setCellValueFactory(new PropertyValueFactory<>("consultant_id"));
        consultantFirstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        consultantLastName.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        chargePerhr.setCellValueFactory(new PropertyValueFactory<>("charge_perhr"));
        consultantssn.setCellValueFactory(new PropertyValueFactory<>("ssn"));
        consultantPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        consultantEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        consultantTableView.setItems(null);
        consultantTableView.setItems(obListconsultant);
    }
    @FXML
    private void addConsultantButtonAction(ActionEvent event) throws IOException
    {
        Parent root2 = FXMLLoader.load(getClass().getResource("EditConsultant.fxml"));
        Scene scene = new Scene(root2);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sigma Database");
    }

    @FXML
    private void addDepartmentButtonAction(ActionEvent event) throws IOException
    {

            Parent root2 = FXMLLoader.load(getClass().getResource("EditDepartment.fxml"));
            Scene scene = new Scene(root2);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Add department");

    }

    @FXML
    private void deleteConsultantButtonAction(ActionEvent event) throws IOException
    {

        consultantSelection = consultantTableView.getSelectionModel().getSelectedItem();

        try
        {
            if (consultantSelection != null)
            {
                // create the mysql database connection
                dc = new DbConnection();
                Connection conn = dc.Connect();

                int n = Integer.valueOf(consultantSelection.getConsultant_id());
                System.out.println("check n" + n);
                Statement statement = conn.createStatement();
                String query = "delete from consultant where consultant_id = " + n;
                //PreparedStatement preparedStmt = conn.prepareStatement(query);

                int rowsAffected = statement.executeUpdate(query);
                System.out.println("rows affected " + rowsAffected);
                
                //Execute SQL query for project table, to show the updated TableView after the delete
            ResultSet resultSetConsultant = statement.executeQuery("select * from consultant");

            obListconsultant.clear();
            while (resultSetConsultant.next())
            {
                //get consultants data from db
                obListconsultant.add(new ConsultantClass(resultSetConsultant.getString(1), resultSetConsultant.getString(2),
                        resultSetConsultant.getString(3), resultSetConsultant.getString(4), resultSetConsultant.getString(5),
                        resultSetConsultant.getString(6), resultSetConsultant.getString(7), resultSetConsultant.getString(8)));

            }

                //Setting the consultant table
        consultantID.setCellValueFactory(new PropertyValueFactory<>("consultant_id"));
        consultantFirstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        consultantLastName.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        chargePerhr.setCellValueFactory(new PropertyValueFactory<>("charge_perhr"));
        consultantssn.setCellValueFactory(new PropertyValueFactory<>("ssn"));
        consultantPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        consultantEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        consultantTableView.setItems(null);
        consultantTableView.setItems(obListconsultant);
                conn.close();
                
            } else
            {
                errorMessageLabel.setText("Select the consultant to delete");
            }
        } catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

    }

    @FXML
    private void deleteDepartmentButtonAction(ActionEvent event) throws IOException
    {

        departmentSelection = departmentTableView.getSelectionModel().getSelectedItem();//.getSelectionModel().getSelectedItem();

        try
        {
            if (departmentSelection != null)
            {
                // create the mysql database connection
                dc = new DbConnection();
                Connection conn = dc.Connect();

                int n = Integer.valueOf(departmentSelection.getDepartment_id());
                Statement statement = conn.createStatement();
                String query = "delete from department where department_id = " + n;
                //PreparedStatement preparedStmt = conn.prepareStatement(query);

                int rowsAffected = statement.executeUpdate(query);
                System.out.println("rows affected " + rowsAffected);

                
                
                // Execute SQL query for department table
            ResultSet resultSet2 = statement.executeQuery("select * from department");

            obListdepartment.clear();
            while (resultSet2.next())
            {
                 
                obListdepartment.add(new DepartmentClass(resultSet2.getString(1), resultSet2.getString(2), resultSet2.getString(3), resultSet2.getString(4)));

            }
                //Setting department TableView
        departmentID.setCellValueFactory(new PropertyValueFactory<>("department_id"));
        departmentAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        departmentName.setCellValueFactory(new PropertyValueFactory<>("department_name"));
        departmentDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        departmentTableView.setItems(null);
        departmentTableView.setItems(obListdepartment);
        
        conn.close();
            } else
            {
                errorMessageLabel.setText("Select the department to delete");
            }
        } catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

    }

    @FXML
    private void editProjectButtonAction(ActionEvent event) throws IOException
    {
//         System.out.println("test id "+ projectSelection.getProject_id());
        if (projectSelection != null)
        {
            System.out.println("test id " + projectSelection.getProject_id());
            Parent root2 = FXMLLoader.load(getClass().getResource("EditProject.fxml"));
            Scene scene = new Scene(root2);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Sigma Database");
        } else
        {
            errorMessageLabel.setText("Select the project to edit");
        }

    }

    @FXML
    private void editDepartmentButtonAction(ActionEvent event) throws IOException
    {
        if (departmentSelection != null)
        {
            System.out.println("test id " + departmentSelection.getDepartment_id());
            Parent root2 = FXMLLoader.load(getClass().getResource("EditDepartment.fxml"));
            Scene scene = new Scene(root2);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Sigma Database");
        } else
        {
            errorMessageLabel.setText("Select the department to edit");
        }

    }

    @FXML
    private void deleteProjectButtonAction(ActionEvent event)
    {
        projectSelection = projectTableView.getSelectionModel().getSelectedItem();//.getSelectionModel().getSelectedItem();

        try
        {
            if (projectSelection != null)
            {
                // create the mysql database connection
                dc = new DbConnection();
                Connection conn = dc.Connect();

                int n = Integer.valueOf(projectSelection.getProject_id());
                Statement statement = conn.createStatement();
                String query = "delete from project where project_id = " + n;

                int rowsAffected = statement.executeUpdate(query);
                System.out.println("rows affected " + rowsAffected);
                
               ResultSet resultSet3 = statement.executeQuery("select * from project");
            
               //updating the project table after the delete
            obListproject.clear();
            while (resultSet3.next())
            {
                //get String from db
                obListproject.add(new ProjectClass(resultSet3.getString(1), resultSet3.getString(2), resultSet3.getString(3)));

            }
                
                /**
                 * To reset the table
                 */
                conn.close();
                
                //Setting the project table
        projectID.setCellValueFactory(new PropertyValueFactory<>("project_id"));
        projectName.setCellValueFactory(new PropertyValueFactory<>("project_name"));
        projectdescription.setCellValueFactory(new PropertyValueFactory<>("description"));

            } else
            {
                errorMessageLabel.setText("Select the specific project to delete");
            }
        } catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        obListconsultant = FXCollections.observableArrayList();
        obListemployee = FXCollections.observableArrayList();
        obListdepartment = FXCollections.observableArrayList();
        obListconsultant = FXCollections.observableArrayList();
        try
        {

            dc = new DbConnection();
            Connection connection = dc.Connect();

            //2. Create a statement
            Statement statement = connection.createStatement();

            //3. Execute SQL query
            ResultSet resultSet = statement.executeQuery("select * from employee");

            //4. process the result set
            while (resultSet.next())
            {

                obListemployee.add(new EmployeeClass(resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7), resultSet.getString(8)));

            }

            //3. Execute SQL query for department table
            ResultSet resultSet2 = statement.executeQuery("select * from department");

            while (resultSet2.next())
            {
                 
                obListdepartment.add(new DepartmentClass(resultSet2.getString(1), resultSet2.getString(2), resultSet2.getString(3), resultSet2.getString(4)));

            }

            //3. Execute SQL query for project table
            ResultSet resultSet3 = statement.executeQuery("select * from project");
            obListproject = FXCollections.observableArrayList();
            while (resultSet3.next())
            {
                //get String from db
                obListproject.add(new ProjectClass(resultSet3.getString(1), resultSet3.getString(2), resultSet3.getString(3)));

            }

            //Execute SQL query for project table
            ResultSet resultSetConsultant = statement.executeQuery("select * from consultant");

            while (resultSetConsultant.next())
            {
                //get consultants data from db
                obListconsultant.add(new ConsultantClass(resultSetConsultant.getString(1), resultSetConsultant.getString(2),
                        resultSetConsultant.getString(3), resultSetConsultant.getString(4), resultSetConsultant.getString(5),
                        resultSetConsultant.getString(6), resultSetConsultant.getString(7), resultSetConsultant.getString(8)));

            }

        } catch (SQLException ex)
        {
            System.out.println("error");
        }
        //Setting employee Table View
        employeeID.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        employeeFirstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        employeeLastName.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        employeeSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        employeessn.setCellValueFactory(new PropertyValueFactory<>("ssn"));
        employeePhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        employeeEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        employeeTableView.setItems(null);
        employeeTableView.setItems(obListemployee);

        //Setting department TableView
        departmentID.setCellValueFactory(new PropertyValueFactory<>("department_id"));
        departmentAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        departmentName.setCellValueFactory(new PropertyValueFactory<>("department_name"));
        departmentDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        departmentTableView.setItems(null);
        departmentTableView.setItems(obListdepartment);

        //Setting the project table
        projectID.setCellValueFactory(new PropertyValueFactory<>("project_id"));
        projectName.setCellValueFactory(new PropertyValueFactory<>("project_name"));
        projectdescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        projectTableView.setItems(null);
        projectTableView.setItems(obListproject);

        //Setting the consultant table
        consultantID.setCellValueFactory(new PropertyValueFactory<>("consultant_id"));
        consultantFirstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        consultantLastName.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        chargePerhr.setCellValueFactory(new PropertyValueFactory<>("charge_perhr"));
        consultantssn.setCellValueFactory(new PropertyValueFactory<>("ssn"));
        consultantPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        consultantEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        consultantTableView.setItems(null);
        consultantTableView.setItems(obListconsultant);
    }

}
