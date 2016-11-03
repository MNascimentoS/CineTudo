/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.model.dao;

import cinetudoproject.model.database.DatabaseMySQL;
import cinetudoproject.model.domain.Assento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author Jose
 */
public class AssentoDAO {
    
    private Connection connection;
    private final DatabaseMySQL database;
    
    public AssentoDAO()
    {
        database = new DatabaseMySQL();
    }
    
    public void insertAssento(Assento assento, int sessao_id)
    {
        final String inserir = "INSERT INTO assento (ocupado, numero, fila, sessao_id) values(?,?,?,?)";
        
        try {
             PreparedStatement salvar = database.connect().prepareStatement(inserir);
             salvar.setInt(1, assento.getOcupado());
             salvar.setInt(2, assento.getNumero());
             salvar.setString(3, assento.getFila());
             salvar.setInt(4, sessao_id);
             salvar.executeUpdate();
             database.desconnect();
             System.out.println("Assento cadastrado com sucesso!");
        } catch (Exception e) {
             System.out.println("ERRRRORRRR:> " +e.getMessage());
        }
    }
    
    public ArrayList<Assento> listar(int sessao_id, boolean todosAssentos) throws ParseException
    {
         final String busca = "SELECT * FROM assento where sessao_id = '"+sessao_id+"'";
         ArrayList<Assento> assentos = new ArrayList<>();
         
         try {
            PreparedStatement stmt = database.connect().prepareStatement(busca);
            ResultSet resultado = stmt.executeQuery();    
            
            while(resultado.next()) {
                Assento assento = buscaAssento(resultado);
                if (todosAssentos){
                    assentos.add(assento);
                } else {
                    if (assento.getOcupado() != 1) {
                        assentos.add(assento);
                    }
                }
            }
            database.desconnect();
        } catch (SQLException ex) {
           
        }
        
        return assentos;
    }
    
    public void update (Assento assento, int sessao_id){
        final String update = "UPDATE assento SET ocupado = '"+assento.getOcupado()+"' WHERE numero = '" + assento.getNumero() + "' and sessao_id = '" + sessao_id + "'";
        try {
             PreparedStatement salvar = database.connect().prepareStatement(update);
             salvar.executeUpdate();
             database.desconnect();
        } catch (Exception e) {
             System.out.println("ERRRRORRRR:> " +e.getMessage());
        }
    }


     private Assento buscaAssento(ResultSet resultadoBusca) throws SQLException, ParseException {
        
        Assento assento = new Assento();
        
        assento.setFila(resultadoBusca.getString("fila"));
        assento.setNumero(resultadoBusca.getInt("numero"));
        assento.setOcupado(resultadoBusca.getInt("ocupado"));

        return assento;
    }
}
