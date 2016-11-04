/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.model.dao;

import cinetudoproject.model.database.DatabaseMySQL;
import cinetudoproject.model.domain.Assento;
import cinetudoproject.model.domain.Ingresso;
import cinetudoproject.model.domain.Venda;
import java.io.IOException;
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
public class VendaDAO {

    private final DatabaseMySQL database;

    public VendaDAO() {
        database = new DatabaseMySQL();
    }

    public void insert(Venda venda) {
        final String inserir = "INSERT INTO Venda (data, horario, cinema_id, valor_total) values(?,?,?,?)";
        try {
            try (PreparedStatement salvar = database.connect().prepareStatement(inserir)) {
                salvar.setString(1, venda.getData());
                salvar.setString(2, venda.getHorario());
                salvar.setInt(3, venda.getCinema_id());
                salvar.setFloat(4, venda.getValor_total());
                salvar.executeUpdate();
                database.desconnect();
            }
           
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Sucesso");
            alert.setContentText("Compra das " + venda.getHorario() + " horas realizada!");
            alert.showAndWait();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Erro");
            alert.setContentText("Erro na compra das " + venda.getHorario() + " horas!");
            alert.showAndWait();
        }
    }

    public ArrayList<Venda> listar() throws IOException, ParseException {
        final String sql = "SELECT * FROM Venda";
        ArrayList<Venda> retorno = new ArrayList();
        
        try {
            PreparedStatement stmt = database.connect().prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Venda venda;
                venda = buscarVenda(resultado);
                retorno.add(venda);
            }
            database.desconnect();
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public Venda buscar(Venda searchVenda) throws IOException, ParseException {
        final String sql = "SELECT * FROM Venda where horario = ? and data = ? and cinema_id = ? and valor_total = ?";
        Venda venda = new Venda();
        
        try {
            PreparedStatement buscar = database.connect().prepareStatement(sql);
            buscar.setString(1, searchVenda.getHorario());
            buscar.setString(2, searchVenda.getData());
            buscar.setInt(3, searchVenda.getCinema_id());
            buscar.setFloat(4, searchVenda.getValor_total());
            ResultSet resultado = buscar.executeQuery();
            resultado.next();
            venda = buscarVenda(resultado);
            database.desconnect();
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return venda;
    }
    
    
    public void update(Venda venda) {
        final String update = "update Venda set valor_total = ? where id = ?";
        try {
            //get the connection
            PreparedStatement salvar = database.connect().prepareStatement(update);
            salvar.setFloat(1, venda.getValor_total());
            salvar.setInt(2, venda.getId());
            salvar.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Sucesso");
            alert.setContentText("Ingresso cancelado com sucesso!");
            alert.showAndWait();
            database.desconnect();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Erro");
            alert.setContentText("Erro na alteração!");
            alert.showAndWait();
            //return false;
        }
    }
    
    
    public Venda buscarVendaId(int vendaId) throws IOException, ParseException {
        
        final String sql = "SELECT * FROM Venda where id = ?";
        Venda venda = new Venda();
        
        try {
            PreparedStatement buscar = database.connect().prepareStatement(sql);
            buscar.setInt(1, vendaId);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            venda = buscarVenda(resultadoBusca);
            buscar.close();
            database.desconnect();
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return venda;
    }
    
    

    private Venda buscarVenda(ResultSet resultadoBusca) throws SQLException, ParseException {
        Venda venda = new Venda();
        venda.setId(resultadoBusca.getInt("id"));
        venda.setData(resultadoBusca.getString("data"));
        venda.setHorario(resultadoBusca.getString("horario"));
        venda.setCinema_id(resultadoBusca.getInt("cinema_id"));
        venda.setValor_total(resultadoBusca.getFloat("valor_total"));

        return venda;
    }
    
    
    public void cancelaVenda(int ingressoID) throws IOException, ParseException{
       //declaracoes
        System.out.println("Ingresso: "+ ingressoID);
        VendaDAO vendadao = new VendaDAO();
        Ingresso ingresso = new Ingresso();
        IngressoDAO ingressodao = new IngressoDAO();
        Venda venda  = new Venda();
        //busca o ingresso 
        ingresso = ingressodao.buscarIngressoId(ingressoID);
        
        //caso o ingresso exista
        if(ingresso != null && ingresso.getNumAssento() != null)
        {           
            System.out.println("Ingresso Retornado: "+ ingresso.getId());
            //busca a venda especifica para fazer alteracao do valor
            venda = vendadao.buscarVendaId(ingresso.getVenda_id());
            System.out.println("Id da venda: " + venda.getId());
            //subtraia o valor da venda
            venda.setValor_total(venda.getValor_total() - ingresso.getPreco());
            //deixa o assento disponivel 
            AssentoDAO assentodao = new AssentoDAO();
            ArrayList<Assento> assentos = assentodao.listar(ingresso.getSessao_id(), true);
            //para cada assento verifique se encontrou o assento pertencente a este ingresso
            for (Assento i : assentos) {
                String numeroDoAssento = ingresso.getNumAssento().replaceAll("\\D+", "");
                //se encontrou o assento deste ingresso
                if (Integer.parseInt(numeroDoAssento) == i.getNumero()) {
                    i.setOcupado(0);
                    assentodao.update(i, ingresso.getSessao_id());
                    break;
                }
            }
           //delete o ingresso 
           ingressodao.delete(ingressoID);
           //atualize o valor da venda
           vendadao.update(venda);
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Aviso");
            alert.setContentText("Ingresso não encontrado!");
            alert.showAndWait();
        }
    }
}
