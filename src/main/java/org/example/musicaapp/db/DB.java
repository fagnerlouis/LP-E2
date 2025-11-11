package org.example.musicaapp.db;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Properties;

public class DB {
    // Config básicas do banco. Deixo em estático para usar de qualquer lugar.
    private static String url, user, password;

    // Bloco estático roda 1x quando a classe é carregada.
    static {
        try {
            // 1) Lê as configs do arquivo db.properties (classpath: resources/)
            Properties p = new Properties();
            try (InputStream in = DB.class.getClassLoader().getResourceAsStream("db.properties")) {
                if (in == null) throw new RuntimeException("db.properties não encontrado");
                p.load(in);
            }

            // 2) Monta a URL do JDBC (com defaults caso algo não venha no arquivo)
            String host = p.getProperty("db.host", "localhost");
            String port = p.getProperty("db.port", "3306");
            String name = p.getProperty("db.name", "projeto2");
            user = p.getProperty("db.user", "root");
            password = p.getProperty("db.password", "");
            url = "jdbc:mysql://" + host + ":" + port + "/" + name
                    + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

            // 3) Garante o driver do MySQL (por via das dúvidas)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 4) Sobe o schema na primeira carga (se o arquivo existir)
            runSchema();

        } catch (Exception e) {
            // Se algo der ruim aqui, é melhor falhar cedo com uma mensagem clara
            throw new RuntimeException("Erro ao inicializar DB: " + e.getMessage(), e);
        }
    }

    // Método simples para pegar uma conexão já com a URL pronta
    public static Connection get() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    // Lê o schema.sql do resources e executa. Se não existir, segue o jogo.
    private static void runSchema() {
        // Tenta achar o arquivo no classpath
        InputStream in = DB.class.getClassLoader().getResourceAsStream("schema.sql");
        if (in == null) {
            System.out.println("ℹ️ schema.sql não encontrado — seguindo sem criação automática.");
            return;
        }

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement st = conn.createStatement();
             BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {

            // Junta o arquivo todo num único String
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line).append('\n');

            // Divide por ';' e executa cada comando simples
            for (String raw : sb.toString().split(";")) {
                String sql = raw.trim();
                if (!sql.isEmpty()) st.execute(sql);
            }

            System.out.println("✅ schema.sql executado (banco/tabelas garantidos).");

        } catch (Exception e) {
            System.err.println("❌ Erro ao rodar schema.sql: " + e.getMessage());
        }
    }
}