class LadderKlimmen extends Attractie implements GokAttractie {
    LadderKlimmen(String naam, double prijs, double oppervlakte, int capaciteit) {
        super(naam, prijs, oppervlakte, capaciteit);
    }
    @Override
    public double kansSpelBelastingBetalen() {
        return getOmzet() * 0.30;
    }
}
