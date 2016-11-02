/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.model.dao;

import cinetudoproject.model.database.DatabaseMySQL;
import cinetudoproject.model.domain.Assento;
import cinetudoproject.model.domain.Filme;
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

    private Connection connection;
    private DatabaseMySQL database;

    public SessaoDAO() {
        database = new DatabaseMySQL();
    }

    public void insertSessao(Sessao sessao) throws ParseException {
        final String inserir = "INSERT INTO sessao (sala_id,filme_id,horario_id,ingresso_disponivel,data,assentos,cinema_id) values(?,?,?,?,?,?,?)";
        
        if(eValida(sessao))
        {
            try {
               PreparedStatement salvar = database.connect().prepareStatement(inserir);
               salvar.setInt(1, sessao.getSala_id());
               salvar.setInt(2, sessao.getFilme_id());
               salvar.setInt(3, sessao.getHorario_id());
               salvar.setInt(4, sessao.getIngresso_disponivel());
               salvar.setDate(5, new java.sql.Date(sessao.getData().getTime()));
               salvar.setInt(6, sessao.getAssento());
               salvar.setInt(7, sessao.getCinema_id());
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
    
    public Sessao buscaSessaoPorHora(int hora_id) {
        Sessao sessao = null;
        final String busca = "SELECT * FROM sessao WHERE hora_id = ?";
        try {
            //DBConect db = new DBConect();
            Connection conn = database.connect();
            PreparedStatement buscar = conn.prepareStatement(busca);
            buscar.setInt(1, hora_id);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            sessao = buscaSessao(resultadoBusca);
            buscar.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERRO AO BUSCAR CONTA COM USUARIO ");
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
    
    
    public ArrayList<Sessao> listarPorSalaEData(int sala_id, java.util.Date data) throws ParseException {
        final String sql = "SELECT * from sessao where sala_id";
        ArrayList<Sessao> retorno = new ArrayList<>();
        
        try {
            PreparedStatement stmt = database.connect().prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            
            while (resultado.next()) {
                Sessao s = buscaSessao(resultado);
                //verificacao de data
                if(s.getData().getDate() == data.getDate() && s.getData().getMonth() == data.getMonth() && s.getData().getYear() == data.getYear())
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
        if (sessoes.isEmpty()) return true;
        //para cada sessao
        for(Sessao i : sessoes)
        {
            //se for no mesmo dia e for na mesma sala e mesmo horario nao permita o cadastro
            if(s.getData().getDate() == i.getData().getDate()&& s.getData().getMonth() == i.getData().getMonth() &&
                    s.getData().getYear() == i.getData().getYear()&& s.getSala_id() == i.getSala_id() && 
                    s.getHorario_id() == i.getHorario_id())
            {
                return false;
            }
        }   
        return true;
    }
    
    public ArrayList<Sessao> buscarSessoesFilmeEData(int filme_id, java.util.Date data) throws ParseException
    {
        ArrayList<Sessao> sessoes = new ArrayList<Sessao>();
        final String busca = "SELECT * FROM sessao WHERE filme_id = '"+filme_id+"'";
         
        try {
            PreparedStatement stmt = database.connect().prepareStatement(busca);
            ResultSet resultado = stmt.executeQuery();    
            if(!resultado.next()){
                System.out.println("Nao achei o filme!");
                return sessoes;
            } 
            while(resultado.next()) {
                //sessoes = new ArrayList<>();
                Sessao s = buscaSessao(resultado);
                //se esta na data buscada adicione a lista de retorno
                if(s != null)
                {
                  if(s.getData().getDate()== data.getDate()&& s.getData().getMonth() == data.getMonth() && 
                     s.getData().getYear() == data.getYear())
                    {
                        sessoes.add(s);
                    }
                }else{
                    System.out.print("NULO!");
                }
            }
            database.desconnect();
        } catch (SQLException ex) {
            Logger.getLogger(GeneroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sessoes;
    }
    
    public Sessao buscaSessaoPorFilme(int filme_id) {
        Sessao sessao = null;
        final String busca = "SELECT * FROM sessao WHERE filme_id = ?";
        try {
            PreparedStatement buscar =  database.connect().prepareStatement(busca);
            buscar.setInt(1, filme_id);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            sessao = buscaSessao(resultadoBusca);
            database.desconnect();
        } catch (Exception e) {
            System.err.println("ERRO AO BUSCAR");
        }
        return sessao;
    }
    
    public Sessao buscarSessaoAtual(int filme_id, int horario_id, int sala_id) throws ParseException
    {
        final String busca = "SELECT * FROM sessao WHERE filme_id = '"+filme_id+"'";
        Sessao s = null;
        
        try {
            PreparedStatement stmt = database.connect().prepareStatement(busca);
            ResultSet resultado = stmt.executeQuery();    
            if(!resultado.next()){
                System.out.println("Nao há sessoes para esta sala!");
                return s;
            } 
            while(resultado.next()) {
                //sessoes = new ArrayList<>();
                Sessao ss = buscaSessao(resultado);
                //se esta na data buscada adicione a lista de retorno
                if(ss != null)
                {
                   if(ss.getHorario_id() == horario_id && ss.getSala_id() == sala_id)
                   {
                       return ss;
                   }
                  //se a sessao for valida retorne-a  
                  /*if(ss.getData().getDate()== data.getDate()&& ss.getData().getMonth() == data.getMonth() && 
                     ss.getData().getYear() == data.getYear() && ss.getHorario_id() == horario_id && ss.getSala_id() == sala_id)
                    {
                        s = new Sessao();
                        s = ss;
                        return s;
                    }*/
                }else{
                    System.out.print("NULO!");
                }
            }
            database.desconnect();
        } catch (SQLException ex) {
            Logger.getLogger(GeneroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return s;
    }
    
    private Sessao buscaSessao(ResultSet resultadoBusca) throws SQLException, ParseException {
        
        Sessao sessao = new Sessao();
        
        sessao.setId(resultadoBusca.getInt("id"));
        sessao.setAssento(resultadoBusca.getInt("assentos"));
        sessao.setIngresso_disponivel(resultadoBusca.getInt("ingresso_disponivel"));
        sessao.setFilme_id(resultadoBusca.getInt("filme_id"));
        sessao.setHorario_id(resultadoBusca.getInt("horario_id"));
        sessao.setSala_id(resultadoBusca.getInt("sala_id"));
        sessao.setData(new Date(resultadoBusca.getDate("data").getTime()));
        sessao.setCinema_id(resultadoBusca.getInt("cinema_id"));
        
        return sessao;
    }
}
