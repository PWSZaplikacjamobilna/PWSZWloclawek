package pl.wloclawek.pwsz.pwszwocawek;

/**
 * Created by Bartosz on 2016-12-31.
 */

public class Event {
    public String nazwaE;
    public String opisE;
    public String dzienE;
    public String godzinaE;


    Event(String nazwa,String opis,String dzien,String godzina){

        this.nazwaE = nazwa;
        this.opisE =opis;
        this.dzienE = dzien;
        this.godzinaE = godzina;
    }

    Event(){

    }
}
