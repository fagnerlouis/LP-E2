package org.example.musicaapp.dao;
import org.example.musicaapp.Personagem; import org.example.musicaapp.db.DB;
import java.sql.*; import java.util.*;
public class PersonagemDAO {
    public void create(Personagem p) throws SQLException {
        try (Connection c=DB.get(); PreparedStatement ps=c.prepareStatement(
            "INSERT INTO musica_personagem (nome,origem,vivo) VALUES (?,?,?)")) {
            ps.setString(1,p.getNome()); ps.setString(2,p.getOrigem()); ps.setBoolean(3,p.isVivo()); ps.executeUpdate();
        }
    }
    public Optional<Personagem> findById(long id) throws SQLException {
        try (Connection c=DB.get(); PreparedStatement ps=c.prepareStatement(
            "SELECT id,nome,origem,vivo FROM musica_personagem WHERE id=?")) {
            ps.setLong(1,id); try (ResultSet rs=ps.executeQuery()){ if(rs.next())
                return Optional.of(new Personagem(rs.getLong(1),rs.getString(2),rs.getString(3),rs.getBoolean(4))); }
        } return Optional.empty();
    }
    public List<Personagem> findAll() throws SQLException {
        List<Personagem> list=new ArrayList<>();
        try (Connection c=DB.get(); PreparedStatement ps=c.prepareStatement(
            "SELECT id,nome,origem,vivo FROM musica_personagem ORDER BY id");
             ResultSet rs=ps.executeQuery()) {
            while(rs.next()) list.add(new Personagem(rs.getLong(1),rs.getString(2),rs.getString(3),rs.getBoolean(4)));
        } return list;
    }
    public void update(long id, Personagem p) throws SQLException {
        try (Connection c=DB.get(); PreparedStatement ps=c.prepareStatement(
            "UPDATE musica_personagem SET nome=?, origem=?, vivo=? WHERE id=?")) {
            ps.setString(1,p.getNome()); ps.setString(2,p.getOrigem()); ps.setBoolean(3,p.isVivo()); ps.setLong(4,id); ps.executeUpdate();
        }
    }
    public void delete(long id) throws SQLException {
        try (Connection c=DB.get(); PreparedStatement ps=c.prepareStatement("DELETE FROM musica_personagem WHERE id=?")) {
            ps.setLong(1,id); ps.executeUpdate();
        }
    }

    public long createReturningId(Personagem p) throws SQLException {
        try (var c = DB.get(); var ps = c.prepareStatement(
                "INSERT INTO musica_personagem (nome,origem,vivo) VALUES (?,?,?)",
                java.sql.Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getOrigem());
            ps.setBoolean(3, p.isVivo());
            ps.executeUpdate();
            try (var rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        throw new SQLException("Não foi possível obter o ID gerado de Personagem.");
    }

}
