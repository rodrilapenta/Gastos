package ar.com.rodrilapenta.gastos;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

/**
 * Created by arielverdugo on 15/9/17.
 */

public class Utils {

    public static Utils instance;

    public Utils() {
    }

    public static Utils getInstance() {
        if(instance == null)
            instance = new Utils();
        return instance;
    }

    public static final int INTENT_ELEGIR_IMAGEN = 1;
    public static ImageView escudo;

    public static byte[] getByteArrayFromBitmap(Bitmap image) {
        if(image != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            byte imageInByte[] = stream.toByteArray();

            return imageInByte;
        }
        return null;
    }

    public static Animation getAlphaAnimation(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.alpha_anim);
    }

    public static ImageView getEscudo() {
        return escudo;
    }

    public static void setEscudo(ImageView escudoParam) {
        escudo = escudoParam;
    }
}
