/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.model.dao;

import cinetudoproject.model.database.DatabaseMySQL;
import cinetudoproject.model.domain.Filme;
import cinetudoproject.model.domain.Genero;
import cinetudoproject.model.domain.Promocao;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        final String inserir = "INSERT INTO promocao (nome,data,desconto,dia_semana,descricao,cinema_id) values(?,?,?,?,?,?)";
        try {
            //get the connection

            Connection conn = database.connect();
            PreparedStatement salvar = conn.prepareStatement(inserir);
            salvar.setString(1, promocao.getNome());
            salvar.setString(2, promocao.getData());
            salvar.setFloat(3, promocao.getDesconto());
            salvar.setInt(4, promocao.getDiaDaSemana());
            salvar.setString(5, promocao.getDescricao());
            salvar.setFloat(6, promocao.getCinema_id());
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
    public void insertPromocao(Promocao promocao, File imageFile) throws FileNotFoundException {
        final String inserir = "INSERT INTO promocao (nome,data,desconto,dia_semana,descricao,cinema_id,image) values(?,?,?,?,?,?,?)";
        try {
            //get the connection
            FileInputStream fis = new FileInputStream(imageFile);
            Connection conn = database.connect();
            PreparedStatement salvar = conn.prepareStatement(inserir);
            salvar.setString(1, promocao.getNome());
            salvar.setString(2, promocao.getData());
            salvar.setFloat(3, promocao.getDesconto());
            salvar.setInt(4, promocao.getDiaDaSemana());
            salvar.setString(5, promocao.getDescricao());
            salvar.setFloat(6, promocao.getCinema_id());
            salvar.setBinaryStream(7, fis, (int) imageFile.length());
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

    public ArrayList<Promocao> listar() throws FileNotFoundException, IOException {
        final String sql = "SELECT * FROM promocao";
        ArrayList<Promocao> retorno = new ArrayList<>();
        
        try {
            connection = database.connect();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Promocao promocao = new Promocao();
                promocao.setNome(resultado.getString("nome"));
                
                File imageFile = new File("src/img/" + promocao.getNome()+".png");
                FileOutputStream fos = new FileOutputStream(imageFile);
                Blob blob = resultado.getBlob("image");
                if (blob != null) {
                    byte b[] = blob.getBytes(1,(int)blob.length());
                    fos.write(b);
                    fos.close();
                    promocao.setImageFile(imageFile);
                }
                
                promocao.setId(resultado.getInt("id"));
                promocao.setCinema_id(resultado.getInt("cinema_id"));
                promocao.setData(resultado.getString("data"));
                promocao.setDescricao(resultado.getString("descricao"));
                promocao.setDesconto(resultado.getFloat("desconto"));
                promocao.setDiaDaSemana(resultado.getInt("dia_semana"));
                retorno.add(promocao);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PromocaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public Promocao buscaPromocao(String nome) {
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
