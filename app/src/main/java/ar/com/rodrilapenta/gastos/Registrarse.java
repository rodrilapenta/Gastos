package ar.com.rodrilapenta.gastos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ar.com.rodrilapenta.gastos.R;

/**
 * Created by arielverdugo on 1/6/17.
 */

public class Registrarse extends Activity {

    private SessionManager sessionManager;
    private Button registrarse;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        registrarse = (Button) findViewById(R.id.registrarBtn);

        registrarse.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = ((EditText) findViewById(R.id.newEmail)).getText().toString();
                String contrasena = ((EditText) findViewById(R.id.newContrasena)).getText().toString();
                int edad = Integer.parseInt(((EditText) findViewById(R.id.newEdad)).getText().toString());
                String confirmarText = ((EditText) findViewById(R.id.newContrasena2)).getText().toString();
                String nombreText = ((EditText) findViewById(R.id.newNombre)).getText().toString();
                String apellidolText = ((EditText) findViewById(R.id.newApellido)).getText().toString();


                if (email.isEmpty() || contrasena.isEmpty() || confirmarText.isEmpty() || nombreText.isEmpty() || apellidolText.isEmpty() || email.isEmpty()) {
                    Toast.makeText(Registrarse.this, "Datos insuficientes", Toast.LENGTH_SHORT).show();

                } else if (confirmarText.equals(contrasena) == true) {
                    UsuarioRepository.getInstance(Registrarse.this).addUsuario(new Usuario(nombreText, apellidolText, edad, email, contrasena));
                    onBackPressed();
                    sessionManager = SessionManager.getInstance(Registrarse.this);
                    sessionManager.guardarEmail(email);
                    Intent intento = new Intent(Registrarse.this,HomeActivity.class);
                    intento.putExtra("email", email);
                    Toast.makeText(Registrarse.this, "Registro con éxito", Toast.LENGTH_SHORT).show();
                    startActivity(intento);
                } else {
                    Toast.makeText(Registrarse.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}