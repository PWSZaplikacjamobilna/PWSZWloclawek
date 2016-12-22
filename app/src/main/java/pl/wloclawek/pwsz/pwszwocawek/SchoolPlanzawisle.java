package pl.wloclawek.pwsz.pwszwocawek;


import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;

public class SchoolPlanzawisle extends Fragment {

    CardView card_view,card_view2,card_view3,card_view4;
    ImageView imgstudenthouse,imggroundfloor,imgIfloor,imgIIfloor;
    Button studenthouse,groundfloor,Ifloor,IIfloor;
    Integer imgsource;
    String text;
    PhotoViewAttacher mAttacher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_school_plan_zawisle, container, false);
        card_view=(CardView)rootView.findViewById(R.id.card_view);
        card_view2=(CardView)rootView.findViewById(R.id.card_view2);
        card_view3=(CardView)rootView.findViewById(R.id.card_view3);
        card_view4=(CardView)rootView.findViewById(R.id.card_view4);
        studenthouse=(Button)rootView.findViewById(R.id.studenthause);
        studenthouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_view.setVisibility(View.VISIBLE);
                card_view2.setVisibility(View.GONE);
                card_view3.setVisibility(View.GONE);
                card_view4.setVisibility(View.GONE);

                Drawable drawable6 = getResources().getDrawable(R.drawable.domstudenta);
                imgstudenthouse.setImageDrawable(drawable6);
                studenthouse.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                groundfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                Ifloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                IIfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));


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

                Drawable drawable5 = getResources().getDrawable(R.drawable.zawisleparter);
                imggroundfloor.setImageDrawable(drawable5);
                studenthouse.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                groundfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                Ifloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                IIfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));


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

                Drawable drawable4 = getResources().getDrawable(R.drawable.zawislefirstpietro);
                imgIfloor.setImageDrawable(drawable4);
                studenthouse.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                groundfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                Ifloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                IIfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

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

                Drawable drawable3 = getResources().getDrawable(R.drawable.zawislesecondpietro);
                imgIIfloor.setImageDrawable(drawable3);
                studenthouse.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                groundfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                Ifloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                IIfloor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));

            }
        });

        imgstudenthouse=(ImageView)rootView.findViewById(R.id.imgstudenthause);
        imgstudenthouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgsource=R.drawable.domstudenta;
                text=getString(R.string.groundfloor2_zawisle);
                openDialog();

            }
        });
        imggroundfloor=(ImageView)rootView.findViewById(R.id.imggroundfloor);
        imggroundfloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgsource=R.drawable.zawisleparter;
                text=getString(R.string.groundfloor_zawisle);
                openDialog();

            }
        });
        imgIfloor=(ImageView)rootView.findViewById(R.id.imgIfloor);
        imgIfloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgsource=R.drawable.zawislefirstpietro;
                text=getString(R.string.Ifloor_zawisle);
                openDialog();

            }
        });
        imgIIfloor=(ImageView)rootView.findViewById(R.id.imgIIfloor);
        imgIIfloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgsource=R.drawable.zawislesecondpietro;
                text=getString(R.string.IIfloor_zawisle);
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



        builder.setNegativeButton("Zamknij", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
        mAttacher.update();
    }
}
