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
import android.webkit.WebView;
import android.widget.Button;

import android.widget.ImageView;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;


public class Informationsession extends Fragment  {

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_informationsession, container, false);


        String data="<!DOCTYPE html><html><body style=\"background-color: #eeeeee;\"><div class=\"webreaderArticle162\"> <p style=\"margin-bottom: 0.0001pt;\">Zimowa sesja egzaminacyjna w roku akademickim 2016/2017 trwa:&nbsp;od 01.02.2017 -&nbsp; do 14.02.2017<br>Zimowa sesja poprawkowa w roku akademickim 2016/2017 trwa:&nbsp;od 15.02.2017 - do 21.02.2017</p> <p style=\"margin-bottom: 0.0001pt;\">Termin wpisów do informatycznego systemu zarządzania procesem dydaktycznym upływa 28.02.2017.<br>Studenci III i IV roku mają obowiązek złożenia uzupełnionego indeksu wraz z kartą okresowych osiągnięć do sekretariatu właściwego Instytutu najpóźniej<strong>&nbsp;do 28.02.2017.</strong></p> <p style=\"margin-bottom: 0.0001pt;\"><strong>Terminarze zimowej sesji egzaminacyjnej w roku akademickim&nbsp;2016/2017</strong></p> <p style=\"margin-bottom: 0.0001pt;\">Terminarz zimowej sesji egzaminacyjnej&nbsp; FILOLOGIA&nbsp; ANGIELSKA</p> <p style=\"margin-bottom: 0.0001pt;\">Terminarz zimowej sesji egzaminacyjnej PEDAGOGIKA</p> <p style=\"margin-bottom: 0.0001pt;\">Terminarz zimowej sesji egzaminacyjnej Zarządzanie - studia stacjonarne</p> <p style=\"margin-bottom: 0.0001pt;\">Terminarz zimowej sesji egzaminacyjnej Inżynieria zarządzania - studia stacjonarne</p> <p style=\"margin-bottom: 0.0001pt;\">Terminarz zimowej sesji egzaminacyjnej Informatyka&nbsp;- studia stacjonarne</p> <p style=\"margin-bottom: 0.0001pt;\">Terminarz zimowej sesji egzaminacyjnej PIELĘGNIARSTWO - studia stacjonarne</p> <p style=\"margin-bottom: 0.0001pt;\">Terminarz zimowej sesji egzaminacyjnej Administracja</p> <p style=\"margin-bottom: 0.0001pt;\">&nbsp;Terminarz zimowej sesji egzaminacyjnej Mechanika i budowa maszyn</p> <div style=\"vertical-align:top; \"> <div class=\"easyplus\"> <div id=\"fb-root\" class=\" fb_reset\"> <div style=\"position: absolute; top: -10000px; height: 0px; width: 0px;\"> <div><iframe name=\"fb_xdm_frame_https\" frameborder=\"0\" allowtransparency=\"true\" allowfullscreen=\"true\" scrolling=\"no\" id=\"fb_xdm_frame_https\" aria-hidden=\"true\" title=\"Facebook Cross Domain Communication Frame\" tabindex=\"-1\" src=\"https://staticxx.facebook.com/connect/xd_arbiter/r/iPrOY23SGAp.js?version=42#channel=f1c83ec2efeb32&amp;origin=https%3A%2F%2Fwww.pwsz.wloclawek.pl\" style=\"border: none;\"></iframe></div> </div> <div style=\"position: absolute; top: -10000px; height: 0px; width: 0px;\"> <div></div> </div> </div> </div> <div style=\"clear: both;\"></div> </div></div></body></html>";
        WebView web = (WebView)  rootView.findViewById(R.id.WebOrg) ;
        web.getSettings().setJavaScriptEnabled(true);
           web.loadDataWithBaseURL("", data, "text/html", "UTF-8", "");

        return rootView;
    }




}
