import java.util.concurrent.TimeUnit;

class Hawaii extends RisicoRijkeAttracties {
    Hawaii(String naam, double prijs, double oppervlakte, int capaciteit, int draaiLimiet, boolean gekeurd) {
        super(naam, prijs, oppervlakte, capaciteit, draaiLimiet, gekeurd);
    }
    @Override
    void onderhoudsbeurt()throws InterruptedException {
        System.out.print("Onderhoudsbeurt voor de Hawaii\nOnderhoudsbeurt klaar\n");
        TimeUnit.MILLISECONDS.sleep(800);
    }
    @Override
    void opstellingsKeuring() throws InterruptedException {
        System.out.print("Opstellingskeuring voor de Hawaii\nHawaii is goedgekeurd\n");
        TimeUnit.MILLISECONDS.sleep(800);
        setGekeurd(true);
    }
}
