import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

class BelastingInspecteur{
    private int belastingBezoek;

    public int getBelastingBezoek() {
        return belastingBezoek;
    }
    public void setBelastingBezoek(int belastingBezoek) {
        this.belastingBezoek = belastingBezoek;
    }

    void checkAttracties(ArrayList<Attractie> attracties, Kassa kassa) throws  InterruptedException { // yyy
        TimeUnit.MILLISECONDS.sleep(800);
        System.out.println("Aantal bezoeken belastingInspecteur : " + kassa.getAantalBezoekBelastingInspecteur());
        TimeUnit.MILLISECONDS.sleep(800);
        System.out.println("Kermisverkoop nummer = " + kassa.getVerkoopnr() + " , Belastingbezoek op verkoop nummer = " + belastingBezoek );
        TimeUnit.MILLISECONDS.sleep(800);
        System.out.println("Belastinginspecteur komt langs en gaat belasting heffen.");

        kassa.setOmzetGokAttractieTotaal(0); kassa.setKansSpelBelastingTotaal(0);
        for (Attractie attr : attracties){
            if (attr instanceof GokAttractie){
                kassa.setOmzetGokAttractieTotaal(kassa.getOmzetGokAttractieTotaal() + attr.getOmzet());
                kassa.setKansSpelBelastingTotaal(kassa.getKansSpelBelastingTotaal() + ((GokAttractie) attr).kansSpelBelastingBetalen());
            }
        }
    }
    void belastingInnen(Kassa kassa){
        kassa.setKansSpelBelastingNogTeBetalen(kassa.getKansSpelBelastingTotaal() - kassa.getKansSpelBelastingBetaald());
        if (kassa.getKansSpelBelastingNogTeBetalen() == 0) {
            System.out.println("Alle kansspelbelasting is al betaald!");
        }
        else{
            System.out.print("Omzet gokattractie(s) = ");
            System.out.printf("%10.2f",kassa.getOmzetGokAttractieTotaal());
            System.out.print("\n   Kansspel belasting = ");
            System.out.printf("%10.2f",kassa.getKansSpelBelastingTotaal());
            System.out.print("\n        Reeds betaald = ");
            System.out.printf("%10.2f",kassa.getKansSpelBelastingBetaald());
            System.out.print("\n           Te betalen = ");
            System.out.printf("%10.2f",kassa.getKansSpelBelastingNogTeBetalen());
            System.out.println("");
            kassa.setKansSpelBelastingBetaald(kassa.getKansSpelBelastingBetaald() + kassa.getKansSpelBelastingNogTeBetalen());
        }
    }
    void bepaalBelastingBezoek(Random rand, int verkoopnr){
        if ( belastingBezoek <= verkoopnr ) {
            belastingBezoek = verkoopnr + rand.nextInt(28) + 2;
        }
    }
}
