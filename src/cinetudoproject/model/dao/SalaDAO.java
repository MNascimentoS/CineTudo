/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.model.dao;

import cinetudoproject.model.database.DatabaseMySQL;
import cinetudoproject.model.domain.Sala;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author Wellington
 */
public class SalaDAO {

    private final DatabaseMySQL database;

    public SalaDAO() {
        database = new DatabaseMySQL();
    }

    //insert method
    public void insertSala(Sala sala, int cinema_id) {
        final String inserir = "INSERT INTO Sala(numero, capacidade, tipo, preco_ingresso, cinema_id) values(?,?,?,?,?)";
        try {
            PreparedStatement salvar = database.connect().prepareStatement(inserir);
            salvar.setInt(1, sala.getNumero());
            salvar.setInt(2, sala.getCapacidade());
            salvar.setString(3, sala.getTipo());
            salvar.setFloat(4, sala.getPreco_ingresso());
            salvar.setInt(5, cinema_id);

            salvar.executeUpdate();
            salvar.close();
            database.desconnect();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Sucesso");
            alert.setContentText("Cadastrado com sucesso!");
            alert.showAndWait();
        } catch (SQLException ex) {
            //Logger.getLogger("Error on: " + SalaDAO.class.getNumero()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Erro");
            alert.setContentText("Erro no cadastro!");
            alert.showAndWait();
        }
    }

    public List<Sala> listar() throws FileNotFoundException, IOException {
        final String sql = "SELECT * FROM Sala order by numero";
        List<Sala> retorno = new ArrayList<>();

        try {
            PreparedStatement stmt = database.connect().prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Sala sala = new Sala();
                sala.setId(resultado.getInt("id"));
                sala.setNumero(resultado.getInt("numero"));
                sala.setCapacidade(resultado.getInt("capacidade"));
                sala.setTipo(resultado.getString("tipo"));
                sala.setPreco_ingresso(resultado.getFloat("preco_ingresso"));
                retorno.add(sala);
            }
            database.desconnect();
        } catch (SQLException ex) {
            Logger.getLogger(FilmeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Sala buscaPorNumeroSala(int numero) {
        Sala sala = null;
        final String busca = "SELECT id, numero, capacidade, tipo, preco_ingresso FROM Sala WHERE numero = ?";
        try {
            PreparedStatement buscar = database.connect().prepareStatement(busca);
            buscar.setInt(1, numero);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            sala = buscaHorario(resultadoBusca);
            buscar.close();
            database.desconnect();
        } catch (SQLException | ParseException e) {
            System.err.println("ERRO AO BUSCAR A SALA: "+ numero);
        }
        return sala;
    }

     public Sala buscaPorSala(int sala_id) {
        Sala sala = null;
        final String busca = "SELECT id, numero, capacidade, tipo, preco_ingresso FROM Sala WHERE id = ?";
        try {
            Connection conn = database.connect();
            PreparedStatement buscar = conn.prepareStatement(busca);
            buscar.setInt(1, sala_id);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            sala = buscaHorario(resultadoBusca);
            database.desconnect();
        } catch (SQLException | ParseException e) {
            System.err.println("ERRO AO BUSCAR A SALA: "+ sala_id);
        }
        return sala;
    }
    
    
    private Sala buscaHorario(ResultSet resultadoBusca) throws SQLException, ParseException {
        Sala sala = new Sala();
        sala.setId(resultadoBusca.getInt(1));
        sala.setNumero(resultadoBusca.getInt(2));
        sala.setCapacidade(resultadoBusca.getInt(3));
        sala.setTipo(resultadoBusca.getString(4));
        sala.setPreco_ingresso(resultadoBusca.getFloat(5));
        return sala;
    }

    public void delete(int salaId) {
        final String delete = "delete from Sala where id = ?";
        try {
            PreparedStatement deletar = database.connect().prepareStatement(delete);
            deletar.setInt(1, salaId);
            deletar.executeUpdate();
            deletar.close();
            database.desconnect();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Sucesso");
            alert.setContentText("Deletado com sucesso!");
            alert.showAndWait();

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Errp");
            alert.setContentText("Erro na remoção!");
            alert.showAndWait();
            //return false;
        }
    }

    public void update(Sala sala) {
        final String update = "update Sala set numero = ?, capacidade = ?, tipo = ? where id = ?";
        try {
            PreparedStatement atualizar = database.connect().prepareStatement(update);
            atualizar.setInt(1, sala.getNumero());
            atualizar.setInt(2, sala.getCapacidade());
            atualizar.setString(3, sala.getTipo());
            atualizar.setInt(4, sala.getId());
            atualizar.executeUpdate();
            atualizar.close();
            database.desconnect();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Sucesso");
            alert.setContentText("Atualizado com sucesso!");
            alert.showAndWait();
       } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Erro");
            alert.setContentText("Erro na atualização!");
            alert.showAndWait();
        }
    }
}
