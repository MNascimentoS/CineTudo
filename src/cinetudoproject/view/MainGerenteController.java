/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;

import cinetudoproject.model.domain.Funcionario;
import static cinetudoproject.view.MainFuncionarioController.rootP;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mateus
 */
public class MainGerenteController implements Initializable {
    @FXML
    private Text usernameLabel;
        
    @FXML
    private JFXHamburger hamburguer;

    @FXML
    private JFXDrawer drawer;
    
    @FXML
    private AnchorPane root;

    public static AnchorPane rootP;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
               
        rootP = root;
        
        try {
            VBox box = FXMLLoader.load(getClass().getResource("SidePanelContentG.fxml"));
            drawer.setSidePane(box);
        } catch (IOException ex) {
            Logger.getLogger(MainFuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //setting the hamburguer button
         HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburguer);
        transition.setRate(-1);
        hamburguer.addEventHandler(MouseEvent.MOUSE_PRESSED,(e)->{
            transition.setRate(transition.getRate()*-1);
            transition.play();
            
            if(drawer.isShown())
            {
                drawer.close();
            }else
                drawer.open();
        });
    }    
    
    public void getUserInfo(Funcionario func)
    {
       usernameLabel.setText("Ola, "+func.getNome());
    }
    
    @FXML
    void cadastrarFuncionario(ActionEvent event) throws Exception
    {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Cadastro de Funcionários");
        Parent root = FXMLLoader.load(getClass().getResource("CadastroFuncionario.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    void cadastrarFilme(ActionEvent event) throws Exception
    {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Cadastro de Filmes");
        Parent root = FXMLLoader.load(getClass().getResource("CadastroFilme.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
