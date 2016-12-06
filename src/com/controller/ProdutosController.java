package com.controller;

import com.model.domain.Produto;
import com.model.dao.ProdutoDAO;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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
public class ProdutosController implements Initializable {

    @FXML
    private Label labelID;

    @FXML
    private Label labelNome;

    @FXML
    private Label labelMarca;

    @FXML
    private Label labelMedida;

    @FXML
    private Label labelCategoria;

    @FXML
    private TableColumn tableColumnID;

    @FXML
    private TableColumn tableColumnNome;

    @FXML
    private TableColumn tableColumnMarca;

    @FXML
    private TableColumn tableColumnMedida;

    @FXML
    private TableColumn tableColumnCategoria;

    @FXML
    private TableView tableViewProdutos;

    @FXML
    private Button buttonInserir;

    @FXML
    private Button buttonAlterar;

    @FXML
    private Button buttonRemover;

    private List<Produto> produtos;
    private ObservableList<Produto> observableListProdutos;
    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarTableViewProdutos();

        tableViewProdutos.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectItemTableViewProdutos((Produto) newValue));
    }

    public void carregarTableViewProdutos() {
        tableColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tableColumnMedida.setCellValueFactory(new PropertyValueFactory<>("medida"));
        tableColumnCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria_tipo"));

        produtos = produtoDAO.listar();

        observableListProdutos = FXCollections.observableArrayList(produtos);
        tableViewProdutos.setItems(observableListProdutos);
    }

    public void selectItemTableViewProdutos(Produto p) {
        if (p != null) {
            labelID.setText(String.valueOf(p.getId()));
            labelNome.setText(p.getNome());
            labelMarca.setText(p.getMarca());
            labelMedida.setText(p.getMedida());
            labelCategoria.setText(p.getCategoria_tipo());
        } else {
            labelID.setText("");
            labelNome.setText("");
            labelMarca.setText("");
            labelMedida.setText("");
            labelCategoria.setText("");
        }
    }

    @FXML
    public void handleButtonInserir() throws IOException {
        Produto p = new Produto();
        boolean buttonConfirmarClicked = showCRUDProdutoDialog(p);

        if (buttonConfirmarClicked) {
            produtoDAO.salvar(p);
            carregarTableViewProdutos();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Produto p = (Produto) tableViewProdutos.getSelectionModel().getSelectedItem();

        if (p != null) {
            boolean buttonConfirmarClicked = showCRUDProdutoDialog(p);

            if (buttonConfirmarClicked) {
                produtoDAO.atualizar(p);
                carregarTableViewProdutos();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um produto na tabela!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        Produto p = (Produto) tableViewProdutos.getSelectionModel().getSelectedItem();

        if (p != null) {
            produtoDAO.apagar(p);
            carregarTableViewProdutos();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um produto na tabela!");
            alert.show();
        }
    }

    public boolean showCRUDProdutoDialog(Produto p) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(CRUDProdutoDialogController.class.getResource("/com/view/CRUDProdutoDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro Produto");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        CRUDProdutoDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setP(p);

        dialogStage.showAndWait();
        return controller.isButtonConfirmarClicked();
    }
}
