/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;

import cinetudoproject.model.dao.CinemaDAO;
import cinetudoproject.model.dao.FuncionarioDAO;
import cinetudoproject.model.domain.Cinema;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javax.swing.JOptionPane;
import cinetudoproject.model.domain.Funcionario;
import com.jfoenix.controls.JFXComboBox;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * FXML Controller class
 *
 * @author mateus
 */
public class CadastroFuncionarioController implements Initializable {

    FuncionarioDAO insereFun = new FuncionarioDAO();

    @FXML
    private JFXComboBox cinemaBox;
    
    @FXML
    private JFXTextField tf_name, tf_cpf, tf_email, tf_user;

    @FXML
    private JFXPasswordField tf_pass;

    private List<Cinema> cinema;;
    private String cinemaNome;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CinemaDAO cinemaDAO = new CinemaDAO();
        cinema = cinemaDAO.listar();
        cinema.forEach((i) -> {
            cinemaBox.getItems().addAll(i.getNome());
        });
        
        cinemaBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                cinemaNome = newValue;
            }
            
        });
    }

    public void cadastro(ActionEvent event) throws Exception {
        if (    tf_name.getText().equals("")
             || tf_cpf.getText().equals("")
             || tf_user.getText().equals("")
             || tf_pass.getText().equals("")
             || tf_email.getText().equals("")
             || cinemaNome == null) 
        {
            JOptionPane.showMessageDialog(null, "Campo necessário não preenchido!");
            return;
        }
        int cinemaId = 0;
        for(Cinema i : cinema){
            if (i.getNome().equals(cinemaNome)){
                cinemaId = i.getId();
            }
        }
        Funcionario funcionario = new Funcionario(cinemaId, tf_name.getText(), tf_cpf.getText(), tf_email.getText(),
                                                  tf_user.getText(), tf_pass.getText());
        
        insereFun.insert(funcionario);
    }

    /*public void salvarDB(Funcionario funcionario){
        
    }*/
}
