/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.model.dao;

import cinetudoproject.model.database.DatabaseMySQL;
import cinetudoproject.model.domain.Funcionario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author José EXAMPLE
 */
public class FuncionarioDAO {
    
    private final DatabaseMySQL database;

    public FuncionarioDAO() {
        database = new DatabaseMySQL();
    }

    //insert method
    public void insertFuncionario(Funcionario funcionario) {
        final String inserir = "INSERT INTO Funcionario(nome, cpf, email, cargo, usuario, senha, cinema_id) values(?,?,?,?,?,?,?)";
        try {
            //get the connection
            PreparedStatement salvar = database.connect().prepareStatement(inserir);
            salvar.setString(1, funcionario.getNome());
            salvar.setString(2, funcionario.getCpf());
            salvar.setString(3, funcionario.getEmail());
            salvar.setString(4, "0");
            salvar.setString(5, funcionario.getUser());
            salvar.setString(6, funcionario.getSenha());
            salvar.setInt(7, funcionario.getCinema_id());
            salvar.executeUpdate();
            salvar.close();
            database.desconnect();
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
        final String update = "update Funcionario set nome = ?, cpf = ?, email = ?, usuario = ?, senha = ?, cinema_id = ? where usuario = ?";
        try {
            //get the connection
            PreparedStatement salvar = database.connect().prepareStatement(update);
            salvar.setString(1, funcionario.getNome());
            salvar.setString(2, funcionario.getCpf());
            salvar.setString(3, funcionario.getEmail());
            salvar.setString(4, funcionario.getUser());
            salvar.setString(5, funcionario.getSenha());
            salvar.setInt(6, funcionario.getCinema_id());
            salvar.setString(7, funcionario.getUser());
            salvar.executeUpdate();
            salvar.close();
            database.desconnect();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Sucesso");
            alert.setContentText("Alterado com sucesso!");
            alert.showAndWait();
            //return true;
        } catch (SQLException ex) {
            Logger.getLogger("Error on: " + FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Erro");
            alert.setContentText("Erro na alteração!");
            alert.showAndWait();
            //return false;
        }
    }
    
    public void delete(int funcionarioId) {
        final String delete = "delete from Funcionario where id = ?";
        try {
            PreparedStatement deletar = database.connect().prepareStatement(delete);
            deletar.setInt(1, funcionarioId);
            deletar.executeUpdate();
            deletar.close();
            database.desconnect();
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
        final String busca = "SELECT id, nome, cpf, email, cargo, usuario, senha, cinema_id FROM Funcionario WHERE usuario = ?";
        try {
            PreparedStatement buscar = database.connect().prepareStatement(busca);
            buscar.setString(1, user);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            func = buscaFuncionario(resultadoBusca);
            buscar.close();
            database.desconnect();
        } catch (SQLException | ParseException e) {
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
