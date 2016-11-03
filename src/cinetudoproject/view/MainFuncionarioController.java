/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;
import cinetudoproject.model.dao.AssentoDAO;
import cinetudoproject.model.dao.FilmeDAO;
import cinetudoproject.model.dao.SessaoDAO;
import cinetudoproject.model.dao.CinemaDAO;
import cinetudoproject.model.dao.HorarioDAO;
import cinetudoproject.model.dao.PromocaoDAO;
import cinetudoproject.model.domain.Assento;
import cinetudoproject.model.domain.Filme;
import cinetudoproject.model.domain.Funcionario;
import cinetudoproject.model.domain.Sessao;
import cinetudoproject.model.domain.Promocao;
import cinetudoproject.model.domain.Cinema;
import cinetudoproject.model.domain.Horario;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author mateus
 */
public class MainFuncionarioController implements Initializable {
    @FXML
    private Text usernameLabel;
    
    @FXML
    private JFXHamburger hamburguer;

    @FXML
    private JFXDrawer drawer;
    
    @FXML
    private AnchorPane root;

    public static AnchorPane rootP;
    
    @FXML
    private Label lb_nome, lb_diretor, lb_ator, lb_duracao, lb_classificacao, lb_genero, lb_horario;

    @FXML
    private ImageView bt_next, bt_back;

    @FXML
    private ImageView bigImage;

