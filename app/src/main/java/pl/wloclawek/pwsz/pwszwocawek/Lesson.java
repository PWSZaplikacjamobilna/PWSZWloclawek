package pl.wloclawek.pwsz.pwszwocawek;

/**
 * Created by Bartosz on 2016-12-26.
 */

class Lesson {
    boolean first;
    String wykladowca;
    String budynek;
    String przedmiot;
    String GodzinaRoz;
    String GodzinaZak;
    String typ;
    String sala;
    String dzień;
    String eta;
    String now;
    String numer;
    String date;

    Lesson(String wykladowca,
           String budynek,
           String przedmiot,
           String GodzinaRoz,
           String GodzinaZak,
           String typ,
           String sala,
           String dzień,
           String eta,
           String now,
           String numer,
           boolean first,
           String date) {
        this.wykladowca = wykladowca;
        this.budynek = budynek;
        this.przedmiot = przedmiot;
        this.GodzinaRoz = GodzinaRoz;
        this.GodzinaZak = GodzinaZak;
        this.typ = typ;
        this.sala = sala;
        this.dzień = dzień;
        this.eta = eta;
        this.now = now;
        this.numer = numer;
        this.first = first;
        this.date=date;
    }
}