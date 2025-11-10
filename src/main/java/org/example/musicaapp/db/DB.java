package org.example.musicaapp.db;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class DB {
    private static String url, user, password;

    static {
        try {
            // Carrega configuração
            Properties p = new Properties();
            try (InputStream in = DB.class.getClassLoader().getResourceAsStream("db.properties")) {
                if (in == null) throw new RuntimeException("db.properties não encontrado");
                p.load(in);
            }

            String host = p.getProperty("db.host", "localhost");
            String port = p.getProperty("db.port", "3306");
            String name = p.getProperty("db.name", "projeto2");
            user = p.getProperty("db.user", "root");
            password = p.getProperty("db.password", "");
            url = "jdbc:mysql://" + host + ":" + port + "/" + name
                    + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

            // Carrega driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Executa schema.sql automaticamente
            runSchema();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inicializar DB: " + e.getMessage());
        }
    }

    public static Connection get() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private static void runSchema() {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://" + getHostPort() + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                user, password);
             Statement st = conn.createStatement()) {

            InputStream schemaStream = DB.class.getClassLoader().getResourceAsStream("schema.sql");
            if (schemaStream == null) {
                System.err.println("⚠️ schema.sql não encontrado, pulando criação automática.");
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(schemaStream));
            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sql.append(line).append("\n");
            }

            // Divide em comandos por ponto e vírgula
            for (String cmd : sql.toString().split(";")) {
                String trimmed = cmd.trim();
                if (!trimmed.isEmpty()) {
                    st.execute(trimmed);
                }
            }
            System.out.println("✅ schema.sql executado com sucesso (banco e tabelas garantidos).");

        } catch (Exception e) {
            System.err.println("Erro ao rodar schema.sql: " + e.getMessage());
        }
    }

    private static String getHostPort() {
        try {
            Properties p = new Properties();
            try (InputStream in = DB.class.getClassLoader().getResourceAsStream("db.properties")) {
                if (in == null) return "localhost:3306";
                p.load(in);
            }
            return p.getProperty("db.host", "localhost") + ":" + p.getProperty("db.port", "3306");
        } catch (Exception e) {
            return "localhost:3306";
        }
    }
}