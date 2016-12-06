package com.controller;

import com.model.dao.CategoriaDAO;
import com.model.dao.CompararDAO;
import com.model.domain.Comparar;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author kds
 */
public class CompararController implements Initializable {

    @FXML
    private TableView<Comparar> tableViewComparar;

    @FXML
    private TableColumn<Comparar, String> tableColumnLoja;

    @FXML
    private TableColumn<Comparar, String> tableColumnProduto;

    @FXML
    private TableColumn<Comparar, Double> tableColumnPreco;

    @FXML
    private TableColumn<Comparar, LocalDate> tableColumnData;

    @FXML
    private TableColumn<Comparar, String> tableColumnMarca;

    @FXML
    private Label labelLoja;

    @FXML
    private Label labelProduto;

    @FXML
    private Label labelMarca;

    @FXML
    private Label labelPreco;

    @FXML
    private Label labelData;

    @FXML
    private ComboBox comboBoxCategoria;

    @FXML
    private Button buttonComparar;

    private CategoriaDAO cdao = new CategoriaDAO();
    private CompararDAO compararDAO = new CompararDAO();
    private List<Comparar> listComparar;
    private ObservableList<String> observableListCategorias;
    private ObservableList<Comparar> observableListComparar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            carregarComboBoxCategorias();
            carregarTableViewComparar();
            tableViewComparar.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> selecionarItemTableViewComparar(newValue));

        } catch (ParseException ex) {
            Logger.getLogger(CompararController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void carregarComboBoxCategorias() {
        observableListCategorias = FXCollections.observableArrayList(cdao.listarTipo());
        comboBoxCategoria.setItems(observableListCategorias);
    }

    public void carregarTableViewComparar() throws ParseException {
        tableColumnLoja.setCellValueFactory(new PropertyValueFactory<>("loja_nome"));
        tableColumnProduto.setCellValueFactory(new PropertyValueFactory<>("produto_nome"));
        tableColumnMarca.setCellValueFactory(new PropertyValueFactory<>("produto_marca"));
        tableColumnPreco.setCellValueFactory(new PropertyValueFactory<>("preco_unitario"));
        tableColumnData.setCellValueFactory(new PropertyValueFactory<>("data"));

        listComparar = compararDAO.listar();

        observableListComparar = FXCollections.observableArrayList(listComparar);
        tableViewComparar.setItems(observableListComparar);
    }

    public void selecionarItemTableViewComparar(Comparar c) {
        if (c != null) {
            labelLoja.setText(c.getLoja_nome());
            labelProduto.setText(c.getProduto_nome());
            labelMarca.setText(c.getProduto_marca());
            labelPreco.setText(String.valueOf(c.getPreco_unitario()));
            labelData.setText(String.valueOf(c.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        } else {
            labelLoja.setText("");
            labelProduto.setText("");
            labelMarca.setText("");
            labelPreco.setText("");
            labelData.setText("");
        }
    }

    public void handleButtonComparar() throws ParseException {
        if (comboBoxCategoria.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma categoria para comparar!");
            alert.show();
        } else {
            carregarTableViewCompararCat(compararDAO.comparar(comboBoxCategoria.getValue().toString()));
        }
    }

    private void carregarTableViewCompararCat(List<Comparar> c) {
        tableColumnLoja.setCellValueFactory(new PropertyValueFactory<>("loja_nome"));
        tableColumnProduto.setCellValueFactory(new PropertyValueFactory<>("produto_nome"));
        tableColumnMarca.setCellValueFactory(new PropertyValueFactory<>("produto_marca"));
        tableColumnPreco.setCellValueFactory(new PropertyValueFactory<>("preco_unitario"));
        tableColumnData.setCellValueFactory(new PropertyValueFactory<>("data"));

        observableListComparar = FXCollections.observableArrayList(c);
        tableViewComparar.setItems(observableListComparar);
    }
}
