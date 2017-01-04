package pl.wloclawek.pwsz.pwszwocawek;

import android.app.Activity;
import android.content.DialogInterface;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import android.os.Bundle;
import android.support.v7.widget.CardView;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageView;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;


public class Contactzawisle extends Fragment  {

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_contactzawisle, container, false);

        recyclerView =(RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));


        ArrayList<String> lista= new ArrayList<>();

        lista.add("Sekretariat Instytutu Nauk o Zdrowiu" +
                "\nmgr Justyna Kociuba" +
                "\nPokój: 106" +
                "\nTel: 696724467" +
                "\nMail: justyna.kociuba@pwsz.wloclawek.pl");

        lista.add("Dyrektor Instytutu Nauk o Zdrowiu" +
                "\ndr Beata Haor" +
                "\nPokój: 103" +
                "\nTel: 668075553" +
                "\nMail: beata.haor@pwsz.wloclawek.pl");

        lista.add("Studium Wychowania Fizycznego i Sportu - Kierownik" +
                "\nmgr Teresa Banachowska" +
                "\nPokój: 20" +
                "\nTel: 668075501" +
                "\nMail: teresa.banachowska@pwsz.wloclawek.pl");

        lista.add("Sekretariat Studium Wychowania Fizycznego i Sportu" +
                "\nmgr Justyna Kociuba" +
                "\nPokój: 20" +
                "\nTel: 696724467" +
                "\nMail: justyna.kociuba@pwsz.wloclawek.pl");

        lista.add("Kierownik Domu Studenta oraz Administrator Obiektu" +
                "\nStanisław Wójcik" +
                "\nPokój: 18" +
                "\nTel: 668075547" +
                "\nMail: stanislaw.wojcik@pwsz.wloclawek.pl");









        recyclerView.setAdapter(new RecyclerAdapterContact(lista));



        return rootView;
    }




}
