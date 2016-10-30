/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;
import cinetudoproject.model.dao.FilmeDAO;
import cinetudoproject.model.dao.SessaoDAO;
import cinetudoproject.model.dao.CinemaDAO;
import cinetudoproject.model.dao.PromocaoDAO;
import cinetudoproject.model.domain.Filme;
import cinetudoproject.model.domain.Funcionario;
import cinetudoproject.model.domain.Sessao;
import cinetudoproject.model.domain.Promocao;
import cinetudoproject.model.domain.Cinema;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
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

    private Funcionario func;
    private ArrayList<Promocao> promocao;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        rootP = root;
    } 
    
    public void getUserInfo(Funcionario func) throws IOException
    {
        this.func = func;
        usernameLabel.setText("Ola, " + func.getNome());
        if (this.func != null){
            CinemaDAO cinemaDAO = new CinemaDAO();
            Cinema cinema = cinemaDAO.buscarCinema(this.func.getCinema_id());
            promocao = new ArrayList();
            ArrayList<Promocao> todaPromocao;
            PromocaoDAO promocaoDAO = new PromocaoDAO();
            todaPromocao = promocaoDAO.listar();
            todaPromocao.forEach((i)->{
                if (cinema.getId() == i.getCinema_id()) {
                    promocao.add(i);
                }
            });   
        }
        
	try {
            loadMoviesList();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainFuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainFuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(MainFuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
             FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SidePanelContent.fxml"));
             VBox box = fxmlLoader.load();
             SidePanelContentController drawercontroller = fxmlLoader.<SidePanelContentController>getController(); 
             drawercontroller.getPromocao(promocao);
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
    
   //carrega os filmes das sessoes disponiveis para o funcionario
    void loadMoviesList() throws FileNotFoundException, IOException, ParseException
    {
        FilmeDAO filmeDao = new FilmeDAO();
        SessaoDAO sessaoDao = new SessaoDAO();
        ArrayList<Filme> filmeList = new ArrayList<>();
        ArrayList<Sessao> sessaoList = sessaoDao.listar();
        
        Date atual = new Date();
        //verifica as sessoes disponiveis para o dia
        for(Sessao i : sessaoList)
        {
            //verifica se a sessao pertence ao intervalo
            if(atual.compareTo(i.getDataInicio()) >= 0 && atual.compareTo(i.getDataFinal()) <= 0)
            {
                //busca o pelo id
                Filme filme = filmeDao.buscaFilme(i.getFilme_id());
                 //se o filme nao estiver na lista, adicione-o
                 if(!Filme.jaExisteNaLista(filmeList, filme))
                 {
                     filmeList.add(filme);
                 }    
            }
        }
        //se tiver filmes exiba-os
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Sessoes");
            alert.setContentText("Nao ha sessoes cadastradas!");
            alert.showAndWait();
        }
    }
}
