/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.imageio.ImageIO;
import model.Filme;
import model.Genero;

/**
 * FXML Controller class
 *
 * @author mateus
 */
public class CadastroFilmeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private ImageView bigImage;
    
    @FXML
    private ImageView classImage;
    
    @FXML
    private JFXButton chooseImage;
    
    @FXML
    private JFXComboBox comboBox;
    
    @FXML
    private JFXComboBox boxGenero;
    
    @FXML
    private JFXTextField tituloFilme;
    
    @FXML
    private JFXTextField nomeDiretor;
    
    @FXML
    private JFXTextField nomeAtor;
    
    @FXML
    private JFXTextField duracaoFilme;
    
    int classificacao;
    String generoMovie;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBox.getItems().addAll(
            "Livre",
            "10 anos",
            "12 anos",
            "14 anos",
            "16 anos",
            "18 anos"
        );
        
        boxGenero.getItems().addAll(
            "Ação",
            "Suspense",
            "Aventura",
            "Terror",
            "Comédia"
        );
        
        comboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String imagePath = null;
                switch(newValue)
                {
                    case "Livre": imagePath = "/img/classificacao/livre.png";
                        classificacao = 0;
                        break;
                    case "10 anos": imagePath = "/img/classificacao/10anos.png";
                        classificacao = 10;
                        break;
                    case "12 anos": imagePath = "/img/classificacao/12anos.png";
                        classificacao = 12;
                        break;
                    case "14 anos": imagePath = "/img/classificacao/14anos.png";
                        classificacao = 14;
                        break;
                    case "16 anos": imagePath = "/img/classificacao/16anos.png";
                        classificacao = 16;
                        break;   
                    case "18 anos": imagePath = "/img/classificacao/18anos.png";
                        classificacao = 18;
                        break;
                }
                Image imageClass = new Image(imagePath);
                classImage.setImage(imageClass);
            }
            
        });
        
        boxGenero.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                generoMovie = newValue;
            }
            
        });
    }    
    
    public void chooseImage(ActionEvent event) throws Exception 
    {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new ExtensionFilter("Images Files", "*.png", "*.PNG","*.jpg", "*.JPG")
        );
        fc.setInitialDirectory(
            new File(System.getProperty("user.home"))
        );
        
        File selectedFile = fc.showOpenDialog(null);
        
        if (selectedFile != null)
        {
            BufferedImage bufferedImage = ImageIO.read(selectedFile);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            //saveToFile(image, selectedFile.getName());
            bigImage.setImage(image);
        } else {
            System.out.println("Image not found");
        }
    }
    
    public void saveToFile(Image image, String nome) 
    {
        File outputFile = new File("/img/filmes/");
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void saveMovie(ActionEvent event) throws Exception 
    {
        validarCampo();
        Genero genero = new Genero(generoMovie);
        Filme filme = new Filme(tituloFilme.getText(), 
                                nomeDiretor.getText(), 
                                nomeAtor.getText(), 
                                10, 
                                classificacao, 
                                genero);
    }
    
    public void validarCampo()
    {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        
        tituloFilme.getValidators().add(validator);
        nomeDiretor.getValidators().add(validator);
        nomeAtor.getValidators().add(validator);
        validator.setMessage("Campo vazio");
    }
}
