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


public class Contactrektorat extends Fragment  {

    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_contactrektorat, container, false);
        recyclerView =(RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));


        ArrayList<String> lista= new ArrayList<>();

        lista.add("PWSZ we Włocławku" +
                "\nul. 3 Maja 17" +
                "\n87-800 Włocławek" +
                "\nTel: 542316080" +
                "\nMail: kancelaria@pwsz.wloclawek.pl");




            recyclerView.setAdapter(new RecyclerAdapterContact(lista));




        return rootView;
    }




}
