/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;
import cinetudoproject.model.dao.AssentoDAO;
import cinetudoproject.model.dao.FilmeDAO;
import cinetudoproject.model.dao.HorarioDAO;
import cinetudoproject.model.dao.SalaDAO;
import cinetudoproject.model.dao.SessaoDAO;
import cinetudoproject.model.domain.Assento;
import cinetudoproject.model.domain.Filme;
import cinetudoproject.model.domain.Funcionario;
import cinetudoproject.model.domain.Sessao;
import cinetudoproject.model.domain.Horario;
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
public class BuscarSessaoController implements Initializable {
    
     @FXML
    private JFXDatePicker dataField;
    @FXML
    private JFXComboBox<String> salaCombo;
    @FXML
    private JFXComboBox<String> horarioSessaoCombo;
    @FXML
    private JFXComboBox<String> assentosDisponiveisCombo;
    @FXML
    private Text usernameLabel;
    @FXML
    private JFXButton backButton;
    @FXML
    private JFXButton searchButton;
    @FXML
    private AnchorPane root;    
 
    private List<Sala> sala;

    private Funcionario func;
    public static AnchorPane rootP;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        rootP = root;
        try {
            //BUSCAR PELAS SALAS
            initSala();
        } catch (IOException ex) {
            System.out.println("Salas não encontradas!");
        }
    } 
    
    public void getUserInfo(Funcionario func) throws IOException
    {
        this.func = func;
        usernameLabel.setText("Ola, " + func.getNome());
    }
    
    public void initSala() throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        SalaDAO salaDAO = new SalaDAO();
        List<Sala> salas = salaDAO.listar();
        //verifica se existem salas, se existirem atribua no combobox
        if(!salas.isEmpty())
        {
            for(Sala sala : salas)
            {
                salaCombo.getItems().addAll("Sala "+sala.getNumero());
            }
            salaCombo.setValue("Sala "+salas.get(0).getNumero());
            salaCombo.setPromptText("Sala "+salas.get(0).getNumero());
        }
    }
    //exibe os assentos disponiveis em uma sessao em uma determinada data
    @FXML
    void mostrarDisponibilidade(ActionEvent event) throws ParseException {
        
         if(validateFields())
         {
            //deleta todos os campos e pega somente os numeros, no caso o numero da sala
            String numeroSala = salaCombo.getValue().replaceAll("\\D+","");
            System.out.println(numeroSala);
            //buscando a sala pelo numero dela
            SalaDAO salaDao = new SalaDAO();
            Sala sala = salaDao.buscaPorNumeroSala(Integer.parseInt(numeroSala));
            //se a sala existe
            if(sala != null)
            {
               //pegando a data
               LocalDate initialDate = dataField.getValue();
               Instant instant = Instant.from(initialDate.atStartOfDay(ZoneId.systemDefault()));
               Date date = Date.from(instant);
               SessaoDAO sessaoDao = new SessaoDAO();
               ArrayList<Sessao> sessoes = sessaoDao.listarPorSalaEData(Integer.parseInt(numeroSala), date);
               //se houver sessoes para esta data exiba-as no combobox
               if(!sessoes.isEmpty())
               {
                   ArrayList<Integer> sessao_id = new ArrayList<>();
                   int contador = 0;
                   
                   for(Sessao t : sessoes)
                   {
                       if(sala.getId() == t.getSala_id())
                       {
                          contador = contador + 1;
                          sessao_id.add(t.getId());
                          HorarioDAO horarioDao = new HorarioDAO();
                          FilmeDAO filmeDao = new FilmeDAO();
                          Horario hora = horarioDao.buscaPorId(t.getHorario_id());
                          Filme filme = filmeDao.buscaFilme(t.getFilme_id());
                          System.out.println(filme.getTitulo()+" às "+hora.getHorario());
                          if(!horarioSessaoCombo.getItems().contains(filme.getTitulo()+" às "+hora.getHorario()))
                                horarioSessaoCombo.getItems().add(filme.getTitulo()+" às "+hora.getHorario());
                          horarioSessaoCombo.setPromptText("Sessões Encontradas!");
                       }
                   }
                   if(sessoes.size() == 0) horarioSessaoCombo.setPromptText("Não há sessões");
                   //se os horarios nos combos estiverem preenchidos
                   if(horarioSessaoCombo.getValue() != null && !sessao_id.isEmpty())
                   {
                       AssentoDAO assentodao = new AssentoDAO();
                       ArrayList<Assento> assentos = new ArrayList<>();
                       //percorre toda o combo horario
                       for(int i =0; i < horarioSessaoCombo.getItems().size(); i++)
                       {
                           //quando chegar no selecionado, busque e carregue as cadeiras disponiveis
                           if(horarioSessaoCombo.getItems().get(i).equals(horarioSessaoCombo.getValue()))
                           {
                               assentos = assentodao.listar(sessao_id.get(i), true);
                               break;
                           }
                       }
                       //se houver assentos
                       if(!assentos.isEmpty())
                       {
                           for(Assento assento : assentos)
                           {
                               if(!assentosDisponiveisCombo.getItems().contains(assento.getNumero()+" - "+assento.getFila()) && assento.getOcupado() == 0)
                                    assentosDisponiveisCombo.getItems().addAll(assento.getNumero()+" - "+assento.getFila());
                           }
                       }else{
                           //remova os elementos se houver algum
                           for(int i = 0; i < assentosDisponiveisCombo.getItems().size(); i++)
                           {
                               assentosDisponiveisCombo.getItems().remove(i);
                           }
                           assentosDisponiveisCombo.setPromptText("Esgotada");
                       }
                   }
               }else{
                   //remove todos os elementos contidos na lista anteriormente
                   for(int i = 0; i < horarioSessaoCombo.getItems().size(); i++)
                   {
                        horarioSessaoCombo.getItems().remove(i);
                   }
                   horarioSessaoCombo.setPromptText("Não há sessões!");
               }
            }else{
                horarioSessaoCombo.setPromptText("Não há sessões!");
            }
         }
    }
    
    boolean validateFields()
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        //valide os campos
        if(salaCombo.getValue() == null  || dataField.getValue() == null){
          alert.setTitle("Cuidado!");
          alert.setContentText("Preencha todos os campos antes de continuar!");
          alert.showAndWait();
          return false;
        } 
       
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
