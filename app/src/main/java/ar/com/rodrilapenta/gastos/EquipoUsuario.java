package ar.com.rodrilapenta.gastos;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by arielverdugo on 8/6/17.
 */

public class EquipoUsuario {

    @DatabaseField(generatedId = true)
    private int id;

    // This is a foreign object which just stores the id from the User object in this table.
    @DatabaseField(foreign = true, columnName = "id_equipo")
    Equipo equipo;

    // This is a foreign object which just stores the id from the Post object in this table.
    @DatabaseField(foreign = true, columnName = "id_usuario")
    Usuario usuario;

    public EquipoUsuario(Equipo equipo,Usuario usuario)
    {
        this.equipo = equipo;
        this.usuario = usuario;
    }

    public EquipoUsuario()
    {

    }


}
