/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.model.dao;

import cinetudoproject.model.database.DatabaseMySQL;
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
import javafx.scene.control.Alert;


/**
 *
 * @author Wellington
 */
public class PromocaoDAO {

    private final DatabaseMySQL database;

    public PromocaoDAO() {
        database = new DatabaseMySQL();
    }

    public void insertPromocao(Promocao promocao) {
        final String inserir = "INSERT INTO Promocao (nome,data,desconto,dia_semana,descricao,cinema_id) values(?,?,?,?,?,?)";
        try {
            //get the connection
            PreparedStatement salvar = database.connect().prepareStatement(inserir);
            salvar.setString(1, promocao.getNome());
            salvar.setString(2, promocao.getData());
            salvar.setFloat(3, promocao.getDesconto());
            salvar.setInt(4, promocao.getDiaDaSemana());
            salvar.setString(5, promocao.getDescricao());
            salvar.setInt(6, promocao.getCinema_id());
            salvar.executeUpdate();
            salvar.close();
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
            alert.setContentText("Erro no cadastro!");
            alert.showAndWait();
            //return false;
        }
    }

    public void insertPromocao(Promocao promocao, File imageFile) throws FileNotFoundException {
        final String inserir = "INSERT INTO Promocao (nome,data,desconto,dia_semana,descricao,cinema_id,image) values(?,?,?,?,?,?,?)";
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
            salvar.setInt(6, promocao.getCinema_id());
            salvar.setBinaryStream(7, fis, (int) imageFile.length());
            salvar.executeUpdate();
            salvar.close();
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
            alert.setContentText("Erro no cadastro!");
            alert.showAndWait();
            //return false;
        }
    }

    public ArrayList<Promocao> listar() throws FileNotFoundException, IOException {
        final String sql = "SELECT * FROM Promocao";
        ArrayList<Promocao> retorno = new ArrayList<>();

        try {
            PreparedStatement stmt = database.connect().prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Promocao promocao = new Promocao();
                promocao.setNome(resultado.getString("nome"));

                File imageFile = new File("src/img/" + promocao.getNome() + ".png");
                FileOutputStream fos = new FileOutputStream(imageFile);
                Blob blob = resultado.getBlob("image");
                if (blob != null) {
                    byte b[] = blob.getBytes(1, (int) blob.length());
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
            database.desconnect();
        } catch (SQLException ex) {
            Logger.getLogger(PromocaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Promocao buscaPromocao(String nome) {
        Promocao promocao = null;
        final String busca = "SELECT id, nome, data, descricao, cinema_id, desconto, image FROM Promocao WHERE nome = ?";
        try {
            PreparedStatement buscar = database.connect().prepareStatement(busca);
            buscar.setString(1, nome);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            promocao = buscaPromocao(resultadoBusca);
            buscar.close();
            database.desconnect();
        } catch (IOException | SQLException | ParseException e) {
            System.err.println("ERRO AO BUSCAR CONTA COM NOME: " + nome);
            //System.exit(0);
        }
        return promocao;
    }

    private Promocao buscaPromocao(ResultSet resultadoBusca) throws SQLException, ParseException, IOException, FileNotFoundException {
        Promocao promocao = new Promocao();
        promocao.setId(resultadoBusca.getInt(1));
        promocao.setNome(resultadoBusca.getString(2));
        
        File imageFile = new File("src/img/" + promocao.getNome() + ".png");
        FileOutputStream fos = new FileOutputStream(imageFile);
        Blob blob = resultadoBusca.getBlob(7);
        if (blob != null) {
            byte b[] = blob.getBytes(1, (int) blob.length());
            fos.write(b);
            fos.close();
            promocao.setImageFile(imageFile);
        }
        
        promocao.setData(resultadoBusca.getString(3));
        promocao.setDescricao(resultadoBusca.getString(4));
        promocao.setCinema_id(resultadoBusca.getInt(5));
        promocao.setDesconto(resultadoBusca.getFloat(6));
        

        return promocao;
    }

    public void update(Promocao promocao, File imageFile) throws FileNotFoundException {
        final String update = "update Promocao set nome = ?,data = ?,desconto = ?,dia_semana = ?,descricao = ?,cinema_id = ?,image = ? where id = ?";
        FileInputStream fis = new FileInputStream(imageFile);
        try {
            PreparedStatement atualizar = database.connect().prepareStatement(update);
            atualizar.setString(1, promocao.getNome());
            atualizar.setString(2, promocao.getData());
            atualizar.setFloat(3, promocao.getDesconto());
            atualizar.setInt(4, promocao.getDiaDaSemana());
            atualizar.setString(5, promocao.getDescricao());
            atualizar.setInt(6, promocao.getCinema_id());
            atualizar.setBinaryStream(7, fis, (int) imageFile.length());
            atualizar.setInt(8, promocao.getId());
            atualizar.executeUpdate();
            atualizar.close();
            database.desconnect();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Sucesso");
            alert.setContentText("Atualizado com sucesso!");
            alert.showAndWait();
        } catch (SQLException ex) {
            //Logger.getLogger("Error on: " + FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Erro");
            alert.setContentText("Erro na atualização!");
            alert.showAndWait();
            //return false;
        }
    }

    public void update(Promocao promocao) throws FileNotFoundException {
        final String update = "update Promocao set nome = ?,data = ?,desconto = ?,dia_semana = ?,descricao = ?,cinema_id = ? where id = ?";
        
        try {

            Connection conn = database.connect();
            PreparedStatement atualizar = conn.prepareStatement(update);
            atualizar.setString(1, promocao.getNome());
            atualizar.setString(2, promocao.getData());
            atualizar.setFloat(3, promocao.getDesconto());
            atualizar.setInt(4, promocao.getDiaDaSemana());
            atualizar.setString(5, promocao.getDescricao());
            atualizar.setInt(6, promocao.getCinema_id());
           
            atualizar.setInt(7, promocao.getId());
            atualizar.executeUpdate();
            atualizar.close();
            database.desconnect();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Sucesso");
            alert.setContentText("Atualizado com sucesso!");
            alert.showAndWait();
        } catch (SQLException ex) {
            //Logger.getLogger("Error on: " + FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Erro");
            alert.setContentText("Erro na atualização!");
            alert.showAndWait();
            //return false;
        }
    }
    
    public void delete(int promocaoId) {
        final String delete = "delete from Promocao where id = ?";
        try {
            PreparedStatement deletar = database.connect().prepareStatement(delete);
            deletar.setInt(1, promocaoId);
            deletar.executeUpdate();
            deletar.close();
            database.desconnect();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Sucesso");
            alert.setContentText("Deletado com sucesso!");
            alert.showAndWait();

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Erro");
            alert.setContentText("Erro na remocção!");
            alert.showAndWait();
        }
    }
}
