/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.model.dao;

import cinetudoproject.model.database.DatabaseMySQL;
import cinetudoproject.model.domain.Venda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
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
        final String inserir = "INSERT INTO venda (data, horario, cinema_id, ingresso_id, valor_total) values(?,?,?,?,?)";
        try {
            //get the connection
            connection = database.connect();
            try (PreparedStatement salvar = connection.prepareStatement(inserir)) {
                salvar.setString(1, venda.getData());
                salvar.setString(2, venda.getHorario());
                salvar.setInt(3, venda.getCinema_id());
                salvar.setInt(4, venda.getIngresso_id());
                salvar.setFloat(5, venda.getValor_total());
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

    public Venda buscaVendaPorHora(String horario) {
        Venda venda = null;
        final String busca = "SELECT id, horario_id, cinema_id, ingresso_id FROM horario WHERE hora = ?";
        try {
            //DBConect db = new DBConect();
            Connection conn = database.connect();
            PreparedStatement buscar = conn.prepareStatement(busca);
            buscar.setString(1, horario);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            venda = buscaHorario(resultadoBusca);
            buscar.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERRO AO BUSCAR VENDA: " + venda);
            //System.exit(0);
        }
        return venda;
    }

    private Venda buscaHorario(ResultSet resultadoBusca) throws SQLException, ParseException {
        Venda venda = new Venda();
        venda.setId(resultadoBusca.getInt(1));
        venda.setHorario(resultadoBusca.getString(2));
        venda.setCinema_id(resultadoBusca.getInt(3));
        venda.setValor_total(resultadoBusca.getFloat(4));
        venda.setIngresso_id(resultadoBusca.getInt(5));

        return venda;
    }

}
