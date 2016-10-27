/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;

import cinetudoproject.model.dao.FilmeDAO;
import cinetudoproject.model.domain.Filme;
import cinetudoproject.model.domain.Funcionario;
import com.jfoenix.controls.JFXComboBox;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author mateus
 */
public class CadastroSessaoController implements Initializable {
    
    @FXML
    private ImageView bigImage;
    @FXML
    private JFXComboBox filmeBox;
    
    private Funcionario func;
    
    List<Filme> filme;
    Filme chooseMovie;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            createFilmeBox();
        } catch (IOException ex) {
            Logger.getLogger(CadastroSessaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void createFilmeBox() throws IOException{
        FilmeDAO filmeDAO = new FilmeDAO();
        filme = filmeDAO.listar();
        filme.forEach((i) -> {
            filmeBox.getItems().addAll(i.getTitulo());
        });
        
        filmeBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filme.forEach((i) -> {
                    if (i.getTitulo().equals(newValue)){
                        chooseMovie = i;
                    }
                });
                
                BufferedImage bufferedImage;
                try {
                    bufferedImage = ImageIO.read(chooseMovie.getImageFile());
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    bigImage.setImage(image);
                } catch (IOException ex) {
                    Logger.getLogger(CadastroSessaoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                //Image imageClass = new Image(imagePath);
                //classImage.setImage(imageClass);
            }
            
        });
    }
    
    @FXML
    void salvarSessao(ActionEvent event) throws IOException {
        
    }
    
    //recebe as informacoes de usuario
    public void getUserInfo(Funcionario func)
    {
       this.func = func;
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
