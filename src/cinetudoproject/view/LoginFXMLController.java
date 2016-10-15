/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.view;

import cinetudoproject.CineTudoProject;
import cinetudoproject.model.dao.FuncionarioDAO;
import cinetudoproject.model.domain.Funcionario;
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
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author bill-01
 */
public class LoginFXMLController implements Initializable {

    //main panel
    @FXML
    private BorderPane pane;

    @FXML
    private JFXPasswordField tv_password;

    @FXML
    private JFXTextField tv_name;

    @FXML
    private Label label;

    @FXML
    private JFXButton btn_login;

    @FXML
    private Text text_erro;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Valida os campos de entrada (login e senha)
        RequiredFieldValidator validator = new RequiredFieldValidator();
        RequiredFieldValidator validatorPass = new RequiredFieldValidator();
        tv_name.getValidators().add(validator);
        tv_password.getValidators().add(validatorPass);

        validator.setMessage("Preencha este campo!");
        validatorPass.setMessage("Preencha este campo!");
        /*listener login*/
        tv_name.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    tv_name.validate();
                }
            }
        });

        /*listener senha*/
        tv_password.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    tv_password.validate();
                }
            }
        });
    }

    /*Quando logar, valide os campos*/
    public void login(ActionEvent event) throws Exception {
        FuncionarioDAO funDB = new FuncionarioDAO();
        Funcionario funcionario = funDB.buscaPorUser(tv_name.getText());
        
        System.err.println(funcionario.getUser());
        System.err.println(funcionario.getSenha());
        
        if (tv_name.getText().equals(funcionario.getUser()) && tv_password.getText().equals(funcionario.getSenha())) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("MainFXML.fxml"));
            Scene scene = new Scene(root);
            stage.hide();
            stage.setScene(scene);
            stage.setTitle("Menu Principal");
            stage.show();

        } else if (tv_name.getText().equals("") || tv_password.getText().equals("")) {//caso haja campos vazios
            System.out.println("campo vazio!");
            text_erro.setText("Preencha todos os campos!");
            text_erro.setVisible(true);
        } else {//usuario ou senha incorretos!
            System.out.println("Usuário ou senha incorretos!");
            text_erro.setText("Usuário ou senha incorretos!");
            text_erro.setVisible(true);
        }
    }

    /*
 * Creates an HBox with two buttons for the top region
     */
    private HBox addHBox() {

        HBox hbox = new HBox();
        hbox.setPadding(new javafx.geometry.Insets(15, 12, 15, 12));
        hbox.setSpacing(10);   // Gap between nodes
        hbox.setStyle("-fx-background-color: #212121;");

        Button buttonCurrent = new Button("Current");
        buttonCurrent.setPrefSize(100, 20);

        Button buttonProjected = new Button("Projected");
        buttonProjected.setPrefSize(100, 20);

        hbox.getChildren().addAll(buttonCurrent, buttonProjected);

        return hbox;
    }

    /*
 * Creates a VBox with a list of links for the left region
     */
    private VBox addVBox() {

        VBox vbox = new VBox();
        vbox.setPadding(new javafx.geometry.Insets(10)); // Set all sides to 10
        vbox.setSpacing(8);              // Gap between nodes

        Text title = new Text("Data");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);

        Hyperlink options[] = new Hyperlink[]{
            new Hyperlink("Sales"),
            new Hyperlink("Marketing"),
            new Hyperlink("Distribution"),
            new Hyperlink("Costs")};

        for (int i = 0; i < 4; i++) {
            // Add offset to left side to indent from title
            VBox.setMargin(options[i], new javafx.geometry.Insets(0, 0, 0, 8));
            vbox.getChildren().add(options[i]);
        }

        return vbox;
    }

    /*
 * Uses a stack pane to create a help icon and adds it to the right side of an HBox
 * 
 * @param hb HBox to add the stack to
     */
    private void addStackPane(HBox hb) {

        StackPane stack = new StackPane();
        Rectangle helpIcon = new Rectangle(30.0, 25.0);
        helpIcon.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop[]{
                    new Stop(0, Color.web("#4977A3")),
                    new Stop(0.5, Color.web("#B0C6DA")),
                    new Stop(1, Color.web("#9CB6CF")),}));
        helpIcon.setStroke(Color.web("#D0E6FA"));
        helpIcon.setArcHeight(3.5);
        helpIcon.setArcWidth(3.5);

        Text helpText = new Text("?");
        helpText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        helpText.setFill(Color.WHITE);
        helpText.setStroke(Color.web("#7080A0"));

        stack.getChildren().addAll(helpIcon, helpText);
        stack.setAlignment(Pos.CENTER_RIGHT);
        // Add offset to right for question mark to compensate for RIGHT 
        // alignment of all nodes
        StackPane.setMargin(helpText, new javafx.geometry.Insets(0, 10, 0, 0));

        hb.getChildren().add(stack);
        HBox.setHgrow(stack, Priority.ALWAYS);

    }

    /*
 * Creates a grid for the center region with four columns and three rows
     */
    private GridPane addGridPane() {

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(0, 10, 0, 10));

        // Category in column 2, row 1
        Text category = new Text("Sales:");
        category.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        grid.add(category, 1, 0);

        // Title in column 3, row 1
        Text chartTitle = new Text("Current Year");
        chartTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        grid.add(chartTitle, 2, 0);

        // Subtitle in columns 2-3, row 2
        Text chartSubtitle = new Text("Goods and Services");
        grid.add(chartSubtitle, 1, 1, 2, 1);

        //House icon in column 1, rows 1-2
        ImageView imageHouse = new ImageView(
                new Image(CineTudoProject.class.getResourceAsStream("graphics/house.png")));
        grid.add(imageHouse, 0, 0, 1, 2);

        // Left label in column 1 (bottom), row 3
        Text goodsPercent = new Text("Goods\n80%");
        GridPane.setValignment(goodsPercent, VPos.BOTTOM);
        grid.add(goodsPercent, 0, 2);

        // Chart in columns 2-3, row 3
        ImageView imageChart = new ImageView(
                new Image(CineTudoProject.class.getResourceAsStream("graphics/piechart.png")));
        grid.add(imageChart, 1, 2, 2, 1);

        // Right label in column 4 (top), row 3
        Text servicesPercent = new Text("Services\n20%");
        GridPane.setValignment(servicesPercent, VPos.TOP);
        grid.add(servicesPercent, 3, 2);

