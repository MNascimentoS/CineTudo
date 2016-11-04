/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;

import cinetudoproject.model.dao.FilmeDAO;
import cinetudoproject.model.dao.GeneroDAO;
import com.jfoenix.validation.RequiredFieldValidator;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.imageio.ImageIO;
import cinetudoproject.model.domain.Filme;
import cinetudoproject.model.domain.Funcionario;
import cinetudoproject.model.domain.Genero;
import cinetudoproject.util.MaskField;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class CadastroFilmeController implements Initializable {

    @FXML
    private JFXTextField tf_busca;
    @FXML
    private ImageView bigImage, classImage;
    @FXML
    private JFXComboBox comboBox, boxGenero;
    @FXML
    private JFXTextField tituloFilme, nomeDiretor, nomeAtor, duracaoFilme;

    private int classificacao;
    private File imageFile;
    private String generoMovie;
    private String novoGenero = "Cadastrar Novo Gênero";

    private Funcionario func;
    private Filme filmeGeral;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configValidators();

        MaskField.timeField(duracaoFilme);
        comboBox.getItems().addAll(
                "Livre", "10 anos",
                "12 anos", "14 anos",
                "16 anos", "18 anos");

        boxGenero.getItems().addAll(novoGenero);
        List<Genero> genero;
        GeneroDAO generoDAO = new GeneroDAO();
        genero = generoDAO.listar();
        genero.forEach((i) -> {
            boxGenero.getItems().addAll(i.getNome());
        });

        comboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String imagePath = null;
                switch (newValue) {
                    case "Livre":
                        imagePath = "/img/classificacao/livre.png";
                        classificacao = 0;
                        break;
                    case "10 anos":
                        imagePath = "/img/classificacao/10anos.png";
                        classificacao = 10;
                        break;
                    case "12 anos":
                        imagePath = "/img/classificacao/12anos.png";
                        classificacao = 12;
                        break;
                    case "14 anos":
                        imagePath = "/img/classificacao/14anos.png";
                        classificacao = 14;
                        break;
                    case "16 anos":
                        imagePath = "/img/classificacao/16anos.png";
                        classificacao = 16;
                        break;
                    case "18 anos":
                        imagePath = "/img/classificacao/18anos.png";
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
                if (newValue.equals(novoGenero)) {
                    generoMovie = "";
                    Stage stage = new Stage();
                    stage.setTitle("Cadastrar Gênero");
                    Pane myPane;
                    try {
                        myPane = FXMLLoader.load(getClass().getResource("CadastroGenero.fxml"));
                        Scene scene = new Scene(myPane);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(CadastroFilmeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    generoMovie = newValue;
                }
            }

        });
    }

    public void chooseImage(ActionEvent event) throws Exception {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new ExtensionFilter("Images Files", "*.png", "*.PNG", "*.jpg", "*.JPG")
        );
        fc.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );

        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            imageFile = selectedFile;
            BufferedImage bufferedImage = ImageIO.read(selectedFile);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            bigImage.setImage(image);
        } else {
            System.out.println("Image not found");
        }
    }

    public void saveMovie(ActionEvent event) throws Exception {
        if (validateFields()) {
            String duracao = duracaoFilme.getText();

            GeneroDAO generoDAO = new GeneroDAO();
            Genero genero = generoDAO.buscaGenero(generoMovie);
            Filme filme = new Filme(tituloFilme.getText(), nomeDiretor.getText(), nomeAtor.getText(),
                    duracao, classificacao, genero, imageFile, func.getCinema_id());
            //try save the movie
            FilmeDAO filmeDAO = new FilmeDAO();
            filmeDAO.insert(filme);
            //back2main(event);
        }
    }

    @FXML
    void delete(ActionEvent event) {
        FilmeDAO filme = new FilmeDAO();
        filme.delete(filmeGeral.getId());
    }

    @FXML
    void update(ActionEvent event) {

    }

    boolean validateFields() {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);

        if (tituloFilme.getText().equals("") || nomeDiretor.getText().equals("")
                || nomeAtor.getText().equals("") || duracaoFilme.getText().equals("")
                || boxGenero.getValue() == null || comboBox.getValue() == null) {

            alert.setTitle("Campos vazios");
            alert.setContentText("Preencha todos os campos antes de continuar!");
            alert.showAndWait();
            return false;
        }

        if (duracaoFilme.getLength() < 8) {
            alert.setTitle("Campo duração inválido");
            alert.setContentText("Preencha a duração do filme segundo o exemplo - HH:MM:SS");
            alert.showAndWait();
            return false;
        }

        return true;
    }

    public void configValidators() {
        // Valida os campos de entrada
        RequiredFieldValidator vTitulo, vDiretor, vAtor, vDuracao;
        vTitulo = new RequiredFieldValidator();
        vDiretor = new RequiredFieldValidator();
        vAtor = new RequiredFieldValidator();
        vDuracao = new RequiredFieldValidator();
        tituloFilme.getValidators().add(vTitulo);
        nomeDiretor.getValidators().add(vDiretor);
        nomeAtor.getValidators().add(vAtor);
        duracaoFilme.getValidators().add(vDuracao);

        vTitulo.setMessage("Preencha este campo!");
        vDiretor.setMessage("Preencha este campo!");
        vAtor.setMessage("Preencha este campo!");
        vDuracao.setMessage("Preencha este campo!");
        /*listener titulo do filme*/
        tituloFilme.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    tituloFilme.validate();
                }
            }
        });
        /*listener nome diretor*/
        nomeDiretor.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    nomeDiretor.validate();
                }
            }
        });
        /*listener nome ator*/
        nomeAtor.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    nomeAtor.validate();
                }
            }
        });
        /*listener duração*/
        duracaoFilme.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    duracaoFilme.validate();
                }
            }
        });
    }

    //recebe as informacoes de usuario
    public void getUserInfo(Funcionario func) {
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
