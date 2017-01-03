package pl.wloclawek.pwsz.pwszwocawek;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RVAdapterEvent extends RecyclerView.Adapter<RVAdapterEvent.PersonViewHolder2> {

    public static class PersonViewHolder2 extends RecyclerView.ViewHolder {

        CardView cv;

        TextView nazwaT;
        TextView dzienT;
        TextView opisT;
        TextView godzinaT;



        PersonViewHolder2(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            nazwaT = (TextView) itemView.findViewById(R.id.nazwaEvent2);

            dzienT = (TextView) itemView.findViewById(R.id.dzienEvent2);
            opisT = (TextView) itemView.findViewById(R.id.OpisEvent2);
            godzinaT = (TextView) itemView.findViewById(R.id.godzinaEvent2);

        }


    }

    List<Event> persons;

    RVAdapterEvent(List<Event> persons) {
        this.persons = persons;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder2 onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_event, viewGroup, false);
        PersonViewHolder2 pvh = new PersonViewHolder2(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder2 personViewHolder, int i) {


            personViewHolder.dzienT.setText(persons.get(i).dzienE);
            personViewHolder.godzinaT.setText(persons.get(i).godzinaE);
            personViewHolder.nazwaT.setText(persons.get(i).nazwaE);
        personViewHolder.opisT.setText(persons.get(i).opisE);



    }

    @Override
    public int getItemCount() {
        return persons.size();
    }
}