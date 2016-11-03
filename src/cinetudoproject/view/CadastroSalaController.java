/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;

import cinetudoproject.model.dao.SalaDAO;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import cinetudoproject.model.domain.Funcionario;
import cinetudoproject.model.domain.Sala;
import cinetudoproject.model.domain.Sala2D;
import cinetudoproject.model.domain.Sala3D;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.validation.NumberValidator;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jose
 */
public class CadastroSalaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Funcionario func;
    SalaDAO saladao = new SalaDAO();

    @FXML
    private JFXComboBox tipoComboBox;

    @FXML
    private JFXTextField tfNumSala;

    @FXML
    private JFXTextField tfCapaMax;

    Sala sala;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configValidators();
        initTipo();

    }

    public void initTipo() {
        tipoComboBox.getItems().addAll("2D", "3D");
    }

    public void configValidators() {
        //validators para cada campo
        NumberValidator tfNumSalaValidator = new NumberValidator();
        NumberValidator tfCapaMaxValidator = new NumberValidator();
        //adiciona o validator e a mensagem de erro
        tfCapaMax.getValidators().add(tfCapaMaxValidator);
        tfCapaMaxValidator.setMessage("Apenas numeros sao aceitos!");
        //adiciona o validator e a mensagem de erro
        tfNumSala.getValidators().add(tfNumSalaValidator);
        tfNumSalaValidator.setMessage("Apenas numeros sao aceitos!");
        //listener no focus do field
        tfNumSala.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if (!newValue) {
                    tfNumSala.validate();
                }
            }
        });
        //listener no focus do field
        tfCapaMax.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if (!newValue) {
                    tfCapaMax.validate();
                }
            }
        });
    }

    //recebe as informacoes de usuario
    public void getUserInfo(Funcionario func) {
        this.func = func;
    }

    @FXML
    void salvarSala(ActionEvent event) {
        //se estiver tudo ok!
        if (validateFields()) {
            if (tipoComboBox.getValue().equals("2D")) {
                Sala sala2d = new Sala2D(Integer.parseInt(tfNumSala.getText()), Integer.parseInt(tfCapaMax.getText()), (String) tipoComboBox.getValue(), (float) 20.00);
                saladao.insertSala(sala2d);
            } else {
                Sala sala3d = new Sala3D(Integer.parseInt(tfNumSala.getText()), Integer.parseInt(tfCapaMax.getText()), (String) tipoComboBox.getValue(), (float) 20.00);
                saladao.insertSala(sala3d);
            }
        }
    }

    @FXML
    void delete(ActionEvent event) {
        SalaDAO deletar = new SalaDAO();
        deletar.delete(sala.getId());
    }

    @FXML
    void update(ActionEvent event) {
        SalaDAO saladao = new SalaDAO();
        //se estiver tudo ok!
        if (validateFields()) {
            Sala salaAux = new Sala(sala.getId(), Integer.parseInt(tfNumSala.getText()), Integer.parseInt(tfCapaMax.getText()), (String) tipoComboBox.getValue());
            saladao.update(salaAux);
        }
    }

    boolean validateFields() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setHeaderText(null);

        if (tfCapaMax.getText().equals("") || tfNumSala.getText().equals("") || tipoComboBox.getValue() == null) {
            alert.setTitle("Campos vazios");
            alert.setContentText("Preencha todos os campos antes de continuar!");
            alert.showAndWait();
            return false;
        } else if (!onlyNumber(tfCapaMax.getText()) || !onlyNumber(tfNumSala.getText())) {//se nao for apenas numeros n permita o cadastro
            alert.setTitle("Apenas numeros");
            alert.setContentText("Apenas numeros sao permitidos!");
            alert.showAndWait();
            return false;
        }

        return true;
    }

    //valida se possui apenas numeros nos campos
    boolean onlyNumber(String campo) {
        for (int i = 0; i < campo.length(); i++) {
            if (!Character.isDigit(campo.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @FXML
    void back2main(ActionEvent event) throws IOException {

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
