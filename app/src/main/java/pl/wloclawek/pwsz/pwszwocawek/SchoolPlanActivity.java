package pl.wloclawek.pwsz.pwszwocawek;

import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class SchoolPlanActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_plan);
        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);


        TabHost.TabSpec tab1 = tabHost.newTabSpec("First Tab");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Second Tab");


        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
        tab1.setIndicator("UL. MECHANIKÓW");
        tab1.setContent(new Intent(this,SchoolPlan_mechanikow.class));

        tab2.setIndicator("ZAWIŚLE");
        tab2.setContent(new Intent(this,SchoolPlan_zawisle.class));



        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
    }
}
