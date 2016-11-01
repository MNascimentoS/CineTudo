/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;

import cinetudoproject.model.dao.CinemaDAO;
import cinetudoproject.model.domain.Cinema;
import cinetudoproject.model.domain.Filme;
import cinetudoproject.model.domain.Funcionario;
import cinetudoproject.model.domain.Horario;
import cinetudoproject.model.domain.Promocao;
import cinetudoproject.model.domain.Sessao;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author mateus
 */
public class VendaFXMLController implements Initializable {
    @FXML 
    private ComboBox cb_cinema, cb_filme,cb_ingresso, cb_tipo, cb_promocao, cb_horario;
    
    private String addIngresso;
    private Funcionario func;
    private Cinema cinema;
    private Filme filme;
    private ArrayList<Sessao> sessaoList;
    private ArrayList<Horario> horarioList;
    private ArrayList<Promocao> promocaoList;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addIngresso = "Adicionar Ingresso";
    }    
    
    public void getUserInfo(Funcionario func)
    {
        this.func = func;
        CinemaDAO cinemaDAO = new CinemaDAO();
        cinema = cinemaDAO.buscarCinema(func.getCinema_id());
    }
    
    public void getMovieInfo(Filme filme, ArrayList<Sessao> sessaoList, ArrayList<Horario> horarioList, ArrayList<Promocao> promocaoList)
    {
        this.filme = filme;
        this.sessaoList = sessaoList;
        this.horarioList = horarioList;
        this.promocaoList = promocaoList;
    }
    
    public void initComponents() {
        cb_cinema.setPromptText(cinema.getNome());
        cb_filme.setPromptText(filme.getTitulo());
        cb_ingresso.getItems().add(addIngresso);
        
    }
    
    @FXML
    void addClicked(ActionEvent event) {

    }
    
    @FXML
    void canClicked(ActionEvent event) {

    }
    
    @FXML
    void assentoClicked(ActionEvent event) {

    }
    
    @FXML
    void cancelClicked(ActionEvent event) {

    }
    
    @FXML
    void buyClicked(ActionEvent event) {

    }
    
}
