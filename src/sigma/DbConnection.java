/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sigma;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Befkadu Degefa
 */
public class DbConnection
{

    public DbConnection ()
    {
        //
    }
    
    public Connection Connect() throws SQLException
    {
        //1. Get a connection to database
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sigmaconnectivity", "root", "root");
        //root is username of the localhost, admin is a password
        
 

//            //The database url string ensure it is correct
//            String url = "jdbc:mysql://localhost:3306/sigmaconnectivity";
//            String user = "root";
//            String password = "admin";
//            
//            //Class.forName("com.mysql.jdbc.Driver");
//            Connection conneection = DriverManager.getConnection(url, user, password);
           return connection;
            

       
    }
}
