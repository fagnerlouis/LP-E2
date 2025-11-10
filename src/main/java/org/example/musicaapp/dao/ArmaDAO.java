package org.example.musicaapp.dao;
import org.example.musicaapp.Arma; import org.example.musicaapp.db.DB;
import java.sql.*; import java.util.*;
public class ArmaDAO {
    public void create(Arma a) throws SQLException {
        try (Connection c=DB.get(); PreparedStatement ps=c.prepareStatement(
            "INSERT INTO musica_arma (modelo,qtd_tiros,dono) VALUES (?,?,?)")) {
            ps.setString(1,a.getModelo()); ps.setInt(2,a.getQtdTiros()); ps.setString(3,a.getDono()); ps.executeUpdate();
        }
    }
    public Optional<Arma> findById(long id) throws SQLException {
        try (Connection c=DB.get(); PreparedStatement ps=c.prepareStatement(
            "SELECT id,modelo,qtd_tiros,dono FROM musica_arma WHERE id=?")) {
            ps.setLong(1,id); try (ResultSet rs=ps.executeQuery()){ if(rs.next())
                return Optional.of(new Arma(rs.getLong(1),rs.getString(2),rs.getInt(3),rs.getString(4))); }
        } return Optional.empty();
    }
    public List<Arma> findAll() throws SQLException {
        List<Arma> list=new ArrayList<>();
        try (Connection c=DB.get(); PreparedStatement ps=c.prepareStatement(
            "SELECT id,modelo,qtd_tiros,dono FROM musica_arma ORDER BY id");
             ResultSet rs=ps.executeQuery()) {
            while(rs.next()) list.add(new Arma(rs.getLong(1),rs.getString(2),rs.getInt(3),rs.getString(4)));
        } return list;
    }
    public void update(long id, Arma a) throws SQLException {
        try (Connection c=DB.get(); PreparedStatement ps=c.prepareStatement(
            "UPDATE musica_arma SET modelo=?, qtd_tiros=?, dono=? WHERE id=?")) {
            ps.setString(1,a.getModelo()); ps.setInt(2,a.getQtdTiros()); ps.setString(3,a.getDono()); ps.setLong(4,id); ps.executeUpdate();
        }
    }
    public void delete(long id) throws SQLException {
        try (Connection c=DB.get(); PreparedStatement ps=c.prepareStatement("DELETE FROM musica_arma WHERE id=?")) {
            ps.setLong(1,id); ps.executeUpdate();
        }
    }

    public long createReturningId(Arma a) throws SQLException {
        try (var c = DB.get(); var ps = c.prepareStatement(
                "INSERT INTO musica_arma (modelo,qtd_tiros,dono) VALUES (?,?,?)",
                java.sql.Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, a.getModelo());
            ps.setInt(2, a.getQtdTiros());
            ps.setString(3, a.getDono());
            ps.executeUpdate();
            try (var rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        throw new SQLException("Não foi possível obter o ID gerado de Arma.");
    }
}
