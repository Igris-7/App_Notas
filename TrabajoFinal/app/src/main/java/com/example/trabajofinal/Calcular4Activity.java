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

public class Calcular4Activity extends AppCompatActivity implements View.OnClickListener {

    Button jbtnAtras4, jbtnCalcular4 , jbtnRegistrar4;
    EditText jtxtNota1, jtxtNota2, jtxtNota3,jtxtNota4, jtxtPeso1, jtxtPeso2, jtxtPeso3, jtxtPeso4;
    int iID = -1;
    TextView jtxtPromedio4;
    String iDni="72890089";
    float suma , promedio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcular4);

        jtxtNota1 = findViewById(R.id.txtNota14);
        jtxtNota2 = findViewById(R.id.txtNota24);
        jtxtNota3 = findViewById(R.id.txtNota34);
        jtxtNota4 = findViewById(R.id.txtNota44);
        jtxtPeso1 = findViewById(R.id.txtPeso14);
        jtxtPeso2 = findViewById(R.id.txtPeso24);
        jtxtPeso3 = findViewById(R.id.txtPeso34);
        jtxtPeso4 = findViewById(R.id.txtPeso44);
        jtxtPromedio4 = findViewById(R.id.txtPromedio4);

        jbtnAtras4= findViewById(R.id.btnAtras4);
        jbtnCalcular4 = findViewById(R.id.btnCalcular4);
        jbtnRegistrar4 = findViewById(R.id.btnRegistrar4);

        jbtnAtras4.setOnClickListener(this);
        jbtnCalcular4.setOnClickListener(this);
        jbtnRegistrar4.setOnClickListener(this);

        iID = getIntent().getIntExtra("id",-1);
        iDni = getIntent().getStringExtra("dni");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnAtras4:
                Atras4();
                break;
            case R.id.btnCalcular4:
                Calcular4();
                break;
            case R.id.btnRegistrar4:
                RegistrarC();
                break;
        }
    }

    private void RegistrarC() {

        AsyncHttpClient calificacion4= new AsyncHttpClient();

        String surl="http://eyner.atwebpages.com/Proyecto/agregar_calificacion4.php";
        RequestParams params = new RequestParams();
        params.put("curso_id",iID);
        params.add("ev1",jtxtNota1.getText().toString().trim());
        params.add("ev2", jtxtNota2.getText().toString().trim());
        params.add("ev3", jtxtNota3.getText().toString().trim());
        params.add("ev4", jtxtNota4.getText().toString().trim());
        params.add("prom", jtxtPromedio4.getText().toString().trim());

        calificacion4.post(surl, params, new BaseJsonHttpResponseHandler() {
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
                        jtxtPromedio4.setText("");
                        jtxtPeso1.setText("");
                        jtxtPeso2.setText("");
                        jtxtPeso3.setText("");
                        jtxtPeso4.setText("");
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

    private void Calcular4() {
        String nota1 = jtxtNota1.getText().toString().trim();
        String nota2 = jtxtNota2.getText().toString().trim();
        String nota3 = jtxtNota3.getText().toString().trim();
        String nota4 = jtxtNota4.getText().toString().trim();
        String peso1 = jtxtPeso1.getText().toString().trim();
        String peso2 = jtxtPeso2.getText().toString().trim();
        String peso3 = jtxtPeso3.getText().toString().trim();
        String peso4 = jtxtPeso4.getText().toString().trim();

        if(nota1.isEmpty() || nota2.isEmpty() || nota3.isEmpty() || peso1.isEmpty() || peso2.isEmpty() || peso3.isEmpty() ||
                nota4.isEmpty() || peso4.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Llene todos los campos",
                    Toast.LENGTH_SHORT).show();
        }

        else
        {
            suma = Float.parseFloat(peso1)/100 + Float.parseFloat(peso2)/100 + Float.parseFloat((peso3))/100+
                    Float.parseFloat(peso4)/100;
            if(Integer.parseInt(nota1) >= 0 && Integer.parseInt(nota1) <=20 &&  Integer.parseInt(nota2) >= 0 &&
                    Integer.parseInt(nota2) <=20 && Integer.parseInt(nota3) >= 0 && Integer.parseInt(nota3) <=20
                    && Integer.parseInt(nota4) >= 0 && Integer.parseInt(nota4) <=20  && suma==1)
            {
               /* Toast.makeText(getApplicationContext(), "Correcto" + suma + "ID: "+ iID + "DNI: "+ iDni ,
                        Toast.LENGTH_SHORT).show();*/

                 promedio = (Integer.parseInt(nota1)*Float.parseFloat(peso1)/100 + Integer.parseInt(nota2)*Float.parseFloat(peso2)/100+
                        Integer.parseInt(nota3)*Float.parseFloat((peso3))/100 + Integer.parseInt(nota4)*Float.parseFloat(peso4)/100);

                jtxtPromedio4.setText(String.valueOf(promedio));

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

    private void Atras4() {
        Intent iCantNotas = new Intent(getApplicationContext(), EvaluacionesActivity.class);
        iCantNotas.putExtra("dni",iDni);
        iCantNotas.putExtra("id",iID);
        finish();
        startActivity(iCantNotas);
    }
}