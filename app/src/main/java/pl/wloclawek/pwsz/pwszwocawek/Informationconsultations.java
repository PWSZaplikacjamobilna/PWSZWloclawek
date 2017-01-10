package pl.wloclawek.pwsz.pwszwocawek;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;


public class Informationconsultations extends Fragment  {

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_informationconsultations, container, false);



        Button b1 = (Button) rootView.findViewById(R.id.button1k);
        Button b2 = (Button) rootView.findViewById(R.id.button2k);
        Button b3 = (Button) rootView.findViewById(R.id.button3k);
        Button b4 = (Button) rootView.findViewById(R.id.button4k);
        Button b5 = (Button) rootView.findViewById(R.id.button5k);
        Button b6 = (Button) rootView.findViewById(R.id.button6k);
        Button b7 = (Button) rootView.findViewById(R.id.button7k);

        b1.setText("Dyżury Zakładu Filologii Angielskiej, Pedagogiki oraz Studium Języków Obcych ");
        b2.setText("Dyżury pracowników dydaktycznych Zakładu Administracji w semestrze zimowym 2016/2017");
        b3.setText("Dyżury pracowników dydaktycznych Zakładu Zarządzania  ");
        b4.setText("Dyżury pracowników dydaktycznych Zakładu Informatyki");
        b5.setText("Dyżury pracowników dydaktycznych Zakładu Mechaniki i budowy maszyn");
        b6.setText("Dyżury pracowników dydaktycznych Instytutu Nauk o Zdrowiu");
        b7.setText("Dyżury pracowników Studium Wychowania Fizycznego i Sportu");

       b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pwsz.wloclawek.pl/images/Dy%C5%BCury_semestr_zima_16-17_17_listop.pdf"));
                startActivity(browserIntent);

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pwsz.wloclawek.pl/images/Wykaz_dyzurow_sem._zimowy_16-17.pdf"));
                startActivity(browserIntent);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pwsz.wloclawek.pl/images/Wykaz_dy%C5%BCur%C3%B3w_zz_w_sem._zim._2016-17.pdf"));
                startActivity(browserIntent);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pwsz.wloclawek.pl/images/Wykaz_dy%C5%BCur%C3%B3w_Inf._w_sem._zim._2016-17.pdf"));
                startActivity(browserIntent);
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pwsz.wloclawek.pl/images/Wykaz_dy%C5%BCur%C3%B3w_sem._zimowy_16-17_MiBM.pdf"));
                startActivity(browserIntent);
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pwsz.wloclawek.pl/images/student/dyzury_2016-17_zima.pdf"));
                startActivity(browserIntent);
            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pwsz.wloclawek.pl/images/student/Dy%C5%BCury_semestr_2016-17_zima.pdf"));
                startActivity(browserIntent);
            }
        });

        return rootView;
    }




}
