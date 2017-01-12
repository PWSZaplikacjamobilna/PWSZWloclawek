package pl.wloclawek.pwsz.pwszwocawek;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class LoginActivity extends AppCompatActivity  {



    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    private static  String METHOD_NAME = "Logowanie";
//  private static final String METHOD_NAME = "HelloWorld";

    private static final String NAMESPACE = "http://tempuri.org/";
//  private static final String NAMESPACE = "http://tempuri.org";

    private static final String URL = "http://77.245.247.158:2196/Service1.svc";
//  private static final String URL = "http://192.168.0.2:8080/webservice1  /Service1.asmx";

     String SOAP_ACTION = "http://tempuri.org/IService1/Logowanie";
    //  final String SOAP_ACTION = "http://tempuri.org/HelloWorld";

    Locale myLocale;


    TextView tv;
    StringBuilder sb;
    private XmlSerializer writer;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mNumerView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    SharedPreferences sharedPref;
    String cookieFromPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
         sharedPref =  getSharedPreferences("tajnaPWSZ", MODE_PRIVATE);
        // Set up the login form.
        mNumerView = (EditText) findViewById(R.id.numer);
        TextView niepam = (TextView) findViewById(R.id.textViewChange);
        niepam.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ChangePassword.class);
                startActivity(intent);
            }
        });



        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mNumerInButton = (Button) findViewById(R.id.numer_sign_in_button);
        Button mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
               startActivity(intent);
            }
        });
        mNumerInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);


        cookieFromPref = sharedPref.getString("tajneCookie","null");

        Toast.makeText(this,"Cookie"+ cookieFromPref,
                Toast.LENGTH_LONG).show();
        Log.i("TAG", "COOKIE ------------------------------------------------------- " + cookieFromPref);
        if(!isOnline()){

            Toast.makeText(this, "Brak połączenia z internetem !",
                    Toast.LENGTH_LONG).show();
        }


        if(!cookieFromPref.equals("null")){
            mAuthTask = new UserLoginTask(cookieFromPref);
            mAuthTask.execute((Void) null);
        }
    }




    public Boolean isOnline() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal==0);
            return reachable;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mNumerView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String numer = mNumerView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(numer)) {
            mNumerView.setError(getString(R.string.error_field_required));
            focusView = mNumerView;
            cancel = true;
        } else if (!isEmailValid(numer)) {
            mNumerView.setError(getString(R.string.error_invalid_email));
            focusView = mNumerView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            mAuthTask = new UserLoginTask(numer, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.length() == 4;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
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

    public void english(View view) {
        setLocale("en");
    }
    public void setLocale(String lang) {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void polish(View view) {
        setLocale("pl");
    }

    public void turkish(View view) {
        setLocale("tr");
    }

    public void germany(View view) {setLocale("de");
    }


    public class PingTask extends AsyncTask<Void, Void, Boolean> {


        boolean res = false;

        @Override
        protected void onPreExecute() {
            showProgress(true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            res=isOnline();
            return res;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if(res == true) {
                showProgress(false);
                mNumerView.setError(getString(R.string.error_incorrect_passwordOrpassword));
                mNumerView.requestFocus();
            }else{
                showProgress(false);
                Snackbar snack= Snackbar.make(mLoginFormView,"Brak połączenia z internetem !!!",Snackbar.LENGTH_LONG);
                snack.show();
            }

        }




    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
           Boolean success =false;
        String cookie = "false";
        String numer;
        Boolean authcookie = false;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;

        }
        UserLoginTask(String cookie) {
           this.cookie = cookie;
            authcookie = true;
            mEmail = "null";
            mPassword = "null";

        }
        @Override
        protected void onPreExecute() {
            showProgress(true);
        }
        @Override
        protected Boolean doInBackground(Void... params) {

            if(authcookie){
                METHOD_NAME ="LogowanieCookie";
                SOAP_ACTION = "http://tempuri.org/IService1/LogowanieCookie";
            }else
            {
                METHOD_NAME ="Logowanie";
                 SOAP_ACTION = "http://tempuri.org/IService1/Logowanie";
            }
            String resultData = "false";
            try {

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                if(!authcookie) {
                    request.addProperty("login", mEmail);
                    request.addProperty("haslo", mPassword);
                }else if  (cookie != null){

                    request.addProperty("cookie", cookie);
                }else{
                    return true;

                }



                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);


                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapPrimitive result = (SoapPrimitive)envelope.getResponse();

                //to get the data
                resultData = result.toString();
                // 0 is the first object of data

                Log.i("TAG", "OK ------------------------------------------------------- " + resultData);



            } catch (Exception e) {
                Log.i("TAG", "ERR ------------------------------------------------------- " + e);


            }

            // TODO: register the new account here.
            if(resultData.equals("false")){

                success =  false;
                return false;
            }else{
                Log.i("TAG", "NUMER -ONE1111--------- " + resultData.toString());

                cookie = resultData.toString().substring(4);
                numer =resultData.toString().substring(0,4);

                return true;
            }

        }

        @Override
        protected void onPostExecute(final Boolean success) {

            mAuthTask = null;
            if (success) {
                Toast.makeText(LoginActivity.this, numer,
                        Toast.LENGTH_LONG).show();

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("tajneCookie", cookie);
                editor.putString("numer", numer);
                editor.commit();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();


            } else {

                showProgress(true);
                PingTask ping = new PingTask();
                ping.execute();


            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

