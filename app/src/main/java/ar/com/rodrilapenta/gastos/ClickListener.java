package ar.com.rodrilapenta.gastos;

import android.view.View;

/**
 * Created by arielverdugo on 5/9/17.
 */

public interface ClickListener{
    public void onClick(View view, int position);
    public void onLongClick(View view,int position);
}
