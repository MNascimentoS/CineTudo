/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;

import cinetudoproject.model.domain.Filme;
import cinetudoproject.model.domain.Funcionario;
import cinetudoproject.model.domain.Horario;
import cinetudoproject.model.domain.Sessao;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author mateus
 */
public class VendaFXMLController implements Initializable {
    private Funcionario func;
    private Filme filme;
    private ArrayList<Sessao> sessaoList;
    private ArrayList<Horario> horarioList;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void getUserInfo(Funcionario func)
    {
        this.func = func;
    }
    
    public void getMovieInfo(Filme filme, ArrayList<Sessao> sessaoList, ArrayList<Horario> horarioList)
    {
        this.filme = filme;
        this.sessaoList = sessaoList;
        this.horarioList = horarioList;
    }
    
}
