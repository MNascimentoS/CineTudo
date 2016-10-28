/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;
import cinetudoproject.model.dao.FilmeDAO;
import cinetudoproject.model.domain.Filme;
import cinetudoproject.model.domain.Funcionario;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.paint.Color;
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
    private JFXListView<Label> moviesListView = new JFXListView<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        rootP = root;
       
        try {
            loadMoviesList();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainFuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
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
                //moviesListView.setMaxWidth(637);
                moviesListView.setTranslateX(-80);
            }else{
                drawer.open();
                moviesListView.setMaxWidth(637);
                moviesListView.setTranslateX(5);
                //moviesListView.widthProperty().
            }
        });
    } 
    
    public void getUserInfo(Funcionario func)
    {
        //usernameLabel.setText("Ola, "+func);
       usernameLabel.setText("Ola, "+func.getNome());
    }
    
   /**@TODO: Descobrir o erro stackoverflow quando percorrido os itens na tela*/ 
    void loadMoviesList() throws FileNotFoundException, IOException
    {
        FilmeDAO filmeDao = new FilmeDAO();
        ArrayList<Filme> filmeList = filmeDao.listar();
        //caso haja filmes cadastrados coloque-os na lista
        if(filmeList != null)
        {
            for(Filme i : filmeList)
            {
              Label lbl = new Label(i.getTitulo()+"\n"+i.getDiretor());
              //lbl.setTextFill(Color.web("#000"));
              ImageView iv = new ImageView(new Image(new FileInputStream(i.getImageFile().getAbsolutePath()), 72, 72, false, false));
              lbl.setGraphic(iv);
              lbl.setGraphicTextGap(15);
              moviesListView.getItems().add(lbl);
            }
            
            moviesListView.setExpanded(true);
            moviesListView.depthProperty().set(1);
        
        }else{
            System.err.println("Nao ha filmes cadastrados!");
        }
    }
}
