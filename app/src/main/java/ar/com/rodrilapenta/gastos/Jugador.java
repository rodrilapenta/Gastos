package ar.com.rodrilapenta.gastos;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by arielverdugo on 7/6/17.
 */

@DatabaseTable(tableName = "jugador")
public class Jugador {

    @DatabaseField(generatedId = true)
    //private int id;
    public int id;


    @DatabaseField
    private String nombre;

    @DatabaseField
    private String apodo;

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] foto;


    public Jugador(){}

    public Jugador(String nombre, String apodo, byte[] foto)
    {
        this.nombre = nombre;
        this.apodo = apodo;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}