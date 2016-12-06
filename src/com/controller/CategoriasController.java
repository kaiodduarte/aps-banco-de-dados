package com.controller;

import com.model.domain.Categoria;
import com.model.dao.CategoriaDAO;
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
public class CategoriasController implements Initializable {

    @FXML
    private Label labelID;

    @FXML
    private Label labelTipo;

    @FXML
    private TableView tableViewCategorias;

    @FXML
    private TableColumn tableColumnID;

    @FXML
    private TableColumn tableColumnNome;

    @FXML
    private Button buttonInserir;

    @FXML
    private Button buttonAlterar;

    @FXML
    private Button buttonRemover;

    private List<Categoria> categorias;
    private ObservableList<Categoria> observableListCategorias;
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarTableViewCategorias();

        tableViewCategorias.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewCategorias((Categoria) newValue));
    }

    public void carregarTableViewCategorias() {
        tableColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        categorias = categoriaDAO.listar();

        observableListCategorias = FXCollections.observableArrayList(categorias);
        tableViewCategorias.setItems(observableListCategorias);
    }

    public void selecionarItemTableViewCategorias(Categoria c) {
        if (c != null) {
            labelID.setText(String.valueOf(c.getId()));
            labelTipo.setText(c.getTipo());
        } else {
            labelID.setText("");
            labelTipo.setText("");
        }
    }

    public void handleButtonInserir() throws IOException {
        Categoria cat = new Categoria();
        boolean buttonConfirmarClicked = showCRUDCategoriasDialog(cat);

        if (buttonConfirmarClicked) {
            categoriaDAO.salvar(cat);
            carregarTableViewCategorias();
        }
    }

    public void handleButtonAlterar() throws IOException {
        Categoria cat = (Categoria) tableViewCategorias.getSelectionModel().getSelectedItem();

        if (cat != null) {
            boolean buttonConfirmarClicked = showCRUDCategoriasDialog(cat);

            if (buttonConfirmarClicked) {
                categoriaDAO.atualizar(cat);
                carregarTableViewCategorias();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma categoria na tabela!");
            alert.show();
        }
    }

    public void handleButtonRemover() throws IOException {
        Categoria cat = (Categoria) tableViewCategorias.getSelectionModel().getSelectedItem();

        if (cat != null) {
            categoriaDAO.apagar(cat);
            carregarTableViewCategorias();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma categoria na tabela!");
            alert.show();
        }
    }

    public boolean showCRUDCategoriasDialog(Categoria cat) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(CRUDCategoriasDialogController.class.getResource("/com/view/CRUDCategoriasDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Categorias");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        CRUDCategoriasDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setCategoria(cat);

        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();
    }
}
