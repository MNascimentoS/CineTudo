/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;
import cinetudoproject.model.domain.Funcionario;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author mateus
 */
public class MainFuncionarioController implements Initializable {
    @FXML
    private Text usernameLabel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    public void getUserInfo(Funcionario func)
    {
        //usernameLabel.setText("Ola, "+func);
       usernameLabel.setText("Ola, "+func.getUser());
    }
    
}
