import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

class SpeelKermis {
    private Random rand   = new Random();
    private Scanner reader = new Scanner(System.in);
    private Kassa   kassa  = new Kassa();
    private ArrayList<Attractie> attracties = new ArrayList<>();
    private BelastingInspecteur belastingInspecteur = new BelastingInspecteur();
    boolean invoeren = false;

    SpeelKermis() throws InterruptedException {
        attracties.add(new BotsAuto     ("BotsAuto"     , 2.50, 100, 10));
        attracties.add(new Spin         ("Spin"         , 2.25, 75 , 5, 5,false));
        attracties.add(new SpiegelPaleis("SpiegelPaleis", 2.75, 88 , 15));
        attracties.add(new SpookHuis    ("SpookHuis"    , 3.20, 200, 20));
        attracties.add(new Hawaii       ("Hawaii"       , 2.90, 400, 10, 10,false));
        attracties.add(new LadderKlimmen("LadderKlimmen", 5   , 75 , 8));
        allesAan();
        openen();
    }
    void openen() throws InterruptedException {
        char input = 'a';
        invoeren = false;
        kassa.setVerkoopnr(0);
        do{
           do {
               kassa.toonMenu("Menu opties", attracties);
               try {
                   input = reader.nextLine().charAt(0);
                   invoeren = true;
               }
               catch (StringIndexOutOfBoundsException e){
                   invoeren = false;
               }
           } while ( invoeren==false );
           invoer(input);
        } while ( input != 'q');
        System.out.println("Kermis Spelen is beeindigd");
    }

