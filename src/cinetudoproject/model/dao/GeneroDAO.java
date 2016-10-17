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
    
    public Genero buscaGenero(int idGenero) {
        Genero genero = null;
        
        final String busca = "SELECT id, nome FROM genero WHERE id = ?";
        try {
            Connection conn = database.connect();
            PreparedStatement buscar = conn.prepareStatement(busca);
            buscar.setInt(1, idGenero);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            genero = buscaGenero(resultadoBusca);
            buscar.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return genero;
    }
    
    public Genero buscaGenero(String nomeGenero) {
        Genero genero = null;
        
        final String busca = "SELECT id, nome FROM genero WHERE nome = ?";
        try {
            Connection conn = database.connect();
            PreparedStatement buscar = conn.prepareStatement(busca);
            buscar.setString(1, nomeGenero);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            genero = buscaGenero(resultadoBusca);
            buscar.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
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
