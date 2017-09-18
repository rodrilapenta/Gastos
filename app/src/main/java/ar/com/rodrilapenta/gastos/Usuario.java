package ar.com.rodrilapenta.gastos;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by arielverdugo on 1/6/17.
 */

@DatabaseTable(tableName = "usuarios")
public class Usuario {


    @DatabaseField(generatedId = true)
        //private int id;
        public int id;


    @DatabaseField
        private String nombre;

        @DatabaseField
        private String apellido;

        @DatabaseField
        private int edad;

        @DatabaseField
        private String email;

        @DatabaseField
        private String contrasena;





        public Usuario() {}

        public Usuario(/*int id,*/ String nombre, String apellido,int edad,String email,String contrasena) {
            /*this.id = id;*/
            this.nombre = nombre;
            this.apellido = apellido;
            this.edad = edad;
            this.email = email;
            this.contrasena = contrasena;
        }


        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getApellido() {
            return apellido;
        }

        public void setApellido(String apellido) {
            this.apellido = apellido;
        }

        public int getEdad() {
            return edad;
        }

        public void setEdad(int edad) {
            this.edad = edad;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getContrasena() {
            return contrasena;
        }

        public void setContrasena(String contrasena) {
            this.contrasena = contrasena;
        }




}


