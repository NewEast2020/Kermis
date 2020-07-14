abstract class RisicoRijkeAttracties extends Attractie {
    private boolean isGekeurd;
    private int     draaiLimiet;
    private int     aantalKeerGedraaid;

    public boolean isGekeurd() {
        return isGekeurd;
    }
    public void setGekeurd(boolean gekeurd) {
        isGekeurd = gekeurd;
    }
    public int getDraaiLimiet() {
        return draaiLimiet;
    }
    public int getAantalKeerGedraaid() {
        return aantalKeerGedraaid;
    }
    public void setAantalKeerGedraaid(int aantalKeerGedraaid) {
        this.aantalKeerGedraaid = aantalKeerGedraaid;
    }

    RisicoRijkeAttracties(String naam, double prijs, double oppervlakte, int capaciteit, int draaiLimiet, boolean gekeurd) {
        super(naam, prijs, oppervlakte, capaciteit);
        this.draaiLimiet = draaiLimiet;
        this.isGekeurd = gekeurd;
    }

    abstract void onderhoudsbeurt()throws InterruptedException;
    abstract void opstellingsKeuring()throws InterruptedException;

    @Override
    public String toString() {
        return super.toString() + ", Gekeurd=" + isJaNee(isGekeurd) + ", DraaiLimiet=" + draaiLimiet +
                ", x Gedraaid=" + aantalKeerGedraaid;
    }
}
