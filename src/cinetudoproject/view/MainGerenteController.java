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
import javax.swing.JOptionPane;

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
   
    private Funcionario func = new Funcionario();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
               
        rootP = root;
       
    }    
    //recebe as informacoes de usuario
    public void getUserInfo(Funcionario func)
    {
         this.func = func;
         usernameLabel.setText("Ola, "+this.func.getNome());
         //carrega o drawer
         try {
             FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SidePanelContentG.fxml"));
             VBox box = fxmlLoader.load();
             SidePanelContentGController drawercontroller = fxmlLoader.<SidePanelContentGController>getController(); 
             drawercontroller.getUserInfo(this.func);
             if(this.func == null) System.err.println("funcionario nulo!");
             drawer.setSidePane(box);
        } catch (IOException ex) {
            System.err.println("Erro ao carregar drawer!");
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
}
