package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = System.getenv().getOrDefault(
        "ARUS_DB_URL",
        "jdbc:mysql://localhost:10004/arus_crm?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
    );
    private static final String USUARIO = System.getenv().getOrDefault("ARUS_DB_USER", "root");
    private static final String PASSWORD = System.getenv().getOrDefault("ARUS_DB_PASSWORD", "root");

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}