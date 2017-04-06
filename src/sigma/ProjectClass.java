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
public class ProjectClass
{
    private final StringProperty project_id;
    private final StringProperty project_name;
    private final StringProperty description;
    
      
    public ProjectClass(String project_id, String project_name, String description)
    {
        this.project_id = new SimpleStringProperty(project_id);
        this.project_name = new SimpleStringProperty(project_name);
        this.description = new SimpleStringProperty(description);
    }
    
    
    public String getProject_id()
    {
        return project_id.get();
    }

  
    public String getProject_name()
    {
        return project_name.get();
    }

    public String getDescription()
    {
        return description.getValue();
    }

    //setters
    public void setProject_id(String value)
    {
        project_id.set(value);
    }
      
    public void setProject_name(String value)
    {
        project_name.set(value);
    }
    public void setDescription(String value)
    {
        description.set(value);
    }
    
    //property values
    public StringProperty project_idproperty(){return project_id;}
    public StringProperty project_nameproperty(){return project_name;}
    public StringProperty descriptionproperty(){return description;}
    
}
