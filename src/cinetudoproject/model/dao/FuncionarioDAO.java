/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.model.dao;

import cinetudoproject.model.database.DatabaseMySQL;
import cinetudoproject.model.domain.Funcionario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author José EXAMPLE
 */
public class FuncionarioDAO {

    Connection connection;
    DatabaseMySQL database;

    public FuncionarioDAO() {
        database = new DatabaseMySQL();
    }

    //insert method
    public void insert(Funcionario funcionario) {
        final String inserir = "INSERT INTO funcionario(nome, cpf, email, cargo, usuario, senha) values(?,?,?,?,?,?)";
        try {
            //get the connection

            Connection conn = database.connect();
            PreparedStatement salvar = conn.prepareStatement(inserir);
            salvar.setString(1, funcionario.getNome());
            salvar.setString(2, funcionario.getCpf());
            salvar.setString(3, funcionario.getEmail());
            salvar.setString(4, "0");
            salvar.setString(5, funcionario.getUser());
            salvar.setString(6, funcionario.getSenha());
            salvar.executeUpdate();
            salvar.close();
            conn.close();
            //return true;
        } catch (SQLException ex) {
            Logger.getLogger("Error on: " + FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            //return false;
        }
    }

    public Funcionario buscaPorUser(String user) {
        Funcionario func = null;
        final String busca = "SELECT usuario, senha FROM funcionario WHERE usuario = ?";
        try {
            //DBConect db = new DBConect();
            Connection conn = database.connect();
            PreparedStatement buscar = conn.prepareStatement(busca);
            buscar.setString(1, user);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            func = buscaFuncionario(resultadoBusca);
            buscar.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERRO AO BUSCAR CONTA COM USUARIO" + user);
            //System.exit(0);
        }
        return func;
    }

    private Funcionario buscaFuncionario(ResultSet resultadoBusca) throws SQLException, ParseException {
        Funcionario funcionario = new Funcionario();
        funcionario.setUser(resultadoBusca.getString(1));
        funcionario.setSenha(resultadoBusca.getString(2));

        return funcionario;
    }
}
