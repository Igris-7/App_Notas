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

import cz.msebera.android.httpclient.Header;

public class RegCurActivity extends AppCompatActivity implements View.OnClickListener{
    Button jbtnAtrasCurso, jbtnRegistrarCurso;
    EditText jtxtNomCurso, jtxtCicloCurso, jtxtNumCreditos, jtxtNomProfe;
    String iDni;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_cur);

        jbtnAtrasCurso = findViewById(R.id.btnAtrasCurso);
        jbtnRegistrarCurso = findViewById(R.id.btnRegistrarCurso);

        jtxtNomCurso = findViewById(R.id.txtNomCurso);
        jtxtNomProfe = findViewById(R.id.txtNomProfe);
        jtxtCicloCurso = findViewById(R.id.txtCicloCurso);
        jtxtNumCreditos = findViewById(R.id.txtNumCreditos);

        jbtnAtrasCurso.setOnClickListener(this);
        jbtnRegistrarCurso.setOnClickListener(this);

        iDni = getIntent().getStringExtra("dni");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAtrasCurso:
                Atras();
                break;
            case R.id.btnRegistrarCurso:
                RegistrarCurso();
                break;

        }
    }

    private void Atras() {
        Intent iMenu = new Intent(getApplicationContext(), Menu1Activity.class);
        iMenu.putExtra("dni",iDni);
        finish();
        startActivity(iMenu);
    }

    private void RegistrarCurso() {

        String sCurso = jtxtNomCurso.getText().toString().trim();
        String sProfesor = jtxtNomProfe.getText().toString().trim();
        String sCiclo = jtxtCicloCurso.getText().toString().trim();
        String sCreditos = jtxtNumCreditos.getText().toString().trim();

        if(sCurso.isEmpty() || sProfesor.isEmpty() || sCiclo.isEmpty() || sCreditos.isEmpty())
        {
            //mensaje emergente del error
            Toast.makeText(getApplicationContext(), "Rellene todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        else
        {
            if(Integer.parseInt(sCiclo)>0 && Integer.parseInt(sCiclo)<=10 &&
                    Integer.parseInt(sCreditos)>3 && Integer.parseInt(sCreditos)<=5)
            {
                RegCurso(sCurso,sProfesor,sCiclo,sCreditos);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Ingrese datos válidos", Toast.LENGTH_SHORT).show();
                return;
            }
        }



    }

    private void RegCurso(String sCurso, String sProfesor, String sCiclo, String sCreditos) {

        AsyncHttpClient curso= new AsyncHttpClient();

        String surl="http://eyner.atwebpages.com/Proyecto/agregar_curso.php";
        RequestParams params = new RequestParams();
        params.add("nombre",sCurso);
        params.add("docente", sProfesor);
        params.add("ciclo",sCiclo);
        params.add("creditos", sCreditos);
        params.add("dni_est", iDni);

        curso.post(surl, params, new BaseJsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                if(statusCode == 200){
                    int retVal = (rawJsonResponse.length() == 0 ? 0 : Integer.parseInt(rawJsonResponse));

                    if(retVal == 1) {
                        Toast.makeText(getApplicationContext(), "Curso registrado!!", Toast.LENGTH_SHORT).show();
                        jtxtNomCurso.setText("");
                        jtxtNomProfe.setText("");
                        jtxtCicloCurso.setText("");
                        jtxtNumCreditos.setText("");
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
}