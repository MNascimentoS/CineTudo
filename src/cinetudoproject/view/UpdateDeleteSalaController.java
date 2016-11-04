/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;

import cinetudoproject.model.dao.SalaDAO;
import cinetudoproject.model.domain.Funcionario;
import cinetudoproject.model.domain.Sala;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import java.io.IOException;
import java.net.URL;
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
import javafx.stage.Stage;

/**
 *
 * @author Wellington
 */
public class UpdateDeleteSalaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXComboBox tipoComboBox;
    @FXML
    private JFXTextField tfNumSala, tfBuscaSala, tfCapaMax;

    private Sala sala;
    private Funcionario func;
    private SalaDAO saladao;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        saladao = new SalaDAO();
        configValidators();
        initTipo();

        tfBuscaSala.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                sala = new SalaDAO().buscaPorNumeroSala(Integer.parseInt(tfBuscaSala.getText()));
                tfNumSala.setText(String.valueOf(sala.getNumero()));
                tfCapaMax.setText(String.valueOf(sala.getCapacidade()));

            }
        });
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
        Alert alert = new Alert(Alert.AlertType.WARNING);
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
