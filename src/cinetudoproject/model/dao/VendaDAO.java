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

    public void insertVenda(Venda venda) {
        final String inserir = "INSERT INTO venda (horario_id, cinema_id, valor_total, ingresso_id) values(?,?,?,?)";
        try {
            //get the connection

            Connection conn = database.connect();
            PreparedStatement salvar = conn.prepareStatement(inserir);
            salvar.setInt(1, venda.getHorario_id());
            salvar.setInt(2, venda.getCinema_id());
            salvar.setFloat(3, venda.getValor_total());
            salvar.setInt(4, venda.getIngresso_id());
            //salvar.setInt(4, venda.getIngressos());
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

    public Venda buscaVendaPorHora(String horario) {
        Venda venda = null;
        final String busca = "SELECT id, horario_id, cinema_id, valor_total, ingresso_id FROM horario WHERE hora = ?";
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
        venda.setHorario_id(resultadoBusca.getInt(2));
        venda.setCinema_id(resultadoBusca.getInt(3));
        venda.setValor_total(resultadoBusca.getFloat(4));
        venda.setIngresso_id(resultadoBusca.getInt(5));

        return venda;
    }

}
