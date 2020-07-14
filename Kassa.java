import java.util.ArrayList;
import java.util.Random;

class Kassa{
    private int aantalBezoekBelastingInspecteur;
    private int totaalKaartVerkoop;
    private int verkoopnr;
    private double totaleOmzet;
    private double omzetGokAttractieTotaal;
    private double kansSpelBelastingTotaal;
    private double kansSpelBelastingBetaald;
    private double kansSpelBelastingNogTeBetalen;

    public void setTotaalKaartVerkoop(int totaalKaartVerkoop) {
        this.totaalKaartVerkoop = totaalKaartVerkoop;
    }
    public void setVerkoopnr(int verkoopnr) {
        this.verkoopnr = verkoopnr;
    }
    public void setTotaleOmzet(double totaleOmzet) {
        this.totaleOmzet = totaleOmzet;
    }
    public void setOmzetGokAttractieTotaal(double omzetGokAttractieTotaal) {
        this.omzetGokAttractieTotaal = omzetGokAttractieTotaal;
    }
    public void setKansSpelBelastingTotaal(double kansSpelBelastingTotaal) {
        this.kansSpelBelastingTotaal = kansSpelBelastingTotaal;
    }
    public void setKansSpelBelastingBetaald(double kansSpelBelastingBetaald) {
        this.kansSpelBelastingBetaald = kansSpelBelastingBetaald;
    }
    public void setKansSpelBelastingNogTeBetalen(double kansSpelBelastingNogTeBetalen) {
        this.kansSpelBelastingNogTeBetalen = kansSpelBelastingNogTeBetalen;
    }
    public int getTotaalKaartVerkoop() {
        return totaalKaartVerkoop;
    }
    public int getVerkoopnr() {
        return verkoopnr;
    }
    public double getTotaleOmzet() {
        return totaleOmzet;
    }
    public double getOmzetGokAttractieTotaal() {
        return omzetGokAttractieTotaal;
    }
    public double getKansSpelBelastingTotaal() {
        return kansSpelBelastingTotaal;
    }
    public double getKansSpelBelastingBetaald() {
        return kansSpelBelastingBetaald;
    }
    public double getKansSpelBelastingNogTeBetalen() {
        return kansSpelBelastingNogTeBetalen;
    }
    public int getAantalBezoekBelastingInspecteur(){
        return this.aantalBezoekBelastingInspecteur;
    }

    void toonMenuKop(String keus) {
        if (keus.equals("Menu opties")) {
            System.out.println("========== Menu opties ==========");
        } else if (keus.equals("Omzet")) {
            System.out.println("====================== Omzet == Kansspel ==");
            System.out.printf("%15.15s", "Verkoopnummer:");
            System.out.printf("%6d",this.verkoopnr);
            System.out.printf("%20.20s", "Belasting:");
            System.out.println("");
        } else if (keus.equals("Kaart Omzet")) {
            System.out.println("========== Kaart Omzet ===========");
        }
    }
    void toonMenu(String keus, ArrayList<Attractie> attracties) {
        this.totaleOmzet = 0;
        this.totaalKaartVerkoop =0;
        this.toonMenuKop(keus);
        int k=0;
        for (Attractie attr : attracties){
            System.out.print("[" + ++k + "] ");
            System.out.printf("%15.15s", attr.getNaam() + ": ");
            if (keus.equals("Menu opties")) {
                System.out.print(SpeelKermis.isDraait(attr.isDraait()));
            }  else if (keus.equals("Omzet")) {
                System.out.printf("%9.2f", attr.getOmzet());
                if (attr instanceof GokAttractie) {
                    System.out.printf("%11.2f",(((GokAttractie) attr).kansSpelBelastingBetalen()));
                }
                this.totaleOmzet += attr.getOmzet();
            } else if (keus.equals("Kaart Omzet")) {
                System.out.printf("%6d", attr.getAantalKaartjesVerkocht());
                this.totaalKaartVerkoop += attr.getAantalKaartjesVerkocht();
            }
            System.out.println(" ");
        }
        this.toonMenuTotalen(keus);
    }

    void toonMenuTotalen(String keus) {
        if (keus.equals("Menu opties")) {
            System.out.print("[v] [v]erkoop kaartjes\n[o] [o]mzet\n[k] [k]aartjes aantal verkocht\n[b] [b]ezoek belastinginspecteur\n[q] [q]uit\n=================================");
        } else if (keus.equals("Omzet")) {
            System.out.printf("%19.19s", "      Totaalomzet: ");
            System.out.printf("%9.2f", this.totaleOmzet);
        } else if (keus.equals("Kaart Omzet")) {
            System.out.printf("%19.19s", "TotaalKaartomzet: ");
            System.out.printf("%6d", this.totaalKaartVerkoop);
        }
        System.out.println(" ");
    }

    void kaartVerkoop(ArrayList<Attractie> attracties, BelastingInspecteur belastingInspecteur, Random rand) throws InterruptedException {
        belastingInspecteur.bepaalBelastingBezoek(rand,this.verkoopnr);
        boolean bezoekBelastingInspecteur = false;
        for(Attractie attr: attracties){
            if (attr.getIsDraait()){
                attr.koopKaartjes(rand.nextInt((int)(attr.getCapaciteit()*3))+1,this);
                this.verkoopnr++;
                if (this.verkoopnr != 0 && this.verkoopnr >= belastingInspecteur.getBelastingBezoek()){
                   if (!bezoekBelastingInspecteur) {
                       bezoekBelastingInspecteur = true;
                       this.aantalBezoekBelastingInspecteur++;
                   }
                   bezoekBelastingInspecteurInnen(belastingInspecteur,attracties,rand);
                }
            }
        }
   }
    void bezoekBelastingInspecteur(BelastingInspecteur belastingInspecteur, ArrayList<Attractie> attracties, Random rand)throws InterruptedException{
        belastingInspecteur.setBelastingBezoek(this.verkoopnr);
        this.aantalBezoekBelastingInspecteur++;
        bezoekBelastingInspecteurInnen(belastingInspecteur,attracties,rand);
    }
    void bezoekBelastingInspecteurInnen(BelastingInspecteur belastingInspecteur, ArrayList<Attractie> attracties, Random rand) throws InterruptedException{
        belastingInspecteur.checkAttracties(attracties,this);
        belastingInspecteur.belastingInnen(this);
        belastingInspecteur.bepaalBelastingBezoek(rand, this.verkoopnr);
    }
}