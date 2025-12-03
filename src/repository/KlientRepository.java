package repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Model.Klient;

public class KlientRepository {

    public void shtoKlient(Klient klient) throws SQLException {
        String sql = "INSERT INTO klient (id, emri, mbiemri) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, klient.getId());
            stmt.setString(2, klient.getEmri());
            stmt.setString(3, klient.getMbiemri());

            stmt.executeUpdate();
        }
    }
}
