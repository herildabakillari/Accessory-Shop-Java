package Model;


public abstract class Produkt {
    protected int id;
    protected String emri;
    protected double cmimi;

    public Produkt(String emri, double cmimi) {
        if (emri == null || emri.isEmpty()) {
            throw new IllegalArgumentException("Emri i produktit nuk mund të jetë bosh!");
        }
        if (cmimi < 0) {
            throw new IllegalArgumentException("Çmimi nuk mund të jetë negativ!");
        }
        this.emri = emri;
        this.cmimi = cmimi;
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
    public double getCmimi() {
        return cmimi;
    }

    public abstract String toString();
}

