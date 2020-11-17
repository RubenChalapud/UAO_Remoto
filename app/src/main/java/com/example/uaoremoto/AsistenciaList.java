package com.example.uaoremoto;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AsistenciaList extends ArrayAdapter<Asistencia> {
    private Activity context;

    //Lista de asistencia
    List<Asistencia> Asistencias;

    public AsistenciaList(Activity context, List<Asistencia> Asistencias){
        super(context, R.layout.layout_asistencia_list, Asistencias);
        this.context = context;
        this.Asistencias = Asistencias;
    }

    // clase adpatadora para desplegar el listado de elementos almacenados en Firebase
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_asistencia_list, null, true);
        //initialize String idprofesor, String nombreprofesor, String apellidoprofesor, String correoprofesor, String contraseñaprofesor, String sintomasprofesor
        TextView textViewIdEst = (TextView) listViewItem.findViewById(R.id.textViewIdEstudiante);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewNombre);
        TextView textviewApellido = (TextView) listViewItem.findViewById(R.id.textviewApellido);
        TextView textviewPrograma = (TextView) listViewItem.findViewById(R.id.textviewPrograma);
        TextView textviewModo = (TextView) listViewItem.findViewById(R.id.textviewModo);
        TextView textViewFecha = (TextView) listViewItem.findViewById(R.id.textviewFecha);
        TextView textviewAsistencia = (TextView) listViewItem.findViewById(R.id.textviewAsistencia);

        //getting user at position
        Asistencia Asistencia = Asistencias.get(position);

        textViewIdEst.setText(Asistencia.getIdestudiante());
        //set user name
        textViewName.setText(Asistencia.getNombreestudiante());
        textviewApellido.setText(Asistencia.getApellidoestudiante());
        textviewPrograma.setText(Asistencia.getProgramaestudiante());
        textviewModo.setText(Asistencia.getModoclase());
        textViewFecha.setText(Asistencia.getFechaclase());
        textviewAsistencia.setText(Asistencia.getAsistenciaclase());

        return listViewItem;
    }
}
