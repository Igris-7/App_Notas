package com.example.trabajofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ListaCurActivity extends AppCompatActivity  implements View.OnClickListener{

    ListView jlvCursos;
    ArrayList<Curso> lista;
    MostrarAdapter adapter = null;
    Button jretroceder;
    String iDni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cur);

        jlvCursos = findViewById(R.id.lvMostrarCursos);
        lista = new ArrayList<>();
        adapter = new MostrarAdapter(this, R.layout.curso_item, lista);
        jlvCursos.setAdapter(adapter);

        iDni = getIntent().getStringExtra("dni");

        MostrarCursos();

        jlvCursos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent iEvaluciones = new Intent(getApplicationContext(), EvaluacionesActivity.class);
                iEvaluciones.putExtra("id",lista.get(position).getCurso_id());
                iEvaluciones.putExtra("dni", iDni);
                finish();
                startActivity(iEvaluciones);
            }
        });


      jretroceder = findViewById(R.id.Retroceder);
        jretroceder.setOnClickListener(this);


    }

    private void MostrarCursos() {

        AsyncHttpClient ahcMostrarCursos = new AsyncHttpClient();

        String sUrl = "http://eyner.atwebpages.com/Proyecto/mostrar_curso.php";
        RequestParams params = new RequestParams();
        params.add("dni_est",iDni);

        ahcMostrarCursos.post(sUrl, params, new BaseJsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Object response) {

                if(statusCode == 200){
                    try {
                        JSONArray jsonArray = new JSONArray(rawJsonResponse);

                        for (int i = 1; i <= jsonArray.length(); i++) {

                            lista.add(new Curso(jsonArray.getJSONObject(i-1).getInt("curso_id"),
                                    jsonArray.getJSONObject(i-1).getString("nombre"),
                                    jsonArray.getJSONObject(i-1).getString("docente"),
                                    jsonArray.getJSONObject(i-1).getString("ciclo"),
                                    jsonArray.getJSONObject(i-1).getString("creditos"),
                                    jsonArray.getJSONObject(i-1).getString("dni_est")));

                        }
                        adapter.notifyDataSetChanged();

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
            case R.id.Retroceder:
                IrMenu();
                break;
        }
    }

    private void IrMenu() {
        Intent imenu = new Intent(getApplicationContext(), Menu1Activity.class);
        imenu.putExtra("dni",iDni);
        finish();
        startActivity(imenu);
    }
}