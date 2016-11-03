/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.model.dao;

import cinetudoproject.model.database.DatabaseMySQL;
import cinetudoproject.model.domain.Ingresso;
import cinetudoproject.model.domain.Venda;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javax.swing.JOptionPane;

/**
 *
 * @author Wellington
 */
public class VendaDAO {

    Connection connection;
    DatabaseMySQL database;

    public VendaDAO() {
        database = new DatabaseMySQL();
    }

    public void insert(Venda venda) {
        final String inserir = "INSERT INTO venda (data, horario, cinema_id, valor_total) values(?,?,?,?)";
        try {
            //get the connection
            connection = database.connect();
            try (PreparedStatement salvar = connection.prepareStatement(inserir)) {
                salvar.setString(1, venda.getData());
                salvar.setString(2, venda.getHorario());
                salvar.setInt(3, venda.getCinema_id());
                salvar.setFloat(4, venda.getValor_total());
                salvar.executeUpdate();
            }
            connection.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Sucesso");
            alert.setContentText("Compra das " + venda.getHorario() + " horas realizada!");
            alert.showAndWait();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro no cadastro" + "\n" + ex.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Erro");
            alert.setContentText("Erro na compra das " + venda.getHorario() + " horas!");
            alert.showAndWait();
        }
    }

    public ArrayList<Venda> listar() throws IOException, ParseException {
        final String sql = "SELECT * FROM ingresso";
        ArrayList<Venda> retorno = new ArrayList();
        
        try {
            connection = database.connect();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Venda venda;
                venda = buscarVenda(resultado);
                retorno.add(venda);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public Venda buscar(Venda searchVenda) throws IOException, ParseException {
        final String sql = "SELECT * FROM venda where horario = ? and data = ? and cinema_id = ? and valor_total = ?";
        Venda venda = new Venda();
        
        try {
            connection = database.connect();
            PreparedStatement buscar = connection.prepareStatement(sql);
            buscar.setString(1, searchVenda.getHorario());
            buscar.setString(2, searchVenda.getData());
            buscar.setInt(3, searchVenda.getCinema_id());
            buscar.setFloat(4, searchVenda.getValor_total());
            ResultSet resultado = buscar.executeQuery();
            resultado.next();
            venda = buscarVenda(resultado);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
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

}
