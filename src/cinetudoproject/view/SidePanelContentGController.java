/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;

import cinetudoproject.model.domain.Funcionario;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author mateus
 */
public class SidePanelContentGController implements Initializable {

    private Funcionario func;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //getUserInfo();
    }   
    
    @FXML
    void cadastrarSessao(ActionEvent event) throws IOException {
       
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Cadastro Sessão");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CadastroSessao.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        CadastroSessaoController CScontroller = fxmlLoader.<CadastroSessaoController>getController(); 
        CScontroller.getUserInfo(this.func);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void cadastrarFilme(ActionEvent event) throws IOException {
      
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Cadastro Filme");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CadastroFilme.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        CadastroFilmeController CFilmcontroller = fxmlLoader.<CadastroFilmeController>getController(); 
        CFilmcontroller.getUserInfo(this.func);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void cadastrarFuncionario(ActionEvent event) throws IOException {
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Cadastro Funcionario");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CadastroFuncionario.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        CadastroFuncionarioController CFcontroller = fxmlLoader.<CadastroFuncionarioController>getController(); 
        CFcontroller.getUserInfo(this.func);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    void cadastrarSala(ActionEvent event) throws IOException {
       
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Cadastro Sala");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CadastroSala.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        CadastroSalaController CScontroller = fxmlLoader.<CadastroSalaController>getController(); 
        CScontroller.getUserInfo(this.func);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }

    void getUserInfo(Funcionario func) {
        this.func = func;
        System.out.println("Novo funcionario logado: "+func.getNome());
    }
}
