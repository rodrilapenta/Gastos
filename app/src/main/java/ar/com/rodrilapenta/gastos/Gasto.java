package ar.com.rodrilapenta.gastos;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by arielverdugo on 7/6/17.
 */

@DatabaseTable(tableName = "equipo")
public class Gasto {

    @DatabaseField(generatedId = true)
    //private int id;
    public int id;


    @DatabaseField
    private String nombre;

    @DatabaseField
    private String apodo;

    @DatabaseField
    private String barrio;

    @DatabaseField
    private String direccion;

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] escudo;


    public Gasto(){}

    public Gasto(String nombre, String apodo, String barrio, String direccion, byte[] escudo)
    {
        this.nombre = nombre;
        this.apodo = apodo;
        this.barrio = barrio;
        this.direccion = direccion;
        this.escudo = escudo;

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

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public byte[] getEscudo()
    {
        return escudo;
    }

    public void setEscudo(byte[]escudo) {
        this.escudo = escudo;
    }



}
