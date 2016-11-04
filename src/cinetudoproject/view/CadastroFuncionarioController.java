/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;

import cinetudoproject.model.dao.CinemaDAO;
import cinetudoproject.model.dao.FuncionarioDAO;
import cinetudoproject.model.domain.Cinema;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javax.swing.JOptionPane;
import cinetudoproject.model.domain.Funcionario;
import cinetudoproject.util.CryptMD5;
import cinetudoproject.util.MaskField;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mateus
 */
public class CadastroFuncionarioController implements Initializable {
    
    @FXML
    private Text nameLabel;
    @FXML
    private JFXComboBox cinemaBox;
    @FXML
    private JFXTextField tf_name, tf_cpf, tf_email, tf_user;
    @FXML
    private JFXPasswordField tf_pass;

    private List<Cinema> cinema;
    private String cinemaNome;
    private FuncionarioDAO insereFun;
    private Funcionario func;
    private MaskField cpfMaskField, passMask;
    private FuncionarioDAO funcionarioDAO;
    private Funcionario funcionarioDados;
     

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
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
    }

    //recebe as informacoes de usuario
    public void getUserInfo(Funcionario func) {
        this.func = func;
        nameLabel.setText("Ola, " + this.func.getNome());
    }

    //tente cadastrar
    public void cadastro(ActionEvent event) throws Exception {
        //caso algum campo esteja vazio
        if (tf_name.getText().equals("") || tf_cpf.getText().equals("")
                || tf_user.getText().equals("") || tf_pass.getText().equals("")
                || tf_email.getText().equals("") || cinemaNome == null) {
            JOptionPane.showMessageDialog(null, "Campo necessário não preenchido!");
            return;
        }

        int cinemaId = 0;
        //para cada cinema, verifique se existe um cinema igual a opcao escolhida
        for (Cinema i : cinema) {
            if (i.getNome().equals(cinemaNome)) {//se sim, pegue o seu id
                cinemaId = i.getId();
            }
        }

        CryptMD5 md5 = new CryptMD5();
        //cria um novo funcionario
        Funcionario funcionario = new Funcionario(cinemaId, tf_name.getText(), tf_cpf.getText(), tf_email.getText(),
                tf_user.getText(), md5.cryptWithMD5(tf_pass.getText()));
        //tente, cadastrar o novo funcionario caso este nao exista
        insereFun.insertFuncionario(funcionario);
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
