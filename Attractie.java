class Attractie {
    private String  naam;
    private double  prijs;
    private double  omzet;
    private double  oppervlakte;
    private int     aantalKaartjesVerkocht;
    private int     aantalKaartjesVerwerkt;
    private int     capaciteit;
    private boolean isDraait;

    public String getNaam() {
        return naam;
    }
    public double getOmzet() {
        return omzet;
    }
    public int getAantalKaartjesVerkocht() {
        return aantalKaartjesVerkocht;
    }
    public int getAantalKaartjesVerwerkt() {
        return aantalKaartjesVerwerkt;
    }
    public void setAantalKaartjesVerwerkt(int aantalKaartjesVerwerkt) {
        this.aantalKaartjesVerwerkt = aantalKaartjesVerwerkt;
    }
    public int getCapaciteit() {
        return capaciteit;
    }
    public boolean isDraait() {
        return isDraait;
    }
    Attractie(String naam, double prijs, double oppervlakte, int capaciteit) {
        this.naam        = naam;
        this.prijs       = prijs;
        this.oppervlakte = oppervlakte;
        this.capaciteit  = capaciteit;
    }
    @Override
    public String toString() {
        return " Naam=" + naam + ", Prijs=" + String.format("%.2f", prijs) +  ", Omzet=" + String.format("%.2f", omzet) +
               ", Kaartjes verkocht=" + aantalKaartjesVerkocht + ", Verwerkt=" + aantalKaartjesVerwerkt +
               ", Draait=" + isJaNee(isDraait) + ", Capaciteit=" + capaciteit;
    }

    void koopKaartjes(int aantal, Kassa kassa) {
        omzet += (aantal * prijs);
        kassa.setTotaleOmzet(kassa.getTotaleOmzet() + (aantal * prijs));
        aantalKaartjesVerkocht += aantal;
        kassa.setTotaalKaartVerkoop(kassa.getTotaalKaartVerkoop() + aantal);
    }
    void setIsDraait(boolean draait) {
        isDraait = draait;
    }
    boolean getIsDraait() {
        return isDraait;
    }
    static String isJaNee(boolean isJaNee) {
        return (isJaNee) ? "Ja" : "Nee";
    }
}