    private Funcionario func;
    private ArrayList<Sessao> sessaoList;
    private ArrayList<Sessao> cSessaoList;
    private ArrayList<Horario> horarioList;
    private ArrayList<Promocao> promocaoList;
    private ArrayList<Filme> filmeList;
    private Filme filme;
    private int listPosition;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        rootP = root;
        bigImage.setBlendMode(BlendMode.SRC_OVER);
    } 
    
    public void nextClicked() {
        System.out.println("next");
        preencherFilme(1);
    }

    public void backClicked() {
        System.out.println("back");
        preencherFilme(-1);
    }
    
    public void getUserInfo(Funcionario func) throws IOException, ParseException
    {
        this.func = func;
        usernameLabel.setText("Ola, " + func.getNome());
        if (this.func != null){
            CinemaDAO cinemaDAO = new CinemaDAO();
            Cinema cinema = cinemaDAO.buscarCinema(this.func.getCinema_id());
            preencherPromocao(cinema);
            preencherSessao(cinema);
            loadMoviesList();
        }
        
        try {
             FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SidePanelContent.fxml"));
             VBox box = fxmlLoader.load();
             SidePanelContentController drawercontroller = fxmlLoader.<SidePanelContentController>getController(); 
             drawercontroller.getPromocao(promocaoList);
             drawercontroller.getUserInfo(this.func);
             //drawer.setBlendMode(BlendMode.SRC_ATOP);
             //box.setBlendMode(BlendMode.SRC_ATOP);
             drawer.setSidePane(box);
        } catch (IOException ex) {
            Logger.getLogger(MainFuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //setting the hamburguer button
        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburguer);
        transition.setRate(-1);
        hamburguer.addEventHandler(MouseEvent.MOUSE_PRESSED,(e)->{
            transition.setRate(transition.getRate()*-1);
            transition.play();
            
            if(drawer.isShown())
            {
                drawer.close();
                //moviesListView.setMaxWidth(637);
                //moviesListView.setTranslateX(-80);
            }else{
                drawer.open();
                //moviesListView.setMaxWidth(637);
                //moviesListView.setTranslateX(5);
                //moviesListView.widthProperty().
            }
        });
    }
    
    public void preencherPromocao(Cinema cinema) throws IOException {
        promocaoList = new ArrayList();
        ArrayList<Promocao> todaPromocao;
        PromocaoDAO promocaoDAO = new PromocaoDAO();
        todaPromocao = promocaoDAO.listar();
        
        if(todaPromocao != null)
        {
            todaPromocao.forEach((i)->{
                if (cinema.getId() == i.getCinema_id()) {
                 promocaoList.add(i);
                }
            });   
        }
    }
    
    public void preencherSessao(Cinema cinema) throws ParseException {
        sessaoList = new ArrayList();
        ArrayList<Sessao> todaSessao;
        SessaoDAO sessaoDAO = new SessaoDAO();
        todaSessao = sessaoDAO.listar();
        if(todaSessao != null)
            todaSessao.forEach((i)->{
                if (i.getCinema_id() == cinema.getId()) {
                    sessaoList.add(i);
                }
            });
    }
    
    public void preencherFilme(int choice) {
        //se a lista de filmes estiver vazia
        if(filmeList.isEmpty()) return;
        
        int listSize = filmeList.size();
        int next = 1; int back = -1;
        if (choice == 0) listPosition = 0;
        if(choice == next) {
            if (listPosition + 1 < listSize) {
                listPosition++;
            }
        } else if (choice == back) {
            if (listPosition > 0) {
                listPosition--;
            }
        }
        
        filme = filmeList.get(listPosition);
        String classificacao = "desconhecido";
        switch(filme.getClassEtaria()) {
            case 0: classificacao = "Livre para todos os públicos";
                break;
            case 1: classificacao = "10 anos";
                break;
            case 2: classificacao = "12 anos";
                break;
            case 3: classificacao = "14 anos";
                break;
            case 4: classificacao = "16 anos";
                break;
            case 5: classificacao = "18 anos";
                break;
        }
        
        lb_nome.setText(filme.getTitulo());
        lb_diretor.setText("Diretor: " + filme.getDiretor());
        lb_ator.setText("Ator: " + filme.getAtorPrincipal());
        lb_duracao.setText("Duracao: " + String.valueOf(filme.getDuracao()));
        lb_genero.setText("Gênero: " + filme.getGenero().getNome());
        lb_classificacao.setText("Classificacao: " + classificacao);
        
        BufferedImage bufferedImage;
        try {
        bufferedImage = ImageIO.read(filmeList.get(listPosition).getImageFile());
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        bigImage.setImage(image);
        } catch (IOException ex) {
            Logger.getLogger(MainFuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cSessaoList = new ArrayList();
        horarioList = new ArrayList();
        lb_horario.setText("Horários:");
        HorarioDAO horarioDAO = new HorarioDAO();
        sessaoList.forEach((i)->{
            Horario horario;
            if (i.getFilme_id() == filme.getId()) {
                cSessaoList.add(i);
                Date date = new Date();
                if(i.getData().getDate()== date.getDate() && i.getData().getMonth() == date.getMonth() && i.getData().getYear() == date.getYear())
                {
                    horario = horarioDAO.buscaPorId(i.getHorario_id());
                    horarioList.add(horario);
                    lb_horario.setText(lb_horario.getText() + " " + horario.getHorario());
                }
            }
        });
    }
    
    //carrega os filmes das sessoes disponiveis para o funcionario
    void loadMoviesList() throws FileNotFoundException, IOException, ParseException
    {
        FilmeDAO filmeDao = new FilmeDAO();
        filmeList = new ArrayList<>();
        
        Date atual = new Date();
        //verifica as sessoes disponiveis para o dia
        for(Sessao i : sessaoList)
        {
            //verifica se a sessao pertence ao intervalo
            if(atual.getDay()   == i.getData().getDay()   &&
               atual.getMonth() == i.getData().getMonth() &&
               atual.getYear()  == i.getData().getYear())
            {
                //busca o pelo id
                Filme filmes = filmeDao.buscaFilme(i.getFilme_id());
                 //se o filme nao estiver na lista, adicione-o
                if(!Filme.jaExisteNaLista(filmeList, filmes))
                {
                    filmeList.add(filmes);
                }    
            }
        }
        //se tiver filmes exiba-os
        if(filmeList == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Sessoes");
            alert.setContentText("Nao ha sessoes cadastradas!");
            alert.showAndWait();
        } else {
            preencherFilme(0);
        }
    }
    
    @FXML
    void buyClicked(ActionEvent event) throws IOException, ParseException {
        if (filme == null) return;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Vendas");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VendaFXML.fxml"));
        Parent rootFuncionario = (Parent)fxmlLoader.load();
        VendaFXMLController Vcontroller = fxmlLoader.<VendaFXMLController>getController(); 
        Vcontroller.getUserInfo(func);
        Vcontroller.getMovieInfo(filme, cSessaoList, horarioList, promocaoList);
        Vcontroller.initComponents();
        Scene scene = new Scene(rootFuncionario);
        stage.setScene(scene);
        stage.show();
    }
}
