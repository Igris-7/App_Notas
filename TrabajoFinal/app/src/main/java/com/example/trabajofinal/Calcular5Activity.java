package com.example.trabajofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class Calcular5Activity extends AppCompatActivity implements View.OnClickListener{

    Button jbtnAtras5, jbtnCalcular5, jbtnRegistrar5;
    EditText jtxtNota1, jtxtNota2, jtxtNota3,jtxtNota4,jtxtNota5, jtxtPeso1, jtxtPeso2, jtxtPeso3, jtxtPeso4, jtxtPeso5;
    int iID = -1;
    String iDni="72890089";
    TextView jtxtPromedio5;
    float suma,promedio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcular5);

        jtxtNota1 = findViewById(R.id.txtNota15);
        jtxtNota2 = findViewById(R.id.txtNota25);
        jtxtNota3 = findViewById(R.id.txtNota35);
        jtxtNota4 = findViewById(R.id.txtNota45);
        jtxtNota5 = findViewById(R.id.txtNota55);
        jtxtPeso1 = findViewById(R.id.txtPeso15);
        jtxtPeso2 = findViewById(R.id.txtPeso25);
        jtxtPeso3 = findViewById(R.id.txtPeso35);
        jtxtPeso4 = findViewById(R.id.txtPeso45);
        jtxtPeso5 = findViewById(R.id.txtPeso55);
        jtxtPromedio5 = findViewById(R.id.txtPromedio5);

        jbtnAtras5= findViewById(R.id.btnAtras5);
        jbtnCalcular5 = findViewById(R.id.btnCalcular5);
        jbtnRegistrar5 = findViewById(R.id.btnRegistrar5);

        jbtnAtras5.setOnClickListener(this);
        jbtnCalcular5.setOnClickListener(this);
        jbtnRegistrar5.setOnClickListener(this);

        iID = getIntent().getIntExtra("id",-1);
        iDni = getIntent().getStringExtra("dni");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnAtras5:
                Atras5();
                break;
            case R.id.btnCalcular5:
                Calcular5();
                break;
            case R.id.btnRegistrar5    :
                RegistrarC();
                break;
        }
    }

    private void RegistrarC() {

        AsyncHttpClient calificacion5= new AsyncHttpClient();

        String surl="http://eyner.atwebpages.com/Proyecto/agregar_calificacion5.php";
        RequestParams params = new RequestParams();
        params.put("curso_id",iID);
        params.add("ev1",jtxtNota1.getText().toString().trim());
        params.add("ev2", jtxtNota2.getText().toString().trim());
        params.add("ev3", jtxtNota3.getText().toString().trim());
        params.add("ev4", jtxtNota4.getText().toString().trim());
        params.add("ev5", jtxtNota5.getText().toString().trim());
        params.add("prom", jtxtPromedio5.getText().toString().trim());

        calificacion5.post(surl, params, new BaseJsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {
                if(statusCode == 200){
                    int retVal = (rawJsonResponse.length() == 0 ? 0 : Integer.parseInt(rawJsonResponse));

                    if(retVal == 1) {
                        Toast.makeText(getApplicationContext(), "Calificacion agregada!!", Toast.LENGTH_SHORT).show();
                        jtxtNota1.setText("");
                        jtxtNota2.setText("");
                        jtxtNota3.setText("");
                        jtxtNota4.setText("");
                        jtxtNota5.setText("");
                        jtxtPromedio5.setText("");
                        jtxtPeso1.setText("");
                        jtxtPeso2.setText("");
                        jtxtPeso3.setText("");
                        jtxtPeso4.setText("");
                        jtxtPeso5.setText("");
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                Toast.makeText(getApplicationContext(), "No se pudo agregar la calificación!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void Calcular5() {
        String nota1 = jtxtNota1.getText().toString().trim();
        String nota2 = jtxtNota2.getText().toString().trim();
        String nota3 = jtxtNota3.getText().toString().trim();
        String nota4 = jtxtNota4.getText().toString().trim();
        String nota5 = jtxtNota5.getText().toString().trim();
        String peso1 = jtxtPeso1.getText().toString().trim();
        String peso2 = jtxtPeso2.getText().toString().trim();
        String peso3 = jtxtPeso3.getText().toString().trim();
        String peso4 = jtxtPeso4.getText().toString().trim();
        String peso5 = jtxtPeso5.getText().toString().trim();

        if(nota1.isEmpty() || nota2.isEmpty() || nota3.isEmpty() || peso1.isEmpty() || peso2.isEmpty() || peso3.isEmpty() ||
                nota4.isEmpty() || peso4.isEmpty() || nota5.isEmpty() || peso5.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Llene todos los campos",
                    Toast.LENGTH_SHORT).show();
        }

        else
        {
            suma = Float.parseFloat(peso1)/100 + Float.parseFloat(peso2)/100 + Float.parseFloat((peso3))/100+
                    Float.parseFloat(peso4)/100 + Float.parseFloat(peso5)/100;
            if(Integer.parseInt(nota1) >= 0 && Integer.parseInt(nota1) <=20 &&  Integer.parseInt(nota2) >= 0 &&
                    Integer.parseInt(nota2) <=20 && Integer.parseInt(nota3) >= 0 && Integer.parseInt(nota3) <=20
                    && Integer.parseInt(nota4) >= 0 && Integer.parseInt(nota4) <=20 && Integer.parseInt(nota5) >= 0
                    && Integer.parseInt(nota5) <=20&& suma==1)
            {
               /* Toast.makeText(getApplicationContext(), "Correcto" + suma + "ID: "+ iID + "DNI: "+ iDni ,
                        Toast.LENGTH_SHORT).show();*/

                promedio = (Integer.parseInt(nota1)*Float.parseFloat(peso1)/100 + Integer.parseInt(nota2)*Float.parseFloat(peso2)/100+
                        Integer.parseInt(nota3)*Float.parseFloat((peso3))/100 + Integer.parseInt(nota4)*Float.parseFloat(peso4)/100 +
                        Integer.parseInt(nota5)* Float.parseFloat(peso5)/100);

                jtxtPromedio5.setText(String.valueOf(promedio));

                if(promedio < 12)
                {
                    Toast.makeText(getApplicationContext(), "Desaprobado",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Aprobado",
                            Toast.LENGTH_SHORT).show();
                }

            }
            else
            {
                Toast.makeText(getApplicationContext(), "Notas o suma de peso incorrecto",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void Atras5() {
        Intent iCantNotas = new Intent(getApplicationContext(), EvaluacionesActivity.class);
        iCantNotas.putExtra("dni",iDni);
        iCantNotas.putExtra("id",iID);
        finish();
        startActivity(iCantNotas);
    }
}