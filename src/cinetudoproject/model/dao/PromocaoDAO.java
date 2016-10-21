/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.model.dao;

import cinetudoproject.model.database.DatabaseMySQL;
import cinetudoproject.model.domain.Promocao;
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
public class PromocaoDAO {

    Connection connection;
    DatabaseMySQL database;

    public PromocaoDAO() {
        database = new DatabaseMySQL();
    }

    public void insertPromocao(Promocao promocao) {
        final String inserir = "INSERT INTO promocao (nome,data,descricao,cinema_id,desconto) values(?,?,?,?,?)";
        try {
            //get the connection

            Connection conn = database.connect();
            PreparedStatement salvar = conn.prepareStatement(inserir);
            salvar.setString(1, promocao.getNome());
            salvar.setString(2, promocao.getData());
            salvar.setFloat(3, promocao.getDesconto());
            salvar.setString(4, promocao.getDescricao());
            salvar.setFloat(5, promocao.getCinema_id());
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

    public Promocao buscaSessaoPorHora(String nome) {
        Promocao promocao = null;
        final String busca = "SELECT id, nome, data, descricao, cinema_id, desconto FROM promocao WHERE nome = ?";
        try {
            //DBConect db = new DBConect();
            Connection conn = database.connect();
            PreparedStatement buscar = conn.prepareStatement(busca);
            buscar.setString(1, nome);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            promocao = buscaPromocao(resultadoBusca);
            buscar.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERRO AO BUSCAR CONTA COM NOME: " + nome);
            //System.exit(0);
        }
        return promocao;
    }

    private Promocao buscaPromocao(ResultSet resultadoBusca) throws SQLException, ParseException {
        Promocao promocao = new Promocao();
        promocao.setId(resultadoBusca.getInt(1));
        promocao.setNome(resultadoBusca.getString(2));
        promocao.setData(resultadoBusca.getString(3));
        promocao.setDescricao(resultadoBusca.getString(4));
        promocao.setCinema_id(resultadoBusca.getInt(5));
        promocao.setDesconto(resultadoBusca.getFloat(6));

        return promocao;
    }
}
