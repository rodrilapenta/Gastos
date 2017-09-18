package ar.com.rodrilapenta.gastos;

/**
 * Created by arielverdugo on 4/9/17.
 */

public class Seccion {
    private int imagen;
    private String texto;

    public Seccion(int imagen, String texto) {
        this.imagen = imagen;
        this.texto = texto;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
