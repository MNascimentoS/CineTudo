/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author bill-01
 */
public class LoginFXMLController implements Initializable {
    
    
    @FXML
    private JFXPasswordField tv_password;

    @FXML
    private JFXTextField tv_name;

    @FXML
    private Label label;

    @FXML
    private JFXButton btn_login;

    
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
      
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        RequiredFieldValidator validator = new RequiredFieldValidator();
        
        tv_name.getValidators().add(validator);
        validator.setMessage("Campo vazio");
        
        tv_name.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue)
                {
                    tv_name.validate();
                }
            }
        });
    }    
    
    public void login(ActionEvent event) throws Exception
    {
        if(tv_name.getText().equals("user") && tv_password.getText().equals("pass"))
        {
              Stage stage = new Stage();
              Parent root = FXMLLoader.load(getClass().getResource("MainFXML.fxml"));
              Scene scene = new Scene(root);
              stage.setScene(scene);
              stage.show();
        }else{
             JOptionPane.showMessageDialog(null,"falha ao autenticar!");
        }
    }
    
}
