package com.model.dao;

import com.model.service.BD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.model.domain.Produto;

/**
 *
 * @author kds
 */
public class ProdutoDAO extends BD {

    final String INSERIR = "INSERT INTO Produto(nome, marca, medida, categoria_id) VALUES(?, ?, ?, ?)";
    final String ATUALIZAR = "UPDATE Produto P, Categoria C SET P.nome=?, P.marca=?, P.medida=?, C.tipo=? WHERE P.id=? AND C.id = P.categoria_id";
    final String LISTAR = "SELECT P.id, P.nome, P.marca, P.medida, C.tipo FROM Produto P, Categoria C WHERE P.categoria_id = C.id";
    final String APAGAR = "DELETE FROM Produto WHERE id=?";
    final String IDPRODUTO = "SELECT id FROM Produto WHERE nome='";
    final String BUSCAR_CATEGORIA = "SELECT id FROM Categoria WHERE tipo='";

    public void salvar(Produto produto) {
        try {
            Connection con = conexao();
            PreparedStatement buscarId = con.prepareStatement(BUSCAR_CATEGORIA + produto.getCategoria_tipo() + "'");
            ResultSet resultado = buscarId.executeQuery();
            resultado.next();
            produto.setCategoria_id(resultado.getInt("id"));
            buscarId.close();

            PreparedStatement salvar = con.prepareStatement(INSERIR, Statement.RETURN_GENERATED_KEYS);

            salvar.setString(1, produto.getNome());
            salvar.setString(2, produto.getMarca());
            salvar.setString(3, produto.getMedida());
            salvar.setInt(4, produto.getCategoria_id());
            salvar.executeUpdate();

            ResultSet rs = salvar.getGeneratedKeys();
            rs.next();
            produto.setId(rs.getInt(1));

            salvar.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Produto> listar() {
        List<Produto> produtos = new ArrayList<>();

        try {
            Connection con = conexao();
            PreparedStatement listar = con.prepareStatement(LISTAR);
            ResultSet resultado = listar.executeQuery();

            while (resultado.next()) {
                Produto produto = extraiProduto(resultado);
                produtos.add(produto);
            }

            listar.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produtos;
    }

    public int getID(String nome) {
        int a = 0;
        try {
            Connection con = conexao();
            PreparedStatement getID = con.prepareStatement(IDPRODUTO + nome + "'");
            ResultSet rs = getID.executeQuery();
            rs.next();
            System.out.println(rs.getInt("id"));
            a = rs.getInt("id");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    public void atualizar(Produto produto) {
        try {
            Connection con = conexao();
            PreparedStatement atualizar = con.prepareStatement(ATUALIZAR);

            atualizar.setString(1, produto.getNome());
            atualizar.setString(2, produto.getMarca());
            atualizar.setString(3, produto.getMedida());
            atualizar.setString(4, produto.getCategoria_tipo());
            atualizar.setInt(5, produto.getId());

            atualizar.executeUpdate();
            atualizar.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void apagar(Produto produto) {
        try {
            Connection con = conexao();
            PreparedStatement apagar = con.prepareStatement(APAGAR);

            apagar.setInt(1, produto.getId());

            apagar.executeUpdate();
            apagar.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Produto extraiProduto(ResultSet resultadoBuscarTodos) throws SQLException {
        Produto produto = new Produto();

        produto.setId(resultadoBuscarTodos.getInt("P.id"));
        produto.setNome(resultadoBuscarTodos.getString("P.nome"));
        produto.setMarca(resultadoBuscarTodos.getString("P.marca"));
        produto.setMedida(resultadoBuscarTodos.getString("P.medida"));
        produto.setCategoria_tipo(resultadoBuscarTodos.getString("C.tipo"));

        return produto;
    }
}
