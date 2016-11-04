/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.model.dao;

import cinetudoproject.model.database.DatabaseMySQL;
import cinetudoproject.model.domain.Cinema;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mateus
 */
public class CinemaDAO {

    private final DatabaseMySQL database;

    public CinemaDAO() {
        database = new DatabaseMySQL();
    }

    public List<Cinema> listar() {
        final String sql = "SELECT * FROM Cinema";
        List<Cinema> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = database.connect().prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Cinema cinema = new Cinema();
                cinema.setId(resultado.getInt("id"));
                cinema.setNome(resultado.getString("nome"));
                cinema.setEndereco(resultado.getString("endereco"));
                cinema.setCpnj(resultado.getString("cnpj"));
                cinema.setValorSemana(resultado.getFloat("valor_semana"));
                cinema.setValorFDS(resultado.getFloat("valor_fimsemana"));
                retorno.add(cinema);
            }
            database.desconnect();
        } catch (SQLException ex) {
            Logger.getLogger(CinemaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Cinema buscarCinema(String nomeCinema) {
        Cinema cinema = new Cinema();
        final String busca = "SELECT id, nome, endereco, cnpj, valor_semana, valor_fimsemana FROM Cinema WHERE nome = "+nomeCinema+"";
        try {
            PreparedStatement buscar = database.connect().prepareStatement(busca);
            ResultSet resultadoBusca = buscar.executeQuery();
            
            if(resultadoBusca.next())
            {
               //TODO  
            }
            
           database.desconnect();
        } catch (SQLException e) {
        }
        return cinema;
    }
    
     public Cinema buscarCinema(int idCinema) {
        Cinema cinema = new Cinema();
        final String busca = "SELECT id, nome, endereco, cnpj, valor_semana, valor_fimsemana FROM Cinema WHERE id = "+idCinema+"";
        try {
            PreparedStatement buscar = database.connect().prepareStatement(busca);
            ResultSet resultadoBusca = buscar.executeQuery();
            
            if(resultadoBusca.next())
            {
                 cinema.setId(resultadoBusca.getInt(1));
                 cinema.setNome(resultadoBusca.getString(2));
                 cinema.setEndereco(resultadoBusca.getString(3));
                 cinema.setCpnj(resultadoBusca.getString(4));
                 cinema.setValorSemana(resultadoBusca.getFloat(5));
                 cinema.setValorFDS(resultadoBusca.getFloat(6));
            }else{
            
            }
            
           database.desconnect();
        } catch (SQLException e) {
        }
        return cinema;
    }


    private Cinema buscaCinema(ResultSet resultadoBusca) throws SQLException, ParseException {
        Cinema cinema = new Cinema();
        cinema.setId(resultadoBusca.getInt(1));
        cinema.setNome(resultadoBusca.getString(2));
        cinema.setEndereco(resultadoBusca.getString(3));
        cinema.setCpnj(resultadoBusca.getString(4));
        cinema.setValorSemana(resultadoBusca.getFloat(5));
        cinema.setValorFDS(resultadoBusca.getFloat(6));
        return cinema;
    }
}
