package pl.wloclawek.pwsz.pwszwocawek;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RVAdapterLesson extends RecyclerView.Adapter<RVAdapterLesson.PersonViewHolder> {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;

        TextView day;
        TextView dzienNow;
        TextView godzinaNow;
        TextView zajeciaNow;
        TextView typsalNow;
        TextView wtkNow;
        TextView etaNow;


        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            day = (TextView) itemView.findViewById(R.id.dayR);

            dzienNow = (TextView) itemView.findViewById(R.id.dzienNow);
            godzinaNow = (TextView) itemView.findViewById(R.id.godzinaNow);
            zajeciaNow = (TextView) itemView.findViewById(R.id.nazwaEvent);
            typsalNow = (TextView) itemView.findViewById(R.id.OpisEvent);
            wtkNow = (TextView) itemView.findViewById(R.id.wykNow);
            etaNow = (TextView) itemView.findViewById(R.id.etaNow);
        }


    }

    List<Lesson> persons;

    RVAdapterLesson(List<Lesson> persons) {
        this.persons = persons;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lesson, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {

        if (persons.get(i).przedmiot.contains("Brak")) {

            personViewHolder.zajeciaNow.setText("Brak Zajęć");
            personViewHolder.godzinaNow.setText("");
            personViewHolder.dzienNow.setText("");
            personViewHolder.etaNow.setText("");
            personViewHolder.typsalNow.setText("");
            personViewHolder.wtkNow.setText("");

        } else {
            personViewHolder.dzienNow.setText(persons.get(i).dzień);
            personViewHolder.etaNow.setText(persons.get(i).eta);
            personViewHolder.godzinaNow.setText(persons.get(i).GodzinaRoz);


            if (persons.get(i).przedmiot.length() >= 28) {
                personViewHolder.zajeciaNow.setText(persons.get(i).przedmiot.substring(0, 28) + "...");
            } else {
                personViewHolder.zajeciaNow.setText(persons.get(i).przedmiot);
            }
            personViewHolder.dzienNow.setText(" " + persons.get(i).GodzinaRoz.substring(0, 10));
            Log.e("TAG", "----------------" + persons.get(i).GodzinaRoz);
            String godz1 = persons.get(i).GodzinaRoz.substring(11).substring(0, 5);
            String godz2 = persons.get(i).GodzinaZak.substring(11).substring(0, 5);
            personViewHolder.godzinaNow.setText(godz1 + "-" + godz2);
            personViewHolder.typsalNow.setText(persons.get(i).typ.replace(" ", "") + ", " + "SALA" + " " + persons.get(i).sala);

            personViewHolder.wtkNow.setText(persons.get(i).wykladowca);

            personViewHolder.etaNow.setText("");


        }


        if (persons.get(i).first == true) {

            SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d MMM");
            Date d = null;
            try {
                d = dateParser.parse(persons.get(i).date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String dayOfTheWeek = sdf.format(d);
            personViewHolder.day.setText(dayOfTheWeek.substring(0, 1).toUpperCase() + dayOfTheWeek.substring(1));
            personViewHolder.day.setVisibility(View.VISIBLE);

        } else {
            personViewHolder.day.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }
}