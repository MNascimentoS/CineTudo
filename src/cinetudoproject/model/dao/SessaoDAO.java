/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.model.dao;

import cinetudoproject.model.database.DatabaseMySQL;
import cinetudoproject.model.domain.Filme;
import cinetudoproject.model.domain.Genero;
import cinetudoproject.model.domain.Sala;
import cinetudoproject.model.domain.Sessao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
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

    public void insertSessao(Sessao sessao) throws ParseException {
        final String inserir = "INSERT INTO sessao (sala_id,filme_id,horario_id,ingresso_disponivel,data_inicio,data_final,assentos) values(?,?,?,?,?,?,?)";
        
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
               salvar.executeUpdate();
               database.desconnect();
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setHeaderText(null);
               alert.setTitle("Sucesso");
               alert.setContentText("Cadastrado com sucesso!");
               alert.showAndWait();
           
            //return true;
            } catch (SQLException ex) {
            //Logger.getLogger("Error on: " + FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Erro");
                alert.setContentText("Erro: "+ex.getMessage());
                alert.showAndWait();
                //return false;
            }
        
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
    
     public ArrayList<Sessao> listar() throws ParseException {
        final String sql = "SELECT id,sala_id,filme_id,horario_id,ingresso_disponivel,data_inicio,data_final,assentos FROM sessao";
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
    //listagem para verificacao de validade para cadastro
    public ArrayList<Sessao>listarAux()
    {
        final String busca = "SELECT sala_id, horario_id, data_inicio, data_final FROM sessao";
         ArrayList<Sessao> sessoes = new ArrayList<>();
         
         try {
            PreparedStatement stmt = database.connect().prepareStatement(busca);
            ResultSet resultado = stmt.executeQuery();
      
            while(resultado.next()) {
                Sessao se = new Sessao();
                se.setSala_id(resultado.getInt(1));
                se.setHorario_id(resultado.getInt(2));
                se.setDataInicio(new Date(resultado.getDate(3).getTime()));
                se.setDataFinal(new Date(resultado.getDate(4).getTime()));
                sessoes.add(se);
            }
            
            database.desconnect();
            
        } catch (SQLException ex) {
            //Logger.getLogger(GeneroDAO.class.getName()).log(Level.SEVERE, null, ex);
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setTitle("Erro");
             alert.setContentText("Erro: "+ex.getMessage());
             alert.showAndWait();
        }
         
         return sessoes;
    }
     
    //verifica se a sessao pode ser cadastrada
    public boolean eValida(Sessao s) throws ParseException
    {
        ArrayList<Sessao> sessoes = listarAux();
    
        for(Sessao i : sessoes)
        {   
            if(s.getDataInicio().getYear() >= i.getDataInicio().getYear()){
               if(s.getDataInicio().getMonth() >= i.getDataInicio().getMonth()){
                     if(s.getDataInicio().getDate()>= i.getDataInicio().getDate()){
                         if(s.getDataFinal().getYear() <= i.getDataFinal().getYear()){
                            if(s.getDataFinal().getMonth() <= i.getDataFinal().getMonth()){
                                if(s.getDataFinal().getDate()<= i.getDataFinal().getDate()){
                                    //compara se o horario e igual e se a sala tbm ai n podera cadastrar
                                    if(s.getSala_id() == i.getSala_id() && s.getHorario_id() == i.getHorario_id())
                                    {
                                         Alert alert = new Alert(Alert.AlertType.WARNING);
                                         alert.setHeaderText(null);
                                         alert.setTitle("Alerta");
                                         alert.setContentText("Ja existe uma sessao cadastrada neste horario para a sala escolhida!");
                                         alert.showAndWait();
                                         return false;
                                    }    
                                }
                            }
                         }
                     }
               }
            }
        }
        
        return true;
    }
    
    public Sessao buscaSessaoPorFilme(String filme) {
        Sessao sessao = null;
        final String busca = "SELECT id,sala_id,filme_id,horario_id,ingresso_disponivel,data_inicio,data_final,assentos FROM sessao WHERE hora = ?";
        try {
            //DBConect db = new DBConect();
            PreparedStatement buscar =  database.connect().prepareStatement(busca);
            buscar.setString(1, filme);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            sessao = buscaSessao(resultadoBusca);
            database.desconnect();
        } catch (Exception e) {
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
        sessao.setDataInicio(new Date(resultadoBusca.getDate(5).getTime()));
        sessao.setDataFinal(new Date(resultadoBusca.getDate(6).getTime()));
        sessao.setAssento(resultadoBusca.getInt(7));
        return sessao;
    }

}
