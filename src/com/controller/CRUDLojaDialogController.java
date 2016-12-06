package com.controller;

import com.model.domain.Loja;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kds
 */
public class CRUDLojaDialogController implements Initializable {

    @FXML
    private Button buttonCancelar;

    @FXML
    private Button buttonConfirmar;

    @FXML
    private TextField textFieldLojasNome;

    @FXML
    private TextField textFieldLojasEndereco;

    @FXML
    private Label labelLojasNome;

    @FXML
    private Label labelLojasEndereco;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Loja loja;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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

    public Loja getLoja() {
        return loja;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;

        this.textFieldLojasNome.setText(loja.getNome());
        this.textFieldLojasEndereco.setText(loja.getEndereco());
    }

    @FXML
    public void handleButtonConfirmar() {

        if (validarEntradaDeDados()) {
            loja.setNome(textFieldLojasNome.getText());
            loja.setEndereco(textFieldLojasEndereco.getText());

            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    public void handleButtonCancelar() {
        dialogStage.close();
    }

    public boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (textFieldLojasNome.getText() == null || textFieldLojasNome.getText().length() == 0) {
            errorMessage += "Nome inválido!\n";
        }

        if (textFieldLojasEndereco.getText() == null || textFieldLojasEndereco.getText().length() == 0) {
            errorMessage += "Endereço inválido!";
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
}
