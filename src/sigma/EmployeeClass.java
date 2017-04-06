/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sigma;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Befkadu Degefa
 */
public class EmployeeClass
{
    
    private final StringProperty employee_id;
    private final StringProperty ssn;
    private final StringProperty first_name;
    private final StringProperty last_name;
    private final StringProperty salary;
    private final StringProperty email;
    private final StringProperty phone;
    private final StringProperty department_id;
    
    

    public EmployeeClass(String employee_id, String first_name, String last_name,String salary, String ssn, String phone, String email, String department_id)
    {
        this.employee_id = new SimpleStringProperty(employee_id);
        this.first_name = new SimpleStringProperty(first_name);
        this.last_name = new SimpleStringProperty(last_name);
        this.salary = new SimpleStringProperty(salary);
        this.ssn = new SimpleStringProperty(ssn);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.department_id = new SimpleStringProperty(department_id);

    }

    public String getEmployee_id()
    {
        return employee_id.get();
    }

    public String getFirst_name()
    {
        return first_name.get();
    }

    public String getLast_name()
    {
        return last_name.getValue();
    }

    public String getSalary()
    {
        return salary.get();
    }

    public String getssn()
    {
        return ssn.get();
    }

    public String getPhone()
    {
        return phone.getValue();
    }

    public String getemail()
    {
        return email.getValue();
    }

    public String getDepartment_id()
    {
        return department_id.get();
    }
    //setters
    public void setEmployee_id(String value)
    {
        employee_id.set(value);
    }

    public void setFirst_name(String value)
    {
        first_name.set(value);
    }

    public void setLast_name(String value)
    {
        last_name.set(value);
    }

    public void setSalary(String value)
    {
        salary.set(value);
    }

    public void setssn(String value)
    {
        ssn.set(value);
    }

    public void setPhone(String value)
    {
        phone.set(value);
    }

    public void setEmail(String value)
    {
        email.set(value);
    }
    
    public void setDepartment_id(String value)
    {
        department_id.set(value);
    }

    //property values
    public StringProperty employee_idproperty() {return employee_id;}

    public StringProperty first_nameproperty() {return first_name;}

    public StringProperty last_nameproperty() { return last_name; }

    public StringProperty salary() { return salary; }

    public StringProperty ssnproperty(){return ssn; }

    public StringProperty phoneproperty() { return phone;  }

    public StringProperty emailproperty() { return email; }
    public StringProperty department_idproperty() { return department_id; }
}