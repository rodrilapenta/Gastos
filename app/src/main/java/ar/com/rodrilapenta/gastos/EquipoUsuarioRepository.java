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

public class EquipoUsuarioRepository
{
    private static EquipoUsuarioRepository instance;

    private Dao<EquipoUsuario, Integer> dao;

    public static EquipoUsuarioRepository getInstance(Context context) {
        if(instance == null)
            instance = new EquipoUsuarioRepository((Context) context);
        return instance;
    }

    private EquipoUsuarioRepository(Context context){
        OrmLiteSqliteOpenHelper helper =  OpenHelperManager.getHelper(context, DatabaseHelper.class);
        try {
            dao = helper.getDao(EquipoUsuario.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<EquipoUsuario> getEquipoUsuarios(){
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addEquipoUsuario(EquipoUsuario c){
        try {
            dao.create(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public EquipoUsuario findEquipoUsuarioById(int id){
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteEquipoUsuarioById(int id){
        try {
            dao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<EquipoUsuario> findWhere(String fieldName,Object value){
        try {
            return dao.queryForEq(fieldName, value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
