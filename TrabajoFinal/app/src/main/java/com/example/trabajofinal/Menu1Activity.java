package com.example.trabajofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.Base64;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

public class Menu1Activity extends AppCompatActivity implements View.OnClickListener{

    ImageView jimgCurso, jimgCalcular, jimgAlarma, jimgExit;
    String iDni, snombre;
    TextView jmostrarNombre;
    ImageView jimgFotoEst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu1);

        jimgCurso = findViewById(R.id.imgCurso);
        jimgAlarma = findViewById(R.id.imgAlarma);
        jimgCalcular = findViewById(R.id.imgCalcular);
        jmostrarNombre = findViewById(R.id.mostrarNombre);
        jimgFotoEst = findViewById(R.id.imgFotoEst);
        jimgExit = findViewById(R.id.imgExit);

        jimgCurso.setOnClickListener(this);
        jimgAlarma.setOnClickListener(this);
        jimgCalcular.setOnClickListener(this);
        jimgExit.setOnClickListener(this);

        iDni = getIntent().getStringExtra("dni");

        MostrarImagenPerfil();

    }

    private void MostrarImagenPerfil() {

        AsyncHttpClient ahcMostrarPerfil = new AsyncHttpClient();

        String sUrl = "http://eyner.atwebpages.com/Proyecto/mostrarPerfil.php";
        RequestParams params = new RequestParams();
        params.add("dni", iDni);

        ahcMostrarPerfil.post(sUrl, params, new BaseJsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                if(statusCode == 200)
                {
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);

                        if (jsonArray.length() == 1) {
                            String nombre = jsonArray.getJSONObject(0).getString("nombres");
                            String bImagen = jsonArray.getJSONObject(0).getString("Imagen");
                            byte[] imageBytes = Base64.decode(bImagen, Base64.DEFAULT);
                            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                            jimgFotoEst.setImageBitmap(decodedImage);
                            jmostrarNombre.setText(nombre);
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

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imgCurso:
                regCurso();
                break;
            case R.id.imgCalcular:
                regNotas();
                break;
            case R.id.imgAlarma:
                regAlarma();
                break;
            case R.id.imgExit :
                Salir();
                break;

        }
    }

    private void Salir() {
        Intent iSalir = new Intent(getApplicationContext(), LoginActivity.class);
        finish();
        startActivity(iSalir);
    }

    private void regAlarma() {
        Intent iRegAlarmas = new Intent(getApplicationContext(), AlarmaActivity.class);
        iRegAlarmas.putExtra("dni",iDni);
        finish();
        startActivity(iRegAlarmas);
    }

    private void regNotas() {
        Intent iRegNotas = new Intent(getApplicationContext(), ListaCurActivity.class);
        iRegNotas.putExtra("dni",iDni);
        finish();
        startActivity(iRegNotas);
    }

    private void regCurso() {
        Intent iRegCurso = new Intent(getApplicationContext(), RegCurActivity.class);
        iRegCurso.putExtra("dni",iDni);
        finish();
        startActivity(iRegCurso);

    }
}