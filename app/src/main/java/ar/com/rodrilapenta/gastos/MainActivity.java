package ar.com.rodrilapenta.gastos;

import android.*;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.security.KeyStore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import ar.com.rodrilapenta.gastos.handlers.FingerprintHandler;
import ar.com.rodrilapenta.gastos.utils.FingerprintUtil;

public class MainActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private UsuarioRepository usuarioRepository;
    private Button ingresar;
    private Button registrarse;
    private EditText loginEmail, loginPassword;
    private final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private FingerprintManager.CryptoObject cryptoObject;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;

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

    private void startFingerprintConfig() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            keyguardManager =
                    (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            fingerprintManager =
                    (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

            if (!fingerprintManager.isHardwareDetected()) {
                Toast.makeText(MainActivity.this, "Tu dispositivo no posee lector de huellas", Toast.LENGTH_SHORT).show();
            }

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Por favor permite a la aplicación utilizar el lector de huellas", Toast.LENGTH_SHORT).show();
            }

            if (!fingerprintManager.hasEnrolledFingerprints()) {
                Toast.makeText(MainActivity.this, "No hay ninguna huella configurada en el dispositivo", Toast.LENGTH_SHORT).show();
            }

            if (!keyguardManager.isKeyguardSecure()) {
                Toast.makeText(MainActivity.this, "Para usar tu huella debes tener activado el bloqueo de pantalla", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    FingerprintUtil.generateKey();
                } catch (FingerprintUtil.FingerprintException e) {
                    e.printStackTrace();
                }
                if (FingerprintUtil.initCipher()) {
                    cryptoObject = new FingerprintManager.CryptoObject(FingerprintUtil.getCipher());
                    FingerprintHandler helper = new FingerprintHandler(this);
                    helper.startAuth(fingerprintManager, cryptoObject);
                }
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startFingerprintConfig();
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