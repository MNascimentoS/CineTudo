/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.model.dao;

import cinetudoproject.model.database.DatabaseMySQL;
import cinetudoproject.model.domain.Filme;
import cinetudoproject.model.domain.Genero;
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
import javax.swing.JOptionPane;

/**
 *
 * @author mateus
 */
public class FilmeDAO {
    Connection connection;
    DatabaseMySQL database;

    public FilmeDAO() {
        database = new DatabaseMySQL();
    }
    
    public void insert(Filme filme) throws FileNotFoundException {
        final String inserir = "INSERT INTO filme(titulo, diretor, ator, duracao, genero_id, classificacao, image, cinema_id) values(?,?,?,?,?,?,?,?)";	
      
        try {
            //to keep image on database
            FileInputStream fis = new FileInputStream(filme.getImageFile());
            //get the connection
            PreparedStatement salvar = database.connect().prepareStatement(inserir);
            salvar.setString(1, filme.getTitulo());
            salvar.setString(2, filme.getDiretor());
            salvar.setString(3, filme.getAtorPrincipal());
            salvar.setInt(4, filme.getDuracao());
            salvar.setInt(5, filme.getGenero().getId());
            salvar.setInt(6, filme.getClassEtaria());
            salvar.setBinaryStream(7, fis, (int) filme.getImageFile().length());
            salvar.setInt(8, filme.getCinema_id());
            
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
            alert.setContentText("Erro no cadastro: \n"+ ex.getMessage());
            alert.showAndWait();
        }
    }
 
   public ArrayList<Filme> listar() throws FileNotFoundException, IOException {
        final String sql = "SELECT * FROM filme";
        ArrayList<Filme> retorno = new ArrayList<>();
        
        try {
            connection = database.connect();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Filme filme = new Filme();
                filme.setTitulo(resultado.getString("titulo"));
                
                File imageFile = new File("src/img/" + filme.getTitulo()+".png");
                FileOutputStream fos = new FileOutputStream(imageFile);
                Blob blob = resultado.getBlob("image");
                byte b[] = blob.getBytes(1,(int)blob.length());
                fos.write(b);
                fos.close();
                filme.setImageFile(imageFile);
                
                Genero genero = new Genero();
                GeneroDAO generoDAO = new GeneroDAO();
                genero.setId(resultado.getInt("genero_id"));
                genero = generoDAO.buscaGenero(genero.getId());
                
                filme.setId(resultado.getInt("id"));
                filme.setAtorPrincipal(resultado.getString("ator"));
                filme.setDiretor(resultado.getString("diretor"));
                filme.setDuracao(resultado.getInt("duracao"));
                filme.setClassEtaria(resultado.getInt("classificacao"));
                filme.setGenero(genero);
                filme.setCinema_id(resultado.getInt("cinema_id"));
                retorno.add(filme);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FilmeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public Filme buscaFilme(String nomeFilme) {
        Filme filme = null;
        
        final String busca = "SELECT id, titulo, diretor, ator, duracao, genero_id, classificacao, image FROM filme WHERE titulo = ?";
        try {
            Connection conn = database.connect();
            PreparedStatement buscar = conn.prepareStatement(busca);
            buscar.setString(1, nomeFilme);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            filme = buscaFilme(resultadoBusca);
            buscar.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filme;
    }
    
    public Filme buscaFilme(int idFilme) {
        Filme filme = null;
        
        final String busca = "SELECT id, titulo, diretor, ator, duracao, genero_id, classificacao, image FROM filme WHERE id = ?";
        try {
            Connection conn = database.connect();
            PreparedStatement buscar = conn.prepareStatement(busca);
            buscar.setInt(1, idFilme);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            filme = buscaFilme(resultadoBusca);
            buscar.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filme;
    }
    
    
    private Filme buscaFilme(ResultSet resultadoBusca) throws SQLException, ParseException, FileNotFoundException, IOException {
        Genero genero = null;
        GeneroDAO generoDAO = new GeneroDAO();
        
        Filme filme = new Filme();
        filme.setId(resultadoBusca.getInt(1));
        filme.setTitulo(resultadoBusca.getString(2));
        filme.setDiretor(resultadoBusca.getString(3));
        filme.setAtorPrincipal(resultadoBusca.getString(4));
        filme.setDuracao(resultadoBusca.getInt(5));      
        int generoId = resultadoBusca.getInt(6);
        genero = generoDAO.buscaGenero(generoId);
        filme.setGenero(genero);
        
        filme.setClassEtaria(resultadoBusca.getInt(6));
        
        File imageFile = new File("src/img/" + filme.getTitulo()+".png");
        FileOutputStream fos = new FileOutputStream(imageFile);
        Blob blob = resultadoBusca.getBlob("image");
        byte b[] = blob.getBytes(1,(int)blob.length());
        fos.write(b);
        fos.close();
        filme.setImageFile(imageFile);

        return filme;
    }
    
    public void delete(int filmeId){
        final String delete = "delete from filme where id = ?";
         try {
            Connection conn = database.connect();
            PreparedStatement deletar = conn.prepareStatement(delete);
            deletar.setInt(1, filmeId);
            deletar.executeUpdate();
            deletar.close();
            conn.close();
            JOptionPane.showMessageDialog(null, "Deletado Com Sucesso!");

        } catch (SQLException ex) {
            // Logger.getLogger("Error on: " + FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro na remoção" + "\n" + ex.getMessage());
            //return false;
        }
    }
    public void update(Filme filme){
        
    }
}
