/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;

import cinetudoproject.CineTudoProject;
import cinetudoproject.model.dao.FuncionarioDAO;
import cinetudoproject.model.domain.Funcionario;
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
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Valida os campos de entrada (login e senha)
        RequiredFieldValidator validator = new RequiredFieldValidator();
        RequiredFieldValidator validatorPass = new RequiredFieldValidator();
        tv_name.getValidators().add(validator);
        tv_password.getValidators().add(validatorPass);

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
        FuncionarioDAO funDB = new FuncionarioDAO();
        Funcionario funcionario = funDB.buscaPorUser(tv_name.getText());
        
        if(funcionario == null)
        {
            System.out.println("Usuário ou senha incorretos!");
            text_erro.setText("Usuário ou senha incorretos!");
            text_erro.setVisible(true);
        }
        
        if (tv_name.getText().equals(funcionario.getUser()) && tv_password.getText().equals(funcionario.getSenha())) {
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

        } else if (tv_name.getText().equals("") || tv_password.getText().equals("")) {//caso haja campos vazios
            System.out.println("campo vazio!");
            text_erro.setText("Preencha todos os campos!");
            text_erro.setVisible(true);
        } else {//usuario ou senha incorretos!
            System.out.println("Usuário ou senha incorretos!");
            text_erro.setText("Usuário ou senha incorretos!");
            text_erro.setVisible(true);
        }
    }
}
