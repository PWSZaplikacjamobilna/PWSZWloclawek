package pl.wloclawek.pwsz.pwszwocawek;

import android.app.Activity;
import android.content.DialogInterface;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import android.os.Bundle;
import android.support.v7.widget.CardView;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageView;

import com.google.android.gms.tasks.Task;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

import static android.content.Context.MODE_PRIVATE;


public class Contactrektorat extends Fragment  {
    public List<Contact> listakontaktfinal;
    public ArrayList<String> lista;
    RecyclerView recyclerView;
    SharedPreferences sharedPref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_contactzawisle, container, false);

        recyclerView =(RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));
        sharedPref = this.getActivity().getSharedPreferences("tajnaPWSZ", MODE_PRIVATE);

        lista= new ArrayList<>();
        KontaktyTask task=new KontaktyTask();
        task.execute();






        return rootView;
    }



    public class KontaktyTask extends AsyncTask<Void, Void, Boolean> {


        private static final int REQUEST_READ_CONTACTS = 0;
        private String METHOD_NAME = "AllContacts";
        private static final String NAMESPACE = "http://tempuri.org/";
        private static final String URL = "http://77.245.247.158:2196/Service1.svc";
        String SOAP_ACTION = "http://tempuri.org/IService1/AllContacts";
        String resultData;
        public List<Contact> listakontakt;


        @Override
        protected void onPreExecute() {


            Log.e("TAG3", "XXXXX-------koperta");
        }
        @Override
        protected Boolean doInBackground(Void... params) {

            Log.e("TAG3", "XXXXX-------koperta");
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("indeks", sharedPref.getString("numer", "null"));
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);
                Log.e("TAG3", "XXXXX-------koperta");
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                Log.e("TAG3", "XXXXX-------wysylka");


                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.debug = true;
                androidHttpTransport.call(SOAP_ACTION, envelope);
                Log.e("dump Request: ", androidHttpTransport.requestDump);
                Log.e("dump response: ", androidHttpTransport.responseDump);


                SoapObject soapaktualneZajecias = (SoapObject) envelope.getResponse();

                listakontakt = new ArrayList<Contact>();


                int o =0;
                for (int i = 0; i < soapaktualneZajecias.getPropertyCount(); i++) {
                    SoapObject pii3 = (SoapObject) soapaktualneZajecias.getProperty(i);







                    Contact new1 = new Contact();

                    for(int y=0;y<7;y++ ){
                        Log.e("TAG3", "YYYYYYYC-----------"+y+"-------"+   pii3.getProperty(y).toString());

                    }
                    new1.Budynek = pii3.getProperty(0).toString();
                    new1.Email = pii3.getProperty(1).toString()+"@pwsz.wloclawek.pl";
                    new1.Imię_i_Nazwisko = pii3.getProperty(3).toString();
                    new1.Nr_telefonu = pii3.getProperty(4).toString();
                    new1.Pokoj =pii3.getProperty(5).toString();
                    new1.Stanowisko =pii3.getProperty(6).toString();
                    //  new1.nazwaE = pii3.getProperty(7).toString();

                    listakontakt.add(new1);





                }
            } catch (Exception e) {

                Log.e("TAG3", e.toString());

                return false;
            }
            return true;

        }

        @Override
        protected void onPostExecute(final Boolean success) {

            listakontaktfinal = listakontakt;
            for (Contact c: listakontaktfinal
                    ) {
                if(c.Budynek.equals("1")) {
                    lista.add(c.Stanowisko +
                            "\n" + c.Imię_i_Nazwisko +
                            "\nPokój: " + c.Pokoj +
                            "\nTel: " + c.Nr_telefonu +
                            "\nMail: " + c.Email);
                }
            }
            recyclerView.setAdapter(new RecyclerAdapterContact(lista));

        }
        @Override
        protected void onCancelled() {


        }
    }


}
