/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;

import cinetudoproject.model.dao.GeneroDAO;
import cinetudoproject.model.domain.Genero;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author mateus
 */
public class CadastroGeneroController implements Initializable {
    @FXML
    private JFXButton btn_salvar;
    @FXML
    private JFXTextField tv_genero;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RequiredFieldValidator vGenero;
        vGenero = new RequiredFieldValidator();
        tv_genero.getValidators().add(vGenero);
        vGenero.setMessage("Preencha este campo!");
        /*listener titulo do genero*/
        tv_genero.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) tv_genero.validate();
            }
        });
    }    
    
    public void salvarGenero(ActionEvent event) throws Exception, Throwable {
        if (tv_genero.getText().equals("")) return;
        
        Genero genero = new Genero();
        Genero generoCadastrado = null;
        GeneroDAO generoDAO = new GeneroDAO();
        genero.setNome(tv_genero.getText());
        generoCadastrado = generoDAO.buscaGenero(genero.getNome());
        
        if(generoCadastrado != null){
            JOptionPane.showMessageDialog(null, "Genero já Cadastrado");
        }else{
            generoDAO.insertGenero(genero);
        }
        
        Stage stage = (Stage) btn_salvar.getScene().getWindow();
        stage.close();
    }
    
}
