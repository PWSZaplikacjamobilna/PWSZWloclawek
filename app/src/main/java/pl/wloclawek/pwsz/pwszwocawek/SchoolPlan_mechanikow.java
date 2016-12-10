package pl.wloclawek.pwsz.pwszwocawek;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import static pl.wloclawek.pwsz.pwszwocawek.R.id.imageView;

public class SchoolPlan_mechanikow extends Activity {


    CardView card_view,card_view2,card_view3,card_view4,card_view5,card_view6;
    ImageView imgbasement,imggroundfloor,imgIfloor,imgIIfloor,imgIIIfloor,imgIVfloor;
   Integer imgsource;
    String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_plan_mechanikow);
        card_view=(CardView)findViewById(R.id.card_view);
        card_view2=(CardView)findViewById(R.id.card_view2);
        card_view3=(CardView)findViewById(R.id.card_view3);
        card_view4=(CardView)findViewById(R.id.card_view4);
        card_view5=(CardView)findViewById(R.id.card_view5);
        card_view6=(CardView)findViewById(R.id.card_view6);
        imgbasement=(ImageView)findViewById(R.id.imgbasement);
        imggroundfloor=(ImageView)findViewById(R.id.imggroundfloor);
        imgIfloor=(ImageView)findViewById(R.id.imgIfloor);
        imgIIfloor=(ImageView)findViewById(R.id.imgIIfloor);
        imgIIIfloor=(ImageView)findViewById(R.id.imgIIIfloor);
        imgIVfloor=(ImageView)findViewById(R.id.imgIVfloor);


    }

    public void clickbasement(View view) {
        card_view.setVisibility(View.VISIBLE);
        card_view2.setVisibility(View.GONE);
        card_view3.setVisibility(View.GONE);
        card_view4.setVisibility(View.GONE);
        card_view5.setVisibility(View.GONE);
        card_view6.setVisibility(View.GONE);
        Drawable drawable6 = getResources().getDrawable(R.drawable.piwnica);
        imgbasement.setImageDrawable(drawable6);


    }

    public void clickgroundfloor(View view) {
        card_view.setVisibility(View.GONE);
        card_view2.setVisibility(View.VISIBLE);
        card_view3.setVisibility(View.GONE);
        card_view4.setVisibility(View.GONE);
        card_view5.setVisibility(View.GONE);
        card_view6.setVisibility(View.GONE);
        Drawable drawable5 = getResources().getDrawable(R.drawable.parter);
        imggroundfloor.setImageDrawable(drawable5);
    }

    public void clickIfloor(View view) {
        card_view.setVisibility(View.GONE);
        card_view2.setVisibility(View.GONE);
        card_view3.setVisibility(View.VISIBLE);
        card_view4.setVisibility(View.GONE);
        card_view5.setVisibility(View.GONE);
        card_view6.setVisibility(View.GONE);
        Drawable drawable4 = getResources().getDrawable(R.drawable.pierwsze);
        imgIfloor.setImageDrawable(drawable4);
    }

    public void clickIIfloor(View view) {
        card_view.setVisibility(View.GONE);
        card_view2.setVisibility(View.GONE);
        card_view3.setVisibility(View.GONE);
        card_view4.setVisibility(View.VISIBLE);
        card_view5.setVisibility(View.GONE);
        card_view6.setVisibility(View.GONE);
        Drawable drawable3 = getResources().getDrawable(R.drawable.drugie);
        imgIIfloor.setImageDrawable(drawable3);
    }

    public void clickIIIfloor(View view) {
        card_view.setVisibility(View.GONE);
        card_view2.setVisibility(View.GONE);
        card_view3.setVisibility(View.GONE);
        card_view4.setVisibility(View.GONE);
        card_view5.setVisibility(View.VISIBLE);
        card_view6.setVisibility(View.GONE);
        Drawable drawable2 = getResources().getDrawable(R.drawable.trzecie);
        imgIIIfloor.setImageDrawable(drawable2);
    }

    public void clickIVfloor(View view) {
        card_view.setVisibility(View.GONE);
        card_view2.setVisibility(View.GONE);
        card_view3.setVisibility(View.GONE);
        card_view4.setVisibility(View.GONE);
        card_view5.setVisibility(View.GONE);
        card_view6.setVisibility(View.VISIBLE);
        Drawable drawable = getResources().getDrawable(R.drawable.czwarte);
        imgIVfloor.setImageDrawable(drawable);

    }



    private void openDialog(){
        LayoutInflater inflater = LayoutInflater.from(SchoolPlan_mechanikow.this);
        View subView = inflater.inflate(R.layout.dialog, null);

        final ImageView subImageView = (ImageView)subView.findViewById(R.id.image);
        Drawable drawable = getResources().getDrawable(imgsource);
        subImageView.setImageDrawable(drawable);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
    }




    public void imgbasement(View view) {
        imgsource=R.drawable.piwnica;
        text=getString(R.string.basement);
        openDialog();
    }

    public void imggroundfloor(View view) {
        imgsource=R.drawable.parter;
        text=getString(R.string.groundfloor);
        openDialog();
    }


    public void imgIfloor(View view) {
        imgsource=R.drawable.pierwsze;
        text=getString(R.string.Ifloor);
        openDialog();
    }

    public void imgIIfloor(View view) {
        imgsource=R.drawable.drugie;
        text=getString(R.string.IIfloor);
        openDialog();
    }

    public void imgIIIfloor(View view) {
        imgsource=R.drawable.trzecie;
        text=getString(R.string.IIIfloor);
        openDialog();
    }

    public void imgIVfloor(View view) {
        imgsource=R.drawable.czwarte;
        text=getString(R.string.IVfloor);
        openDialog();
    }
}
