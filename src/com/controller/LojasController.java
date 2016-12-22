package com.controller;

import com.model.dao.LojaDAO;
import com.model.domain.Loja;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kds
 */
public class LojasController implements Initializable {

    @FXML
    private TableView<Loja> tableViewLojas;

    @FXML
    private TableColumn<Loja, String> tableColumnID;

    @FXML
    private TableColumn<Loja, String> tableColumnNome;

    @FXML
    private TableColumn<Loja, String> tableColumnEndereço;

    @FXML
    private Label labelID;

    @FXML
    private Label labelNome;

    @FXML
    private Label labelEndereço;

    @FXML
    private Button buttonInserir;

    @FXML
    private Button buttonAlterar;

    @FXML
    private Button buttonRemover;

    private List<Loja> listLojas;
    private ObservableList<Loja> observableListLojas;
    private final LojaDAO lojaDAO = new LojaDAO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarTableViewLojas();

        tableViewLojas.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewLojas(newValue));
    }

    public void carregarTableViewLojas() {
        tableColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnEndereço.setCellValueFactory(new PropertyValueFactory<>("endereco"));

        listLojas = lojaDAO.listar();

        observableListLojas = FXCollections.observableArrayList(listLojas);
        tableViewLojas.setItems(observableListLojas);
    }

    public void selecionarItemTableViewLojas(Loja loja) {
        if (loja != null) {
            labelID.setText(String.valueOf(loja.getId()));
            labelNome.setText(loja.getNome());
            labelEndereço.setText(loja.getEndereco());
        } else {
            labelID.setText("");
            labelNome.setText("");
            labelEndereço.setText("");
        }
    }

    public void handleButtonInserir() throws IOException {
        Loja loja = new Loja();
        boolean buttonConfirmarClicked = showCRUDLojaDialog(loja);

        if (buttonConfirmarClicked) {
            lojaDAO.salvar(loja);
            carregarTableViewLojas();
        }
    }

    public void handleButtonAlterar() throws IOException {
        Loja loja = tableViewLojas.getSelectionModel().getSelectedItem();

        if (loja != null) {
            boolean buttonConfirmarClicked = showCRUDLojaDialog(loja);

            if (buttonConfirmarClicked) {
                lojaDAO.atualizar(loja);
                carregarTableViewLojas();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um loja na tabela!");
            alert.show();
        }
    }

    public void handleButtonRemover() {
        Loja loja = tableViewLojas.getSelectionModel().getSelectedItem();
        if (loja != null) {
            lojaDAO.apagar(loja.getId());
            carregarTableViewLojas();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um loja na tabela!");
            alert.show();
        }
    }

    public boolean showCRUDLojaDialog(Loja loja) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(CRUDLojaDialogController.class.getResource("/com/view/CRUDLojaDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Lojas");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        CRUDLojaDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setLoja(loja);

        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();
    }
}
