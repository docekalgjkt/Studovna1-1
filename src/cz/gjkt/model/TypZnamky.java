package cz.gjkt.model;

public class TypZnamky {

        private static int id;
        private String nazev;
        private String popis;
        private int min;
        private int vaha;
        private int rocnik;
        private int pololeti;
        private int predmet;

        public String toString(){return "Typ Znamky: " + id + ", " + nazev + ", " + popis + ", " + min + ", " + vaha + ", " + rocnik + ", " + pololeti + ", " + predmet;}

        public int getId(){return id;}
        public String getNazev(){return nazev;}
        public String getPopis(){return popis;}
        public int getMin(){return min;}
        public int getVaha(){return vaha;}
        public int getRocnik(){return rocnik;}
        public int getPololeti(){return pololeti;}
        public int getPredmet(){return predmet;}

        public void setId(int id){this.id = id;}
        public void setId(String id){this.id = Integer.parseInt(id);}
        public void setNazev(String nazev){this.nazev = nazev;}
        public void setPopis(String popis){this.popis = popis;}
        public void setMin(int min){this.min = min;}
        public void setMin(String min){this.min = Integer.parseInt(min);}
        public void setVaha(int vaha){this.vaha = vaha;}
        public void setVaha(String vaha){this.vaha = Integer.parseInt(vaha);}
        public void setRocnik(int rocnik){this.rocnik = rocnik;}
        public void setRocnik(String rocnik){this.rocnik = Integer.parseInt(rocnik);}
        public void setPololeti(int pololeti){this.pololeti = pololeti;}
        public void setPololeti(String pololeti){this.pololeti = Integer.parseInt(pololeti);}
        public void setPredmet(int predmet){this.predmet = predmet;}
        public void setPredmet(String predmet){this.predmet = Integer.parseInt(predmet);}
}
