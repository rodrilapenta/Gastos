package ar.com.rodrilapenta.gastos;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by arielverdugo on 7/6/17.
 */

@DatabaseTable(tableName = "equipo")
public class Gasto {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String descripcion;

    @DatabaseField
    private String lugar;

    @DatabaseField
    private Double monto;

    @DatabaseField
    private Date fecha;

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] imagen;


    public Gasto(){}

    public Gasto(int id, String descripcion, String lugar, Double monto, Date fecha, byte[] imagen) {
        this.id = id;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.monto = monto;
        this.fecha = fecha;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}