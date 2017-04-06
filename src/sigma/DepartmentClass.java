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
public class DepartmentClass
{

    private final StringProperty department_id;
    private final StringProperty address;
    private final StringProperty department_name;
    private final StringProperty description;

    public DepartmentClass(String department_id, String address, String department_name, String description)
    {
        this.department_id = new SimpleStringProperty(department_id);
        this.address = new SimpleStringProperty(address);
        this.department_name = new SimpleStringProperty(department_name);
        this.description = new SimpleStringProperty(description);
    }

    public String getDepartment_id()
    {
        return department_id.get();
    }

    public String getAddress()
    {
        return address.get();
    }

    public String getDepartment_name()
    {
        return department_name.get();
    }

    public String getDescription()
    {
        return description.getValue();
    }

    //setters
    public void setDepartment_id(String value)
    {
        department_id.set(value);
    }
    
    public void setAddress(String value)
    {
        address.set(value);
    }
    
    public void setDepartment_name(String value)
    {
        department_name.set(value);
    }
    public void setDescription(String value)
    {
        description.set(value);
    }
    
    //property values
    public StringProperty department_idproperty(){return department_id;}
    public StringProperty addressproperty(){return address;}
    public StringProperty department_nameproperty(){return department_name;}
    public StringProperty department_descriptionproperty(){return description;}
}
