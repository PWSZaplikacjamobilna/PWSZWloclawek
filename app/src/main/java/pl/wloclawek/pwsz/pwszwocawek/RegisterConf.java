package pl.wloclawek.pwsz.pwszwocawek;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class RegisterConf extends AppCompatActivity {

    private RegisterConf.UserLoginTask mAuthTask = null;
    private EditText token;
    private Button buttonR;
    private View mProgressView;
    private View mLoginFormView;
    String numer;

    SharedPreferences sharedPref;
    String cookieFromPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_conf);


        token = (EditText) findViewById(R.id.token);
        Button mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        sharedPref =  getSharedPreferences("tajnaPWSZ", MODE_PRIVATE);
        cookieFromPref = sharedPref.getString("tajneCookie","null");

        if (!isOnline()) {

            Toast.makeText(this, "Brak połączenia z internetem !",
                    Toast.LENGTH_LONG).show();
        }


    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        token.setError(null);


        // Store values at the time of the login attempt.
        String tokenTXT = token.getText().toString();
        Intent intent = getIntent();
         numer =intent.getStringExtra("numer");
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(tokenTXT)) {
            token.setError(getString(R.string.error_field_required));
            focusView = token;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new RegisterConf.UserLoginTask(tokenTXT);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isNumerValid(String numer) {
        //TODO: Replace this with your own logic
        return numer.length() == 4;
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


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private String METHOD_NAME = "RejstracjaPotwierdzenie";
        private static final String NAMESPACE = "http://tempuri.org/";
        private static final String URL = "http://77.245.247.158:2196/Service1.svc";
        String SOAP_ACTION = "http://tempuri.org/IService1/RejstracjaPotwierdzenie";
        String error;
        String cookie = "false";
        Boolean jest = false;
        String mNumer;
        String mToken;


        UserLoginTask(String token) {

            mToken= token;
            mNumer = numer;
        }
        @Override
        protected void onPreExecute() {
            showProgress(true);
        }
        @Override
        protected Boolean doInBackground(Void... params) {

            String resultData = "false";
            try {

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("numer",mNumer);
                request.addProperty("token",mToken);


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);


                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.debug = true;
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapPrimitive result = (SoapPrimitive) envelope.getResponse();

                Log.d("dump Request: " ,androidHttpTransport.requestDump);
                Log.d("dump response: " ,androidHttpTransport.responseDump);
                //to get the data
                resultData = result.toString();
                // 0 is the first object of data
//                Log.e("TAG",mNumer);
//                Log.e("TAG",kierunek+"kierunek");
//                Log.e("TAG",grupaw+"");
//                Log.e("TAG",grupal+"");
//                Log.e("TAG",mPassword+"");
//                Log.e("TAG",promo+"");
//                Log.e("TAG",spec+"");
//                Log.e("TAG",rok+"");
                Log.i("TAG", "OK -------------------rEJSTRACJA--------------------------- " + resultData);


            } catch (Exception e) {
                Log.i("TAG", "ERR -----------rEJESTRACJA-------------------------------- " + e);


            }

            if(resultData.equals("false") ){

                jest= true;
                return false;
            }
            if (resultData.contains("ERR")  ) {
                error =resultData.toString();
                return false;
            } else {
                cookie=resultData;

                return true;
            }

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            if(jest){
                showProgress(false);
                Toast.makeText(RegisterConf.this, "Błędny token.",
                        Toast.LENGTH_LONG).show();
            }
            if (success) {
                Toast.makeText(RegisterConf.this, cookie,
                        Toast.LENGTH_LONG).show();

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("tajneCookie", cookie);
                editor.commit();
                Intent intent = new Intent(RegisterConf.this, MainActivity.class);
                intent.putExtra("tajneCookie",cookie);
                startActivity(intent);
                finish();
            } else {
                showProgress(false);
                Toast.makeText(RegisterConf.this, "Błąd !",
                        Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }



}
