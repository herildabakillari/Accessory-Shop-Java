package repository;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Produkt;

public class ProduktRepository {

    public int shtoProdukt(Produkt produkt, String lloji, String ekstra) throws SQLException {
        String sql = "INSERT INTO produkt (emri, cmimi, lloji, ekstra) VALUES (?, ?, ?, ?)";
        int id = -1;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, produkt.getEmri());
            stmt.setDouble(2, produkt.getCmimi());
            stmt.setString(3, lloji);
            stmt.setString(4, ekstra);

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }
        }

        return id;
    }
}
