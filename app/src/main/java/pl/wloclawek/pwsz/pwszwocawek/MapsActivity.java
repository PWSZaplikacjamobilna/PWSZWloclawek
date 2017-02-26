package pl.wloclawek.pwsz.pwszwocawek;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Spinner spinnerplace;
    String location;
  View mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
  mapView = mapFragment.getView();
        spinnerplace = (Spinner) findViewById(R.id.spinnerplace);
        ArrayAdapter adapter=ArrayAdapter.createFromResource(this,R.array.miejsce,android.R.layout.simple_spinner_dropdown_item);
        spinnerplace.setAdapter(adapter);
        spinnerplace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                switch((int)position)
                {
                    case 0:
                        location="brak";
                        szukaj();
                        break;
                    case 1:
                        location="mechanikow";
                        szukaj();
                        break;
                    case 2:
                        location="zawisle";
                        szukaj();
                        break;
                    case 3:
                        location="3maja";
                        szukaj();
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void szukaj() {
            mMap.clear();

        if (location.equals("brak")) {

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(52.659, 19.059), 11));

        }
        if (location.equals("mechanikow")) {


            LatLng mechanikow = new LatLng(52.668729, 19.040183);
            mMap.addMarker(new MarkerOptions().position(mechanikow).title("ul. Mechaników 3").icon(BitmapDescriptorFactory.fromResource(R.drawable.university)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mechanikow, 13));


        }

        if (location.equals("zawisle")) {


            LatLng mechanikow = new LatLng(52.671947, 19.082564);
            mMap.addMarker(new MarkerOptions().position(mechanikow).title("ul. Obrońców Wisły 1920 21/25").icon(BitmapDescriptorFactory.fromResource(R.drawable.university)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mechanikow, 13));

        }

        if (location.equals("3maja")) {


            LatLng mechanikow = new LatLng(52.658027, 19.069735);
            mMap.addMarker(new MarkerOptions().position(mechanikow).title("ul. 3 Maja 17").icon(BitmapDescriptorFactory.fromResource(R.drawable.university)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mechanikow, 13));


    }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        float zoomLevel = 11;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(52.659, 19.059), zoomLevel));


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);

        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 20, 20);
        }


    }

    public void clicknormal(View view) {

            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

    public void clicksatellite(View view) {

            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


    }
}
