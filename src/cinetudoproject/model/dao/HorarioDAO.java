/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.model.dao;

import cinetudoproject.model.database.DatabaseMySQL;
import cinetudoproject.model.domain.Horario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

/**
 *
 * @author Wellington
 */
public class HorarioDAO {
    
    
    private final DatabaseMySQL database;

    public HorarioDAO() {
        database = new DatabaseMySQL();
    }
    
    public void insertHorario(Horario horario) {
        final String inserir = "INSERT INTO Horario (hora) values(?)";
        try {
            PreparedStatement salvar = database.connect().prepareStatement(inserir);
            salvar.setString(1, horario.getHorario());
            salvar.executeUpdate();
            salvar.close();
            database.desconnect();
         
        } catch (SQLException ex) {
        }
    }

    public Horario buscaPorHora(String horario) {
        Horario hora = null;
        final String busca = "SELECT id, hora FROM Horario WHERE hora = ?";
        try {
            Connection conn = database.connect();
            PreparedStatement buscar = conn.prepareStatement(busca);
            buscar.setString(1, horario);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            hora = buscaHorario(resultadoBusca);
            database.desconnect();
        } catch (SQLException | ParseException e) {
            System.err.println("ERRO AO BUSCAR CONTA COM USUARIO "+ hora);
            //System.exit(0);
        }
        return hora;
    }
    
    public Horario buscaPorId(int id) {
        Horario hora = null;
        final String busca = "SELECT id, hora FROM Horario WHERE id = ?";
        try {
            PreparedStatement buscar = database.connect().prepareStatement(busca);
            buscar.setInt(1, id);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            hora = buscaHorario(resultadoBusca);
            buscar.close();
            database.desconnect();
        } catch (SQLException | ParseException e) {
            System.err.println("ERRO AO BUSCAR CONTA COM USUARIO "+ hora);
            //System.exit(0);
        }
        return hora;
    }
    
    private Horario buscaHorario(ResultSet resultadoBusca) throws SQLException, ParseException {
        Horario horario = new Horario();
        horario.setId(resultadoBusca.getInt(1));
        horario.setHorario(resultadoBusca.getString(2));
        return horario;
    }

}
