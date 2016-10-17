/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.model.dao;

import cinetudoproject.model.database.DatabaseMySQL;
import cinetudoproject.model.domain.Filme;
import cinetudoproject.model.domain.Funcionario;
import cinetudoproject.model.domain.Genero;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Blob;
import java.sql.Connection;
import static java.sql.JDBCType.BLOB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.sql.Types.BLOB;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        final String inserir = "INSERT INTO filme(titulo, diretor, ator, duracao, genero_id, classificacao, image) values(?,?,?,?,?,?,?)";	
        try {
            
            //get the connection
            Genero genero = null;
            GeneroDAO generoDAO = new GeneroDAO();
            genero = generoDAO.buscaGenero(filme.getGenero().getNome());
            Connection conn = database.connect();
            PreparedStatement salvar = conn.prepareStatement(inserir);
            salvar.setString(1, filme.getTitulo());
            salvar.setString(2, filme.getDiretor());
            salvar.setString(3, filme.getAtorPrincipal());
            salvar.setInt(4, filme.getDuracao());
            salvar.setInt(5, genero.getId());
            salvar.setInt(6, filme.getClassEtaria());
            salvar.setString(7, filme.getImageFile().getAbsolutePath());
            
            salvar.executeUpdate();
            salvar.close();
            conn.close();
            JOptionPane.showMessageDialog(null, "Cadastrado com Sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro no cadastro" + "\n" + ex.getMessage());
        }
    }
    
    public Filme buscaFilme(String nomeFilme) {
        Filme filme = null;
        
        final String busca = "SELECT titulo, diretor, ator, duracao, genero_id, classificacao, image FROM filme WHERE nome = ?";
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
    
    private Filme buscaFilme(ResultSet resultadoBusca) throws SQLException, ParseException {
        Genero genero = null;
        GeneroDAO generoDAO = new GeneroDAO();
        
        Filme filme = new Filme();
        filme.setTitulo(resultadoBusca.getString(1));
        filme.setDiretor(resultadoBusca.getString(2));
        filme.setAtorPrincipal(resultadoBusca.getString(3));
        filme.setDuracao(resultadoBusca.getInt(4));
        
        int generoId = resultadoBusca.getInt(5);
        genero = generoDAO.buscaGenero(generoId);
        filme.setGenero(genero);
        
        filme.setClassEtaria(resultadoBusca.getInt(6));
        
        File imageFile = new File(resultadoBusca.getString(7));
        filme.setImageFile(imageFile);
        return filme;
    }
}
