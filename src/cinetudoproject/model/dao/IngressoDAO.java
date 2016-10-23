/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.model.dao;

import cinetudoproject.model.database.DatabaseMySQL;
import cinetudoproject.model.domain.Ingresso;
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
public class IngressoDAO {

    Connection connection;
    DatabaseMySQL database;

    public IngressoDAO() {
        database = new DatabaseMySQL();
    }

    public void insertHorario(Ingresso ingresso) {
        final String inserir = "INSERT INTO ingresso (preco, tipo, venda_id, sessao_id) values(?,?,?,?)";
        try {
            //get the connection

            Connection conn = database.connect();
            PreparedStatement salvar = conn.prepareStatement(inserir);
            salvar.setFloat(1, ingresso.getPreco());
            salvar.setInt(2, ingresso.getTipo());
            salvar.setInt(3, ingresso.getVenda_id());
            salvar.setInt(4, ingresso.getSessao_id());

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

    public Ingresso buscaIngressoPorSessao(int sessao) {
        Ingresso ingresso = null;
        final String busca = "SELECT id, preco, tipo, sessao_id, venda_id FROM ingresso WHERE sessao_id = ?";
        try {
            //DBConect db = new DBConect();
            Connection conn = database.connect();
            PreparedStatement buscar = conn.prepareStatement(busca);
            buscar.setInt(1, sessao);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            ingresso = buscaIngresso(resultadoBusca);
            buscar.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERRO AO BUSCAR A SESSAO: " + sessao);
            //System.exit(0);
        }
        return ingresso;
    }

    private Ingresso buscaIngresso(ResultSet resultadoBusca) throws SQLException, ParseException {
        Ingresso ingresso = new Ingresso();
        ingresso.setId(resultadoBusca.getInt(1));
        ingresso.setPreco(resultadoBusca.getFloat(2));
        ingresso.setTipo(resultadoBusca.getInt(3));
        ingresso.setSessao_id(resultadoBusca.getInt(4));
        ingresso.setVenda_id(resultadoBusca.getInt(5));
        return ingresso;
    }
}
