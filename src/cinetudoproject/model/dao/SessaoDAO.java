/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.model.dao;

import cinetudoproject.model.database.DatabaseMySQL;
import cinetudoproject.model.domain.Sessao;
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
public class SessaoDAO {

    Connection connection;
    DatabaseMySQL database;

    public SessaoDAO() {
        database = new DatabaseMySQL();
    }

    public void insertSessao(Sessao sessao) {
        final String inserir = "INSERT INTO sessao (sala_id,filme_id,horario_id,ingresso_disponivel,data_inicio,data_final,assentos) values(?,?,?,?,?,?,?)";
        try {
            //get the connection

            Connection conn = database.connect();
            PreparedStatement salvar = conn.prepareStatement(inserir);
            salvar.setInt(1, sessao.getSala_id());
            salvar.setInt(2, sessao.getFilme_id());
            salvar.setInt(3, sessao.getHorario_id());
            salvar.setInt(4, sessao.getIngresso_disponivel());
            salvar.setString(5, sessao.getDataInicio());
            salvar.setString(6, sessao.getDataFinal());
            salvar.setInt(7, sessao.getAssento());
            salvar.executeUpdate();
            salvar.close();
            conn.close();
            JOptionPane.showMessageDialog(null, "Cadastrado com Sucesso");
            //return true;
        } catch (SQLException ex) {
            //Logger.getLogger("Error on: " + FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro no cadastro" + "\n" + ex.getMessage());
            //return false;
        }
    }
    
    public Sessao buscaSessaoPorHora(String hora) {
        Sessao sessao = null;
        final String busca = "SELECT id,sala_id,filme_id,horario_id,ingresso_disponivel,data_inicio,data_final,assentos FROM sessao WHERE hora = ?";
        try {
            //DBConect db = new DBConect();
            Connection conn = database.connect();
            PreparedStatement buscar = conn.prepareStatement(busca);
            buscar.setString(1, hora);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            sessao = buscaSessao(resultadoBusca);
            buscar.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERRO AO BUSCAR CONTA COM USUARIO "+ hora);
            //System.exit(0);
        }
        return sessao;
    }
    
    public Sessao buscaSessaoPorFilme(String filme) {
        Sessao sessao = null;
        final String busca = "SELECT id,sala_id,filme_id,horario_id,ingresso_disponivel,data_inicio,data_final,assentos FROM sessao WHERE hora = ?";
        try {
            //DBConect db = new DBConect();
            Connection conn = database.connect();
            PreparedStatement buscar = conn.prepareStatement(busca);
            buscar.setString(1, filme);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            sessao = buscaSessao(resultadoBusca);
            buscar.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERRO AO BUSCAR CONTA COM USUARIO "+ filme);
            //System.exit(0);
        }
        return sessao;
    }
    
    private Sessao buscaSessao(ResultSet resultadoBusca) throws SQLException, ParseException {
        Sessao sessao = new Sessao();
        sessao.setId(resultadoBusca.getInt(1));
        sessao.setSala_id(resultadoBusca.getInt(2));
        sessao.setFilme_id(resultadoBusca.getInt(3));
        sessao.setHorario_id(resultadoBusca.getInt(4));
        sessao.setIngresso_disponivel(resultadoBusca.getInt(5));
        sessao.setDataInicio(resultadoBusca.getString(5));
        sessao.setDataFinal(resultadoBusca.getString(6));
        sessao.setAssento(resultadoBusca.getInt(7));
        return sessao;
    }

}
