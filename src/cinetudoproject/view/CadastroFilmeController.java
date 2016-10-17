/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;
import cinetudoproject.model.dao.FilmeDAO;
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
import cinetudoproject.model.domain.Filme;
import cinetudoproject.model.domain.Genero;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import javax.swing.JOptionPane;

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
    File imageFile;
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
            "Terror",
            "Aventura",
            "Suspense",
            "Romance",
            "Comédia",
            "Ficção Científica",
            "Animação"
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
            imageFile = selectedFile;
            BufferedImage bufferedImage = ImageIO.read(selectedFile);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            bigImage.setImage(image);
        } else {
            System.out.println("Image not found");
        }
    }
    
    public void saveMovie(ActionEvent event) throws Exception 
    {
        //FileInputStream fileInput = new FileInputStream(imageFile);
        //OutputBlob blobOutput = new OutputBlob(fileInput, file.length());
        
        Genero genero = new Genero(1, generoMovie);
        Filme filme = new Filme();
        filme.setTitulo(tituloFilme.getText());
        filme.setDiretor(nomeDiretor.getText());
        filme.setAtorPrincipal(nomeAtor.getText());
        filme.setDuracao(Integer.parseInt(duracaoFilme.getText()));
        filme.setClassEtaria(classificacao);
        filme.setGenero(genero);
        filme.setImageFile(imageFile);
        FilmeDAO filmeDAO = new FilmeDAO();
        filmeDAO.insert(filme);
    }
    
    public boolean validarCampo()
    {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        
        tituloFilme.getValidators().add(validator);
        nomeDiretor.getValidators().add(validator);
        nomeAtor.getValidators().add(validator);
        validator.setMessage("Campo vazio");
        return false;
    }
}
