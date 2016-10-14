/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.model.dao;

import cinetudoproject.model.database.Database;
import cinetudoproject.model.database.DatabaseMySQL;
import cinetudoproject.model.domain.Funcionario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author José
 * EXAMPLE
 */
public class FuncionarioDAO {
    
    Connection connection;
    DatabaseMySQL database;

    public FuncionarioDAO() {
        database = new DatabaseMySQL();
    }

    //insert method
    public boolean insert(Funcionario funcionario)
   {
       String sql = "INSERT INTO funcionario(nome, cpf, email, cargo, usuario, senha) values(?,?,?,?,?,?)";
        try {
            PreparedStatement save = connection.prepareStatement(sql);
            //get the connection
            Connection conn = database.connect();
            save.setString(1, funcionario.getNome());
            save.setString(2, funcionario.getCpf());
            save.setString(3, funcionario.getEmail());
            save.setString(4, "0");
            save.setString(5, funcionario.getUser());
            save.setString(6, funcionario.getSenha());
            save.executeUpdate();
            save.execute();
            save.close();
            database.desconnect(conn);//disconnect 
            return true;
        } catch (SQLException ex) {
            Logger.getLogger("Error on: "+FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
