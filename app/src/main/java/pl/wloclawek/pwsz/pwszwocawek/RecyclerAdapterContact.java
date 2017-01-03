package pl.wloclawek.pwsz.pwszwocawek;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static pl.wloclawek.pwsz.pwszwocawek.R.id.imageView;

/**
 * Created by EmilK on 2016-11-30.
 */
public class RecyclerAdapterContact extends RecyclerView.Adapter<RecyclerAdapterContact.ViewHolder> {
    ArrayList<String> lista;


    public RecyclerAdapterContact(ArrayList<String> lista) {
        this.lista = lista;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclercontact, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text.setText(lista.get(position));
        holder.tel.setImageResource(R.drawable.phone);
        holder.mail.setImageResource(R.drawable.mail);


    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text;
        ImageView tel;
        ImageView mail;


        public ViewHolder(View v) {
            super(v);

            text = (TextView) itemView.findViewById(R.id.text);
            tel = (ImageView) itemView.findViewById(R.id.tel);
            mail = (ImageView) itemView.findViewById(R.id.mail);
            v.setOnClickListener(this);
            tel.setOnClickListener(this);
            mail.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            String tekst = lista.get(getAdapterPosition());
            String tel = tekst.substring(tekst.lastIndexOf("Tel:") + 5, tekst.lastIndexOf("Mail") - 1);
            String mail = tekst.substring(tekst.lastIndexOf("Mail:") + 6);

            if (v.getId() == R.id.tel) {

                Uri callIntentUri = Uri.parse("tel:"+tel);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, callIntentUri);
                v.getContext().startActivity(callIntent);

            } else if (v.getId() == R.id.mail) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{mail});
                i.putExtra(Intent.EXTRA_SUBJECT, "");
                i.putExtra(Intent.EXTRA_TEXT   , "");
                try {
                    v.getContext().startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(itemView.getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

            }



        }


    }
}
