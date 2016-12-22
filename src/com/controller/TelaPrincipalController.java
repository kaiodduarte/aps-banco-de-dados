package com.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author kds
 */
public class TelaPrincipalController implements Initializable {

    @FXML
    private MenuItem menuItemCadastroCategorias;

    @FXML
    private MenuItem menuItemCadastroCompras;

    @FXML
    private MenuItem menuItemCadastroLojas;

    @FXML
    private MenuItem menuItemCadastroProdutos;

    @FXML
    private Menu menuComparar;

    @FXML
    private AnchorPane anchorPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void handleMenuItemCadastroLojas() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/com/view/Lojas.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleMenuItemCadastroCategorias() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/com/view/Categorias.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleMenuItemCadastroProdutos() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/com/view/Produtos.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleMenuItemCadastroCompras() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/com/view/Compras.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleMenuItemComparar() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/com/view/Comparar.fxml"));
        anchorPane.getChildren().setAll(a);
    }
}
