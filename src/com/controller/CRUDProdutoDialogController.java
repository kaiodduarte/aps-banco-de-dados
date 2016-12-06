package com.controller;

import com.model.domain.Produto;
import com.model.dao.CategoriaDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author kds
 */
public class CRUDProdutoDialogController implements Initializable {

    final String LISTAR_TIPO = "SELECT tipo FROM Categoria";

    @FXML
    private Button buttonConfirmar;

    @FXML
    private Button buttonCancelar;

    @FXML
    private TextField textFieldNome;

    @FXML
    private TextField textFieldMedida;

    @FXML
    private TextField textFieldMarca;

    @FXML
    private ComboBox comboBoxCategoria;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Produto p;

    CategoriaDAO cdao = new CategoriaDAO();
    ObservableList<String> list = FXCollections.observableArrayList(cdao.listarTipo());

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxCategoria.setItems(list);
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

    public Produto getP() {
        return p;
    }

    public void setP(Produto p) {
        this.p = p;

        textFieldNome.setText(p.getNome());
        textFieldMarca.setText(p.getMarca());
        textFieldMedida.setText(p.getMedida());
        comboBoxCategoria.setValue(p.getCategoria_tipo());
    }

    @FXML
    public void handleButtonConfirmar() {

        if (validarEntradaDeDados()) {

            p.setNome(textFieldNome.getText());
            p.setMarca(textFieldMarca.getText());
            p.setMedida(textFieldMedida.getText());
            p.setCategoria_tipo((String) comboBoxCategoria.getValue());

            buttonConfirmarClicked = true;
            dialogStage.close();
        }

    }

    @FXML
    public void handleButtonCancelar() {
        dialogStage.close();
    }

    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (textFieldNome.getText() == null || textFieldNome.getText().length() == 0) {
            errorMessage += "Nome inválido!\n";
        }
        if (textFieldMarca.getText() == null || textFieldMarca.getText().length() == 0) {
            errorMessage += "Marca inválida!\n";
        }
        if (textFieldMedida.getText() == null || textFieldMedida.getText().length() == 0) {
            errorMessage += "Medida inválida!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }
}
