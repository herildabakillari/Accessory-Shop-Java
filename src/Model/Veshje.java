package Model;


public class Veshje extends Produkt {
    private String masa;
    private String materiali;

    public Veshje(String emri, double cmimi, String masa, String materiali) {
        super(emri, cmimi);
        if (masa == null || masa.isEmpty())
            throw new IllegalArgumentException("Masa nuk mund të jetë bosh!");
        if (materiali == null || materiali.isEmpty())
            throw new IllegalArgumentException("Materiali nuk mund të jetë bosh!");
        this.masa = masa;
        this.materiali = materiali;
    }

    public String getMasa() {
        return masa;
    }
    public String getMateriali() {
        return materiali;
    }

    @Override
    public String toString() {
        return emri + " (Veshje) - " + cmimi + "€, Masa: " + masa + ", Materiali: " + materiali;
    }
}

