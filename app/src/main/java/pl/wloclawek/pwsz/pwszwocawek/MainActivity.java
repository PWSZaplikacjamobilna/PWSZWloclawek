package pl.wloclawek.pwsz.pwszwocawek;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.sql.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String METHOD_NAME = "Logowanie";
//  private static final String METHOD_NAME = "HelloWorld";

    private static final String NAMESPACE = "http://localhost:1320/";
//  private static final String NAMESPACE = "http://tempuri.org";

    private static final String URL = "http://localhost:1320/Service1.svc";
//  private static final String URL = "http://192.168.0.2:8080/webservice1  /Service1.asmx";

    final String SOAP_ACTION = "http://localhost:1320/IService1/Logowanie";
    //  final String SOAP_ACTION = "http://tempuri.org/HelloWorld";
    SharedPreferences sharedPref;

    String cookieFromPref;
    ListView listView;
    TextView dzienNow ;
    TextView godzinaNow;
    TextView zajeciaNow ;
    TextView typsalNow;
    TextView dzienNext;
    TextView godzinNext ;
    TextView typssalNext ;
    TextView zajeciaNext ;
    TextView wykNext ;
    TextView wtkNow ;
    TextView etaNext ;
    TextView etaNow ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = getSharedPreferences("tajnaPWSZ", MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

         listView = (ListView) findViewById(R.id.listView13);
        String[] values = new String[]{"Ładowanie ...",
                "Ładowanie ...",
                "Ładowanie ...",
                "Ładowanie ...",
        };

         dzienNow = (TextView)findViewById(R.id.dzienNow);
         godzinaNow = (TextView)findViewById(R.id.godzinaNow);
         zajeciaNow = (TextView)findViewById(R.id.zajecieNow);
         typsalNow = (TextView)findViewById(R.id.typsalaNow);
         dzienNext = (TextView)findViewById(R.id.dzienNext);
         godzinNext = (TextView)findViewById(R.id.godzinyNext);
         typssalNext = (TextView)findViewById(R.id.typsalaNext);
         zajeciaNext = (TextView)findViewById(R.id.zajecieNext);
         wykNext = (TextView)findViewById(R.id.wykNext);
         wtkNow = (TextView)findViewById(R.id.wykNow);
         etaNext = (TextView)findViewById(R.id.etaNext);
         etaNow = (TextView)findViewById(R.id.etaNow);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);

        cookieFromPref = sharedPref.getString("tajneCookie", "null");
        Toast.makeText(this, cookieFromPref,
                Toast.LENGTH_LONG).show();
        NewsTask data = new NewsTask();
        data.execute();
        AktualneTask data2 = new AktualneTask();
        data2.execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_wyloguj) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("tajneCookie", "null");
            editor.commit();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_planbudynkow) {
            Intent intent = new Intent(this, SchoolPlanActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class NewsTask extends AsyncTask<Void, Void, Boolean> {



        private static final int REQUEST_READ_CONTACTS = 0;
        private   String METHOD_NAME = "NewsData";
        private static final String NAMESPACE = "http://tempuri.org/";
        private static final String URL = "http://77.245.247.158:2196/Service1.svc";
        String SOAP_ACTION = "http://tempuri.org/IService1/NewsData";
        String resultData;
        String[] newsarray;
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                class News {
                    String komunikat;

                }

                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapObject soap = (SoapObject) envelope.getResponse();
                Log.d("TAG", "-------PropertyCount" + soap.getPropertyCount());
                Log.d("TAG", "-------AttributeCount" + soap.getAttributeCount());

                News[] news = new News[soap.getPropertyCount()];
                Log.d("TAG", "-------News" + soap.getPropertyCount());

                for (int i = 0; i < news.length; i++) {
                    SoapObject pii = (SoapObject) soap.getProperty(i);
                    News new1 = new News();
                    new1.komunikat = pii.getProperty(0).toString();
                    Log.d("TAG", "-------News" + pii.getProperty(0).toString());
                    news[i] = new1;

                }

                newsarray = new String[news.length ];
                int index = 0;
                for (News k : news) {
                    newsarray[index] = "\r\n "+k.komunikat;
                    index++;
                }

            }catch(Exception e){

                Log.e("TAG",e.toString());
            }
            return true;

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, newsarray){
                @Override
                public View getView(int position, View convertView, ViewGroup parent){
                    // Get the Item from ListView
                    View view = super.getView(position, convertView, parent);

                    // Initialize a TextView for ListView each Item
                    TextView tv = (TextView) view.findViewById(android.R.id.text1);

                    // Set the text color of TextView (ListView Item)
                    tv.setTextColor(Color.DKGRAY);

                    // Generate ListView Item using TextView
                    return view;
                }
            };
            listView.setAdapter(adapter);

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
        }

        private static final int REQUEST_READ_CONTACTS = 0;
        private String METHOD_NAME = "Aktualne";
        private static final String NAMESPACE = "http://tempuri.org/";
        private static final String URL = "http://77.245.247.158:2196/Service1.svc";
        String SOAP_ACTION = "http://tempuri.org/IService1/Aktualne";
        String resultData;
        public AktualneZajecia[] aktualnedata;

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                Log.e("TAG", "XXXXX-------adding");
                request.addProperty("numer", sharedPref.getString("tajneCookie", "null"));
                Log.e("TAG", "XXXXX-------added");
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


                SoapObject soap = (SoapObject) envelope.getResponse();
                Log.e("TAG", "XXXXX-------PropertyCount" + soap.getPropertyCount());
                Log.e("TAG", "XXXXX-------AttributeCount" + soap.getAttributeCount());
                SoapObject soapaktualneZajecias = (SoapObject) soap.getProperty(0);
                Log.e("TAG", "-------KIER" + soapaktualneZajecias.getPropertyCount());
                AktualneZajecia[] aktualne = new AktualneZajecia[soapaktualneZajecias.getPropertyCount()];


                for (int i = 0; i < aktualne.length; i++) {
                    SoapObject pii2 = (SoapObject) soapaktualneZajecias.getProperty(i);

                    AktualneZajecia new1 = new AktualneZajecia();

                    new1.GodzinaRoz = pii2.getProperty(0).toString();
                    new1.GodzinaZak = pii2.getProperty(1).toString();
                    new1.budynek = pii2.getProperty(2).toString();
                    new1.dzień = pii2.getProperty(3).toString();
                    new1.eta =pii2.getProperty(4).toString();
                    new1.przedmiot = pii2.getProperty(5).toString();

                    new1.sala = pii2.getProperty(6).toString();
                    new1.typ = pii2.getProperty(7).toString();
                    new1.wykladowca = pii2.getProperty(8).toString();

                    aktualne[i] = new1;

                }

                aktualnedata = aktualne;


            } catch (Exception e) {

                Log.e("TAG", e.toString());
            }
            return true;

        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if(aktualnedata[0].przedmiot.length()>=25)
            zajeciaNow.setText(aktualnedata[0].przedmiot.substring(0,25)+"...");
            dzienNow.setText(aktualnedata[0].dzień.substring(0,10));
            String godz1 = aktualnedata[0].GodzinaRoz.substring(2).replace('H',':').replace('M',' ');
            String godz2 = aktualnedata[0].GodzinaZak.substring(2).replace('H',':').replace('M',' ');
            godzinaNow.setText(godz1+"-"+godz2);
            typsalNow.setText(aktualnedata[0].typ + ", sala " + aktualnedata[0].sala);
            wtkNow.setText(aktualnedata[0].wykladowca);
            String znak = aktualnedata[0].eta.substring(0,1);
            etaNow.setText(aktualnedata[0].eta.substring(1));
            if(znak.equals("Z")){
                etaNow.setTextColor(Color.YELLOW);
            }
            if(znak.equals("R")){
                etaNow.setTextColor(Color.RED);
            }


            if(aktualnedata[1].przedmiot.length()>=25)
            zajeciaNext.setText(aktualnedata[1].przedmiot.substring(0,25)+"...");
            dzienNext.setText(aktualnedata[1].dzień.substring(0,10));
             godz1 = aktualnedata[1].GodzinaRoz.substring(2).replace('H',':').replace('M',' ');
             godz2 = aktualnedata[1].GodzinaZak.substring(2).replace('H',':').replace('M',' ');
            godzinNext.setText(godz1+"-"+godz2);
            typssalNext.setText(aktualnedata[1].typ + ", sala " + aktualnedata[1].sala);
            wykNext.setText(aktualnedata[1].wykladowca);
            String znak2 = aktualnedata[1].eta.substring(0,1);
            etaNext.setText(aktualnedata[1].eta.substring(1));
            if(znak2.equals("Z")){
                etaNext.setTextColor(Color.YELLOW);
            }
            if(znak2.equals("R")){
                etaNext.setTextColor(Color.RED);
            }

        }

    }

}
