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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Wellington
 */
public class HorarioDAO {
    Connection connection;
    DatabaseMySQL database;

    public HorarioDAO() {
        database = new DatabaseMySQL();
    }
    
    public void insertHorario(Horario horario) {
        final String inserir = "INSERT INTO horario (hora) values(?)";
        try {
            //get the connection
            Connection conn = database.connect();
            PreparedStatement salvar = conn.prepareStatement(inserir);
            salvar.setString(1, horario.getHorario());
            salvar.executeUpdate();
            salvar.close();
            conn.close();
            //JOptionPane.showMessageDialog(null, "Cadastrado com Sucesso");
            //return true;
        } catch (SQLException ex) {
            Logger.getLogger("Error on: " + FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            //JOptionPane.showMessageDialog(null, "Erro no cadastro" + "\n" + ex.getMessage());
            //return false;
        }
    }

    public Horario buscaPorHora(String horario) {
        Horario hora = null;
        final String busca = "SELECT id, hora FROM horario WHERE hora = ?";
        try {
            //DBConect db = new DBConect();
            Connection conn = database.connect();
            PreparedStatement buscar = conn.prepareStatement(busca);
            buscar.setString(1, horario);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            hora = buscaHorario(resultadoBusca);
            buscar.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERRO AO BUSCAR CONTA COM USUARIO "+ hora);
            //System.exit(0);
        }
        return hora;
    }
    
    public Horario buscaPorId(int id) {
        Horario hora = null;
        final String busca = "SELECT id, hora FROM horario WHERE id = ?";
        try {
            //DBConect db = new DBConect();
            Connection conn = database.connect();
            PreparedStatement buscar = conn.prepareStatement(busca);
            buscar.setInt(1, id);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            hora = buscaHorario(resultadoBusca);
            buscar.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
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
