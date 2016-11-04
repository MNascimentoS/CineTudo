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
import javafx.scene.control.Alert;
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
    public void insertFuncionario(Funcionario funcionario) {
        final String inserir = "INSERT INTO funcionario(nome, cpf, email, cargo, usuario, senha, cinema_id) values(?,?,?,?,?,?,?)";
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
            salvar.setInt(7, funcionario.getCinema_id());
            salvar.executeUpdate();
            salvar.close();
            conn.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Sucesso");
            alert.setContentText("Cadastrado com sucesso!");
            alert.showAndWait();
            //return true;
        } catch (SQLException ex) {
            Logger.getLogger("Error on: " + FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Erro");
            alert.setContentText("Erro no cadastro!");
            alert.showAndWait();
            //return false;
        }
    }

    public void update(Funcionario funcionario) {
        final String update = "update funcionario set nome = ?, cpf = ?, email = ?, usuario = ?, senha = ?, cinema_id = ? where usuario = ?";
        try {
            //get the connection
            Connection conn = database.connect();
            PreparedStatement salvar = conn.prepareStatement(update);
            salvar.setString(1, funcionario.getNome());
            salvar.setString(2, funcionario.getCpf());
            salvar.setString(3, funcionario.getEmail());
            salvar.setString(4, funcionario.getUser());
            salvar.setString(5, funcionario.getSenha());
            salvar.setInt(6, funcionario.getCinema_id());
            salvar.setString(7, funcionario.getUser());
            salvar.executeUpdate();
            salvar.close();
            conn.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Sucesso");
            alert.setContentText("Alterado com sucesso!");
            alert.showAndWait();
            //return true;
        } catch (SQLException ex) {
            Logger.getLogger("Error on: " + FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Sucesso");
            alert.setContentText("Erro na alteração!");
            alert.showAndWait();
            //return false;
        }
    }
    
    public void delete(int funcionarioId) {
        final String delete = "delete from funcionario where id = ?";
        try {
            Connection conn = database.connect();
            PreparedStatement deletar = conn.prepareStatement(delete);
            deletar.setInt(1, funcionarioId);
            deletar.executeUpdate();
            deletar.close();
            conn.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Sucesso");
            alert.setContentText("Deletado com sucesso!");
            alert.showAndWait();
       } catch (SQLException ex) {
            Logger.getLogger("Error on: " + FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Erro");
            alert.setContentText("Erro na remoção!");
            alert.showAndWait();
            //return false;
        }
    }

    public Funcionario buscaPorUser(String user) {
        Funcionario func = null;
        final String busca = "SELECT id, nome, cpf, email, cargo, usuario, senha, cinema_id FROM funcionario WHERE usuario = ?";
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
            //e.printStackTrace();
            System.err.println("ERRO AO BUSCAR CONTA COM USUARIO "+ user);
            //System.exit(0);
        }
        return func;
    }

    private Funcionario buscaFuncionario(ResultSet resultadoBusca) throws SQLException, ParseException {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(resultadoBusca.getInt(1));
        funcionario.setNome(resultadoBusca.getString(2));
        funcionario.setCpf(resultadoBusca.getString(3));
        funcionario.setEmail(resultadoBusca.getString(4));
        funcionario.setCargo(resultadoBusca.getInt(5));
        funcionario.setUser(resultadoBusca.getString(6));
        funcionario.setSenha(resultadoBusca.getString(7));
        funcionario.setCinema_id(resultadoBusca.getInt(8));

        return funcionario;
    }
}
