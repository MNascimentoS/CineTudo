/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
        
        
        
        comboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String imagePath = null;
                switch(newValue)
                {
                    case "Livre": imagePath = "/img/classificacao/livre.png";
                        break;
                    case "10 anos": imagePath = "/img/classificacao/10anos.png";
                        break;
                    case "12 anos": imagePath = "/img/classificacao/12anos.png";
                        break;
                    case "14 anos": imagePath = "/img/classificacao/14anos.png";
                        break;
                    case "16 anos": imagePath = "/img/classificacao/16anos.png";
                        break;   
                    case "18 anos": imagePath = "/img/classificacao/18anos.png";
                        break;
                }
                Image imageClass = new Image(imagePath);
                classImage.setImage(imageClass);
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
}
