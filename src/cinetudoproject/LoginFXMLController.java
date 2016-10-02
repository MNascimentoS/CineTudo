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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Funcionario;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        RequiredFieldValidator validator = new RequiredFieldValidator();

        tv_name.getValidators().add(validator);
        validator.setMessage("Campo vazio");

        tv_name.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    tv_name.validate();
                }
            }
        });
    }

    public void login(ActionEvent event) throws Exception {
        Funcionario funcionario = buscaPorUser(tv_name.getText());

        if (tv_name.getText().equals(funcionario.getUser()) && tv_password.getText().equals(funcionario.getSenha())) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("MainFXML.fxml"));
            Scene scene = new Scene(root);
            stage.hide();
            stage.setScene(scene);
            stage.show();

        } else {
            JOptionPane.showMessageDialog(null, "Usuario ou senha incorreto!");
        }
    }

    private Connection conexao() {
        String url = "jdbc:mysql://localhost:3306/cinetudo";
        String user = "root";
        String password = "rosimeiremota28";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (e instanceof ClassNotFoundException) {
                JOptionPane.showMessageDialog(null, "VERIFIQUE SE O DRIVER DO BANCO DE DADOS ESTÁ NO CLASSPATH");
            } else {
                JOptionPane.showMessageDialog(null, "VERIFIQUE SE O BANCO ESTÁ RODANDO E SE OS DADOS DE CONEXÃO ESTÃO CORRETOS");
            }
            //System.exit(0);
            // o sistema deverá sair antes de chegar aqui...
            return null;
        }
    }

    public Funcionario buscaPorUser(String user) {
        Funcionario func = null;
        final String busca = "SELECT usuario, senha FROM funcionario WHERE usuario = ?";
        try {
            Connection conn = conexao();
            PreparedStatement buscar = conn.prepareStatement(busca);
            buscar.setString(1, user);
            ResultSet resultadoBusca = buscar.executeQuery();
            resultadoBusca.next();
            func = buscaFuncionario(resultadoBusca);
            buscar.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERRO AO BUSCAR CONTA COM USUARIO" + user);
            //System.exit(0);
        }
        return func;
    }

    private Funcionario buscaFuncionario(ResultSet resultadoBusca) throws SQLException, ParseException {
        Funcionario funcionario = new Funcionario();
        funcionario.setUser(resultadoBusca.getString(1));
        funcionario.setSenha(resultadoBusca.getString(2));

        //System.err.println(funcionario.getNome());
        //System.err.println(funcionario.getSenha());
        return funcionario;
    }
}
