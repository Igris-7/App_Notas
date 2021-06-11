package com.example.trabajofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EvaluacionesActivity extends AppCompatActivity implements View.OnClickListener{

    Button  jbtnAgregar, jbtnEvAtras;;
    EditText jtxtCantidadNotas;
    String iDni="72890089";
    int iID=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluaciones);

        jbtnAgregar = findViewById(R.id.btnAgregar);
        jbtnEvAtras = findViewById(R.id.btnEvAtras);
        jtxtCantidadNotas = findViewById(R.id.txtCantidadNotas);

        jbtnAgregar.setOnClickListener(this);
        jbtnEvAtras.setOnClickListener(this);


        iID = getIntent().getIntExtra("id",-1);
        iDni = getIntent().getStringExtra("dni");


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAgregar:
                AgregarCantidad();
                break;
            case R.id.btnEvAtras:
                Atras();
                break;
        }
    }

    private void AgregarCantidad() {

       String cantidad = jtxtCantidadNotas.getText().toString().trim();

        if(cantidad.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Ingrese una cantidad",
                    Toast.LENGTH_SHORT).show();
        }

        else
        {
            if(Integer.parseInt(cantidad )>5 || Integer.parseInt(cantidad )<3)
            {
                Toast.makeText(getApplicationContext(), "Cantidad de notas entre 3-5",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                if(Integer.parseInt(cantidad )== 3)
                {
                    Intent iReg3Notas = new Intent(getApplicationContext(), Calcular3Activity.class);
                    iReg3Notas.putExtra("id",iID);
                    iReg3Notas.putExtra("dni",iDni);
                    finish();
                    startActivity(iReg3Notas);
                }
                if(Integer.parseInt(cantidad )== 4)
                {
                    Intent iReg4Notas = new Intent(getApplicationContext(), Calcular4Activity.class);
                    iReg4Notas.putExtra("id",iID);
                    iReg4Notas.putExtra("dni",iDni);
                    startActivity(iReg4Notas);
                    finish();
                }
                if(Integer.parseInt(cantidad )== 5)
                {
                    Intent iReg5Notas = new Intent(getApplicationContext(), Calcular5Activity.class);
                    iReg5Notas.putExtra("id",iID);
                    iReg5Notas.putExtra("dni",iDni);
                    startActivity(iReg5Notas);
                    finish();
                }
            }
        }

    }

  /*private void AgregarVista() {

        View vista_fila = getLayoutInflater().inflate(R.layout.agregar_fila, null, false);

        EditText editText = (EditText)vista_fila.findViewById(R.id.nota_agregar);
        EditText editText1 =(EditText)vista_fila.findViewById(R.id.peso_agregar);
        ImageView imageClose = (ImageView)vista_fila.findViewById(R.id.imagen_eliminar);

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EliminarVista(vista_fila);
            }
        });

        layoutLista.addView(vista_fila);
    }*/

   /* private void EliminarVista(View view)
    {
        layoutLista.removeView(view);
    }*/

    private void Atras() {
        Intent iMenu2 = new Intent(getApplicationContext(), ListaCurActivity.class);
        iMenu2.putExtra("dni",iDni);
        iMenu2.putExtra("id",iID);
        finish();
        startActivity(iMenu2);
    }

}