package org.example.musicaapp.dao;
import org.example.musicaapp.Cidade; import org.example.musicaapp.db.DB;
import java.sql.*; import java.util.*;
public class CidadeDAO {
    public void create(Cidade cdd) throws SQLException {
        try (Connection c=DB.get(); PreparedStatement ps=c.prepareStatement(
            "INSERT INTO musica_cidade (nome,regiao,violenta) VALUES (?,?,?)")) {
            ps.setString(1,cdd.getNome()); ps.setString(2,cdd.getRegiao()); ps.setBoolean(3,cdd.isViolenta()); ps.executeUpdate();
        }
    }
    public Optional<Cidade> findById(long id) throws SQLException {
        try (Connection c=DB.get(); PreparedStatement ps=c.prepareStatement(
            "SELECT id,nome,regiao,violenta FROM musica_cidade WHERE id=?")) {
            ps.setLong(1,id); try (ResultSet rs=ps.executeQuery()){ if(rs.next())
                return Optional.of(new Cidade(rs.getLong(1),rs.getString(2),rs.getString(3),rs.getBoolean(4))); }
        } return Optional.empty();
    }
    public List<Cidade> findAll() throws SQLException {
        List<Cidade> list=new ArrayList<>();
        try (Connection c=DB.get(); PreparedStatement ps=c.prepareStatement(
            "SELECT id,nome,regiao,violenta FROM musica_cidade ORDER BY id");
             ResultSet rs=ps.executeQuery()) {
            while(rs.next()) list.add(new Cidade(rs.getLong(1),rs.getString(2),rs.getString(3),rs.getBoolean(4)));
        } return list;
    }
    public void update(long id, Cidade cdd) throws SQLException {
        try (Connection c=DB.get(); PreparedStatement ps=c.prepareStatement(
            "UPDATE musica_cidade SET nome=?, regiao=?, violenta=? WHERE id=?")) {
            ps.setString(1,cdd.getNome()); ps.setString(2,cdd.getRegiao()); ps.setBoolean(3,cdd.isViolenta()); ps.setLong(4,id); ps.executeUpdate();
        }
    }
    public void delete(long id) throws SQLException {
        try (Connection c=DB.get(); PreparedStatement ps=c.prepareStatement("DELETE FROM musica_cidade WHERE id=?")) {
            ps.setLong(1,id); ps.executeUpdate();
        }
    }
    public long createReturningId(Cidade cdd) throws SQLException {
        try (var c = DB.get(); var ps = c.prepareStatement(
                "INSERT INTO musica_cidade (nome,regiao,violenta) VALUES (?,?,?)",
                java.sql.Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, cdd.getNome());
            ps.setString(2, cdd.getRegiao());
            ps.setBoolean(3, cdd.isViolenta());
            ps.executeUpdate();
            try (var rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        throw new SQLException("Não foi possível obter o ID gerado de Cidade.");
    }

}
