package com.model.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author kds
 */
public abstract class BD {
    // dados para acesso ao banco, atualize de acordo com o seu banco de dados

    final String USUARIO = "kds";
    final String SENHA = "";
    final String URL_BANCO = "jdbc:mysql://localhost:3306/APS";

    // constantes de acesso
    final String CLASSE_DRIVER = "com.mysql.jdbc.Driver";

    // abre uma nova conexão com o banco de dados. Se algum erro for lançado
    // aqui, verifique o erro com atenção e se o banco está rodando
    public Connection conexao() {
        try {
            Class.forName(CLASSE_DRIVER);
            return DriverManager.getConnection(URL_BANCO, USUARIO, SENHA);
        } catch (ClassNotFoundException | SQLException e) {
            if (e instanceof ClassNotFoundException) {
                System.err.println("VERIFIQUE SE O DRIVER DO BANCO DE DADOS ESTÁ NO CLASSPATH");
            } else {
                System.err.println("VERIFIQUE SE O BANCO ESTÁ RODANDO E SE OS DADOS DE CONEXÃO ESTÃO CORRETOS");
            }

            System.exit(0);
            // o sistema deverá sair antes de chegar aqui...
            return null;
        }
    }
}
