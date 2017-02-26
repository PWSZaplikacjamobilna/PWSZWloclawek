package pl.wloclawek.pwsz.pwszwocawek;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Calendar;

public class AddToEvent extends AppCompatActivity {


    EditText tyt, opis;
    Button add;
    View mProgressView;
    View mLoginFormView;
    private int mYear, mMonth, mDay, mHour, mMinute;
    SharedPreferences sharedPref;
    String numerFromPref;
    Button btnDatePicker, btnTimePicker;
    String data;
    String czas;

    boolean change = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_event);


        tyt = (EditText) findViewById(R.id.Tyt);
        opis = (EditText) findViewById(R.id.Opis);
        add = (Button) findViewById(R.id.add_button);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        btnDatePicker = (Button) findViewById(R.id.buttonDzien);
        btnTimePicker = (Button) findViewById(R.id.buttonGodzina);



        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AddToEvent.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                btnDatePicker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                data=year+"-"+(monthOfYear+1)+"-"+dayOfMonth+" ";

                                change = true;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddToEvent.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                btnTimePicker.setText(hourOfDay + ":" + minute);
                                czas = hourOfDay + ":" + minute;

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

        });

        sharedPref = getSharedPreferences("tajnaPWSZ", MODE_PRIVATE);
        numerFromPref = sharedPref.getString("numer", "null");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(change == true){
                AddTask add = new AddTask();
                add.execute();}
                else {
                    Toast.makeText(AddToEvent.this, R.string.ustawdate,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
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


    public class AddTask extends AsyncTask<Void, Void, Boolean> {

        private String METHOD_NAME = "AddEvent";
        private static final String NAMESPACE = "http://tempuri.org/";
        private static final String URL = "http://77.245.247.158:2196/Service1.svc";
        String SOAP_ACTION = "http://tempuri.org/IService1/AddEvent";
        String error;

        String tytT = tyt.getText().toString();
        String opisT = opis.getText().toString();
        String dataT = data;
        String numer = numerFromPref;

        @Override
        protected void onPreExecute() {
            showProgress(true);

        }


        @Override
        protected Boolean doInBackground(Void... params) {

            String resultData = "false";
            try {

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("indeks", numer);
                request.addProperty("data", data+czas);
                request.addProperty("nazwaopis", tytT + ";" + opisT);
                Log.d("TAG2","-------------tyt: "+ tytT);
                Log.d("TAG2","-------------opis: "+ opisT);
                Log.d("TAG2","-------------data: "+ dataT);
                Log.d("TAG2","-------------indeks: "+ numer);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);


                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.debug = true;
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapPrimitive result = (SoapPrimitive) envelope.getResponse();

                Log.d("dump Request: ", androidHttpTransport.requestDump);
                Log.d("dump response: ", androidHttpTransport.responseDump);
                //to get the data
                resultData = result.toString();

                Log.i("TAG2", "OK -------------------dodawanie--------------------------- " + resultData);


            } catch (Exception e) {
                Log.i("TAG2", "ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR -----------dodawanie-------------------------------- " + e);


            }

            if (resultData.contains("ERR")) {
                error = resultData.toString();
                return false;
            } else {


                return true;
            }

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            showProgress(false);

            Intent intent = new Intent(AddToEvent.this, AllEvents.class);
            startActivity(intent);
            finish();
        }

        @Override
        protected void onCancelled() {
            showProgress(false);
        }
    }


}
