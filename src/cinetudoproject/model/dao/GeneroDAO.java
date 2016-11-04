/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.model.dao;

import cinetudoproject.model.database.DatabaseMySQL;
import cinetudoproject.model.domain.Genero;
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
 * @author mateus
 */
public class GeneroDAO {
   
    private final DatabaseMySQL database;

    public GeneroDAO() {
        database = new DatabaseMySQL();
    }
    
    //insert method
    public void insertGenero(Genero genero) {
        final String inserir = "INSERT INTO Genero(nome) values(?)";
        try {
            PreparedStatement salvar = database.connect().prepareStatement(inserir);
            salvar.setString(1, genero.getNome());
            salvar.executeUpdate();
            salvar.close();
            database.desconnect();
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("sucesso");
            alert.setContentText("Cadastrado com sucesso!");
            alert.showAndWait();
        } catch (SQLException ex) {
            Logger.getLogger("Error on: " + GeneroDAO.class.getName()).log(Level.SEVERE, null, ex);
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("erro");
            alert.setContentText("Erro ao cadastrar!");
            alert.showAndWait();
        }
    }
    
    public Genero buscaGenero(int idGenero) {
        Genero genero = null;
        
        final String busca = "SELECT id, nome FROM Genero WHERE id = ? order by nome";
        try {
            PreparedStatement buscar = database.connect().prepareStatement(busca);
            buscar.setInt(1, idGenero);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            genero = buscaGenero(resultadoBusca);
            buscar.close();
            database.desconnect();
        } catch (SQLException | ParseException ex) {
            Logger.getLogger("Error na busca: " + GeneroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return genero;
    }
    
    public List<Genero> listar() {
        final String sql = "SELECT * FROM Genero";
        List<Genero> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = database.connect().prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Genero genero = new Genero(resultado.getInt("id"), 
                                           resultado.getString("nome"));
                retorno.add(genero);
            }
            database.desconnect();
        } catch (SQLException ex) {
            Logger.getLogger(GeneroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public Genero buscaGenero(String nomeGenero) {
        Genero genero = null;
        final String busca = "SELECT id, nome FROM Genero WHERE nome = ?";
        try {
            PreparedStatement buscar = database.connect().prepareStatement(busca);
            buscar.setString(1, nomeGenero);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            genero = buscaGenero(resultadoBusca);
            buscar.close();
            database.desconnect();
        } catch (SQLException | ParseException e) {
          
        }
        return genero;
    }
    
    private Genero buscaGenero(ResultSet resultadoBusca) throws SQLException, ParseException {
        Genero genero = new Genero();
        genero.setId(resultadoBusca.getInt(1));
        genero.setNome(resultadoBusca.getString(2));
        System.out.println(genero.getNome());
        return genero;
    }
}
