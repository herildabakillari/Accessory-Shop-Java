package Model;


import java.util.ArrayList;
import java.util.List;

public class Porosi {
    private Klient klienti;
    private List<Produkt> produktet;

    public Porosi(Klient klienti) {
        this.klienti = klienti;
        this.produktet = new ArrayList<>();
    }

    public Klient getKlienti() {
        return klienti;
    }

    public void shtoProdukt(Produkt p) {
        produktet.add(p);
    }

    public List<Produkt> getProduktet() {
        return produktet;
    }

    public double llogaritTotalin() {
        return produktet.stream().mapToDouble(Produkt::getCmimi).sum();
    }
}

