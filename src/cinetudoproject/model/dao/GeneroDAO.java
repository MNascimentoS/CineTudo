/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.model.dao;

import cinetudoproject.model.database.DatabaseMySQL;
import cinetudoproject.model.domain.Genero;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author mateus
 */
public class GeneroDAO {
    Connection connection;
    DatabaseMySQL database;

    public GeneroDAO() {
        database = new DatabaseMySQL();
    }
    
    //insert method
    public void insertGenero(Genero genero) {
        final String inserir = "INSERT INTO genero(nome) values(?)";
        try {
            Connection conn = database.connect();
            PreparedStatement salvar = conn.prepareStatement(inserir);
            salvar.setString(1, genero.getNome());
            salvar.executeUpdate();
            salvar.close();
            conn.close();
            JOptionPane.showMessageDialog(null, "Cadastrado com Sucesso");
        } catch (SQLException ex) {
            Logger.getLogger("Error on: " + GeneroDAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro no cadastro" + "\n" + ex.getMessage());
        }
    }
    
    public Genero buscaGenero(int idGenero) {
        Genero genero = null;
        
        final String busca = "SELECT id, nome FROM genero WHERE id = ? order by nome";
        try {
            connection = database.connect();
            PreparedStatement buscar = connection.prepareStatement(busca);
            buscar.setInt(1, idGenero);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            genero = buscaGenero(resultadoBusca);
            buscar.close();
            connection.close();
        } catch (Exception ex) {
            Logger.getLogger("Error na busca: " + GeneroDAO.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return genero;
    }
    
    public List<Genero> listar() {
        final String sql = "SELECT * FROM genero";
        List<Genero> retorno = new ArrayList<>();
        try {
            connection = database.connect();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Genero genero = new Genero(resultado.getInt("id"), 
                                           resultado.getString("nome"));
                retorno.add(genero);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GeneroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public Genero buscaGenero(String nomeGenero) {
        Genero genero = null;
        final String busca = "SELECT id, nome FROM genero WHERE nome = ?";
        try {
            connection = database.connect();
            PreparedStatement buscar = connection.prepareStatement(busca);
            buscar.setString(1, nomeGenero);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            genero = buscaGenero(resultadoBusca);
            buscar.close();
            connection.close();
        } catch (Exception e) {
            //Logger.getLogger("Error na busca: " + GeneroDAO.class.getName()).log(Level.SEVERE, null, e);
            //e.printStackTrace();
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
