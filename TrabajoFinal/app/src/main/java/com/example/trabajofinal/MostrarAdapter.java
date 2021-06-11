package com.example.trabajofinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MostrarAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Curso> listaCurso;

    public MostrarAdapter(Context context, int layout, ArrayList<Curso> listaCurso) {
        this.context = context;
        this.layout = layout;
        this.listaCurso = listaCurso;
    }


    @Override
    public int getCount() {
    return listaCurso.size();
    }

    @Override
    public Object getItem(int position) {
        return listaCurso.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView Curso, Ciclo, Creditos, Docente;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View fila =convertView;
        ViewHolder holder = new ViewHolder();

        if(fila == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            fila = inflater.inflate(layout, null);

            holder.Curso =fila.findViewById(R.id.lblItemNomCurso);
            holder.Ciclo = fila.findViewById(R.id.lblItemCiclo);
            holder.Creditos = fila.findViewById(R.id.lblItemNumCreditos);
            holder.Docente = fila.findViewById(R.id.lblItemNomProfesor);
            fila.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)fila.getTag();
        }

         Curso cursos = listaCurso.get(position);

         holder.Curso.setText(cursos.getNombre());
         holder.Ciclo.setText(cursos.getCiclo());
         holder.Creditos.setText(cursos.getCreditos());
         holder.Docente.setText(cursos.getDocente());


        return fila;
    }
}
