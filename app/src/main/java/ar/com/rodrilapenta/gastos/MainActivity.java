package ar.com.rodrilapenta.gastos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.rodrilapenta.gastos.R;

public class MainActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private UsuarioRepository usuarioRepository;
    private Button ingresar;
    private Button registrarse;
    private EditText loginEmail, loginPassword;
    private final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        findViewById(R.id.google_sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });
        ingresar =(Button) findViewById(R.id.ingresarBtn);
        registrarse =(Button) findViewById(R.id.registrarBtn);
        loginEmail = (EditText) findViewById(R.id.loginEmail);
        loginPassword = (EditText) findViewById(R.id.loginPassword);

        ingresar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                usuarioRepository = UsuarioRepository.getInstance(MainActivity.this);
                Map<String, String> credentials = new HashMap<>();
                credentials.put("email", loginEmail.getText().toString());
                credentials.put("contrasena", loginPassword.getText().toString());
                List<Usuario> users = usuarioRepository.findWhere(credentials);

                if(users != null && users.size() != 0) {
                    sessionManager = SessionManager.getInstance(MainActivity.this);
                    sessionManager.guardarEmail(loginEmail.getText().toString());
                    Intent intento = new Intent(MainActivity.this,HomeActivity.class);
                    intento.putExtra("email", loginEmail.getText().toString());
                    startActivity(intento);
                }
                else {
                    Toast.makeText(MainActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registrarse.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this,Registrarse.class));
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            sessionManager = SessionManager.getInstance(MainActivity.this);
            sessionManager.guardarEmail(acct.getEmail());
            Intent intento = new Intent(MainActivity.this,HomeActivity.class);
            intento.putExtra("email", loginEmail.getText().toString());
            Toast.makeText(MainActivity.this, "¡Bienvenido, " + acct.getDisplayName() + "!", Toast.LENGTH_SHORT).show();
            startActivity(intento);
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            //updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            //updateUI(false);

            Toast.makeText(MainActivity.this, result.getStatus().getStatusCode() + " - " + result.getStatus().getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }
}