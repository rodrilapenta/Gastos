package ar.com.rodrilapenta.gastos;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;




/**
 * Created by arielverdugo on 25/4/17.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME = "Followuptest.sqlite";
    //si modifico la bd cambiar db version, y ejecuta onUpgrade
    private static final int DB_VERSION = 5;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Usuario.class);
            TableUtils.createTable(connectionSource, Equipo.class);
            TableUtils.createTable(connectionSource, EquipoUsuario.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {

            TableUtils.dropTable(connectionSource,Equipo.class,true);
            TableUtils.createTableIfNotExists(connectionSource, Usuario.class);
            TableUtils.createTableIfNotExists(connectionSource, Equipo.class);
            TableUtils.createTableIfNotExists(connectionSource, EquipoUsuario.class);
            TableUtils.clearTable(connectionSource,Equipo.class);



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUsuario(Usuario u)
    {

    }
}