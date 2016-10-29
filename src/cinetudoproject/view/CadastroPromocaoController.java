/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;

import cinetudoproject.model.dao.CinemaDAO;
import cinetudoproject.model.dao.PromocaoDAO;
import cinetudoproject.model.domain.Cinema;
import cinetudoproject.model.domain.Funcionario;
import cinetudoproject.model.domain.Promocao;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author mateus
 */
public class CadastroPromocaoController implements Initializable {

    @FXML
    private ImageView bigImage;
    @FXML
    private JFXTextField tf_nome, tf_desconto;
    @FXML
    private JFXComboBox cb_semana, cb_desconto;
    @FXML
    private BorderPane borderDesconto;
    @FXML
    private JFXTextArea ta_descricao;
    private Funcionario func;
    private Promocao promocao;
    private String addDesconto;
    private String dia;
    private float desconto;
    private File imageFile;
    private boolean entrou;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addDesconto = "Adicionar um novo desconto";
        createComboBoxSemana();
        createComboBoxDesconto();
    }    
    
    public void createComboBoxSemana() {
        cb_semana.getItems().addAll("Segunda-Feira", "Terça-Feira",
                                    "Quarta-Feira", "Quinta-Feira",
                                    "Sexta-Feira", "Sabado", "Domingo");
        cb_semana.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                dia = newValue;
            }
        });
    }
    
    public void createComboBoxDesconto() {
        cb_desconto.getItems().addAll(addDesconto);
        cb_desconto.getItems().addAll("20%", "40%", "60%", "80%", "100%");
        cb_desconto.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.equals(addDesconto)) { 
                    borderDesconto.setVisible(true);
                    desconto = 0;
                } else {
                    borderDesconto.setVisible(false);
                    desconto = Integer.parseInt(newValue.substring(0, newValue.length() - 1));
                }
            }
        });
    }
    
    public void chooseImage(ActionEvent event) throws Exception {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images Files", "*.png", "*.PNG","*.jpg", "*.JPG")
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
    
    public void addDesconto(ActionEvent event) {
        entrou = false;
        ArrayList<String> stringDesconto = new ArrayList();
        
        if (tf_desconto.getText().equals("")) return;
        int lenght = tf_desconto.getLength();
        cb_desconto.getItems().forEach((i)->{
            if (!i.toString().equals(addDesconto)) {
                if (Integer.parseInt(i.toString().substring(0, i.toString().length() - 1)) <
                    Integer.parseInt(tf_desconto.getText().substring(0, lenght - 1)))
                {
                    stringDesconto.add(i.toString());
                } else if (!entrou) {
                    stringDesconto.add(tf_desconto.getText());
                    stringDesconto.add(i.toString());
                    entrou = true;
                } else {
                    stringDesconto.add(i.toString());
                }
            }
        });
        cb_desconto.getItems().clear();
        cb_desconto.getItems().addAll(addDesconto);
        stringDesconto.forEach((i)->{
            cb_desconto.getItems().addAll(i);
        });
        borderDesconto.setVisible(false);
        cb_desconto.getSelectionModel().clearSelection();
    }
    
    public boolean validateFields(){
        return !(desconto == 0 &&
                 dia.equals("") &&
                 tf_nome.getText().equals("") &&
                 ta_descricao.getText().equals("") );
    }
    
    public void save(ActionEvent event) throws FileNotFoundException, IOException{
        PromocaoDAO promocaoDAO = new PromocaoDAO();
        
        if (!validateFields()) return;
        
        promocao.setNome(tf_nome.getText());
        promocao.setData(dia);
        promocao.setDesconto(desconto);
        promocao.setDescricao(ta_descricao.getText());
        promocao.setImageFile(imageFile);
        
        if (imageFile == null) promocaoDAO.insertPromocao(promocao);
        else promocaoDAO.insertPromocao(promocao, imageFile);
        
        back2main(event);
    }
    
    //recebe as informacoes de usuario
    public void getUserInfo(Funcionario func)
    {
       promocao = new Promocao();
       this.func = func;
       CinemaDAO cinemaDAO = new CinemaDAO();
       Cinema cinema = cinemaDAO.buscarCinema(this.func.getCinema_id());
       promocao.setCinema_id(cinema.getId());
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
