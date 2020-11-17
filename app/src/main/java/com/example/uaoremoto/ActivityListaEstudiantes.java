package com.example.uaoremoto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityListaEstudiantes extends AppCompatActivity {
    private TextView textNombre;
    public String idcurso;
    public String idestudiante, fechaclase, modoclase, asistenciaclase, nombreestudiante, apellidoestudiante, programaestudiante;

    //Inicializar menu
    DrawerLayout drawerLayout;

    //lista que almacena todos los usuarios de la base de datos de Firebase
    List<Estudiante> Estudiantes;
    //lista que almacena todos los usuarios clase de la base de datos de Firebase
    List<Clase> Clases;
    //lista que almacena todos los usuarios clase de la base de datos de Firebase
    List<Asistencia> Asistencias;

    DatabaseReference databaseReference;

    //Lista de estudiantes
    ListView listViewEstudiantesA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_estudiantes);

        //Menu
        drawerLayout = findViewById(R.id.drawer_layout);

        textNombre =(TextView)findViewById(R.id.textViewNombreCurso);
        final String nombrecurso = getIntent().getStringExtra("nombrecurso");
        textNombre.setText(nombrecurso);

        listViewEstudiantesA = (ListView) findViewById(R.id.listViewEstudiantes);

        //  referenciamos datos de firebase
        databaseReference = FirebaseDatabase.getInstance().getReference();

        idcurso = getIntent().getStringExtra("idcurso");
        Clases = new ArrayList<Clase>();
        Estudiantes = new ArrayList<Estudiante>();
        Asistencias = new ArrayList<Asistencia>();
        crearListaAsistencia();
    }

    private void crearListaAsistencia() {
        databaseReference.child("Clases").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Clases.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        //obtenemos los usuarios de la consola de Firebase
                        Clase Clase = snapshot.getValue(Clase.class);
                        // agregamos usuarios a la lista
                        Clases.add(Clase);
                    }
                    //comprobamos el correo y asociamos a un id
                    for (int i = 0; i < Clases.size(); i++) {
                        Clase Clase = Clases.get(i);
                        if(Clase.getIdcurso().equals(idcurso)){
                            idestudiante = Clase.getIdestudiante();
                            fechaclase = Clase.getFechaclase();
                            modoclase = Clase.getModoclase();
                            asistenciaclase = Clase.getAsistenciaclase();

                            databaseReference.child("Estudiantes").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        Asistencias.clear();
                                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                            //obtenemos los usuarios de la consola de Firebase
                                            Estudiante Estudiante = snapshot.getValue(Estudiante.class);
                                            // agregamos usuarios a la lista
                                            Estudiantes.add(Estudiante);
                                        }
                                        //comprobamos el correo y asociamos a un id
                                        for (int i = 0; i < Estudiantes.size(); i++) {
                                            Estudiante Estudiante = Estudiantes.get(i);
                                            if(Estudiante.getIdestudiante().equals(idestudiante)){
                                                nombreestudiante = Estudiante.getNombreestudiante();
                                                apellidoestudiante = Estudiante.getApellidoestudiante();
                                                programaestudiante = Estudiante.getProgramaestudiante();
                                                //String idestudiante, String fechaclase, String nombreestudiante, String apellidoestudiante, String programaestudiante, String modoclase, String asistenciaclase
                                                Asistencia Asistencia = new Asistencia(idestudiante, fechaclase, nombreestudiante, apellidoestudiante, programaestudiante, modoclase, asistenciaclase);
                                                Asistencias.add(Asistencia);
                                            }
                                        }
                                        //Creamos un adaptador para mostrar los usuarios de la lista
                                        AsistenciaList UserAdapter = new AsistenciaList(ActivityListaEstudiantes.this, Asistencias);
                                        //Setiamos al adaptador el listado a desplegar
                                        listViewEstudiantesA.setAdapter(UserAdapter);
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
}