/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.model.dao;

import cinetudoproject.model.database.DatabaseMySQL;
import cinetudoproject.model.domain.Cinema;
import cinetudoproject.model.domain.Filme;
import cinetudoproject.model.domain.Genero;
import java.io.File;
import java.sql.Connection;
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

    Connection connection;
    DatabaseMySQL database;

    public CinemaDAO() {
        database = new DatabaseMySQL();
    }

    public List<Cinema> listar() {
        final String sql = "SELECT * FROM cinema";
        List<Cinema> retorno = new ArrayList<>();
        try {
            connection = database.connect();
            PreparedStatement stmt = connection.prepareStatement(sql);
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
        } catch (SQLException ex) {
            Logger.getLogger(CinemaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Cinema buscarCinema(String nomeCinema) {
        Cinema cinema = new Cinema();
        final String busca = "SELECT id, nome, endereco, cnpj, valor_semana, valor_fimsemana FROM cinema WHERE nome = "+nomeCinema+"";
        try {
            connection = database.connect();
            PreparedStatement buscar = connection.prepareStatement(busca);
            ResultSet resultadoBusca = buscar.executeQuery();
            
            if(resultadoBusca.next())
            {
               //TODO  
            }
            
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cinema;
    }
    
     public Cinema buscarCinema(int idCinema) {
        Cinema cinema = new Cinema();
        final String busca = "SELECT id, nome, endereco, cnpj, valor_semana, valor_fimsemana FROM cinema WHERE id = "+idCinema+"";
        try {
            connection = database.connect();
            PreparedStatement buscar = connection.prepareStatement(busca);
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
            
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
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
