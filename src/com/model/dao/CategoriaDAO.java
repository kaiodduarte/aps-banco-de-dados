package com.model.dao;

import com.model.service.BD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.model.domain.Categoria;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author kds
 */
public class CategoriaDAO extends BD {

    final String INSERIR = "INSERT INTO Categoria(tipo) VALUES(?)";
    final String ATUALIZAR = "UPDATE Categoria SET tipo=? WHERE id = ?";
    final String LISTAR = "SELECT * FROM Categoria";
    final String LISTAR_TIPO = "SELECT tipo FROM Categoria";
    final String APAGAR = "DELETE FROM Categoria WHERE id = ?";

    public void salvar(Categoria categoria) {
        try {
            Connection con = conexao();
            PreparedStatement salvar = con.prepareStatement(INSERIR, Statement.RETURN_GENERATED_KEYS);

            salvar.setString(1, categoria.getTipo());
            salvar.executeUpdate();
            ResultSet rs = salvar.getGeneratedKeys();
            rs.next();

            categoria.setId(rs.getInt(1));

            salvar.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Categoria> listar() {
        List<Categoria> categorias = new ArrayList<>();

        try {
            Connection con = conexao();
            PreparedStatement listar = con.prepareStatement(LISTAR);
            ResultSet resultado = listar.executeQuery();

            while (resultado.next()) {
                Categoria categoria = extraiCategoria(resultado);
                categorias.add(categoria);
            }

            listar.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorias;
    }

    public ObservableList<String> listarTipo() {
        ObservableList<String> categorias = FXCollections.observableArrayList();

        try {
            Connection con = conexao();
            PreparedStatement listar = con.prepareStatement(LISTAR_TIPO);
            ResultSet resultado = listar.executeQuery();

            while (resultado.next()) {
                categorias.add(resultado.getString("tipo"));
            }

            listar.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorias;
    }

    public void apagar(Categoria categoria) {
        try {
            Connection con = conexao();
            PreparedStatement apagar = con.prepareStatement(APAGAR);

            apagar.setInt(1, categoria.getId());
            apagar.executeUpdate();

            apagar.close();
            con.close();
        } catch (SQLException e) {

        }
    }

    public void atualizar(Categoria categoria) {
        try {
            Connection con = conexao();
            PreparedStatement atualizar = con.prepareStatement(ATUALIZAR);

            atualizar.setString(1, categoria.getTipo());
            atualizar.setInt(2, categoria.getId());
            atualizar.executeUpdate();

            atualizar.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Categoria extraiCategoria(ResultSet resultBusca) throws SQLException {
        Categoria categoria = new Categoria();

        categoria.setId(resultBusca.getInt("id"));
        categoria.setTipo(resultBusca.getString("tipo"));

        return categoria;
    }
}
