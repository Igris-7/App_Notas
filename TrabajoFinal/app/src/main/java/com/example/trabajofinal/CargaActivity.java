package com.example.trabajofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

public class CargaActivity extends AppCompatActivity {
    ProgressBar jpbCarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga);

        jpbCarga = findViewById(R.id.pbCarga);

        Thread tCarga = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                } finally {
                    Intent iLogin = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(iLogin);
                    finish();
                }
            }
        };



        tCarga.start();



    }
}