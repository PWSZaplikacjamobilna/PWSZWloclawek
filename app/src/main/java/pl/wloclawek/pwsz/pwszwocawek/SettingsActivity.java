package pl.wloclawek.pwsz.pwszwocawek;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    Locale myLocale;
    Switch s;
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sharedPref = getSharedPreferences("tajnaPWSZ", MODE_PRIVATE);

s = (Switch) findViewById(R.id.switch1);
        s.setChecked(sharedPref.getBoolean("noti", false));

    }
    public void polish(View view) {
        setLocale("pl");
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

    public void turkish(View view) {
        setLocale("tr");
    }

    public void germany(View view) {setLocale("de");
    }

    public void switchclick(View view) {

           SharedPreferences.Editor editor = sharedPref.edit();
           editor.putBoolean("noti", s.isChecked());
        editor.commit();

    }
}
