/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.model.dao;

import cinetudoproject.model.database.DatabaseMySQL;
import cinetudoproject.model.domain.Filme;
import cinetudoproject.model.domain.Genero;
import cinetudoproject.model.domain.Sala;
import java.io.File;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Wellington
 */
public class SalaDAO {

    Connection connection;
    DatabaseMySQL database;

    public SalaDAO() {
        database = new DatabaseMySQL();
    }

    //insert method
    public void insertSala(Sala sala) {
        final String inserir = "INSERT INTO sala(numero, capacidade, tipo, preco_ingresso) values(?,?,?,?)";
        try {
            Connection conn = database.connect();
            PreparedStatement salvar = conn.prepareStatement(inserir);
            salvar.setInt(1, sala.getNumero());
            salvar.setInt(2, sala.getCapacidade());
            salvar.setString(3, sala.getTipo());
            salvar.setFloat(4, sala.getPreco_ingresso());

            salvar.executeUpdate();
            salvar.close();
            conn.close();
            JOptionPane.showMessageDialog(null, "Cadastrado com Sucesso");
        } catch (SQLException ex) {
            //Logger.getLogger("Error on: " + SalaDAO.class.getNumero()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro no cadastro" + "\n" + ex.getMessage());
        }
    }

    public List<Sala> listar() throws FileNotFoundException, IOException {
        final String sql = "SELECT * FROM sala order by numero";
        List<Sala> retorno = new ArrayList<>();
        
        try {
            connection = database.connect();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Sala sala = new Sala();
                sala.setId(resultado.getInt("id"));
                sala.setNumero(resultado.getInt("numero"));
                sala.setCapacidade(resultado.getInt("capacidade"));
                sala.setTipo(resultado.getString("tipo"));
                sala.setPreco_ingresso(resultado.getFloat("preco_ingresso"));
                retorno.add(sala);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FilmeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public Sala buscaPorNumeroSala(int numero) {
        Sala sala = null;
        final String busca = "SELECT id, numero, capacidade, preco_ingresso FROM sala WHERE numero = ?";
        try {
            //DBConect db = new DBConect();
            Connection conn = database.connect();
            PreparedStatement buscar = conn.prepareStatement(busca);
            buscar.setInt(1, numero);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            sala = buscaHorario(resultadoBusca);
            buscar.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERRO AO BUSCAR A SALA: "+ numero);
            //System.exit(0);
        }
        return sala;
    }
    
    private Sala buscaHorario(ResultSet resultadoBusca) throws SQLException, ParseException {
        Sala sala = new Sala();
        sala.setId(resultadoBusca.getInt(1));
        sala.setNumero(resultadoBusca.getInt(2));
        sala.setCapacidade(resultadoBusca.getInt(3));
        sala.setTipo(resultadoBusca.getString(4));
        sala.setPreco_ingresso(resultadoBusca.getFloat(5));
        return sala;
    }
}
