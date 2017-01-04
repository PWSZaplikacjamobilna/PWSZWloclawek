package pl.wloclawek.pwsz.pwszwocawek;

/**
 * Created by Bartosz on 2017-01-04.
 */

public class Contact {
    public String IDKontakt;
    public String Stanowisko;
    public String Imię_i_Nazwisko;
    public String Pokoj;
    public String Nr_telefonu;
    public String Email;
    public String Budynek;

    Contact(String IDKontakt,String Stanowisko,String Imię_i_Nazwisko,String Pokoj,String Nr_telefonu,String Email,String Budynek){

        this.IDKontakt = IDKontakt;
        this.Stanowisko =Stanowisko;
        this.Imię_i_Nazwisko = Imię_i_Nazwisko;
        this.Pokoj = Pokoj;
        this.Nr_telefonu =Nr_telefonu;
        this.Email = Email;
        this.Budynek = Budynek;
    }
    Contact(){}

}