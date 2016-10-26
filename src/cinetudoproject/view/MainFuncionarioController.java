/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;
import cinetudoproject.model.domain.Funcionario;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author mateus
 */
public class MainFuncionarioController implements Initializable {
    @FXML
    private Text usernameLabel;
    
    @FXML
    private JFXHamburger hamburguer;

    @FXML
    private JFXDrawer drawer;
    
    @FXML
    private AnchorPane root;

    public static AnchorPane rootP;
    
    @FXML
    private JFXListView<Label> moviesListView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        rootP = root;
       
        try {
            loadMoviesList();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainFuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            VBox box = FXMLLoader.load(getClass().getResource("SidePanelContent.fxml"));
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
        //usernameLabel.setText("Ola, "+func);
       usernameLabel.setText("Ola, "+func.getNome());
    }
    
   /**@TODO: Descubrir o erro stackoverflow quando percorrido os itens na tela*/ 
    void loadMoviesList() throws FileNotFoundException
    {
         for (int i = 0; i < 30; i++) {
            Label lbl = new Label("Item: "+i);
            lbl.setGraphic(new ImageView(new Image(new FileInputStream("/home/jose/Desktop/movi.png"))));
            moviesListView.getItems().add(lbl);
         }
         
         moviesListView.setExpanded(true);
         moviesListView.depthProperty().set(1);
         //moviesListView.setPadding(new Insets(10, 0, 0, 0));
         //moviesListView.setPadding(new Insets(50,0,50,0));
    }
    
      @FXML
    void mude(ActionEvent event) {
       
    }
}
