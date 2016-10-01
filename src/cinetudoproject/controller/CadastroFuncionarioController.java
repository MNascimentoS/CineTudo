/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javax.swing.JOptionPane;
import cinetudoproject.model.domain.Funcionario;
/**
 * FXML Controller class
 *
 * @author mateus
 */
public class CadastroFuncionarioController implements Initializable {
    @FXML
    private JFXTextField tf_name, tf_cpf, tf_email, tf_user;
    
    @FXML
    private JFXPasswordField tf_pass;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
    public void cadastro(ActionEvent event) throws Exception
    {
        if ( tf_name.getText()  == null || 
             tf_cpf.getText()   == null || 
             tf_email.getText() == null || 
             tf_user.getText()  == null || 
             tf_pass.getText()  == null    )
        {
            JOptionPane.showMessageDialog(null,"Campo necessário não preenchido!");
            return;
        }
        
        Funcionario funcionario = new Funcionario(tf_name.getText(),
                                                  tf_cpf.getText(),
                                                  tf_email.getText(),
                                                  tf_user.getText(),
                                                  tf_pass.getText());
        
        salvarDB(funcionario);
    }
    
    public void salvarDB(Funcionario funcionario){
        
    }
}
