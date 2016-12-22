package com.controller;

import com.model.domain.Categoria;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kds
 */
public class CRUDCategoriasDialogController implements Initializable {

    @FXML
    private Button buttonCancelar;

    @FXML
    private Button buttonConfirmar;

    @FXML
    private TextField textFieldTipo;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Categoria cat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            cat.setTipo(textFieldTipo.getText());

            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }

    public void handleButtonCancelar() {
        dialogStage.close();
    }

    public boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (textFieldTipo.getText() == null || textFieldTipo.getText().length() == 0) {
            errorMessage += "Tipo inválido!\n";
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

    public Categoria getCategoria() {
        return cat;
    }

    public void setCategoria(Categoria cat) {
        this.cat = cat;

        textFieldTipo.setText(cat.getTipo());
    }
}
