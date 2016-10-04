/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Wellington
 */
public class DBConect {
    private String url;
    private String user;
    private String password;

    public DBConect() {
        this.url = "jdbc:mysql://localhost:3306/cinetudo";
        this.user = "root";
        this.password = " ";
    }

    public String getUrl() {
        return url;
    }

    public final void setUrl(String url) {
        this.url = url;
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

    public final void setPassword(String password) {
        this.password = password;
    }
    
    public Connection conexao() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(getUrl(), getUser(), getPassword());
        } catch (ClassNotFoundException | SQLException e) {
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
    
    
}
