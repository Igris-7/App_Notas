package com.example.trabajofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.WorkManager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class AlarmaActivity extends AppCompatActivity implements View.OnClickListener {

    Button jbtn_SelFecha, jbtn_SelHora, jbtn_Guardar, jbtn_Eliminar, jbtn_AtrasAlarma;
    EditText jAsunto, jasunto, jdetalle;
    TextView jtv_fecha, jtv_hora;

    Calendar actual = Calendar.getInstance();
    Calendar calendar = Calendar.getInstance();
    String iDni;

    private int minutos, hora , dia , mes, anio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarma);

        jbtn_SelFecha = findViewById(R.id.btn_SelFecha);
        jbtn_SelHora = findViewById(R.id.btn_SelHora);
        jbtn_Guardar = findViewById(R.id.btn_Guardar);
        jbtn_AtrasAlarma = findViewById(R.id.btn_AtrasAlarma);
        jtv_fecha = findViewById(R.id.tv_fecha);
        jtv_hora = findViewById(R.id.tv_hora);
        jasunto = findViewById(R.id.asunto);
        jdetalle = findViewById(R.id.detalle);


        iDni = getIntent().getStringExtra("dni");

        jbtn_SelFecha.setOnClickListener(this);
        jbtn_SelHora.setOnClickListener(this);
        jbtn_Guardar.setOnClickListener(this);
        jbtn_AtrasAlarma.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_SelFecha:
                    SelecFecha();
                break;
            case R.id.btn_SelHora:
                    SelecHora();
                break;
            case R.id.btn_Guardar:
                    GuardarAlarma();
                break;
            case R.id.btn_AtrasAlarma:
                AtrasMenu();
                break;
        }
    }

    private void GuardarAlarma() {

        String tag = generateKey();
        Long alerta = calendar.getTimeInMillis() - System.currentTimeMillis();
        int random = (int)(Math.random() *50 +1);

        Data data = GuardarData(jasunto.getText().toString().trim(), jdetalle.getText().toString().trim(), random);
        Workmanagermoti.GuardarNoti(alerta, data, tag);

        Toast.makeText(getApplicationContext(), "Notificación guardada", Toast.LENGTH_SHORT).show();

    }

    private void SelecHora() {

        hora = actual.get(Calendar.HOUR_OF_DAY);
        minutos = actual.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int h, int m) {

                calendar.set(Calendar.HOUR_OF_DAY,h);
                calendar.set(Calendar.MINUTE,m);

                jtv_hora.setText(String.format("%02d:%02d",h,m));
            }
        },hora, minutos,true);
        timePickerDialog.show();
    }

    private void AtrasMenu() {
        Intent iMenu = new Intent(getApplicationContext(), Menu1Activity.class);
        iMenu.putExtra("dni",iDni);
        finish();
        startActivity(iMenu);
    }

    private void SelecFecha() {
        //para la fecha actual
        anio = actual.get(Calendar.YEAR);
        mes = actual.get(Calendar.MONTH);
        dia = actual.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int y, int m, int d) {
                //para setear
                calendar.set(Calendar.DAY_OF_MONTH,d);
                calendar.set(Calendar.MONTH,m);
                calendar.set(Calendar.YEAR,y);

                //Cambiar el formato

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                String strDate = format.format(calendar.getTime());
                jtv_fecha.setText(strDate);
            }
        },anio, mes, dia);
        datePickerDialog.show();
    }

    private String generateKey()
    {
        return UUID.randomUUID().toString(); //generar numero aleatorio
    }

    private Data GuardarData(String titulo, String detalle , int id_noti)
    {
        return new Data.Builder()
                .putString("titulo",titulo)
                .putString("detalle",detalle)
                .putInt("id_noti", id_noti).build();
    }

}