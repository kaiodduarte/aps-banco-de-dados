package com.model.dao;

import com.model.domain.Compra;
import com.model.service.BD;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kds
 */
public class CompraDAO extends BD {

    final String INSERIR = "INSERT INTO Loja_Produto (quantidade, preco_uni, preco_tot, data, loja_id, produto_id) VALUES(?, ?, ?, ?, ?, ?)";
    final String LISTAR = "SELECT * FROM Loja_Produto C, Loja L, Produto P WHERE P.id = C.produto_id AND L.id = C.loja_id";
    final String APAGAR = "DELETE FROM Loja_Produto WHERE id = ?";
    final String GASTO = "SELECT SUM(preco_tot) AS gasto FROM Loja_Produto";

    public void salvar(Compra compra) {
        try {
            Connection con = conexao();
            PreparedStatement salvar = con.prepareStatement(INSERIR, Statement.RETURN_GENERATED_KEYS);

            salvar.setDouble(1, compra.getQuantidade());
            salvar.setDouble(2, compra.getPrecoUnitario());
            salvar.setDouble(3, compra.getPrecoTotal());
            salvar.setDate(4, Date.valueOf(compra.getData()));
            salvar.setInt(5, compra.getLoja_id());
            salvar.setInt(6, compra.getProduto_id());

            salvar.executeUpdate();

            ResultSet rs = salvar.getGeneratedKeys();
            rs.next();
            compra.setId(rs.getInt(1));

            salvar.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Compra> listar() throws ParseException {
        List<Compra> compras = new ArrayList<>();

        try {
            Connection con = conexao();
            PreparedStatement listar = con.prepareStatement(LISTAR);
            ResultSet resultado = listar.executeQuery();

            while (resultado.next()) {
                Compra compra = extraiCompra(resultado);
                compras.add(compra);
            }

            listar.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return compras;
    }

    public void apagar(Compra compra) {
        try {
            Connection con = conexao();
            PreparedStatement apagar = con.prepareStatement(APAGAR);

            apagar.setInt(1, compra.getId());

            apagar.executeUpdate();
            apagar.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // extrain o objeto Compra do result set
    private Compra extraiCompra(ResultSet resultadoBusca) throws SQLException, ParseException {
        Compra compra = new Compra();

        compra.setData(resultadoBusca.getDate("C.data").toLocalDate());
        compra.setPrecoTotal(resultadoBusca.getDouble("C.preco_tot"));
        compra.setPrecoUnitario(resultadoBusca.getDouble("C.preco_uni"));
        compra.setProduto_nome(resultadoBusca.getString("P.nome"));
        compra.setLoja_nome(resultadoBusca.getString("L.nome"));
        compra.setQuantidade(resultadoBusca.getDouble("C.quantidade"));
        compra.setMarca(resultadoBusca.getString("P.marca"));
        compra.setMedida(resultadoBusca.getString("P.medida"));
        compra.setId(resultadoBusca.getInt("C.id"));

        return compra;
    }

    public Double getGasto() {
        Double res = 0.0;

        try {
            Connection con = conexao();
            PreparedStatement gasto = con.prepareStatement(GASTO);
            ResultSet rs = gasto.executeQuery();
            rs.next();

            res += rs.getDouble("gasto");

            gasto.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
