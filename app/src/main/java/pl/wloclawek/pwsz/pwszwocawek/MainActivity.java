package pl.wloclawek.pwsz.pwszwocawek;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import uk.co.senab.photoview.PhotoViewAttacher;

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
    PhotoViewAttacher mAttacher;
     String salaA;
    String salaN;
    String budynekA;
    String budynekN;
    TextView txMenu;
ImageView myImgView;


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

        CardView aktC = (CardView) findViewById(R.id.card_viewNOW);
        aktC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id = getResources().getIdentifier("s"+salaA.toLowerCase(),"drawable" , MainActivity.this.getPackageName());
               openDialog(salaA,id,budynekA);
            }
        });

        CardView nextC = (CardView) findViewById(R.id.card_viewNEXT);
        nextC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id = getResources().getIdentifier("s"+salaN.toLowerCase(),"drawable" , MainActivity.this.getPackageName());
                openDialog(salaN,id,budynekN);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        LinearLayout lo= (LinearLayout) hView.findViewById(R.id.iv_nav_backID);

         txMenu = (TextView)  hView.findViewById(R.id.numer);

        txMenu.setText(sharedPref.getString("numer", "null"));
//        lo.setBackgroundResource(R.drawable.day);

         listView = (ListView) findViewById(R.id.listView13);
        String[] values = new String[]{"Ładowanie ...",
                "Ładowanie ......",
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


        LoopApp data2 = new LoopApp();
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
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

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
            Intent intent = new Intent(this, SchoolPlanTabbed.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_jakdojade) {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openDialog(String sala, int salaR,String budynek){
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.dialog, null);

        final ImageView subImageView = (ImageView)subView.findViewById(R.id.image);
        Drawable drawable = getResources().getDrawable(salaR);
        subImageView.setImageDrawable(drawable);
         mAttacher = new PhotoViewAttacher(subImageView);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(budynek+" - "+getString(R.string.sala)+" "+sala);

        builder.setView(subView);
        AlertDialog alertDialog = builder.create();



        builder.setNegativeButton("Zamknij", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
        mAttacher.update();
    }

    public class NewsTask extends AsyncTask<Void, Void, Boolean> {



        private static final int REQUEST_READ_CONTACTS = 0;
        private String METHOD_NAME = "NewsData";
        private static final String NAMESPACE = "http://tempuri.org/";
        private static final String URL = "http://77.245.247.158:2196/Service1.svc";
        String SOAP_ACTION = "http://tempuri.org/IService1/NewsData";
        String resultData;
        String[] newsarray;
        @Override
        protected Boolean doInBackground(Void... params) {
            System.setProperty("http.keepAlive", "false");

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
            if(newsarray == null)
            {
                NewsTask data = new NewsTask();
                data.execute();
                return;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, newsarray){
                @Override
                public View getView(int position, View convertView, ViewGroup parent){
                    // Get the Item from ListView
                    View view = super.getView(position, convertView, parent);

                    // Initialize a TextView for ListView each Item
                    TextView tv = (TextView) view.findViewById(android.R.id.text1);

                    // Set the text color of TextView (ListView Item)
                    tv.setTextColor(Color.parseColor("#757575"));
                    tv.setTextSize(14);


                    // Generate ListView Item using TextView
                    return view;
                }
            };
            listView.setAdapter(adapter);

        }


    }


    public class LoopApp extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);

// This schedule a runnable task every 2 minutes
            scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
                public void run() {
                    runTest();
                }
            }, 0, 1, TimeUnit.MINUTES);

            return true;
        }

        public void runTest() {
            AktualneTask data2 = new AktualneTask();
            data2.execute();
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
        private String METHOD_NAME = "test";
        private static final String NAMESPACE = "http://tempuri.org/";
        private static final String URL = "http://77.245.247.158:2196/Service1.svc";
        String SOAP_ACTION = "http://tempuri.org/IService1/test";
        String resultData;
        public AktualneZajecia[] aktualnedata;
        public int now = 99;
        public int next = 99;

        boolean succcc = true;
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


                SoapObject soap = (SoapObject) envelope.getResponse();


                SoapObject soapaktualneZajecias = (SoapObject) soap.getProperty(0);

                AktualneZajecia[] aktualne = new AktualneZajecia[soapaktualneZajecias.getPropertyCount()];


                for (int i = 0; i < aktualne.length; i++) {
                    SoapObject pii2 = (SoapObject) soapaktualneZajecias.getProperty(i);

                    AktualneZajecia new1 = new AktualneZajecia();
                            for(int t=0 ; t<12 ; t++){
                    Log.e("TAG", "RRRRRR-------CHECK"+t+"---"+pii2.getProperty(t).toString());
                    }
                    new1.GodzinaRoz = pii2.getProperty(11).toString();
                    new1.GodzinaZak = pii2.getProperty(1).toString();
                    new1.budynek = pii2.getProperty(0).toString();
                    new1.przedmiot = pii2.getProperty(6).toString();
                    new1.sala = pii2.getProperty(9).toString();
                    new1.typ = pii2.getProperty(7).toString();
                    new1.wykladowca = pii2.getProperty(4).toString();
                    new1.now = pii2.getProperty(10).toString();
                    new1.eta = pii2.getProperty(3).toString();
                    new1.numer =pii2.getProperty(5).toString();
                    if(new1.now.equals("NOW")){
                        now = i;
                    }
                    if(new1.now.equals("NEXT")){
                        next =i;
                    }
                    aktualne[i] = new1;

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



            if(now == 99){




          zajeciaNow.setText("                                             Brak");

            dzienNow.setText("");

            godzinaNow.setText("");
            typsalNow.setText("");
            wtkNow.setText("");
                etaNow.setText("");

            }else {


                if (aktualnedata[now].przedmiot.length() >= 28) {
                    zajeciaNow.setText(aktualnedata[now].przedmiot.substring(0, 28) + "...");
                } else {
                    zajeciaNow.setText(aktualnedata[now].przedmiot);
                }
                dzienNow.setText(" " + aktualnedata[now].GodzinaRoz.substring(0, 10));
                Log.e("TAG", "----------------" + aktualnedata[now].GodzinaRoz);
                String godz1 = aktualnedata[now].GodzinaRoz.substring(11).substring(0, 5);
                String godz2 = aktualnedata[now].GodzinaZak.substring(11).substring(0, 5);
                godzinaNow.setText(godz1 + "-" + godz2);
                typsalNow.setText(aktualnedata[now].typ.replace(" ", "") + ", "+getString(R.string.sala)+" " + aktualnedata[now].sala);
                salaA=aktualnedata[now].sala;
                budynekA=aktualnedata[now].budynek;
                wtkNow.setText(aktualnedata[now].wykladowca);
                String znak = aktualnedata[0].eta.substring(0,1);
                  etaNow.setText(aktualnedata[0].eta.substring(1));
                  if(znak.equals("Z")){
                      etaNow.setTextColor(Color.YELLOW);
                  }
                  if(znak.equals("R")){
                      etaNow.setTextColor(Color.RED);
                  }
            }






            if(next == 99){




                zajeciaNext.setText("       Na dzisiaj koniec HEHEUHEHAHEAHUEH");

                dzienNext.setText("");

                godzinNext.setText("");
                typssalNext.setText("");
                wykNext.setText("");

            }else {


                if (aktualnedata[next].przedmiot.length() >= 28) {
                    zajeciaNext.setText(aktualnedata[next].przedmiot.substring(0, 28) + "...");
                } else {
                    zajeciaNext.setText(aktualnedata[next].przedmiot);
                }
                dzienNext.setText(" " + aktualnedata[next].GodzinaRoz.substring(0, 10));
                String godz1 = aktualnedata[next].GodzinaRoz.substring(11).substring(0, 5);
                String godz2 = aktualnedata[next].GodzinaZak.substring(11).substring(0, 5);
                godzinNext.setText(godz1 + "-" + godz2);
                typssalNext.setText(aktualnedata[next].typ.replace(" ", "") + ", "+getString(R.string.sala)+" " + aktualnedata[next].sala);
                salaN=aktualnedata[next].sala;
                budynekN=aktualnedata[next].budynek;
                wykNext.setText(aktualnedata[next].wykladowca);
            String znak2 = aktualnedata[1].eta.substring(0,1);
            etaNext.setText(aktualnedata[1].eta.substring(1));
            if(znak2.equals("Z")){
                etaNext.setTextColor(Color.YELLOW);
            }
            if(znak2.equals("R")){
                etaNext.setTextColor(Color.RED);
            }

            }


            if(success == true) {
                NewsTask data = new NewsTask();
                data.execute();
            }else
            {
                AktualneTask akt = new AktualneTask();
                akt.execute();
            }
        }

    }

}
