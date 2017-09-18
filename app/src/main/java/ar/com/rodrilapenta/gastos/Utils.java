package ar.com.rodrilapenta.gastos;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by arielverdugo on 15/9/17.
 */

public class Utils {
    public static byte[] getByteArrayFromBitmap(Bitmap image) {
        if(image != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            byte imageInByte[] = stream.toByteArray();

            return imageInByte;
        }
        return null;
    }
}
