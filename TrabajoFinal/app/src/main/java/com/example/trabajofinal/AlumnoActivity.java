package com.example.trabajofinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;

import cz.msebera.android.httpclient.Header;

public class AlumnoActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_CODE_GALLERY = 1;
    EditText jidDNI, jidNombre, jidTelefono, jidUsuario, jidPassword, jConfirmarPassword;
    Button jbtnElegir, jbtnRegEst, jbtnAtras;
    ImageView jimgFotoEst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);

        jidDNI = findViewById(R.id.idDNI);
        jidNombre = findViewById(R.id.idNombre);
        jidTelefono = findViewById(R.id.idTelefono);
        jidUsuario = findViewById(R.id.idUsuario);
        jidPassword = findViewById(R.id.idPassword);
        jConfirmarPassword = findViewById(R.id.ConfirmarPassword);

        jbtnElegir = findViewById(R.id.btnElegir);
        jbtnAtras = findViewById(R.id.btnAtras);
        jbtnRegEst = findViewById(R.id.btnRegEst);

        jimgFotoEst = findViewById(R.id.imgFotoEst);

        jbtnElegir.setOnClickListener(this);
        jbtnAtras.setOnClickListener(this);
        jbtnRegEst.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case  R.id.btnAtras:
                atras();
                break;
            case R.id.btnRegEst:
                registrarEstudiante();
                break;
            case R.id.btnElegir:
                elegirImagen();
                break;
        }
    }

    private void elegirImagen() {
        ActivityCompat.requestPermissions(this,
                new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_CODE_GALLERY);
    }

    private void registrarEstudiante() {

        String sdni = jidDNI.getText().toString().trim();
        String snombres = jidNombre.getText().toString().trim();
        String stelefono =  jidTelefono.getText().toString().trim();
        String susuario =  jidUsuario.getText().toString().trim();
        String sclave = jidPassword.getText().toString().trim();
        String sconfClave = jConfirmarPassword.getText().toString().trim();

        ImageView myImage = (ImageView) findViewById(R.id.imgFotoEst);

        if ( null == myImage.getDrawable()){

            //mensaje emergente del error
            Toast.makeText(getApplicationContext(), "Elija una imagen", Toast.LENGTH_SHORT).show();
            return;

        } else{

            if(sdni.isEmpty() || snombres.isEmpty() || stelefono.isEmpty() || susuario.isEmpty() || sclave.isEmpty() || sconfClave.isEmpty() )
            {
                //mensaje emergente del error
                Toast.makeText(getApplicationContext(), "Llene todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            else
            {
                if(sclave.equals(sconfClave))
                {
                    registroE(sdni, snombres, stelefono, susuario, sclave);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "No coinciden las contraseñas", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

        }

    }

    private void registroE(String sdni, String snombres, String stelefono, String susuario, String sclave) {

        AsyncHttpClient ahcRegistrarEstudiante = new AsyncHttpClient();
        Hash hash = new Hash();

        String sUrl = "http://eyner.atwebpages.com/Proyecto/agregar_estudiante.php";
        RequestParams params = new RequestParams();
        params.add("dni", sdni);
        params.add("nombres", snombres);
        params.add("telefono", stelefono);
        params.add("usuario", susuario);
        params.add("clave", hash.StringToHash(sclave,"SHA1"));
        params.add("imagen", ImageViewToBase64(jimgFotoEst));

        ahcRegistrarEstudiante.post(sUrl, params, new BaseJsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                if(statusCode == 200){
                    int retVal = (rawJsonResponse.length() == 0 ? 0 : Integer.parseInt(rawJsonResponse));

                    if(retVal == 1) {
                        Toast.makeText(getApplicationContext(), "Estudiante registrado!!", Toast.LENGTH_SHORT).show();
                        jidDNI.setText("");
                        jidNombre.setText("");
                        jidTelefono.setText("");
                        jidUsuario.setText("");
                        jidPassword.setText("");
                        jConfirmarPassword.setText("");
                        jimgFotoEst.setImageResource(R.mipmap.ic_launcher);
                    }
                }

                else
                {
                    Toast.makeText(getApplicationContext(), "No se pudo registrar!!", Toast.LENGTH_SHORT).show();
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

    private void atras() {
        Intent iatras= new Intent(this, LoginActivity.class);
        startActivity(iatras);
    }

    private String ImageViewToBase64(ImageView jimgFotoEst) {

        Bitmap bitmap = ((BitmapDrawable)jimgFotoEst.getDrawable()).getBitmap();

        //se convierte a un arregleo de bytes
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        byte[] byteArray = stream.toByteArray();
        String imagen = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return imagen;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(resultCode == RESULT_OK && data != null){
                Uri uri = data.getData();
                jimgFotoEst.setImageURI(uri);
            }
            else{
                Toast.makeText(this, "Debe elegir una imagen", Toast.LENGTH_SHORT).show();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent iFileChooser = new Intent(Intent.ACTION_PICK);
                iFileChooser.setType("image/*");
                startActivityForResult(iFileChooser, REQUEST_CODE_GALLERY);
            }
        }
        else
            Toast.makeText(this, "No se puede acceder al almacenamiento externo",
                    Toast.LENGTH_SHORT).show();
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}



