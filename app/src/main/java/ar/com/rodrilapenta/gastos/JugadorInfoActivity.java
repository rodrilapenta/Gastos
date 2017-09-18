package ar.com.rodrilapenta.gastos;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import ar.com.rodrilapenta.gastos.R;

import java.util.List;

/**
 * Created by arielverdugo on 9/9/17.
 */

public class JugadorInfoActivity extends Activity {

    private TextView totalEquipos;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipoinfo);

        List<Equipo> equipos = EquipoRepository.getInstance(this).getEquipos();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerEquipo);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        
        mAdapter = new SeccionAdapterEquipoInfo(equipos);
        mRecyclerView.setAdapter(mAdapter);


    }



}
