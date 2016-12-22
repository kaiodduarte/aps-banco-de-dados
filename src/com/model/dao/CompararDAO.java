package com.model.dao;

import com.model.domain.Comparar;
import com.model.service.BD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kds
 */
public class CompararDAO extends BD {

    final String COMPARAR = "SELECT DISTINCT L.nome, P.nome, P.marca, C.preco_uni, C.data FROM Loja L, Produto P, Loja_Produto C WHERE L.id = C.loja_id AND P.id = C.produto_id AND P.categoria_id =?";
    final String LISTAR = "SELECT DISTINCT L.nome, P.nome, P.marca, C.preco_uni, C.data FROM Loja L, Produto P, Loja_Produto C WHERE L.id = C.loja_id AND P.id = C.produto_id";
    final String BUSCAR_CATEGORIA = "SELECT id FROM Categoria WHERE tipo='";

    public List<Comparar> listar() throws ParseException {
        List<Comparar> cp = new ArrayList<>();

        try {
            Connection con = conexao();
            PreparedStatement listar = con.prepareStatement(LISTAR);
            ResultSet rs = listar.executeQuery();

            while (rs.next()) {
                Comparar c = new Comparar();
                c.setLoja_nome(rs.getString(1));
                c.setProduto_nome(rs.getString(2));
                c.setProduto_marca(rs.getString(3));
                c.setPreco_unitario(rs.getDouble(4));
                c.setData(rs.getDate(5).toLocalDate());

                cp.add(c);
            }

            listar.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cp;
    }

    public List<Comparar> comparar(String categoria) {
        List<Comparar> cp = new ArrayList<>();

        try {
            Connection con = conexao();
            PreparedStatement buscarId = con.prepareStatement(BUSCAR_CATEGORIA + categoria + "'");
            ResultSet resultado = buscarId.executeQuery();
            resultado.next();

            PreparedStatement comparar = con.prepareStatement(COMPARAR);
            comparar.setInt(1, resultado.getInt("id"));
            ResultSet rs = comparar.executeQuery();

            while (rs.next()) {
                Comparar c = new Comparar();
                c.setLoja_nome(rs.getString(1));
                c.setProduto_nome(rs.getString(2));
                c.setProduto_marca(rs.getString(3));
                c.setPreco_unitario(rs.getDouble(4));
                c.setData(rs.getDate(5).toLocalDate());

                cp.add(c);
            }

            comparar.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cp;
    }
}
