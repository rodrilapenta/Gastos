package ar.com.rodrilapenta.gastos;

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

public class SeccionAdapter extends RecyclerView.Adapter<SeccionAdapter.SeccionViewHolder> {
    private List<Seccion> secciones;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class SeccionViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView imagen;
        public TextView nombre;
        public SeccionViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imagenSeccion);
            nombre = (TextView) v.findViewById(R.id.textoSeccion);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SeccionAdapter(List<Seccion> secciones) {
        this.secciones = secciones;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SeccionAdapter.SeccionViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_card_seccion, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new SeccionViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(SeccionViewHolder viewHolder, int i) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        viewHolder.imagen.setImageResource(secciones.get(i).getImagen());
        viewHolder.nombre.setText(secciones.get(i).getTexto());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return secciones.size();
    }
}