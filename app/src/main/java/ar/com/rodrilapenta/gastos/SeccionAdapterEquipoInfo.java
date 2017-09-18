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

public class SeccionAdapterEquipoInfo extends RecyclerView.Adapter<SeccionAdapterEquipoInfo.SeccionEquipoInfoViewHolder> {
    private List<Equipo> equipos;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class SeccionEquipoInfoViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView imagenEquipo;
        public TextView nombreEquipo;
        public TextView apodoEquipo;
        public TextView barrioEquipo;
        public TextView direccionEquipo;


        public SeccionEquipoInfoViewHolder(View v) {
            super(v);

            imagenEquipo = (ImageView) v.findViewById(R.id.imagenEquipoInfo);
            nombreEquipo = (TextView) v.findViewById(R.id.nombreEquipoInfo);
            apodoEquipo = (TextView) v.findViewById(R.id.apodoEquipoInfo);
            barrioEquipo = (TextView) v.findViewById(R.id.barrioEquipoInfo);
            direccionEquipo = (TextView) v.findViewById(R.id.direccionEquipoInfo);


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SeccionAdapterEquipoInfo(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SeccionAdapterEquipoInfo.SeccionEquipoInfoViewHolder onCreateViewHolder(ViewGroup parent,
                                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_card_equipoinfo, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new SeccionEquipoInfoViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(SeccionEquipoInfoViewHolder viewHolder, int i) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if(equipos.get(i).getEscudo() != null) {
            Bitmap imagenEquipo = BitmapFactory.decodeByteArray(equipos.get(i).getEscudo(), 0, equipos.get(i).getEscudo().length);
            viewHolder.imagenEquipo.setImageBitmap(imagenEquipo);

        }
        else {
            viewHolder.imagenEquipo.setImageResource(R.drawable.sinimagen);
        }
        viewHolder.nombreEquipo.setText(equipos.get(i).getNombre());
        viewHolder.apodoEquipo.setText(equipos.get(i).getApodo());
        viewHolder.barrioEquipo.setText(equipos.get(i).getBarrio());
        viewHolder.direccionEquipo.setText(equipos.get(i).getDireccion());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return equipos.size();
    }
}