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


public class Contactmechanikow extends Fragment  {

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_contactmechanikow, container, false);
        recyclerView =(RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));


        ArrayList<String> lista= new ArrayList<>();

        lista.add("Prorektor ds. nauczania i studentów" +
                "\ndr Robert Musiałkiewicz" +
                "\nPokój: 10" +
                "\nTel: 728310549" +
                "\nMail: robert.musialkiewicz@pwsz.wloclawek.pl");



        recyclerView.setAdapter(new RecyclerAdapterContact(lista));




        return rootView;
    }




}
