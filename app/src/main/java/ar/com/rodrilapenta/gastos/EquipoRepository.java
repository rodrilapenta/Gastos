package ar.com.rodrilapenta.gastos;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by arielverdugo on 6/6/17.
*/

public class EquipoRepository
{
    private static EquipoRepository instance;

    private Dao<Equipo, Integer> dao;

    public static EquipoRepository getInstance(Context context) {
        if(instance == null)
            instance = new EquipoRepository((Context) context);
        return instance;
    }

    private EquipoRepository(Context context){
        OrmLiteSqliteOpenHelper helper =  OpenHelperManager.getHelper(context, DatabaseHelper.class);
        try {
            dao = helper.getDao(Equipo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Equipo> getEquipos(){
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addEquipo(Equipo c){
        try {
            dao.create(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Equipo findEquipoById(int id){
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteEquipoById(int id){
        try {
            dao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Equipo> findWhere(String fieldName,Object value){
        try {
            return dao.queryForEq(fieldName, value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
