/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;

import cinetudoproject.model.domain.Funcionario;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author bill-01
 */
public class MainFXMLController implements Initializable {

    @FXML
    private JFXButton cadastrarFun;
    @FXML
    private Text usernameLabel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
     }
    //to get user data
    public void getUserInfo(Funcionario func)
    {
        //usernameLabel.setText("Ola, "+func);
       usernameLabel.setText("Ola, "+func.getUser());
    }
    
    @FXML
    void cadastrarFuncionario(ActionEvent event) throws Exception
    {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("CadastroFuncionario.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
