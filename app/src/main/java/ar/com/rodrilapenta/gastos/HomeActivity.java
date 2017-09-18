package ar.com.rodrilapenta.gastos;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ar.com.rodrilapenta.gastos.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView tituloSeccion;
    private ImageView agregar;
    private ImageView escudo;
    public static final int INTENT_ELEGIR_IMAGEN = 1;
    private Boolean imageSelected = false;


    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(
                HomeActivity.this).create();

        alertDialog.setTitle("Cerrar sesión");
        alertDialog.setMessage("¿Está seguro de que desea cerrar sesión?");

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                sessionManager.logout(HomeActivity.this);
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //DO NOTHING
            }
        });

        alertDialog.show();
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener) {

            this.clicklistener = clicklistener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return false;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recycleView.findChildViewUnder(e.getX(), e.getY());


                    if (child != null && clicklistener != null) {
                        clicklistener.onLongClick(child, recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
                clicklistener.onClick(child, rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sessionManager = new SessionManager(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        RecyclerTouchListener r = new RecyclerTouchListener(this,
                mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well

                manageClickEvent(view, position);
            }

            @Override
            public void onLongClick(View view, int position) {
                manageLongClickEvent(view, position);
            }
        });

        mRecyclerView.addOnItemTouchListener(r);

        //if the content do not change
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<Seccion> secciones = new ArrayList<Seccion>();
        secciones.add(new Seccion(R.drawable.basketballteam, "Equipos"));
        secciones.add(new Seccion(R.drawable.basketballplayer, "Jugadores"));
        secciones.add(new Seccion(R.drawable.basketballfield, "Situación"));

        mAdapter = new SeccionAdapter(secciones);
        mRecyclerView.setAdapter(mAdapter);

        agregar = (ImageView) findViewById(R.id.agregar);
        tituloSeccion = (TextView) findViewById(R.id.textoSeccion);
    }

    private void manageClickEvent(View seccion, int posicion) {
        TextView textoSeccion = (TextView) seccion.findViewById(R.id.textoSeccion);

        switch (textoSeccion.getText().toString()) {
            case "Equipos":
                Toast.makeText(HomeActivity.this, getString(R.string.EQUIPOS_CARD_TITLE), Toast.LENGTH_SHORT).show();
                Intent intento = new Intent(HomeActivity.this, EquipoInfoActivity.class);
                startActivity(intento);
                break;
            case "Jugadores":
                Toast.makeText(HomeActivity.this, getString(R.string.JUGADORES_CARD_TITLE), Toast.LENGTH_SHORT).show();
                break;
            case "Situaciones":
                Toast.makeText(HomeActivity.this, getString(R.string.SITUACION_CARD_TITLE), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void manageLongClickEvent(View seccion, int posicion) {
        String textoSeccion = ((TextView) seccion.findViewById(R.id.textoSeccion)).getText().toString();

        switch (textoSeccion) {
            case "Equipos":
                Intent intento = new Intent(HomeActivity.this, EquipoInfoActivity.class);
                startActivity(intento);
                break;
            case "Jugadores":
                Toast.makeText(HomeActivity.this, "Jugadores", Toast.LENGTH_SHORT).show();
                break;
            case "Situación":
                Toast.makeText(HomeActivity.this, "Situación", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    public void onClickAgregar(View v) {
        ViewGroup row = (ViewGroup) v.getParent();
        TextView textoSeccion = (TextView) row.findViewById(R.id.textoSeccion);
        final ViewGroup popupView = (ViewGroup) getLayoutInflater().inflate(R.layout.dialog_agregar_equipo, null);


        switch (textoSeccion.getText().toString()) {
            case "Equipos":

                escudo = (ImageView) popupView.findViewById(R.id.escudoNuevoEquipo);
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

                        //utiliza la constante INTENT_ELEGIR_IMAGEN en onActivityResult
                        startActivityForResult(chooserIntent, INTENT_ELEGIR_IMAGEN);
                    }
                });


                AlertDialog.Builder alertDialogBuilder =
                        new AlertDialog.Builder(HomeActivity.this)
                                .setTitle("Equipo")
                                .setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // capturar y guardar en bd
                                        final String NOMBRE = (((TextView) popupView.findViewById(R.id.nombreNuevoEquipo)).getText().toString());
                                        final String ALIAS = (((TextView) popupView.findViewById(R.id.apodoNuevoEquipo)).getText().toString());
                                        final String BARRIO = (((TextView) popupView.findViewById(R.id.barrioNuevoEquipo)).getText().toString());
                                        final String DOMICILIO = (((TextView) popupView.findViewById(R.id.domicilioNuevoEquipo)).getText().toString());
                                        Bitmap ESCUDO = null;
                                        if (imageSelected) {
                                            ESCUDO = ((BitmapDrawable) ((ImageView) popupView.findViewById(R.id.escudoNuevoEquipo)).getDrawable()).getBitmap();
                                        }

                                        if (NOMBRE.isEmpty() || ALIAS.isEmpty() || BARRIO.isEmpty() || DOMICILIO.isEmpty()) {
                                            Toast.makeText(HomeActivity.this, "Datos insuficientes", Toast.LENGTH_SHORT).show();

                                        } else {
                                            Equipo e = new Equipo(NOMBRE, ALIAS, BARRIO, DOMICILIO, Utils.getByteArrayFromBitmap(ESCUDO));
                                            EquipoRepository.getInstance(HomeActivity.this).addEquipo(e);
                                            Toast.makeText(HomeActivity.this, "Equipo agregado", Toast.LENGTH_SHORT).show();
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

                break;
            case "Jugadores":
                Toast.makeText(HomeActivity.this, "Jugadores!", Toast.LENGTH_SHORT).show();
                break;
            case "Situación":
                Toast.makeText(HomeActivity.this, "Situación!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case INTENT_ELEGIR_IMAGEN:
                    Uri selectedImageUri = data.getData();

                    if (selectedImageUri != null) {
                        try {
                            escudo.setImageBitmap(decodeUri(selectedImageUri));
                            imageSelected = true;
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }
        }
    }

    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;

        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);
        // The new size we want to scale to
        final int REQUIRED_SIZE = 150;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
                    || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            sessionManager.logout(this);
        }
        return true;
    }
}