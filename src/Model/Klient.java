package Model;

public class Klient {
    private int id;
    private String emri;
    private String mbiemri;

    public Klient(int id, String emri, String mbiemri) {
        if (emri == null || emri.isEmpty() || mbiemri == null || mbiemri.isEmpty()) {
            throw new IllegalArgumentException("Emri dhe mbiemri nuk mund të jenë bosh!");
        }
        this.id = id;
        this.emri = emri;
        this.mbiemri = mbiemri;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getEmri() {
        return emri;
    }

    public String getMbiemri() {
        return mbiemri;
    }

    @Override
    public String toString() {
        return "Klient: " + emri + " " + mbiemri + " (ID: " + id + ")";
    }
}
