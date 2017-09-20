package ar.com.rodrilapenta.gastos;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ar.com.rodrilapenta.gastos.interfaces.RecyclerViewClickListener;

/**
 * Created by arielverdugo on 4/9/17.
 */

public class SeccionAdapter extends RecyclerView.Adapter<SeccionAdapter.SeccionViewHolder> {
    private List<Seccion> secciones;
    private static Context context;
    private static RecyclerViewClickListener itemListener;
    static float pixelDensity;

    // Provide a suitable constructor (depends on the kind of dataset)
    public SeccionAdapter(List<Seccion> secciones, Context context, RecyclerViewClickListener itemListener) {
        this.secciones = secciones;
        this.context = context;
        this.itemListener = itemListener;
        this.pixelDensity = context.getResources().getDisplayMetrics().density;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class SeccionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public ImageView imagen;
        public TextView nombre;
        public Button anadir, ver;
        public ImageButton accionesSeccion;
        public LinearLayout revealView, layoutButtons;
        public Boolean imageSelected = false, flag = false;


        public SeccionViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imagenSeccion);
            nombre = (TextView) v.findViewById(R.id.tituloSeccion);
            anadir = (Button) v.findViewById(R.id.anadirSeccion);
            ver = (Button) v.findViewById(R.id.verSecciones);
            revealView = (LinearLayout) v.findViewById(R.id.linearView);
            layoutButtons = (LinearLayout) v.findViewById(R.id.layoutButtons);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            itemListener.recyclerViewListClicked(v, this.getLayoutPosition());
        }
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
    public void onBindViewHolder(final SeccionViewHolder viewHolder, int i) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        viewHolder.imagen.setImageResource(secciones.get(i).getImagen());
        viewHolder.nombre.setText(secciones.get(i).getTexto());

        switch (secciones.get(i).getTexto()) {
            case "Equipos":
                viewHolder.ver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, context.getString(R.string.EQUIPOS_CARD_TITLE), Toast.LENGTH_SHORT).show();
                        Intent intento = new Intent(context, EquipoInfoActivity.class);
                        context.startActivity(intento);
                    }
                });

                viewHolder.anadir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final ViewGroup popupView = (ViewGroup) ((LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE )).inflate(R.layout.dialog_agregar_equipo, null);
                        final ImageView escudo = (ImageView) popupView.findViewById(R.id.escudoNuevoEquipo);
                        escudo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //para ir a la galeria
                                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                getIntent.setType("image/*");

                                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                pickIntent.setType("image/*");

                                Intent chooserIntent = Intent.createChooser(getIntent, "Elegir imagen");
                                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
                                Utils utils = Utils.getInstance();
                                utils.setEscudo(escudo);
                                //utiliza la constante INTENT_ELEGIR_IMAGEN en onActivityResult
                                ((Activity) context).startActivityForResult(chooserIntent, Utils.INTENT_ELEGIR_IMAGEN);
                            }
                        });


                        AlertDialog.Builder alertDialogBuilder =
                                new AlertDialog.Builder(context)
                                        .setTitle("Equipo")
                                        .setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // capturar y guardar en bd
                                                final String NOMBRE = (((TextView) popupView.findViewById(R.id.nombreNuevoEquipo)).getText().toString());
                                                final String ALIAS = (((TextView) popupView.findViewById(R.id.apodoNuevoEquipo)).getText().toString());
                                                final String BARRIO = (((TextView) popupView.findViewById(R.id.barrioNuevoEquipo)).getText().toString());
                                                final String DOMICILIO = (((TextView) popupView.findViewById(R.id.domicilioNuevoEquipo)).getText().toString());
                                                Bitmap ESCUDO = null;
                                                if (viewHolder.imageSelected) {
                                                    ESCUDO = ((BitmapDrawable) ((ImageView) popupView.findViewById(R.id.escudoNuevoEquipo)).getDrawable()).getBitmap();
                                                }

                                                if (NOMBRE.isEmpty() || ALIAS.isEmpty() || BARRIO.isEmpty() || DOMICILIO.isEmpty()) {
                                                    Toast.makeText(context, "Datos insuficientes", Toast.LENGTH_SHORT).show();

                                                } else {
                                                    Equipo e = new Equipo(NOMBRE, ALIAS, BARRIO, DOMICILIO, Utils.getByteArrayFromBitmap(ESCUDO));
                                                    EquipoRepository.getInstance(context).addEquipo(e);
                                                    Toast.makeText(context, "Equipo agregado", Toast.LENGTH_SHORT).show();
                                                    dialog.dismiss();
                                                }
                                            }
                                        })
                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                        alertDialogBuilder.setView(popupView);
                        alertDialogBuilder.show();
                    }
                });
                break;
            case "Jugadores":
                break;
            case "Situaciones":
                break;
            default:
                break;
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return secciones.size();
    }
}