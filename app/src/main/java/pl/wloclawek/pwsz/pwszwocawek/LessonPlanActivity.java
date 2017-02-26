package pl.wloclawek.pwsz.pwszwocawek;

/**
 * Created by Bartosz on 2016-12-26.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

public class LessonPlanActivity extends AppCompatActivity {

    private List<Lesson> persons;
    private RecyclerView rv;
    SharedPreferences sharedPref;

    String cookieFromPref;
     View mLoginFormView;
     View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lesson_plan);

        rv=(RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        mLoginFormView = findViewById(R.id.rv);
        mProgressView = findViewById(R.id.loading_progress);




        AktualneTask akt = new AktualneTask();
        akt.execute();
        sharedPref = getSharedPreferences("tajnaPWSZ", MODE_PRIVATE);
        cookieFromPref = sharedPref.getString("tajneCookie", "null");



    }

    private void initializeData(List<AktualneTask.AktualneZajecia>  aktualnedata){
        try {
            persons = new ArrayList<>();
            boolean nowy = true;
            String nowydzien = " ";


            int dni = 0;
            for (AktualneTask.AktualneZajecia aky : aktualnedata) {
                Log.e("TAG", "----------------" + aky.przedmiot);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                c.getTime();
                c.add(Calendar.DATE, dni);  // number of days to add
                String dt = sdf.format(c.getTime());  // dt is now the new date

                if (aky.przedmiot.contains("BRAK")) {
                    persons.add(new Lesson("Brak", "Brak ", "Brak ", "Brak", "Brak", "Brak", "Brak", "Brak", "Brak", "Brak", "Brak ", true, dt));

                    dni++;
                    //  Log.e("TAG", "TTTTTIME-------SI------------------------------------------OPOPOPOPOPO---"+dt);

                } else {
                    if (aky.GodzinaRoz.substring(0, 10).contains(nowydzien)) {
                        nowy = false;
                    } else {
                        dni++;
                        nowy = true;
                        nowydzien = aky.GodzinaRoz.substring(0, 10);
                    }

                    persons.add(new Lesson(aky.wykladowca, aky.budynek, aky.przedmiot, aky.GodzinaRoz, aky.GodzinaZak, aky.typ, aky.sala, aky.dzień, aky.eta, aky.now, aky.numer, nowy, aky.GodzinaRoz.substring(0, 10)));

                    //Log.e("TAG", "TTTTTIME-------MS-----------------------------------------------OPOPOPOPOPO---"+aky.GodzinaRoz.substring(0, 10));
                }


            }

        }catch (Exception e){


            Toast.makeText(this, getString(R.string.brakinternetu),
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LessonPlanActivity.this, MainActivity.class);
            startActivity(intent);

            finish();

        }



    }

    private void initializeAdapter(){
        RVAdapterLesson adapter = new RVAdapterLesson(persons);
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









    public class AktualneTask extends AsyncTask<Void, Void, Boolean> {

        class AktualneZajecia {
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
        }

        private static final int REQUEST_READ_CONTACTS = 0;
        private String METHOD_NAME = "AktualneNa7";
        private static final String NAMESPACE = "http://tempuri.org/";
        private static final String URL = "http://77.245.247.158:2196/Service1.svc";
        String SOAP_ACTION = "http://tempuri.org/IService1/AktualneNa7";
        String resultData;
        public List<AktualneZajecia>  aktualnedata;
        public int now = 99;
        public int next = 99;

        boolean succcc = true;

        @Override
        protected void onPreExecute() {
            showProgress(true);
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                Log.e("TAG", "XXXXX-------adding");

                request.addProperty("cookie", sharedPref.getString("tajneCookie", "null"));
                Log.e("TAG", "COOOOOOOOOOOOOOOOOOOOOOOOOKIE"+sharedPref.getString("tajneCookie", "null"));
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String date = df.format(Calendar.getInstance().getTime());
                request.addProperty("date", date);
                Log.e("TAG", "XXXXX-------added+"+date);
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



               List<AktualneZajecia> aktualne = new ArrayList<AktualneZajecia>();


                int o =0;
                for (int i = 0; i < soapaktualneZajecias.getPropertyCount(); i++) {
                    SoapObject pii2 = (SoapObject) soapaktualneZajecias.getProperty(i);

                   // Log.e("TAG", "faza1 Lessons  getPropertyCount Cunt  -----C---" + pii2.getPropertyCount());
                    //Log.e("TAG", "faza1 Lessons  getAttributeCount Cunt  -----C---" + pii2.getAttributeCount());

                    SoapObject pii3 = (SoapObject) pii2.getProperty(0);

                    Log.e("TAG", "faza1 Lesson1  getPropertyCount Cunt  -----C---" + pii3.getPropertyCount());
                    Log.e("TAG", "faza1 Lesson1  getAttributeCount Cunt  -----C---" + pii3.getAttributeCount());




                    if (pii3.getPropertyCount() >0) {
                      //  Log.e("TAG", "faza11212 zajecia  pii3.getPropertyCount() Cunt  -----323---" + pii3.getPropertyCount());
                        for (int u = 0; u < pii3.getPropertyCount(); u++) {
                         //   Log.e("TAG", "faza1 pii4 = (SoapObject) pii3.getProperty(u) -----C---" + pii3.getPropertyCount());
                            SoapObject pii4 = (SoapObject) pii3.getProperty(u);
                         //   Log.e("TAG", "faza1 zajecia  getPropertyCount Cunt  -----X---" + pii4.getPropertyCount());
                            Log.e("TAG", "GODZINA ROZPOACZECIA zajecia  getAttributeCount Cunt  ----X---" + pii4.getProperty(11).toString());


                            AktualneZajecia new1 = new AktualneZajecia();
                            new1.GodzinaRoz = pii4.getProperty(13).toString();
                            new1.GodzinaZak = pii4.getProperty(1).toString();
                            new1.budynek = pii4.getProperty(0).toString();
                            new1.przedmiot = pii4.getProperty(7).toString();
                            new1.sala = pii4.getProperty(10).toString();
                            new1.typ = pii4.getProperty(8).toString();
                            new1.wykladowca = pii4.getProperty(5).toString();
                            new1.now = pii4.getProperty(11).toString();
                            new1.eta = "ETA";
                            new1.numer = pii4.getProperty(6).toString();

//                            for(int f = 0; f<14; f++){
//                                Log.e("TAG","%%%%%%%%%prop ----------"+f+"----"+pii4.getProperty(f).toString());
//                            }


                            if (new1.wykladowca.contains("anyType")) {
                                new1.wykladowca = pii4.getProperty(3).toString();
                                new1.wykladowca = new1.wykladowca.replace("Seminarium -", "");
                            }
                            if (new1.now.equals("NOW")) {
                                now = i;
                            }
                            if (new1.now.equals("NEXT")) {
                                next = i;
                            }
                            Log.e("TAG", "ooooOooOOo  getAttributeCount Cunt  -----C---" + o);
                            aktualne.add(new1);

                        }


                    }else{
                        AktualneZajecia new2= new AktualneZajecia();
                        new2.przedmiot = "BRAK";
                        aktualne.add(new2);

                    }
                }
                aktualnedata = aktualne;


            } catch (Exception e) {

                Log.e("TAG", e.toString());
                succcc = false;
                return false;
            }
            return true;

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            showProgress(false);
            initializeData(aktualnedata);
            initializeAdapter();


            }
        @Override
        protected void onCancelled() {
            showProgress(false);
        }

        }

    }


