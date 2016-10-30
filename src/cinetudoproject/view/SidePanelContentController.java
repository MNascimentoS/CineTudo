package cinetudoproject.view;

import cinetudoproject.model.domain.Promocao;
import com.jfoenix.controls.JFXButton;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javax.imageio.ImageIO;


public class SidePanelContentController implements Initializable {

    @FXML
    private JFXButton b1;
    @FXML
    private JFXButton b2;
    @FXML
    private JFXButton b3;
    @FXML
    private JFXButton exit;
    @FXML
    private ImageView promo_image;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void changeColor(ActionEvent event) {
        JFXButton btn = (JFXButton) event.getSource();
        System.out.println(btn.getText());
        switch(btn.getText())
        {
            case "Color 1":MainFuncionarioController.rootP.setStyle("-fx-background-color:#00FF00");
                break;
            case "Color 2":MainFuncionarioController.rootP.setStyle("-fx-background-color:#0000FF");
                break;
            case "Color 3":MainFuncionarioController.rootP.setStyle("-fx-background-color:#FF0000");
                break;
        }
    }
    
    public void getPromocao(ArrayList<Promocao> promocao) {
        int size = promocao.size();
        Random random = new Random();
        int rn = random.nextInt(size);
        Promocao ePromocao = promocao.get(rn);
        
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(ePromocao.getImageFile());
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            promo_image.setImage(image);
        } catch (IOException ex) {
            Logger.getLogger(CadastroSessaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }
    
}
