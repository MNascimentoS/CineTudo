/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;

import cinetudoproject.model.dao.CinemaDAO;
import cinetudoproject.model.dao.IngressoDAO;
import cinetudoproject.model.dao.VendaDAO;
import cinetudoproject.model.domain.Cinema;
import cinetudoproject.model.domain.Filme;
import cinetudoproject.model.domain.Funcionario;
import cinetudoproject.model.domain.Horario;
import cinetudoproject.model.domain.Ingresso;
import cinetudoproject.model.domain.Promocao;
import cinetudoproject.model.domain.Sessao;
import cinetudoproject.model.domain.Venda;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mateus
 */
public class VendaFXMLController implements Initializable {
    @FXML
    private JFXButton cancel_button,  buy_button, add_button, can_button, assento_button;
    @FXML
    private Label lb_total;
    @FXML 
    private ComboBox cb_cinema, cb_filme,cb_ingresso, cb_tipo, cb_promocao, cb_horario;
    
    private String addIngresso;
    private Funcionario func;
    private Cinema cinema;
    private Filme filme;
    private ArrayList<Sessao> sessaoList;
    private ArrayList<Horario> horarioList;
    private ArrayList<Promocao> promocaoList;
    private ArrayList<Ingresso> ingressoList;
    
    private Venda venda;
    private int tipoIngresso;
    private Horario cHorario;
    private int sessaoId;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addIngresso = "Adicionar Ingresso";
        tipoIngresso = -1;          
    }    
    
    //Pegar informaçoes do funcionário
    public void getUserInfo(Funcionario func)
    {
        this.func = func;
        CinemaDAO cinemaDAO = new CinemaDAO();
        cinema = cinemaDAO.buscarCinema(func.getCinema_id());
        venda = new Venda();
        Date atual = new Date();
        venda.setCinema_id(cinema.getId());
        venda.setData(String.valueOf(atual.getDay() + "/" + atual.getMonth() + "/" + atual.getYear()));
        venda.setHorario(String.valueOf(atual.getHours() + ":" + atual.getMinutes()));
    }
    
    //Pegar dados das sessões, filmes e horários
    public void getMovieInfo(Filme filme, ArrayList<Sessao> sessaoList, ArrayList<Horario> horarioList, ArrayList<Promocao> promocaoList)
    {
        this.filme = filme;
        this.sessaoList = sessaoList;
        this.horarioList = horarioList;
        this.promocaoList = promocaoList;
    }
    
    public void initComponents() {
        cb_cinema.setPromptText(cinema.getNome());  //setando o nome do cinema
        cb_filme.setPromptText(filme.getTitulo());  //setando o nome do filme
        
        cb_tipo.getItems().add("Meia");
        cb_tipo.getItems().add("Inteira");
        //checando o valor recebido do tipo do ingresso
        cb_tipo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.equals("Meia")) tipoIngresso = 0;
                else tipoIngresso = 1;
            }        
        });
        
        if (!promocaoList.isEmpty()){ //checando se tem promoções cadastradas
            Date atual = new Date();
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(atual);
            int day = gc.get(GregorianCalendar.DAY_OF_WEEK);
            //adiciona apenas as promoções do dia
            promocaoList.forEach((i)->{
                if (day == i.getDiaDaSemana()){
                    cb_promocao.getItems().add(i.getNome());
                }
            });
            //recebe a promoção selecionada
            cb_promocao.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                promocaoList.forEach((i)->{
                    if (newValue.equals(i.getNome())) {
                        cinema.addPromocao(i);
                    }
                });
            }        
        });
        } else {
            cb_promocao.getItems().add("Nenhuma promoção cadastrada.");
        }
        
        //adiciona os horários do filme
        horarioList.forEach((i)->{
            cb_horario.getItems().add(i.getHorario());
        });
        //recebe o horário selecionado
        cb_horario.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                horarioList.forEach((i)->{
                    if (newValue.equals(i.getHorario())) { //checar qual horário foi escolhido
                        cHorario = i;
                    }
                });
            }        
        });
        
        //adicionar um novo ingresso
        cb_ingresso.getItems().add(addIngresso);
        //recebe se o usuário pediu para cadastrar um novo ingresso
        cb_ingresso.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.equals(addIngresso)) { //se for escolhido para adicionar ingresso
                    ativarIngresso();
                }
            }
        });
    }
    
    //ativa os itens da tela
    public void ativarIngresso() {
        buy_button.setDisable(true);
        cancel_button.setDisable(true);
        add_button.setVisible(true);
        can_button.setVisible(true);
        assento_button.setDisable(false);
        cb_tipo.setDisable(false);
        cb_promocao.setDisable(false);
        cb_horario.setDisable(false);
    }
    //desativa os itens da tela
    public void desativarIngresso() {
        cb_ingresso.getSelectionModel().clearSelection();
        cb_tipo.getSelectionModel().clearSelection();
        cb_promocao.getSelectionModel().clearSelection();
        cb_horario.getSelectionModel().clearSelection();
        buy_button.setDisable(false);
        cancel_button.setDisable(false);
        add_button.setVisible(false);
        can_button.setVisible(false);
        assento_button.setDisable(true);
        cb_tipo.setDisable(true);
        cb_promocao.setDisable(true);
        cb_horario.setDisable(true);
    }
    
    @FXML
    void addClicked(ActionEvent event) throws IOException, ParseException {
        desativarIngresso();
        sessaoList.forEach((i)->{
            if (i.getHorario_id() == cHorario.getId()) {
                sessaoId = i.getId();
            }
        });
        Ingresso ingresso = new Ingresso();
        ingresso.setTipo(tipoIngresso);
        ingresso.setSessao_id(sessaoId);
        ingresso.setPreco(cinema.getValorIngresso(), true);
        
        venda.addIngressos(ingresso);
        
        String retorno1 = "";
        String retorno2 = "";
        DecimalFormat formatter = new DecimalFormat("#.00");
        try{
          retorno1 = formatter.format(venda.getValor_total());
          retorno2 = formatter.format(ingresso.getPreco());
        }catch(Exception ex){
          System.err.println("Erro ao formatar numero: " + ex);
        }
        
        lb_total.setText(retorno1 + " R$");
        cb_ingresso.getItems().add("Valor: " + retorno2);
    }
    
    @FXML
    void canClicked(ActionEvent event) {
        desativarIngresso();
    }
    
    @FXML
    void assentoClicked(ActionEvent event) {

    }
    
    @FXML
    void buyClicked(ActionEvent event) throws IOException, ParseException {
        if (!venda.getIngressos().isEmpty()) {
            ingressoList = new ArrayList();
            IngressoDAO ingressoDAO = new IngressoDAO();
            ArrayList<Ingresso> todosIngressos;
            ingressoList = venda.getIngressos();
            
            ingressoList.forEach((i)->{
                ingressoDAO.insert(i);
            });
            
            VendaDAO vendaDAO = new VendaDAO();
            todosIngressos = ingressoDAO.listar();
            ingressoList.forEach((i)->{
                todosIngressos.forEach((j)->{
                    if (i.getTipo()      == j.getTipo()      &&
                        i.getPreco()     == j.getPreco()     &&
                        i.getSessao_id() == j.getSessao_id()    ) 
                    {
                        i.setId(j.getId());
                        venda.setIngresso_id(i.getId());
                        vendaDAO.insert(venda);
                    }
                });
            });
            
            back2main(event);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Erro");
            alert.setContentText("Nenhum ingresso foi adicionado");
            alert.showAndWait();
        }
    }
    
    @FXML
    void cancelClicked(ActionEvent event) throws IOException, ParseException {
        back2main(event);
    }
    
    @FXML
    void back2main(ActionEvent event) throws IOException, ParseException {
        System.out.println("Back Event!");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Menu Funcionário");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainFuncionario.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        MainFuncionarioController Fcontroller = fxmlLoader.<MainFuncionarioController>getController();
        Fcontroller.getUserInfo(this.func);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
