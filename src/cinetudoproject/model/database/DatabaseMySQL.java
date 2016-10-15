/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author José Júnior
 */
public class DatabaseMySQL implements Database{
    
    private Connection connection;
    private String user, password, url;
    
    public DatabaseMySQL()
    {
        this.url = "jdbc:mysql://localhost:3306/cinetudo";
        this.user = "root";
        this.password = "rosimeiremota28";
    }
   
    @Override
    public Connection connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(getUrl(),getUser(),getPassword());
            return this.connection;
        }  catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (e instanceof ClassNotFoundException) {
                JOptionPane.showMessageDialog(null, "VERIFIQUE SE O DRIVER DO BANCO DE DADOS ESTÁ NO CLASSPATH");
            } else {
                JOptionPane.showMessageDialog(null, "VERIFIQUE SE O BANCO ESTÁ RODANDO E SE OS DADOS DE CONEXÃO ESTÃO CORRETOS");
            }
            System.exit(0);
            return null;
        }
    }

    @Override
    public void desconnect(Connection conn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
       public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
