/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;


import cinetudoproject.model.dao.AssentoDAO;
import cinetudoproject.model.dao.CinemaDAO;
import cinetudoproject.model.dao.FilmeDAO;
import cinetudoproject.model.dao.HorarioDAO;
import cinetudoproject.model.dao.SalaDAO;
import cinetudoproject.model.dao.SessaoDAO;
import cinetudoproject.model.domain.Assento;
import cinetudoproject.model.domain.Cinema;
import cinetudoproject.model.domain.Filme;
import cinetudoproject.model.domain.Funcionario;
import cinetudoproject.model.domain.Horario;
import cinetudoproject.model.domain.Sala;
import cinetudoproject.model.domain.Sessao;
import cinetudoproject.util.MaskField;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import sun.java2d.loops.MaskFill;

/**
 * FXML Controller class
 *
 * @author mateus
 */
public class CadastroSessaoController implements Initializable {
    
    @FXML
    private ImageView bigImage;
    @FXML
    private JFXComboBox cinemaBox, filmeBox, salaBox, horarioBox;
    @FXML
    private BorderPane paneHorario;
    @FXML
    private TextField  fieldHorario;
    @FXML
    private JFXDatePicker dataInicial;
    @FXML
    private JFXDatePicker dataFinal;
    
    private Funcionario func;

    private final String adicionarHorario = "Adicionar Horário";
    
    private List<Filme> filme;
    private Filme chooseMovie;
    private List<Sala> sala;
    private Sala chooseSala;
    private Horario horario;
    private Cinema cinema;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        MaskField.timeField((JFXTextField) fieldHorario, 5);
        
        try {
            createFilmeBox();
            createSalaBox();
            createHorarioBox();
        } catch (IOException ex) {
            Logger.getLogger(CadastroSessaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void createFilmeBox() throws IOException{
        FilmeDAO filmeDAO = new FilmeDAO();
        filme = filmeDAO.listar();
        filme.forEach((i) -> {
            filmeBox.getItems().addAll(i.getTitulo());
        });
        
        filmeBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filme.forEach((i) -> {
                    if (i.getTitulo().equals(newValue)){
                        chooseMovie = i;
                    }
                });
                
                BufferedImage bufferedImage;
                try {
                    bufferedImage = ImageIO.read(chooseMovie.getImageFile());
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    bigImage.setImage(image);
                } catch (IOException ex) {
                    Logger.getLogger(CadastroSessaoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
    }
    
    public void createSalaBox() throws IOException {
        chooseSala = new Sala();
        SalaDAO salaDAO = new SalaDAO();
        sala = salaDAO.listar();
        sala.forEach((i) -> {
            salaBox.getItems().addAll("Sala: " + i.getNumero() + ", do tipo: " + i.getTipo());
        });
        
        salaBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                sala.forEach((i) -> {
                    if (("Sala: " + i.getNumero() + ", do tipo: " + i.getTipo()).equals(newValue)){
                        chooseSala = i;
                    }
                });
            }
        });
    }
    
    public void createHorarioBox() throws IOException {
        horarioBox.getItems().addAll(adicionarHorario);
        horarioBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.equals(adicionarHorario)){
                    horarioBox.getSelectionModel().clearSelection();
                    paneHorario.setVisible(true);
                }
            }
        });
    }
    
    @FXML
    void salvarHorario(ActionEvent event) throws IOException {
        /**@TODO: checar se o horário não interfere em outra sessão*/
        /*Horario novoHorario = new Horario();
        novoHorario.setHorario(fieldHorario.getText());*/
        horarioBox.getItems().addAll(fieldHorario.getText());
        fieldHorario.setText("");
        paneHorario.setVisible(false);
    }
    
    //checar se as datas são válidas
    boolean isValidDate(Date data) throws ParseException{
        //formato da data para poder ser comparado
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.format(data);

        //setando o horário para 0
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        //data atual
        Date today = c.getTime();
        //se as datas iniciais e finais são depois da data atual
        return (data.after(today) || data.equals(today));
    }
    
    
    @FXML
    void salvarSessao(ActionEvent event) throws IOException, ParseException {
        //data inicial
        LocalDate inicialDate = dataInicial.getValue();
        Instant instant = Instant.from(inicialDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        //checar se as datas são válidas
        if(!isValidDate(date)) {
            alert.setTitle("Erro");
            alert.setContentText("Data Inválida. Por favor, escolha outra.");
            alert.showAndWait();
            return;
        }
        
        horario = new Horario();
        HorarioDAO horarioDAO = new HorarioDAO();
        Sessao sessao = new Sessao();
        SessaoDAO sessaoDAO = new SessaoDAO();
        
        //irá adicionar uma sessão para cada horário
        horarioBox.getItems().forEach((i)->{
            if(!i.toString().equals(adicionarHorario)){
                horario.setHorario(i.toString());
                horarioDAO.insertHorario(horario);
                horario = horarioDAO.buscaPorHora(horario.getHorario());
                sessao.setCinema_id(cinema.getId());
                sessao.setHorario_id(horario.getId());
                sessao.setFilme_id(chooseMovie.getId());
                sessao.setSala_id(chooseSala.getId());
                sessao.setData(date);
                sessao.setSala(chooseSala);
                sessao.setAssento(chooseSala.getCapacidade());
                sessao.setIngresso_disponivel(chooseSala.getCapacidade());
                try {
                    sessaoDAO.insertSessao(sessao, horario);
                    ArrayList<Sessao> sessoes = sessaoDAO.listar();
                    if(sessoes != null || !sessoes.isEmpty())
                    {
                        sessoes.forEach((t) -> {
                            if(t.getHorario_id() == sessao.getHorario_id() && t.getSala_id() == sessao.getSala_id()
                            && t.getData().getDate() == sessao.getData().getDate() && t.getData().getMonth()== sessao.getData().getMonth()
                             && t.getData().getYear() == sessao.getData().getYear())
                            {
                                  System.out.println("Achei a sessao!"); 
                                  sessao.setId(t.getId());
                            }
                        }); 
                        //se conseguiu achou a sessao
                        if(sessao.getId() > 0)
                        {
                            AssentoDAO assentoDAO = new AssentoDAO();
                            //cadastra os assentos na sessao
                            for(Assento as : sessao.getAssentos())
                            {
                                System.out.println("Fila: "+ as.getFila()+" Número: "+as.getNumero());
                                assentoDAO.insertAssento(as, sessao.getId());
                            }
                        }
                    }         
                } catch (ParseException ex) {
                    Logger.getLogger(CadastroSessaoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    //recebe as informacoes de usuario
    public void getUserInfo(Funcionario func)
    {
       this.func = func;
       CinemaDAO cinemaDAO = new CinemaDAO();
       cinema = cinemaDAO.buscarCinema(this.func.getCinema_id());
       cinemaBox.setPromptText(cinema.getNome());
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
