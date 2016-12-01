package pl.wloclawek.pwsz.pwszwocawek;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;


public class RegisterActivity extends AppCompatActivity {


    private static final int REQUEST_READ_CONTACTS = 0;



    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mNumerView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    Spinner spinnerKierunek;
    Spinner spinnerRok;
    Spinner spinnerPromotor;
    Spinner spinnerGrupaW;
    Spinner spinnerGrupaL;
    Spinner spinnerSpec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Set up the login form.
        mNumerView = (AutoCompleteTextView) findViewById(R.id.numer);


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

        Button mEmailSignInButton = (Button) findViewById(R.id.numer_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

         spinnerKierunek = (Spinner) findViewById(R.id.spinnerKierunek);
         spinnerRok = (Spinner) findViewById(R.id.spinnerRok);
         spinnerGrupaW = (Spinner) findViewById(R.id.spinnerGrupaWykladowa);
         spinnerGrupaL = (Spinner) findViewById(R.id.spinnerGrupaLab);
         spinnerSpec  = (Spinner) findViewById(R.id.spinnerSpecjalnosc);
         spinnerPromotor= (Spinner) findViewById(R.id.spinnerPromotor);
        // Initializing a String Array
        String[] kierunek = new String[]{
                "Kierunek",
                "Inforamtyka",
                "Zarządzanie",
        };
        String[] rok = new String[]{
                "Rok",
                "I",
                "II",
        };
        String[] grupa = new String[]{
                "Grupa",
                "A",
                "B",
        };
        String[] promotor = new String[]{
                "Promotor",
                "dr inz. Adam Kowalski",

        };

        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapterK = new ArrayAdapter<String>(
                this,R.layout.spinner_item,kierunek
        );
        spinnerArrayAdapterK.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKierunek.setAdapter(spinnerArrayAdapterK);

        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapterR = new ArrayAdapter<String>(
                this,R.layout.spinner_item,rok
        );
        spinnerArrayAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRok.setAdapter(spinnerArrayAdapterR);

        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapterG= new ArrayAdapter<String>(
                this,R.layout.spinner_item,grupa
        );
        spinnerArrayAdapterG.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGrupaL.setAdapter(spinnerArrayAdapterG);

        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapterGW= new ArrayAdapter<String>(
                this,R.layout.spinner_item,grupa
        );
        spinnerArrayAdapterG.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGrupaW.setAdapter(spinnerArrayAdapterG);

        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapterP = new ArrayAdapter<String>(
                this,R.layout.spinner_item,promotor
        );
        spinnerArrayAdapterP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPromotor.setAdapter(spinnerArrayAdapterP);

         GetDataTask data = new GetDataTask();
         data.execute();
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
            showProgress(true);
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




    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class GetDataTask extends AsyncTask<Void, Void, Boolean> {

        private   String METHOD_NAME = "RejstracjaDane2";
//  private static final String METHOD_NAME = "HelloWorld";

        private  final String NAMESPACE = "http://tempuri.org/";
//  private static final String NAMESPACE = "http://tempuri.org";

        private  final String URL = "http://77.245.247.158:2196/Service1.svc";
//  private static final String URL = "http://192.168.0.2:8080/webservice1  /Service1.asmx";

        String SOAP_ACTION = "http://tempuri.org/IService1/RejstracjaDane2";
        //  final String SOAP_ACTION = "http://tempuri.org/HelloWorld";
        String resultData;
        String[] kierunkiarray;
        String[] rokarray;
        String[] promotorarray;
        String[] specjalnoscarray;
        String[]  grupalrray;
        String[]  grupawrray;

        @Override
        protected Boolean doInBackground(Void... params) {
            try {

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);



                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                class Kierunek{
                    String id;
                    String kierunek;
                }
                class Promotor{
                    String id;
                    String promotor;
                }
                class Rok{
                    String id;
                    String rok;
                }
                class Spec{
                    String id;
                    String specjalnosc;
                }
                class GrupaW{
                    String id;
                    String grupa;
                }
                class GrupaL{
                    String id;
                    String grupa;
                }


                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapObject soap = (SoapObject) envelope.getResponse();

                SoapObject soapGrupaL= (SoapObject)soap.getProperty(0);
                SoapObject soapGrupaW = (SoapObject)soap.getProperty(1);
                SoapObject soapKierunki = (SoapObject)soap.getProperty(2);
                SoapObject soapPromotor = (SoapObject)soap.getProperty(3);
                SoapObject soapRok = (SoapObject)soap.getProperty(4);
                SoapObject soapSpecjalnosc = (SoapObject)soap.getProperty(5);


                Kierunek[] kierunki = new Kierunek[soapKierunki.getPropertyCount()];
                Log.e("TAG","-------KIER"+soapKierunki.getPropertyCount());
                for (int i = 0; i < kierunki.length; i++) {
                    SoapObject pii = (SoapObject)soapKierunki.getProperty(i);
                    Kierunek kierunek = new Kierunek();
                    kierunek.id = pii.getProperty(0).toString();
                    kierunek.kierunek = pii.getProperty(1).toString();
                    kierunki[i] = kierunek;
                }

                Promotor[] promotors = new Promotor[soapPromotor.getPropertyCount()];
                Log.e("TAG","-------PRO"+soapPromotor.getPropertyCount());
                for (int i = 0; i < promotors.length; i++) {
                    SoapObject pii = (SoapObject)soapPromotor.getProperty(i);
                    Promotor promotor = new Promotor();
                    promotor.id = pii.getProperty(0).toString();
                    promotor.promotor = pii.getProperty(1).toString();
                    promotors[i] = promotor;
                }
                Rok[] roks = new Rok[soapRok.getPropertyCount()];
                Log.e("TAG","-------ROK"+soapRok.getPropertyCount());
                for (int i = 0; i < roks.length; i++) {
                    SoapObject pii = (SoapObject)soapRok.getProperty(i);
                    Rok rok = new Rok();
                    rok.id = pii.getProperty(0).toString();
                    rok.rok = pii.getProperty(1).toString();
                    roks[i] = rok;
                }

                Spec[] specs = new Spec[soapSpecjalnosc.getPropertyCount()];
                Log.e("TAG","-------SPEC"+soapSpecjalnosc.getPropertyCount());
                for (int i = 0; i < specs.length; i++) {
                    SoapObject pii = (SoapObject)soapSpecjalnosc.getProperty(i);
                    Spec spec = new Spec();
                    spec.id = pii.getProperty(0).toString();
                    spec.specjalnosc = pii.getProperty(1).toString();
                    specs[i] = spec;
                }
                GrupaW[] grupyW = new GrupaW[soapGrupaW.getPropertyCount()];
                Log.e("TAG","-------GRUPAW"+soapGrupaW.getPropertyCount());
                for (int i = 0; i < grupyW.length; i++) {
                    SoapObject pii = (SoapObject)soapGrupaW.getProperty(i);
                    GrupaW spec = new GrupaW();
                    spec.id = pii.getProperty(1).toString();
                    spec.grupa = pii.getProperty(0).toString();
                    grupyW[i] = spec;
                }
                GrupaL[] grupyL = new GrupaL[soapGrupaL.getPropertyCount()];
                Log.e("TAG","-------GRUPAL"+soapGrupaL.getPropertyCount());
                for (int i = 0; i < grupyL.length; i++) {
                    SoapObject pii = (SoapObject)soapGrupaL.getProperty(i);
                    GrupaL spec = new GrupaL();
                    spec.id = pii.getProperty(1).toString();
                    spec.grupa = pii.getProperty(0).toString();
                    grupyL[i] = spec;
                }

                Log.e("TAG","-------LISTA");
                for (Spec k:
                        specs) {
                    Log.e("TAG","-------"+k.specjalnosc);

                }

                kierunkiarray = new String[kierunki.length+1];
                kierunkiarray[0] = "Wybierz kierunek ...";
                int index = 1;
                for (Kierunek k : kierunki) {

                    kierunkiarray[index] = k.kierunek;
                    index++;
                }
                promotorarray = new String[promotors.length+1];
                promotorarray[0] = "Wybierz promotora ...";
                 index = 1;
                for (Promotor k : promotors) {
                    promotorarray[index] = k.promotor;
                    index++;
                }
                specjalnoscarray = new String[specs.length+1];
                specjalnoscarray[0] = "Wybierz specjalność ...";
                index = 1;
                for (Spec k : specs) {
                    specjalnoscarray[index] = k.specjalnosc;
                    index++;
                }
                rokarray = new String[roks.length+1];
                rokarray[0] = "Wybierz rok studiów ...";
                index = 1;
                for (Rok k : roks) {
                    rokarray[index] = k.rok;
                    index++;
                }

                grupawrray = new String[grupyW.length+1];
                grupawrray[0] = "Wybierz grupę wykładową...";
                index = 1;
                for (GrupaW k : grupyW) {
                    grupawrray[index] = k.grupa;
                    index++;
                }
                grupalrray = new String[grupyL.length+1];
                grupalrray[0] = "Wybierz grupę laboratoryjną...";
                index = 1;
                for (GrupaL k : grupyL) {
                    grupalrray[index] = k.grupa;
                    index++;
                }



                //to get the data
                //resultData = result.toString();
                // 0 is the first object of data

                Log.i("TAG", "OK ------------------------------------------------------- " + resultData);



            } catch (Exception e) {
                Log.i("TAG", "ERR ------------------------------------------------------- " + e);


            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            ArrayAdapter<String> spinnerArrayAdapterK = new ArrayAdapter<String>(
                    RegisterActivity.this,R.layout.spinner_item,kierunkiarray
            );
            spinnerArrayAdapterK.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerKierunek.setAdapter(spinnerArrayAdapterK);


            ArrayAdapter<String> spinnerArrayAdapterS = new ArrayAdapter<String>(
                    RegisterActivity.this,R.layout.spinner_item,specjalnoscarray
            );
            spinnerArrayAdapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSpec.setAdapter(spinnerArrayAdapterS);


            ArrayAdapter<String> spinnerArrayAdapterA = new ArrayAdapter<String>(
                    RegisterActivity.this,R.layout.spinner_item,promotorarray
            );
            spinnerArrayAdapterA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerPromotor.setAdapter(spinnerArrayAdapterA);


            ArrayAdapter<String> spinnerArrayAdapterR = new ArrayAdapter<String>(
                    RegisterActivity.this,R.layout.spinner_item,rokarray
            );
            spinnerArrayAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerRok.setAdapter(spinnerArrayAdapterR);

            ArrayAdapter<String> spinnerArrayAdapterGW = new ArrayAdapter<String>(
                    RegisterActivity.this,R.layout.spinner_item,grupawrray
            );
            spinnerArrayAdapterGW.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerGrupaW.setAdapter(spinnerArrayAdapterGW);

            ArrayAdapter<String> spinnerArrayAdapterGL = new ArrayAdapter<String>(
                    RegisterActivity.this,R.layout.spinner_item,grupalrray
            );
            spinnerArrayAdapterGL.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerGrupaL.setAdapter(spinnerArrayAdapterGL);




        }

        @Override
        protected void onCancelled() {

        }
    }


    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.


            // TODO: register the new account here.

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

