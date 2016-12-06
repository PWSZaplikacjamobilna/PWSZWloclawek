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
import android.os.Bundle;
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
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class RegisterActivity extends AppCompatActivity {


    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mNumerView;
    private EditText mPasswordView;
    private EditText mPasswordView2;
    private View mProgressView;
    private View mLoginFormView;

    Spinner spinnerKierunek;
    Spinner spinnerRok;
    Spinner spinnerPromotor;
    Spinner spinnerGrupaW;
    Spinner spinnerGrupaL;
    Spinner spinnerSpec;
    SharedPreferences sharedPref;
    String cookieFromPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Set up the login form.
        mNumerView = (AutoCompleteTextView) findViewById(R.id.numer);


        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView2 = (EditText) findViewById(R.id.password2);
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

        Button mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new OnClickListener() {
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
        spinnerSpec = (Spinner) findViewById(R.id.spinnerSpecjalnosc);
        spinnerPromotor = (Spinner) findViewById(R.id.spinnerPromotor);
        // Initializing a String Array
        String[] ladowanie = new String[]{
                getString(R.string.ladowanie),
        };


        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapterK = new ArrayAdapter<String>(
                this, R.layout.spinner_item, ladowanie
        );
        spinnerArrayAdapterK.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKierunek.setAdapter(spinnerArrayAdapterK);

        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapterS = new ArrayAdapter<String>(
                this, R.layout.spinner_item, ladowanie
        );
        spinnerArrayAdapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpec.setAdapter(spinnerArrayAdapterS);

        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapterR = new ArrayAdapter<String>(
                this, R.layout.spinner_item, ladowanie
        );
        spinnerArrayAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRok.setAdapter(spinnerArrayAdapterR);

        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapterG = new ArrayAdapter<String>(
                this, R.layout.spinner_item, ladowanie
        );
        spinnerArrayAdapterG.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGrupaL.setAdapter(spinnerArrayAdapterG);

        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapterGW = new ArrayAdapter<String>(
                this, R.layout.spinner_item, ladowanie
        );
        spinnerArrayAdapterG.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGrupaW.setAdapter(spinnerArrayAdapterG);

        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapterP = new ArrayAdapter<String>(
                this, R.layout.spinner_item, ladowanie
        );
        spinnerArrayAdapterP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPromotor.setAdapter(spinnerArrayAdapterP);
        sharedPref =  getSharedPreferences("tajnaPWSZ", MODE_PRIVATE);
        cookieFromPref = sharedPref.getString("tajneCookie","null");

        if (!isOnline()) {

            Toast.makeText(this, "Brak połączenia z internetem !",
                    Toast.LENGTH_LONG).show();
        }


        GetDataTask data = new GetDataTask();
        data.execute();
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
        mNumerView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String numer = mNumerView.getText().toString();
        String password = mPasswordView.getText().toString();
        String password2 = mPasswordView2.getText().toString();
        String grupal = spinnerGrupaL.getSelectedItem().toString();
        String grupaw = spinnerGrupaW.getSelectedItem().toString();
        String promo = spinnerPromotor.getSelectedItem().toString();
        String rok = spinnerRok.getSelectedItem().toString();
        String spec = spinnerSpec.getSelectedItem().toString();
        String kierunek = spinnerKierunek.getSelectedItem().toString();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if (!TextUtils.isEmpty(password) && !password.equals(password2)) {
            mPasswordView.setError(getString(R.string.error_takiesame));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(numer)) {
            mNumerView.setError(getString(R.string.error_field_required));
            focusView = mNumerView;
            cancel = true;
        } else if (!isNumerValid(numer)) {

            mNumerView.setError(getString(R.string.error_invalid_email));
            focusView = mNumerView;
            cancel = true;
        } else if (grupal.equals("Ładowanie ...") || grupal.equals(getString(R.string.wybGrupaL))) {
            focusView = spinnerGrupaL;
            ((TextView) spinnerGrupaL.getSelectedView()).setError("");
            cancel = true;
        }
        if (grupaw.equals("Ładowanie ...") || grupaw.equals(getString(R.string.wybGrupaW))) {
            focusView = spinnerGrupaW;
            ((TextView) spinnerGrupaW.getSelectedView()).setError("");
            cancel = true;
        }
        if (kierunek.equals("Ładowanie ...") || kierunek.equals(getString(R.string.wybKier))) {
            focusView = spinnerKierunek;
            ((TextView) spinnerKierunek.getSelectedView()).setError("");
            cancel = true;
        }
        if (promo.equals("Ładowanie ...") || promo.equals(getString(R.string.wybPromo))) {
            focusView = spinnerPromotor;
            ((TextView) spinnerPromotor.getSelectedView()).setError("");
            cancel = true;
        }
        if (rok.equals("Ładowanie ...") || rok.equals(getString(R.string.wybRok))) {
            focusView = spinnerRok;
            ((TextView) spinnerRok.getSelectedView()).setError("");
            cancel = true;
        }
        if (spec.equals("Ładowanie ...") || spec.equals(getString(R.string.wybSpec))) {
            focusView = spinnerSpec;
            ((TextView) spinnerSpec.getSelectedView()).setError("");
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
    public class GetDataTask extends AsyncTask<Void, Void, Boolean> {

        private String METHOD_NAME = "RejstracjaDane2";
//  private static final String METHOD_NAME = "HelloWorld";

        private final String NAMESPACE = "http://tempuri.org/";
//  private static final String NAMESPACE = "http://tempuri.org";

        private final String URL = "http://77.245.247.158:2196/Service1.svc";
//  private static final String URL = "http://192.168.0.2:8080/webservice1  /Service1.asmx";

        String SOAP_ACTION = "http://tempuri.org/IService1/RejstracjaDane2";
        //  final String SOAP_ACTION = "http://tempuri.org/HelloWorld";
        String resultData;
        String[] kierunkiarray;
        String[] rokarray;
        String[] promotorarray;
        String[] specjalnoscarray;
        String[] grupalrray;
        String[] grupawrray;

        @Override
        protected Boolean doInBackground(Void... params) {
            try {

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                class Kierunek {
                    String id;
                    String kierunek;
                }
                class Promotor {
                    String id;
                    String promotor;
                }
                class Rok {
                    String id;
                    String rok;
                }
                class Spec {
                    String id;
                    String specjalnosc;
                }
                class GrupaW {
                    String id;
                    String grupa;
                }
                class GrupaL {
                    String id;
                    String grupa;
                }


                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapObject soap = (SoapObject) envelope.getResponse();

                SoapObject soapGrupaL = (SoapObject) soap.getProperty(0);
                SoapObject soapGrupaW = (SoapObject) soap.getProperty(1);
                SoapObject soapKierunki = (SoapObject) soap.getProperty(2);
                SoapObject soapPromotor = (SoapObject) soap.getProperty(3);
                SoapObject soapRok = (SoapObject) soap.getProperty(4);
                SoapObject soapSpecjalnosc = (SoapObject) soap.getProperty(5);


                Kierunek[] kierunki = new Kierunek[soapKierunki.getPropertyCount()];
                Log.e("TAG", "-------KIER" + soapKierunki.getPropertyCount());
                for (int i = 0; i < kierunki.length; i++) {
                    SoapObject pii = (SoapObject) soapKierunki.getProperty(i);
                    Kierunek kierunek = new Kierunek();
                    kierunek.id = pii.getProperty(1).toString();
                    kierunek.kierunek = pii.getProperty(0).toString();
                    kierunki[i] = kierunek;
                }

                Promotor[] promotors = new Promotor[soapPromotor.getPropertyCount()];
                Log.e("TAG", "-------PRO" + soapPromotor.getPropertyCount());
                for (int i = 0; i < promotors.length; i++) {
                    SoapObject pii = (SoapObject) soapPromotor.getProperty(i);
                    Promotor promotor = new Promotor();
                    promotor.id = pii.getProperty(1).toString();
                    promotor.promotor = pii.getProperty(0).toString();
                    promotors[i] = promotor;
                }
                Rok[] roks = new Rok[soapRok.getPropertyCount()];
                Log.e("TAG", "-------ROK" + soapRok.getPropertyCount());
                for (int i = 0; i < roks.length; i++) {
                    SoapObject pii = (SoapObject) soapRok.getProperty(i);
                    Rok rok = new Rok();
                    rok.id = pii.getProperty(1).toString();
                    rok.rok = pii.getProperty(0).toString();
                    roks[i] = rok;
                }

                Spec[] specs = new Spec[soapSpecjalnosc.getPropertyCount()];
                Log.e("TAG", "-------SPEC" + soapSpecjalnosc.getPropertyCount());
                for (int i = 0; i < specs.length; i++) {
                    SoapObject pii = (SoapObject) soapSpecjalnosc.getProperty(i);
                    Spec spec = new Spec();
                    spec.id = pii.getProperty(0).toString();
                    spec.specjalnosc = pii.getProperty(1).toString();
                    specs[i] = spec;
                }
                GrupaW[] grupyW = new GrupaW[soapGrupaW.getPropertyCount()];
                Log.e("TAG", "-------GRUPAW" + soapGrupaW.getPropertyCount());
                for (int i = 0; i < grupyW.length; i++) {
                    SoapObject pii = (SoapObject) soapGrupaW.getProperty(i);
                    GrupaW spec = new GrupaW();
                    spec.id = pii.getProperty(0).toString();
                    spec.grupa = pii.getProperty(1).toString();
                    grupyW[i] = spec;
                }
                GrupaL[] grupyL = new GrupaL[soapGrupaL.getPropertyCount()];
                Log.e("TAG", "-------GRUPAL" + soapGrupaL.getPropertyCount());
                for (int i = 0; i < grupyL.length; i++) {
                    SoapObject pii = (SoapObject) soapGrupaL.getProperty(i);
                    GrupaL spec = new GrupaL();
                    spec.id = pii.getProperty(1).toString();
                    spec.grupa = pii.getProperty(0).toString();
                    grupyL[i] = spec;
                }

                Log.e("TAG", "-------LISTA");
                for (Spec k :
                        specs) {
                    Log.e("TAG", "-------" + k.specjalnosc);

                }

                kierunkiarray = new String[kierunki.length + 1];
                kierunkiarray[0] = getString(R.string.wybKier);
                int index = 1;
                for (Kierunek k : kierunki) {

                    kierunkiarray[index] = k.kierunek;
                    index++;
                }
                promotorarray = new String[promotors.length + 1];
                promotorarray[0] = getString(R.string.wybPromo);
                index = 1;
                for (Promotor k : promotors) {
                    promotorarray[index] = k.promotor;
                    index++;
                }
                specjalnoscarray = new String[specs.length + 1];
                specjalnoscarray[0] = getString(R.string.wybSpec);
                index = 1;
                for (Spec k : specs) {
                    specjalnoscarray[index] = k.specjalnosc;
                    index++;
                }
                rokarray = new String[roks.length + 1];
                rokarray[0] = getString(R.string.wybRok);
                index = 1;
                for (Rok k : roks) {
                    rokarray[index] = k.rok;
                    index++;
                }

                grupawrray = new String[grupyW.length + 1];
                grupawrray[0] = getString(R.string.wybGrupaW);
                index = 1;
                for (GrupaW k : grupyW) {
                    grupawrray[index] = k.grupa;
                    index++;
                }
                grupalrray = new String[grupyL.length + 1];
                grupalrray[0] = getString(R.string.wybGrupaL);
                index = 1;
                for (GrupaL k : grupyL) {
                    grupalrray[index] = k.grupa;
                    index++;
                }


                //to get the data
                //resultData = result.toString();
                // 0 is the first object of data

                Log.i("TAG", "OK --------dane------------------------------ " + resultData);


            } catch (Exception e) {
                Log.i("TAG", "ERR --------dane------------------------------------------ " + e);


            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            try {
                ArrayAdapter<String> spinnerArrayAdapterK = new ArrayAdapter<String>(
                        RegisterActivity.this, R.layout.spinner_item, kierunkiarray
                );
                spinnerArrayAdapterK.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerKierunek.setAdapter(spinnerArrayAdapterK);


                ArrayAdapter<String> spinnerArrayAdapterS = new ArrayAdapter<String>(
                        RegisterActivity.this, R.layout.spinner_item, specjalnoscarray
                );
                spinnerArrayAdapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSpec.setAdapter(spinnerArrayAdapterS);


                ArrayAdapter<String> spinnerArrayAdapterA = new ArrayAdapter<String>(
                        RegisterActivity.this, R.layout.spinner_item, promotorarray
                );
                spinnerArrayAdapterA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerPromotor.setAdapter(spinnerArrayAdapterA);


                ArrayAdapter<String> spinnerArrayAdapterR = new ArrayAdapter<String>(
                        RegisterActivity.this, R.layout.spinner_item, rokarray
                );
                spinnerArrayAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerRok.setAdapter(spinnerArrayAdapterR);

                ArrayAdapter<String> spinnerArrayAdapterGW = new ArrayAdapter<String>(
                        RegisterActivity.this, R.layout.spinner_item, grupawrray
                );
                spinnerArrayAdapterGW.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerGrupaW.setAdapter(spinnerArrayAdapterGW);

                ArrayAdapter<String> spinnerArrayAdapterGL = new ArrayAdapter<String>(
                        RegisterActivity.this, R.layout.spinner_item, grupalrray
                );
                spinnerArrayAdapterGL.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerGrupaL.setAdapter(spinnerArrayAdapterGL);

            } catch (Exception e) {

            }


        }

        @Override
        protected void onCancelled() {

        }
    }


    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private String METHOD_NAME = "Rejestracja";
        private static final String NAMESPACE = "http://tempuri.org/";
        private static final String URL = "http://77.245.247.158:2196/Service1.svc";
        String SOAP_ACTION = "http://tempuri.org/IService1/Rejestracja";
            String error;
        String cookie = "false";
        Boolean jest = false;
        String mNumer;
        String mPassword;
        int grupal = spinnerGrupaL.getSelectedItemPosition() ;
        int grupaw = spinnerGrupaW.getSelectedItemPosition() ;
        int promo = spinnerPromotor.getSelectedItemPosition() ;
        int rok = spinnerRok.getSelectedItemPosition() ;
        int spec = spinnerSpec.getSelectedItemPosition();
        int kierunek = spinnerKierunek.getSelectedItemPosition() ;

        UserLoginTask(String email, String password) {
            mNumer = email;
            mPassword = password;

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

                request.addProperty("indeks",mNumer);
                request.addProperty("haslo",mPassword);
                request.addProperty("kierunek",kierunek);
                request.addProperty("rok",rok);
                request.addProperty("grupaW",grupaw);
                request.addProperty("grupaL",grupal);
                request.addProperty("promotor",promo);
                request.addProperty("specjalnosc",spec);


//                PropertyInfo p_indeks = new PropertyInfo();
//                p_indeks.setName("indeks");
//                p_indeks.setValue("0000");
//                p_indeks.setType(PropertyInfo.STRING_CLASS);
//                request.addProperty(p_indeks);
//
//
//
//                PropertyInfo h = new PropertyInfo();
//                h.setName("haslo");
//                h.setValue(mPassword);
//                h.setType(String.class);
//                request.addProperty(h);
//
//
//                PropertyInfo k = new PropertyInfo();
//                k.setName("kierunek");
//                k.setValue(kierunek);
//                k.setType(PropertyInfo.INTEGER_CLASS);
//                request.addProperty(k);
//
//
//                PropertyInfo r = new PropertyInfo();
//                r.setName("rok");
//                r.setValue(rok);
//                r.setType(PropertyInfo.INTEGER_CLASS);
//                request.addProperty(r);
//
//                PropertyInfo gw = new PropertyInfo();
//                gw.setName("grupaW");
//                gw.setValue(grupaw);
//                gw.setType(PropertyInfo.INTEGER_CLASS);
//                request.addProperty(gw);
//
//                PropertyInfo gl = new PropertyInfo();
//                gl.setName("grupaL");
//                gl.setValue(grupal);
//                gl.setType(PropertyInfo.INTEGER_CLASS);
//                request.addProperty(gl);
//
//                PropertyInfo pro = new PropertyInfo();
//                pro.setName("promotor");
//                pro.setValue(promo);
//                pro.setType(PropertyInfo.INTEGER_CLASS);
//                request.addProperty(pro);
//
//                PropertyInfo specjal = new PropertyInfo();
//                specjal.setName("specjalnosc");
//                specjal.setValue(spec);
//                specjal.setType(PropertyInfo.INTEGER_CLASS);
//                request.addProperty(specjal);


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

            if(resultData.equals("jest") ){

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
                Toast.makeText(RegisterActivity.this, "Istnieje już konto z takim numerem indeksu.",
                        Toast.LENGTH_LONG).show();
            }
            if (success) {
                Toast.makeText(RegisterActivity.this, cookie,
                        Toast.LENGTH_LONG).show();

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("tajneCookie", cookie);
                editor.commit();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                showProgress(false);
                Toast.makeText(RegisterActivity.this, "Błąd !",
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

