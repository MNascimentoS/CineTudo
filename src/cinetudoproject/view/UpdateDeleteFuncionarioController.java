/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;

import cinetudoproject.model.dao.CinemaDAO;
import cinetudoproject.model.dao.FuncionarioDAO;
import cinetudoproject.model.domain.Cinema;
import cinetudoproject.model.domain.Funcionario;
import cinetudoproject.util.MaskField;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Wellington
 */
public class UpdateDeleteFuncionarioController implements Initializable {

    @FXML
    private Text nameLabel;
    @FXML
    private JFXComboBox cinemaBox;
    @FXML
    private JFXTextField tf_name, tf_cpf, tf_email, tf_user, tf_userBusca;
    @FXML
    private JFXPasswordField tf_pass;

    private List<Cinema> cinema;
    private String cinemaNome;
    private FuncionarioDAO funcionarioDAO, insereFun;
    private Funcionario funcionarioDados, func;
    private MaskField cpfMaskField, passMask;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        funcionarioDados = new Funcionario();
        funcionarioDAO = new FuncionarioDAO();
        insereFun = new FuncionarioDAO();
        cpfMaskField = new MaskField();
        cpfMaskField.cpfCnpjField(tf_cpf);
        passMask = new MaskField();
        passMask.maxField(tf_pass, 6);
        //preenche com todos os cinemas
        CinemaDAO cinemaDAO = new CinemaDAO();
        cinema = cinemaDAO.listar();
        cinema.forEach((i) -> {
            cinemaBox.getItems().addAll(i.getNome());
        });

        cinemaBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                cinemaNome = newValue;
            }

        });

        tf_userBusca.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue
            ) {
                if (!newValue) {
                    funcionarioDados = funcionarioDAO.buscaPorUser(tf_userBusca.getText());
                    tf_cpf.setText(funcionarioDados.getCpf());
                    tf_email.setText(funcionarioDados.getEmail());
                    tf_name.setText(funcionarioDados.getNome());
                    tf_pass.setText(funcionarioDados.getSenha());
                    tf_user.setText(funcionarioDados.getUser());

                }
            }
        }
        );
    }

    //recebe as informacoes de usuario
    public void getUserInfo(Funcionario func) {
        this.func = func;
        nameLabel.setText("Ola, " + this.func.getNome());
    }

   
    public void update(ActionEvent event) throws Exception {
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        Funcionario funcionario;
        int cinemaId = 0;
        for (Cinema i : cinema) {
            if (i.getNome().equals(cinemaNome)) {//se sim, pegue o seu id
                cinemaId = i.getId();
            }
        }
        funcionario = new Funcionario(funcionarioDados.getId(), cinemaId, tf_name.getText(), tf_cpf.getText(), tf_email.getText(), tf_user.getText(), tf_pass.getText());
        funcionarioDAO.update(funcionario);
    }

    public void delete(ActionEvent event) throws Exception {
        FuncionarioDAO funcionario = new FuncionarioDAO();
        funcionario.delete(funcionarioDados.getId());
    }

    //cancelar event - Voltar para o menu principal
    @FXML
    void back2menu(ActionEvent event) throws IOException {
        System.out.println("Back Event!");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Menu Gerente");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainGerente.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        MainGerenteController Gcontroller = fxmlLoader.<MainGerenteController>getController();
        Gcontroller.getUserInfo(this.func);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

