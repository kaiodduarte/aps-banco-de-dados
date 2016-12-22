package com.controller;

import com.model.dao.CategoriaDAO;
import com.model.dao.CompraDAO;
import com.model.dao.LojaDAO;
import com.model.dao.ProdutoDAO;
import com.model.domain.Compra;
import com.model.domain.Produto;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author kds
 */
public class CRUDCompraDialogController implements Initializable {

    @FXML
    private Button buttonCancelar;

    @FXML
    private Button buttonConfirmar;

    @FXML
    private TextField textFieldProduto;

    @FXML
    private TextField textFieldMarca;

    @FXML
    private TextField textFieldMedida;

    @FXML
    private TextField textFieldPreco;

    @FXML
    private TextField textFieldQuantidade;

    @FXML
    private ComboBox<String> comboBoxCategoria;

    @FXML
    private ComboBox<String> comboBoxLoja;

    @FXML
    private DatePicker datePicker;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Produto p = new Produto();
    private Compra c = new Compra();
    String str;

    private ProdutoDAO pdao = new ProdutoDAO();
    private CompraDAO cdao = new CompraDAO();
    private CategoriaDAO categoriaDAO = new CategoriaDAO();
    private LojaDAO lojaDAO = new LojaDAO();

    private ObservableList<String> observableListCategorias;
    private ObservableList<String> observableListLojas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarComboBoxCategorias();
        carregarComboBoxProdutos();
    }

    public void carregarComboBoxCategorias() {
        observableListCategorias = FXCollections.observableArrayList(categoriaDAO.listarTipo());
        comboBoxCategoria.setItems(observableListCategorias);
    }

    public void carregarComboBoxProdutos() {
        observableListLojas = FXCollections.observableArrayList(lojaDAO.listarLoja());
        comboBoxLoja.setItems(observableListLojas);
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    public void setButtonConfirmarClicked(boolean buttonConfirmarClicked) {
        this.buttonConfirmarClicked = buttonConfirmarClicked;
    }

    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            p.setCategoria_tipo(comboBoxCategoria.getValue().toString());
            p.setNome(textFieldProduto.getText());
            p.setMarca(textFieldMarca.getText());
            p.setMedida(textFieldMedida.getText());

            pdao.salvar(p);

            c.setMarca(textFieldMarca.getText());
            c.setData(datePicker.getValue());
            c.setQuantidade(Double.valueOf(textFieldQuantidade.getText()));
            c.setPrecoUnitario(Double.valueOf(textFieldPreco.getText()));
            c.setProduto_id(p.getId());
            c.setPrecoTotal(c.getQuantidade() * c.getPrecoUnitario());
            c.setLoja_nome(comboBoxLoja.getValue().toString());
            c.setLoja_id(lojaDAO.getID(comboBoxLoja.getValue().toString()));
            c.setProduto_nome(textFieldProduto.getText());
            c.setMedida(textFieldMedida.getText());

            cdao.salvar(c);
            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }

    public void handleButtonCancelar() {
        dialogStage.close();
    }

    public boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (textFieldProduto.getText() == null || textFieldProduto.getText().length() == 0) {
            errorMessage += "Produto inválido!\n";
        }

        if (textFieldPreco.getText() == null || textFieldPreco.getText().length() == 0) {
            errorMessage += "Preço inválido!\n";
        }

        if (textFieldQuantidade.getText() == null || textFieldQuantidade.getText().length() == 0) {
            errorMessage += "Quantidade inválida!\n";
        }

        if (comboBoxCategoria.getValue() == null) {
            errorMessage += "Categoria inválida!\n";
        }

        if (comboBoxLoja.getValue() == null) {
            errorMessage += "Loja inválida!\n";
        }

        if (datePicker.getValue() == null) {
            errorMessage += "Data inválida!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }

    public Produto getP() {
        return p;
    }

    public void setP(Produto p) {
        this.p = p;
    }

    public Compra getC() {
        return c;
    }

    public void setC(Compra c) {
        this.c = c;
    }
}
