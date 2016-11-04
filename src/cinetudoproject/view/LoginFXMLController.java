/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;

import cinetudoproject.model.dao.FuncionarioDAO;
import cinetudoproject.model.domain.Funcionario;
import cinetudoproject.util.CryptMD5;
import cinetudoproject.util.MaskField;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
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
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author bill-01
 */
public class LoginFXMLController implements Initializable {
    
    //main panel
    @FXML
    private BorderPane pane;
    @FXML
    private JFXPasswordField tv_password;
    @FXML
    private JFXTextField tv_name;
    @FXML
    private Label label;
    @FXML
    private JFXButton btn_login;
    @FXML
    private Text text_erro;
    
    private boolean logar = false;
  
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
        MaskField.maxField(tv_password, 6);
        MaskField.maxField(tv_name, 16);
        // Valida os campos de entrada (login e senha)
        RequiredFieldValidator validator = new RequiredFieldValidator();
        RequiredFieldValidator validatorPass = new RequiredFieldValidator();
        tv_name.getValidators().add(validator);
        tv_password.getValidators().add(validatorPass);
        //mask.timeField(tv_name);
        validator.setMessage("Preencha este campo!");
        validatorPass.setMessage("Preencha este campo!");
        /*listener login*/
        tv_name.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    tv_name.validate();
                }
            }
        });

        /*listener senha*/
        tv_password.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    tv_password.validate();
                }
            }
        });
    }
    
    /*Quando logar, valide os campos*/
    public void login(ActionEvent event) throws Exception {
        
        if (tv_name.getText().equals("") || tv_password.getText().equals("")) {//caso haja campos vazios
            System.out.println("campo vazio!");
            text_erro.setText("Preencha todos os campos!");
            text_erro.setVisible(true);
            return;
        }
        
        FuncionarioDAO funDB = new FuncionarioDAO();
        Funcionario funcionario = funDB.buscaPorUser(tv_name.getText());
        
        if(funcionario == null)
        {
            System.out.println("Usuário ou senha incorretos!");
            text_erro.setText("Usuário ou senha incorretos!");
            text_erro.setVisible(true);
        }
        
        CryptMD5 md5 = new CryptMD5();
         
        String pass;
        if(funcionario.getCargo() == 0)
        {
            pass = md5.cryptWithMD5(tv_password.getText());
        }else{
            pass = tv_password.getText();
        }
            
        if (tv_name.getText().equals(funcionario.getUser()) &&  pass.equals(funcionario.getSenha())) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader;
            Parent root;
            switch (funcionario.getCargo()) {
                case 0: stage.setTitle("Menu Funcionário");
                    fxmlLoader = new FXMLLoader(getClass().getResource("MainFuncionario.fxml"));
                    root = (Parent)fxmlLoader.load();
                    MainFuncionarioController Fcontroller = fxmlLoader.<MainFuncionarioController>getController(); 
                    Fcontroller.getUserInfo(funcionario);
                    break;
                case 1: stage.setTitle("Menu Gerente");
                    fxmlLoader = new FXMLLoader(getClass().getResource("MainGerente.fxml"));
                    root = (Parent)fxmlLoader.load();
                    MainGerenteController Gcontroller = fxmlLoader.<MainGerenteController>getController();
                    Gcontroller.getUserInfo(funcionario);
                    break;
                default:
                    return;
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } else {//usuario ou senha incorretos!
            System.out.println("Usuário ou senha incorretos!");
            text_erro.setText("Usuário ou senha incorretos!");
            text_erro.setVisible(true);
        }
      
    }
}
