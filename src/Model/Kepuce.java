package Model;

public class Kepuce extends Produkt {
    private int numri;

    public Kepuce(String emri, double cmimi, int numri) {
        super(emri, cmimi);
        if (numri <= 0)
            throw new IllegalArgumentException("Numri i këpucës nuk mund të jetë zero ose negativ!");
        this.numri = numri;
    }

    public int getNumri() {
        return numri;
    }

    @Override
    public String toString() {
        return emri + " (Këpucë) - " + cmimi + "€, Numri: " + numri;
    }
}