    void invoer(char input) throws InterruptedException {
        switch (input) {
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6': attractieStartenStoppen(input);break;
            case 'b': kassa.bezoekBelastingInspecteur(belastingInspecteur,attracties,rand);break;
            case 'k': kassa.toonMenu("Kaart Omzet",attracties);break;
            case 'o': kassa.toonMenu("Omzet",attracties);break;
            case 'v': kassa.kaartVerkoop(attracties,belastingInspecteur,rand);
                      kassa.toonMenu("Kaart Omzet",attracties);TimeUnit.SECONDS.sleep(3);
                      attractieDraaien(); break;
            case 'q': break;
            case 't': toonAttractie();break;
            default : System.out.println("Verkeerde invoer!");break;
        }
    }
    void attractieStartenStoppen(int indexWaarde) {
        char input= (char)( '0' + indexWaarde + 1);
        attractieStartenStoppen(input);
    }
    void attractieStarten(int index) {
        attracties.get(index).setIsDraait(true);
    }
    void attractieStoppen(int index) {
        attracties.get(index).setIsDraait(false);
    }
    void controleGekeurd(int i)throws InterruptedException{
        if (!((RisicoRijkeAttracties) attracties.get(i)).isGekeurd()) {
            System.out.println(attracties.get(i).getNaam() +" niet gekeurd, wordt nu gekeurd..");TimeUnit.MILLISECONDS.sleep(800);
            attractieStoppen(i);
            System.out.println(attracties.get(i).getNaam() + statusDraai(attracties.get(i).getIsDraait()));TimeUnit.MILLISECONDS.sleep(800);
            ((RisicoRijkeAttracties) attracties.get(i)).opstellingsKeuring();
            attractieStarten(i);
            System.out.println(attracties.get(i).getNaam() + statusDraai(attracties.get(i).getIsDraait()));TimeUnit.MILLISECONDS.sleep(800);
            System.out.println(attracties.get(i));TimeUnit.MILLISECONDS.sleep(800);
        }
    }
    void attractieStartenStoppen(char input){
        int i = Character.getNumericValue(input) - 1;
        attracties.get(i).setIsDraait(changeSwitch(attracties.get(i).getIsDraait()));
    }
    void attractieDraaien() throws InterruptedException {
        System.out.println("==================================");
        System.out.println("draai attractie");
        int aantalOnverwerkt=0, hlp_atlDraaien=0, hlp_atlDraaienStd=0, hlp_limDraaien=0;
        for (int i = 0; i < attracties.size(); i++) {
            hlp_atlDraaienStd = 0;
            if (attracties.get(i).isDraait()) {
                aantalOnverwerkt = attracties.get(i).getAantalKaartjesVerkocht() - attracties.get(i).getAantalKaartjesVerwerkt();
                if (aantalOnverwerkt == 0 || aantalOnverwerkt <=  attracties.get(i).getCapaciteit() * 0.75) continue;
                System.out.println("Begin..." + attracties.get(i));TimeUnit.SECONDS.sleep(3);
                if (attracties.get(i) instanceof RisicoRijkeAttracties){
                    controleGekeurd(i);
                }
                while (aantalOnverwerkt != 0 && aantalOnverwerkt > attracties.get(i).getCapaciteit() * 0.75) {
                    System.out.print("instappen max(" + attracties.get(i).getCapaciteit() + "): ");
                    for (int instap = 1;
                         instap <= attracties.get(i).getCapaciteit() && aantalOnverwerkt > 0;
                         instap++, aantalOnverwerkt--) {
                        System.out.print( instap + ", " ) ;
                        TimeUnit.MILLISECONDS.sleep(250);
                        attracties.get(i).setAantalKaartjesVerwerkt(attracties.get(i).getAantalKaartjesVerwerkt()+1 );
                    }
                    if (attracties.get(i) instanceof RisicoRijkeAttracties){
                        ((RisicoRijkeAttracties) attracties.get(i)).setAantalKeerGedraaid(((RisicoRijkeAttracties)attracties.get(i)).getAantalKeerGedraaid() + 1);
                        hlp_atlDraaien = ((RisicoRijkeAttracties) attracties.get(i)).getAantalKeerGedraaid();
                        hlp_limDraaien = ((RisicoRijkeAttracties) attracties.get(i)).getDraaiLimiet();
                        draaiStopUitstappen(hlp_atlDraaien,aantalOnverwerkt);
                        checkOnderhoudNodig(hlp_atlDraaien, hlp_limDraaien,i);
                    }
                    else {
                        hlp_atlDraaienStd++;
                        draaiStopUitstappen(hlp_atlDraaienStd,aantalOnverwerkt);
                    }
                }
                System.out.println("\nEind..." + attracties.get(i)+"\n");
                System.out.print("========================================================================================");
                System.out.println("======================================");
            }
        }
    }
    void draaiStopUitstappen (int atlDraaien, int atlOnverwerkt)throws InterruptedException {
        System.out.print("draaien");
        for (int k=1; k<=7; k++ ) {
            System.out.print(".");
            TimeUnit.MILLISECONDS.sleep(500);
        }
        System.out.print("...gestopt: " + atlDraaien + " maal, ");TimeUnit.MILLISECONDS.sleep(800);
        System.out.print("uitstappen.");TimeUnit.MILLISECONDS.sleep(800);
        System.out.println(" Aantal in de wachtrij: " + atlOnverwerkt);TimeUnit.MILLISECONDS.sleep(800);
    }
    void checkOnderhoudNodig(int hlp_atlDraaien,int hlp_limDraaien, int index)throws InterruptedException{
        try{
            if  ((hlp_atlDraaien > 0) && (hlp_atlDraaien % hlp_limDraaien == 0))
                throw new DraaiLimietException();
        }
        catch (DraaiLimietException e) {
            System.out.print("\nOnderhoudsbeurt nodig\n");TimeUnit.MILLISECONDS.sleep(800);
            System.out.print("Aanroep onderhoudsmonteur\n");TimeUnit.MILLISECONDS.sleep(800);
            attractieStoppen(index);
            System.out.println(attracties.get(index).getNaam() + statusDraai(attracties.get(index).getIsDraait()));TimeUnit.MILLISECONDS.sleep(800);
            ((RisicoRijkeAttracties) attracties.get(index)).onderhoudsbeurt();
            attractieStarten(index);
            System.out.println(attracties.get(index).getNaam() + statusDraai(attracties.get(index).getIsDraait()));TimeUnit.MILLISECONDS.sleep(800);
        }
    }
    void toonAttractie() {
        for(Attractie attr: attracties){
            System.out.println(attr);
        }
    }
    void allesAan() {
        for (int i = 0; i < attracties.size(); i++) {
            attracties.get(i).setIsDraait(changeSwitch(attracties.get(i).getIsDraait()));
        }
    }
    static String isDraait(boolean isDraait) {
        return (isDraait) ? "gestart" : "        gestopt";
    }
    static String statusDraai(boolean isDraait) {
        return (isDraait) ? " gestart" : " gestopt";
    }
    static boolean changeSwitch(boolean isDraait) {
        return !isDraait;
    }
}
