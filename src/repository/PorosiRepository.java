package repository;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Model.Porosi;
import Model.Produkt;

public class PorosiRepository {

    public int shtoFature(Porosi porosi) throws SQLException {
        String sqlFature = "INSERT INTO fature (klient_id, data_porosis, total) VALUES (?, ?, ?)";
        int fatureId = -1;

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sqlFature, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, porosi.getKlienti().getId());
                stmt.setDate(2, new Date(System.currentTimeMillis()));
                stmt.setDouble(3, porosi.llogaritTotalin());

                stmt.executeUpdate();

                try (var rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        fatureId = rs.getInt(1);
                    } else {
                        throw new SQLException("Nuk u gjenerua ID për faturën!");
                    }
                }
            }

            // Insert produktet e porosisë
            String sqlFatureProdukt = "INSERT INTO fature_produkt (fature_id, produkt_id, sasia, cmimi) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt2 = conn.prepareStatement(sqlFatureProdukt)) {
                for (Produkt p : porosi.getProduktet()) {
                    if (p.getId() <= 0) {
                        throw new SQLException("Produkti nuk ka ID valide në DB!");
                    }
                    stmt2.setInt(1, fatureId);
                    stmt2.setInt(2, p.getId());
                    stmt2.setInt(3, 1); // sasia e produktit, për momentin 1
                    stmt2.setDouble(4, p.getCmimi());
                    stmt2.addBatch();
                }
                stmt2.executeBatch();
            }

            conn.commit();
            conn.setAutoCommit(true);
        }

        return fatureId;
    }
}
