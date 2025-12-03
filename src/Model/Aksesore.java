package Model;


public class Aksesore extends Produkt {
    private String lloji;

    public Aksesore(String emri, double cmimi, String lloji) {
        super(emri, cmimi);
        if (lloji == null || lloji.isEmpty())
            throw new IllegalArgumentException("Lloji nuk mund të jetë bosh!");
        this.lloji = lloji;
    }

    public String getLloji() {
        return lloji;
    }

    @Override
    public String toString() {
        return emri + " (Aksesore) - " + cmimi + "€, Lloji: " + lloji;
    }
}
