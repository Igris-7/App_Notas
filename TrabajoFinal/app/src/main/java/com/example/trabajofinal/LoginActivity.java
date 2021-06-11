package com.example.trabajofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    Button jbtnMinIngresar, jbtnMinSalir , jtxtRegistrar;
    EditText jtxtUsuario, jtxtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        jbtnMinIngresar = findViewById(R.id.btnIngresar);
        jbtnMinSalir = findViewById(R.id.btnSalir);
        jtxtRegistrar = findViewById(R.id.txtRegistrar);
        jtxtUsuario = findViewById(R.id.txtUsuario);
        jtxtPassword = findViewById(R.id.txtPassword);

        jbtnMinIngresar.setOnClickListener(this);
        jbtnMinSalir.setOnClickListener(this);
        jtxtRegistrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnIngresar:
                Ingresar();
                break;
            case R.id.btnSalir:
                Salir();
                break;
            case R.id.txtRegistrar:
                registrarAlumno();
                break;
        }
    }

    private void registrarAlumno() {
        Intent iRegistro = new Intent(this, AlumnoActivity.class);
        startActivity(iRegistro);
    }

    private void Ingresar() {

        String sUsuario = jtxtUsuario.getText().toString().trim();
        String sPassword = jtxtPassword.getText().toString().trim();

        if(sUsuario.isEmpty() || sPassword.isEmpty())
        {
            //mensaje emergente del error
            Toast.makeText(getApplicationContext(), "Llene todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        UsuarioValido(sUsuario,sPassword);

    }

    private void UsuarioValido(String sUsuario, String sPassword) {

        AsyncHttpClient estudiante = new AsyncHttpClient();

        Hash hash = new Hash();

        String surl="http://eyner.atwebpages.com/Proyecto/login.php";
        RequestParams params = new RequestParams();
        params.add("user",sUsuario);
        params.add("pwd", hash.StringToHash(sPassword,"SHA1"));

        estudiante.post(surl, params, new BaseJsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                if(statusCode == 200)
                {
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);

                        if(jsonArray.length() > 0)
                        {
                            String sDNI = jsonArray.getJSONObject(0).getString("dni");
                            if(sDNI.equals("-1")){
                                Toast.makeText(getApplicationContext(), "Usuario o Clave incorrecta",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Bienvenido: " + sUsuario,
                                        Toast.LENGTH_SHORT).show();
                                Intent imenu = new Intent(getApplicationContext(), Menu1Activity.class);
                                imenu.putExtra("nombre",sUsuario);
                                imenu.putExtra("dni",sDNI);
                                finish();
                                startActivity(imenu);

                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {

            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void Salir() {
        System.exit(0);
    }
}