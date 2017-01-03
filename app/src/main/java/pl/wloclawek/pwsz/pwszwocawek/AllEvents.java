package pl.wloclawek.pwsz.pwszwocawek;

/**
 * Created by Bartosz on 2016-12-26.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AllEvents extends AppCompatActivity {

    private List<Event> persons;
    private RecyclerView rv;
    SharedPreferences sharedPref;

    String cookieFromPref;
    View mLoginFormView;
    View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_all_events);

        rv=(RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        mLoginFormView = findViewById(R.id.rv);
        mProgressView = findViewById(R.id.loading_progress);




//        AktualneTask akt = new AktualneTask();
//        akt.execute();
        sharedPref = getSharedPreferences("tajnaPWSZ", MODE_PRIVATE);
        cookieFromPref = sharedPref.getString("tajneCookie", "null");
        Toast.makeText(this, cookieFromPref,
                Toast.LENGTH_LONG).show();





        EventTask task = new EventTask();
        task.execute();
    }

    private void initializeData(List<Event> lista){
        persons = new ArrayList<>();
        boolean nowy = true;
        String nowydzien = " ";

        for (Event e:lista
             ) {
            persons.add(new Event(e.nazwaE,e.opisE, e.dzienE, e.godzinaE));
        }




        }




    private void initializeAdapter(){
        RVAdapterEvent adapter = new RVAdapterEvent(persons);
        rv.setAdapter(adapter);

    }




    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }









    public class EventTask extends AsyncTask<Void, Void, Boolean> {



        private static final int REQUEST_READ_CONTACTS = 0;
        private String METHOD_NAME = "AllEvents";
        private static final String NAMESPACE = "http://tempuri.org/";
        private static final String URL = "http://77.245.247.158:2196/Service1.svc";
        String SOAP_ACTION = "http://tempuri.org/IService1/AllEvents";
        String resultData;
        public List<Event> listaevent;


        @Override
        protected void onPreExecute() {
            showProgress(true);
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                Log.e("TAG", "XXXXX-------adding");

                request.addProperty("indeks", sharedPref.getString("numer", "null"));
                Log.e("TAG", "indeks"+sharedPref.getString("numer", "null"));

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);
                Log.e("TAG", "XXXXX-------koperta");
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                Log.e("TAG", "XXXXX-------wysylka");


                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.debug = true;
                androidHttpTransport.call(SOAP_ACTION, envelope);
                Log.e("dump Request: ", androidHttpTransport.requestDump);
                Log.e("dump response: ", androidHttpTransport.responseDump);


                SoapObject soapaktualneZajecias = (SoapObject) envelope.getResponse();

                Log.e("TAG", "faza0 LessonPlan  getPropertyCount Cunt  -----TABLICA1---"+soapaktualneZajecias.getPropertyCount());
                Log.e("TAG", "faza0 LessonPlan  getAttributeCount Cunt  -----C---"+soapaktualneZajecias.getAttributeCount());



                listaevent = new ArrayList<Event>();


                int o =0;
                for (int i = 0; i < soapaktualneZajecias.getPropertyCount(); i++) {
                    SoapObject pii3 = (SoapObject) soapaktualneZajecias.getProperty(i);

                    // Log.e("TAG", "faza1 Lessons  getPropertyCount Cunt  -----C---" + pii2.getPropertyCount());
                    //Log.e("TAG", "faza1 Lessons  getAttributeCount Cunt  -----C---" + pii2.getAttributeCount());


                    Log.e("TAG", "faza1 Lesson1  getPropertyCount Cunt  -----C---" + pii3.getPropertyCount());
                    Log.e("TAG", "faza1 Lesson1  getAttributeCount Cunt  -----C---" + pii3.getAttributeCount());



                    for(int t=0;t<10;t++){
                        if(pii3.getProperty(t) != null)
                        Log.e("TAG", "ooooOooO)--------------------"+pii3.getProperty(t).toString()+"t="+t);
                    }

                    Event new1 = new Event();
                            new1.dzienE = pii3.getProperty(0).toString().substring(0, 10);
                            new1.godzinaE = pii3.getProperty(0).toString().substring(0, 5);
                            new1.opisE = pii3.getProperty(8).toString();
                            new1.nazwaE = pii3.getProperty(7).toString();

                    listaevent.add(new1);





                }
            } catch (Exception e) {

                Log.e("TAG", e.toString());

                return false;
            }
            return true;

        }

        @Override
        protected void onPostExecute(final Boolean success) {

            initializeData(listaevent);
            initializeAdapter();


            showProgress(false);




        }
        @Override
        protected void onCancelled() {

            showProgress(false);
        }

    }

}


