package pl.wloclawek.pwsz.pwszwocawek;

import android.app.Activity;
import android.content.DialogInterface;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import android.os.Bundle;
import android.support.v7.widget.CardView;

import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;


public class SchoolPlanmechanikow extends Fragment  {

    PhotoViewAttacher mAttacher;
    CardView card_view,card_view2,card_view3,card_view4,card_view5,card_view6;
    ImageView imgbasement,imggroundfloor,imgIfloor,imgIIfloor,imgIIIfloor,imgIVfloor;
    Button basement,groundfloor,Ifloor,IIfloor,IIIfloor,IVfloor;
   Integer imgsource;
    String text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_school_plan_mechanikow, container, false);
        card_view=(CardView)rootView.findViewById(R.id.card_view);
        card_view2=(CardView)rootView.findViewById(R.id.card_view2);
        card_view3=(CardView)rootView.findViewById(R.id.card_view3);
        card_view4=(CardView)rootView.findViewById(R.id.card_view4);
        card_view5=(CardView)rootView.findViewById(R.id.card_view5);
        card_view6=(CardView)rootView.findViewById(R.id.card_view6);

        basement=(Button)rootView.findViewById(R.id.basement);
        basement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_view.setVisibility(View.VISIBLE);
                card_view2.setVisibility(View.GONE);
                card_view3.setVisibility(View.GONE);
                card_view4.setVisibility(View.GONE);
                card_view5.setVisibility(View.GONE);
                card_view6.setVisibility(View.GONE);
                Drawable drawable6 = getResources().getDrawable(R.drawable.piwnica);
                imgbasement.setImageDrawable(drawable6);

                basement.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                groundfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                Ifloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                IIfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                IIIfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                IVfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

            }
        });

        groundfloor=(Button)rootView.findViewById(R.id.groundfloor);
        groundfloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_view.setVisibility(View.GONE);
                card_view2.setVisibility(View.VISIBLE);
                card_view3.setVisibility(View.GONE);
                card_view4.setVisibility(View.GONE);
                card_view5.setVisibility(View.GONE);
                card_view6.setVisibility(View.GONE);
                Drawable drawable5 = getResources().getDrawable(R.drawable.parter);
                imggroundfloor.setImageDrawable(drawable5);
                basement.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                groundfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                Ifloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                IIfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                IIIfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                IVfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

            }
        });
        Ifloor=(Button)rootView.findViewById(R.id.Ifloor);
        Ifloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_view.setVisibility(View.GONE);
                card_view2.setVisibility(View.GONE);
                card_view3.setVisibility(View.VISIBLE);
                card_view4.setVisibility(View.GONE);
                card_view5.setVisibility(View.GONE);
                card_view6.setVisibility(View.GONE);
                Drawable drawable4 = getResources().getDrawable(R.drawable.pierwsze);
                imgIfloor.setImageDrawable(drawable4);
                basement.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                groundfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                Ifloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                IIfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                IIIfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                IVfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        IIfloor=(Button)rootView.findViewById(R.id.IIfloor);
        IIfloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_view.setVisibility(View.GONE);
                card_view2.setVisibility(View.GONE);
                card_view3.setVisibility(View.GONE);
                card_view4.setVisibility(View.VISIBLE);
                card_view5.setVisibility(View.GONE);
                card_view6.setVisibility(View.GONE);
                Drawable drawable3 = getResources().getDrawable(R.drawable.drugie);
                imgIIfloor.setImageDrawable(drawable3);
                basement.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                groundfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                Ifloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                IIfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                IIIfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                IVfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        IIIfloor=(Button)rootView.findViewById(R.id.IIIfloor);
        IIIfloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_view.setVisibility(View.GONE);
                card_view2.setVisibility(View.GONE);
                card_view3.setVisibility(View.GONE);
                card_view4.setVisibility(View.GONE);
                card_view5.setVisibility(View.VISIBLE);
                card_view6.setVisibility(View.GONE);
                Drawable drawable2 = getResources().getDrawable(R.drawable.trzecie);
                imgIIIfloor.setImageDrawable(drawable2);
                basement.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                groundfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                Ifloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                IIfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                IIIfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                IVfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        IVfloor=(Button)rootView.findViewById(R.id.IVfloor);
        IVfloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_view.setVisibility(View.GONE);
                card_view2.setVisibility(View.GONE);
                card_view3.setVisibility(View.GONE);
                card_view4.setVisibility(View.GONE);
                card_view5.setVisibility(View.GONE);
                card_view6.setVisibility(View.VISIBLE);
                Drawable drawable = getResources().getDrawable(R.drawable.czwarte);
                imgIVfloor.setImageDrawable(drawable);
                basement.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                groundfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                Ifloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                IIfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                IIIfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                IVfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
            }
        });

        imgbasement=(ImageView)rootView.findViewById(R.id.imgbasement);
        imgbasement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgsource=R.drawable.piwnica;
                text=getString(R.string.basement);
                openDialog();

            }
        });
        imggroundfloor=(ImageView)rootView.findViewById(R.id.imggroundfloor);
        imggroundfloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgsource=R.drawable.parter;
                text=getString(R.string.groundfloor);
                openDialog();

            }
        });
        imgIfloor=(ImageView)rootView.findViewById(R.id.imgIfloor);
        imgIfloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgsource=R.drawable.pierwsze;
                text=getString(R.string.Ifloor);
                openDialog();

            }
        });
        imgIIfloor=(ImageView)rootView.findViewById(R.id.imgIIfloor);
        imgIIfloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgsource=R.drawable.drugie;
                text=getString(R.string.IIfloor);
                openDialog();


            }
        });
        imgIIIfloor=(ImageView)rootView.findViewById(R.id.imgIIIfloor);
        imgIIIfloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgsource=R.drawable.trzecie;
                text=getString(R.string.IIIfloor);
                openDialog();

            }
        });
        imgIVfloor=(ImageView)rootView.findViewById(R.id.imgIVfloor);
        imgIVfloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgsource=R.drawable.czwarte;
                text=getString(R.string.IVfloor);
                openDialog();

            }
        });



        return rootView;
    }

    private void openDialog(){
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View subView = inflater.inflate(R.layout.dialog, null);

        final ImageView subImageView = (ImageView)subView.findViewById(R.id.image);
        Drawable drawable = getResources().getDrawable(imgsource);
        subImageView.setImageDrawable(drawable);
        mAttacher = new PhotoViewAttacher(subImageView);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(text);

        builder.setView(subView);
        AlertDialog alertDialog = builder.create();



        builder.setNegativeButton(getString(R.string.close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
        mAttacher.update();
    }


}
