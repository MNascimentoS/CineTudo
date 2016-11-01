/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.model.dao;

import cinetudoproject.model.database.DatabaseMySQL;
import cinetudoproject.model.domain.Sessao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

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

    public void insertSessao(Sessao sessao) throws ParseException {
        final String inserir = "INSERT INTO sessao (sala_id,filme_id,horario_id,ingresso_disponivel,data_inicio,data_final,assentos, cinema_id) values(?,?,?,?,?,?,?,?)";
        
        if(eValida(sessao))
        {
            try {
               PreparedStatement salvar = database.connect().prepareStatement(inserir);
               salvar.setInt(1, sessao.getSala_id());
               salvar.setInt(2, sessao.getFilme_id());
               salvar.setInt(3, sessao.getHorario_id());
               salvar.setInt(4, sessao.getIngresso_disponivel());
               salvar.setDate(5, new java.sql.Date(sessao.getDataInicio().getTime()));
               salvar.setDate(6, new java.sql.Date(sessao.getDataFinal().getTime()));
               salvar.setInt(7, sessao.getAssento());
               salvar.setInt(8, sessao.getCinema_id());
               salvar.executeUpdate();
               database.desconnect();
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setHeaderText(null);
               alert.setTitle("Sucesso");
               alert.setContentText("Cadastrado com sucesso!");
               alert.showAndWait();
           
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Erro");
                alert.setContentText("Erro: "+ex.getMessage());
                alert.showAndWait();
            }
        
        }else{
             Alert alert = new Alert(Alert.AlertType.WARNING);
             alert.setHeaderText(null);
             alert.setTitle("Cuidado");
             alert.setContentText("Ja existe uma sessao para este horario nesta sala!");
             alert.showAndWait();
        }
    }
    
    public Sessao buscaSessaoPorHora(String hora) {
        Sessao sessao = null;
        final String busca = "SELECT id,sala_id,filme_id,horario_id,ingresso_disponivel,data_inicio,data_final,assentos,cinema_id FROM sessao WHERE hora = ?";
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
    
     public ArrayList<Sessao> listar() throws ParseException {
        final String sql = "SELECT * from sessao";
        ArrayList<Sessao> retorno = new ArrayList<>();
        
        try {
            PreparedStatement stmt = database.connect().prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            
            while (resultado.next()) {
                Sessao s = buscaSessao(resultado);
                retorno.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GeneroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
 
    //verifica se a sessao pode ser cadastrada
    public boolean eValida(Sessao s) throws ParseException
    {
        ArrayList<Sessao> sessoes = listar();
        //para cada sessao
        for(Sessao i : sessoes)
        {
            //se estiver no intervalo
            if(s.getDataInicio().compareTo(i.getDataInicio()) >= 0 && s.getDataFinal().compareTo(i.getDataFinal()) <= 0)
            {
                    //se for na mesma sala e mesmo horario nao permita o cadastro
                    if(s.getSala_id() == i.getSala_id() && s.getHorario_id() == i.getHorario_id())
                    {
                        return false;
                    }
            }
        }
        
        return true;
    }
    
    public Sessao buscaSessaoPorFilme(String filme) {
        Sessao sessao = null;
        final String busca = "SELECT id,sala_id,filme_id,horario_id,ingresso_disponivel,data_inicio,data_final,assentos,cinema_id FROM sessao WHERE hora = ?";
        try {
            PreparedStatement buscar =  database.connect().prepareStatement(busca);
            buscar.setString(1, filme);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            sessao = buscaSessao(resultadoBusca);
            database.desconnect();
        } catch (Exception e) {
            System.err.println("ERRO AO BUSCAR CONTA COM USUARIO "+ filme);
        }
        return sessao;
    }
    
    private Sessao buscaSessao(ResultSet resultadoBusca) throws SQLException, ParseException {
        
        Sessao sessao = new Sessao();
        
        sessao.setId(resultadoBusca.getInt(1));
        sessao.setAssento(resultadoBusca.getInt(2));
        sessao.setIngresso_disponivel(resultadoBusca.getInt(3));
        sessao.setFilme_id(resultadoBusca.getInt(4));
        sessao.setHorario_id(resultadoBusca.getInt(5));
        sessao.setSala_id(resultadoBusca.getInt(6));
        sessao.setDataFinal(new Date(resultadoBusca.getDate(7).getTime()));
        sessao.setDataInicio(new Date(resultadoBusca.getDate(8).getTime()));
        sessao.setCinema_id(resultadoBusca.getInt(9));
        
        return sessao;
    }
}
