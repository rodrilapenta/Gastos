package ar.com.rodrilapenta.gastos;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by arielverdugo on 6/6/17.
 */

public class UsuarioRepository
{
    private static UsuarioRepository instance;

    private Dao<Usuario, Integer> dao;

    public static UsuarioRepository getInstance(Context context) {
        if(instance == null)
            instance = new UsuarioRepository(context);
        return instance;
    }

    private UsuarioRepository(Context context){
        OrmLiteSqliteOpenHelper helper =  OpenHelperManager.getHelper(context, DatabaseHelper.class);
        try {
            dao = helper.getDao(Usuario.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Usuario> getUsuarios(){
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addUsuario(Usuario c){
        try {
            dao.create(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Usuario findUsuarioById(int id){
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteUsuarioById(int id){
        try {
            dao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Usuario> findWhere(String fieldName,Object value){
        try {
            return dao.queryForEq(fieldName, value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //buscar n usuarios por n condiciones
    public List<Usuario> findWhere(Map<String, String> params){
        try {
            QueryBuilder<Usuario, Integer> queryBuilder = dao.queryBuilder();
            Where<Usuario, Integer> where = queryBuilder.where();

            for(Map.Entry<String, String> param: params.entrySet()) {
                where.eq(param.getKey(), param.getValue());
            }

            where.and(params.size());
            PreparedQuery<Usuario> preparedQuery = queryBuilder.prepare();
            return dao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Usuario findUniqueWhere(Map<String, String> params) {
        try {
            QueryBuilder<Usuario, Integer> queryBuilder = dao.queryBuilder();
            Where<Usuario, Integer> where = queryBuilder.where();

            for(Map.Entry<String, String> param: params.entrySet()) {
                where.eq(param.getKey(), param.getValue());
            }

            where.and(params.size());
            PreparedQuery<Usuario> preparedQuery = queryBuilder.prepare();
            List<Usuario> usuarios = dao.query(preparedQuery);
            if(usuarios.size() == 0) {
                return null;
            }
            else if(usuarios.size() > 1) {
                throw new Exception("HAY MAS DE UN USUARIO CON ESOS PARAMETROS");
            }
            else {
                return usuarios.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
