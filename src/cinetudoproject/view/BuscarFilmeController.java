/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;
import cinetudoproject.model.dao.FilmeDAO;
import cinetudoproject.model.dao.SalaDAO;
import cinetudoproject.model.dao.SessaoDAO;
import cinetudoproject.model.domain.Filme;
import cinetudoproject.model.domain.Funcionario;
import cinetudoproject.model.domain.Sessao;
import cinetudoproject.model.domain.Promocao;
import cinetudoproject.model.domain.Cinema;
import cinetudoproject.model.domain.Sala;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.URL;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author mateus
 */
public class BuscarFilmeController implements Initializable {
    
    @FXML
    private Text usernameLabel;
    @FXML
    private JFXTextField searchField;
    @FXML
    private JFXDatePicker dataField;
    @FXML
    private JFXComboBox<String> salaCombo;
    @FXML
    private JFXButton backButton;
    @FXML
    private AnchorPane root;    
 
     private List<Sala> sala;

    private Funcionario func;
    public static AnchorPane rootP;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        rootP = root;
    } 
    
    public void getUserInfo(Funcionario func) throws IOException
    {
        this.func = func;
        usernameLabel.setText("Ola, " + func.getNome());
    }
    
    public void SearchMovies() throws ParseException
    {
        salaCombo.setVisible(false);
        
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        
        if(validateFields())
        {
           FilmeDAO filmeDao = new FilmeDAO();
           Filme filme = filmeDao.buscaFilme(searchField.getText());
           
           if(filme != null)
           {
               //data inicial
               LocalDate initialDate = dataField.getValue();
               Instant instant = Instant.from(initialDate.atStartOfDay(ZoneId.systemDefault()));
               Date date = Date.from(instant);
               SessaoDAO s = new SessaoDAO();
               //buscar as sessoes do dia
              ArrayList<Sessao> sessoes = s.buscarSessoesFilmeEData(filme.getId(), date);
               //se tiver sessoes deste filme neste dia
               if(sessoes != null)
               {
                   ArrayList<Sala> salas = new ArrayList<Sala>();
                   SalaDAO salaDao = new SalaDAO();
                   for(Sessao i : sessoes)
                   {
                      Sala sala = salaDao.buscaPorSala(i.getSala_id());
                      if(sala != null)
                      {
                          if(!salas.contains(sala))//se nao contem a nova sala, guarde na lista
                          {
                              salas.add(sala);
                          }
                      }
                   } 
                   //se as salas foram preenchidas adicione no combobox
                   if(!salas.isEmpty())
                   {
                       for(Sala sa : salas)
                       {
                         salaCombo.getItems().addAll("Sala "+sa.getNumero()+" - "+sa.getTipo());
                       }
                       salaCombo.setVisible(true);
                       salaCombo.setPromptText("Sala "+salas.get(0).getNumero()+" - "+salas.get(0).getTipo());
                   }
                   
                   if(sessoes.isEmpty())
                   {
                        alert.setTitle("Não há sessões");
                        alert.setContentText("Nenhuma sessão disponível nesta data!");
                        alert.showAndWait(); 
                   }
               }else{
                  alert.setTitle("Não Existem Sessões");
                  alert.setContentText("Nenhuma sessão adicionada para este filme!");
                  alert.showAndWait(); 
               }
           }else{
               alert.setTitle("Filme Inexistente");
               alert.setContentText("Filme ainda não cadastrado!");
               alert.showAndWait(); 
           } 
        }else{
            alert.setTitle("Campos vazios");
            alert.setContentText("O campo filme não pode estar vazio!");
            alert.showAndWait();
        } 
    }
    
    boolean validateFields()
    {
        if(searchField.getText().equals("")) return false;
        return true;
    }
    
    @FXML
    void back2main(ActionEvent event) throws IOException, ParseException {
        System.out.println("Back Event!");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Menu Funcionário");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainFuncionario.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        MainFuncionarioController Gcontroller = fxmlLoader.<MainFuncionarioController>getController();
        Gcontroller.getUserInfo(this.func);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
