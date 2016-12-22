package com.model.dao;

import com.model.service.BD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.model.domain.Loja;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author kds
 */
public class LojaDAO extends BD {

    final String INSERIR = "INSERT INTO Loja(nome, endereco) VALUES(?, ?)";
    final String ATUALIZAR = "UPDATE Loja SET nome=?, endereco=? WHERE id = ?";
    final String LISTAR_LOJA = "SELECT nome FROM Loja";
    final String IDLOJA = "SELECT id FROM Loja WHERE nome='";
    final String LISTAR = "SELECT * FROM Loja";
    final String APAGAR = "DELETE FROM Loja WHERE id =?";

    public void salvar(Loja loja) {
        try {
            Connection con = conexao();
            PreparedStatement salvar = con.prepareStatement(INSERIR, Statement.RETURN_GENERATED_KEYS);

            salvar.setString(1, loja.getNome());
            salvar.setString(2, loja.getEndereco());
            salvar.executeUpdate();

            ResultSet rs = salvar.getGeneratedKeys();
            rs = salvar.getGeneratedKeys();
            rs.next();
            loja.setId(rs.getInt("id"));

            salvar.close();
            con.close();
        } catch (SQLException e) {

        }
    }

    public int getID(String nome) {
        int a = 0;
        try {
            Connection con = conexao();
            PreparedStatement getID = con.prepareStatement(IDLOJA + nome + "'");
            ResultSet rs = getID.executeQuery();
            rs.next();
            a = rs.getInt("id");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    public List<Loja> listar() {
        List<Loja> lojas = new ArrayList<>();

        try {
            Connection con = conexao();
            PreparedStatement listar = con.prepareCall(LISTAR);
            ResultSet resultado = listar.executeQuery();

            while (resultado.next()) {
                Loja e = new Loja();
                e.setId(resultado.getInt("id"));
                e.setNome(resultado.getString("nome"));
                e.setEndereco(resultado.getString("endereco"));
                lojas.add(e);
            }

            listar.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lojas;
    }

    public ObservableList<String> listarLoja() {
        ObservableList<String> lojas = FXCollections.observableArrayList();

        try {
            Connection con = conexao();
            PreparedStatement buscar = con.prepareStatement(LISTAR_LOJA);

            ResultSet resultado = buscar.executeQuery();
            while (resultado.next()) {
                lojas.add(resultado.getString("nome"));
            }

            buscar.close();
            con.close();
        } catch (SQLException e) {

        }

        return lojas;
    }

    public void atualizar(Loja loja) {
        try {
            Connection con = conexao();
            PreparedStatement atualizar = con.prepareStatement(ATUALIZAR);

            atualizar.setString(1, loja.getNome());
            atualizar.setString(2, loja.getEndereco());
            atualizar.setInt(3, loja.getId());

            atualizar.executeUpdate();
            atualizar.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void apagar(int id) {
        try {
            Connection con = conexao();
            PreparedStatement apagar = con.prepareStatement(APAGAR);

            apagar.setInt(1, id);
            apagar.execute();

            apagar.close();
            con.close();
        } catch (SQLException e) {

        }
    }

    public Loja extraiLoja(ResultSet resultadoBuscarTodos) throws SQLException {
        Loja loja = new Loja();

        loja.setId(resultadoBuscarTodos.getInt(1));
        loja.setNome(resultadoBuscarTodos.getString(2));
        loja.setEndereco(resultadoBuscarTodos.getString(3));

        return loja;
    }

}
