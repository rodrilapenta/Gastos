package ar.com.rodrilapenta.gastos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ar.com.rodrilapenta.gastos.R;

import java.util.List;

/**
 * Created by arielverdugo on 4/9/17.
 */

public class SeccionAdapterJugadorInfo extends RecyclerView.Adapter<SeccionAdapterJugadorInfo.SeccionJugadorInfoViewHolder> {
    private List<Jugador> jugadores;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class SeccionJugadorInfoViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView imagenJugador;
        public TextView nombreJugador;
        public TextView apodoJugador;

        public SeccionJugadorInfoViewHolder(View v) {
            super(v);

            imagenJugador = (ImageView) v.findViewById(R.id.imagenJugadorInfo);
            nombreJugador = (TextView) v.findViewById(R.id.nombreJugadorInfo);
            apodoJugador = (TextView) v.findViewById(R.id.apodoJugadorInfo);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SeccionAdapterJugadorInfo(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SeccionAdapterJugadorInfo.SeccionJugadorInfoViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                    int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_card_jugadorinfo, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new SeccionJugadorInfoViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(SeccionJugadorInfoViewHolder viewHolder, int i) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if(jugadores.get(i).getFoto() != null) {
            Bitmap imagenEquipo = BitmapFactory.decodeByteArray(jugadores.get(i).getFoto(), 0, jugadores.get(i).getFoto().length);
            viewHolder.imagenJugador.setImageBitmap(imagenEquipo);

        }
        else {
            viewHolder.imagenJugador.setImageResource(R.drawable.sinimagen);
        }
        viewHolder.nombreJugador.setText(jugadores.get(i).getNombre());
        viewHolder.apodoJugador.setText(jugadores.get(i).getApodo());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return jugadores.size();
    }
}