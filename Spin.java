import java.util.concurrent.TimeUnit;

class Spin extends RisicoRijkeAttracties implements GokAttractie{
    Spin(String naam, double prijs, double oppervlakte, int capaciteit, int draaiLimiet, boolean gekeurd) {
        super(naam, prijs, oppervlakte, capaciteit, draaiLimiet, gekeurd);
    }
    @Override
    void onderhoudsbeurt() throws InterruptedException {
        System.out.print("Onderhoudsbeurt voor de spin\nOnderhoudsbeurt klaar\n");
        TimeUnit.MILLISECONDS.sleep(800);
    }
    @Override
    void opstellingsKeuring()throws InterruptedException{
        System.out.print("Opstellingskeuring voor de spin\nSpin is goedgekeurd\n");
        TimeUnit.MILLISECONDS.sleep(800);
        setGekeurd(true);
    }
    @Override
    public double kansSpelBelastingBetalen() {
        return getOmzet() * 0.30;
    }
}
