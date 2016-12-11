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
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);

        cookieFromPref = sharedPref.getString("tajneCookie", "null");
        Toast.makeText(this, cookieFromPref,
                Toast.LENGTH_LONG).show();
        NewsTask data = new NewsTask();
        data.execute();
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
        else if (id == R.id.nav_jakdojade) {
            Intent intent = new Intent(this, MapsActivity.class);
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
}
