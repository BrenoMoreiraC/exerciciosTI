package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    // *** ALTERE ESTAS CONFIGURAÇÕES CONFORME SEU BANCO ***
	private static final String URL = "jdbc:postgresql://localhost:5432/ti2cc_banco";
    private static final String USUARIO = "postgres";
    private static final String SENHA   = "1008";

    public static Connection conectar() {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver PostgreSQL não encontrado! Verifique se o .jar está no Build Path.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    public static void fechar(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
