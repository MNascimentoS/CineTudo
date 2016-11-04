/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;

import cinetudoproject.model.dao.CinemaDAO;
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
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author acso
 */
public class DeleteVendaController implements Initializable {
    
    @FXML
    private JFXButton cancelarIngresso, cancelaButton;
    @FXML
    private JFXTextField tf_buscaIngresso;

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
    public void getUserInfo(Funcionario func) {
        this.func = func;
        CinemaDAO cinemaDAO = new CinemaDAO();
        cinema = cinemaDAO.buscarCinema(func.getCinema_id());
        ingressoList = new ArrayList();
        venda = new Venda();
        Date atual = new Date();
        venda.setCinema_id(cinema.getId());
        venda.setData(String.valueOf(atual.getDay() + "/" + atual.getMonth() + "/" + atual.getYear()));
        venda.setHorario(String.valueOf(atual.getHours() + ":" + atual.getMinutes()));
    }

    //Pegar dados das sessões, filmes e horários
    public void getMovieInfo(Filme filme, ArrayList<Sessao> sessaoList, ArrayList<Horario> horarioList, ArrayList<Promocao> promocaoList) {
        this.filme = filme;
        this.sessaoList = sessaoList;
        this.horarioList = horarioList;
        this.promocaoList = promocaoList;
    }

    public boolean isValid() {
        return cHorario != null && tipoIngresso != -1;
    }

    @FXML
    void addClicked(ActionEvent event) throws IOException, ParseException {
        if (!isValid()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Erro");
            alert.setContentText("Você deixou de preencher algum campo");
            alert.showAndWait();
            return;
        }
        sessaoList.forEach((i) -> {
            if (i.getHorario_id() == cHorario.getId()) {
                sessaoId = i.getId();
            }
        });
        Ingresso ingresso = new Ingresso();
        ingresso.setTipo(tipoIngresso);
        ingresso.setSessao_id(sessaoId);
        ingresso.setPreco(cinema.getValorIngresso(), true);
        ingressoList.add(ingresso);
        venda.addIngressos(ingresso);

        String retorno1 = "";
        String retorno2 = "";
        DecimalFormat formatter = new DecimalFormat("#.00");
        try {
            retorno1 = formatter.format(venda.getValor_total());
            retorno2 = formatter.format(ingresso.getPreco());
        } catch (Exception ex) {
            System.err.println("Erro ao formatar numero: " + ex);
        }

    }
    
    @FXML
    void cancelaIngresso(ActionEvent event) throws IOException, ParseException {
        VendaDAO vendaDAO = new VendaDAO();
        vendaDAO.cancelaVenda(Integer.parseInt(tf_buscaIngresso.getText()));
    }
    
    @FXML
    void back2main(ActionEvent event) throws IOException, ParseException {
        
        System.out.println("Back Event!");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Menu Gerente");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainGerente.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        MainGerenteController Fcontroller = fxmlLoader.<MainGerenteController>getController();
        Fcontroller.getUserInfo(this.func);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
