package com.controller;

import com.model.dao.CompraDAO;
import com.model.dao.ProdutoDAO;
import com.model.domain.Compra;
import com.model.domain.Produto;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kds
 */
public class ComprasController implements Initializable {

    @FXML
    private Button buttonInserir;

    @FXML
    private Button buttonRemover;

    @FXML
    private TableView<Compra> tableViewCompras;

    @FXML
    private TableColumn<Compra, Integer> tableColumnID;

    @FXML
    private TableColumn<Compra, String> tableColumnLoja;

    @FXML
    private TableColumn<Compra, Double> tableColumnPrecoTotal;

    @FXML
    private TableColumn<Compra, LocalDate> tableColumnData;

    @FXML
    private Label labelID;

    @FXML
    private Label labelPrecoUni;

    @FXML
    private Label labelMarca;

    @FXML
    private Label labelMedida;

    @FXML
    private Label labelData;

    @FXML
    private Label labelQuantidade;

    @FXML
    private Label labelProduto;

    @FXML
    private Label labelLoja;

    @FXML
    private Label labelPrecoTotal;

    @FXML
    private Label labelGasto;

    CompraDAO cdao = new CompraDAO();
    ProdutoDAO pdao = new ProdutoDAO();
    List<Compra> listCompras;
    ObservableList<Compra> observableListCompras;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            carregarTableViewCompras();
        } catch (ParseException ex) {
            Logger.getLogger(ComprasController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableViewCompras.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewCompras(newValue));
    }

    public void carregarTableViewCompras() throws ParseException {
        tableColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnLoja.setCellValueFactory(new PropertyValueFactory<>("loja_nome"));
        tableColumnPrecoTotal.setCellValueFactory(new PropertyValueFactory<>("precoTotal"));
        tableColumnData.setCellValueFactory(new PropertyValueFactory<>("data"));
        labelGasto.setText(String.format("%.2f", cdao.getGasto()));

        listCompras = cdao.listar();

        observableListCompras = FXCollections.observableArrayList(listCompras);
        tableViewCompras.setItems(observableListCompras);
    }

    public void selecionarItemTableViewCompras(Compra compra) {
        if (compra != null) {
            labelID.setText(String.valueOf(compra.getId()));
            labelProduto.setText(compra.getProduto_nome());
            labelMarca.setText(compra.getMarca());
            labelLoja.setText(compra.getLoja_nome());
            labelPrecoTotal.setText(String.format("%.2f", compra.getPrecoTotal()));
            labelPrecoUni.setText(String.format("%.2f", compra.getPrecoUnitario()));
            labelMedida.setText(compra.getMedida());
            labelData.setText(String.valueOf(compra.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            labelQuantidade.setText(String.format("%.2f", compra.getQuantidade()));

        } else {
            labelID.setText("");
            labelProduto.setText("");
            labelMarca.setText("");
            labelLoja.setText("");
            labelPrecoTotal.setText("");
            labelPrecoUni.setText("");
            labelMedida.setText("");
            labelData.setText("");
            labelQuantidade.setText("");
        }
    }

    @FXML
    public void handleButtonInserir() throws IOException, ParseException {
        Compra c = new Compra();
        c.setData(LocalDate.now());
        Produto p = new Produto();

        boolean buttonConfirmarClicked = showCRUDCompraDialog(c, p);

        if (buttonConfirmarClicked) {
            carregarTableViewCompras();
        }
    }

    @FXML
    public void handleButtonRemove() throws ParseException {
        Compra c = (Compra) tableViewCompras.getSelectionModel().getSelectedItem();

        if (c != null) {
            cdao.apagar(c);
            carregarTableViewCompras();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma compra na tabela!");
            alert.show();
        }
    }

    public boolean showCRUDCompraDialog(Compra c, Produto p) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(CRUDCompraDialogController.class.getResource("/com/view/CRUDCompraDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro Produto");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        CRUDCompraDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);

        dialogStage.showAndWait();
        return controller.isButtonConfirmarClicked();
    }
}