//        grid.setGridLinesVisible(true);
        return grid;
    }

    /*
 * Creates a horizontal flow pane with eight icons in four rows
     */
    private FlowPane addFlowPane() {

        FlowPane flow = new FlowPane();
        flow.setPadding(new javafx.geometry.Insets(5, 0, 5, 0));
        flow.setVgap(4);
        flow.setHgap(4);
        flow.setPrefWrapLength(170); // preferred width allows for two columns
        flow.setStyle("-fx-background-color: DAE6F3;");

        ImageView pages[] = new ImageView[8];
        for (int i = 0; i < 8; i++) {
            pages[i] = new ImageView(
                    new Image(CineTudoProject.class.getResourceAsStream(
                            "graphics/chart_" + (i + 1) + ".png")));
            flow.getChildren().add(pages[i]);
        }

        return flow;
    }

    /*
 * Creates a horizontal (default) tile pane with eight icons in four rows
     */
    private TilePane addTilePane() {

        TilePane tile = new TilePane();
        tile.setPadding(new javafx.geometry.Insets(5, 0, 5, 0));
        tile.setVgap(4);
        tile.setHgap(4);
        tile.setPrefColumns(2);
        tile.setStyle("-fx-background-color: DAE6F3;");

        ImageView pages[] = new ImageView[8];
        for (int i = 0; i < 8; i++) {
            pages[i] = new ImageView(
                    new Image(CineTudoProject.class.getResourceAsStream(
                            "graphics/chart_" + (i + 1) + ".png")));
            tile.getChildren().add(pages[i]);
        }

        return tile;
    }

    /*
 * Creates an anchor pane using the provided grid and an HBox with buttons
 * 
 * @param grid Grid to anchor to the top of the anchor pane
     */
    private AnchorPane addAnchorPane(GridPane grid) {

        AnchorPane anchorpane = new AnchorPane();

        Button buttonSave = new Button("Save");
        Button buttonCancel = new Button("Cancel");

        HBox hb = new HBox();
        hb.setPadding(new javafx.geometry.Insets(0, 10, 10, 10));
        hb.setSpacing(10);
        hb.getChildren().addAll(buttonSave, buttonCancel);

        anchorpane.getChildren().addAll(grid, hb);
        // Anchor buttons to bottom right, anchor grid to top
        AnchorPane.setBottomAnchor(hb, 8.0);
        AnchorPane.setRightAnchor(hb, 5.0);
        AnchorPane.setTopAnchor(grid, 10.0);

        return anchorpane;
    }
}
