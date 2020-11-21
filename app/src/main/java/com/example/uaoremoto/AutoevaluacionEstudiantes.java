package com.example.uaoremoto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AutoevaluacionEstudiantes extends AppCompatActivity {
    private CheckBox c1, c2, c3, c4, c5, c6, c7, c8, cns;
    private Button btnValidar;
    public String email;
    DatabaseReference databaseReference;
    //lista que almacena todos los usuarios clase de la base de datos de Firebase
    List<Estudiante> Estudiantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autoevaluacion_estudiantes);

        btnValidar = (Button) findViewById(R.id.btnValidar);
        email = getIntent().getStringExtra("email");

        //  referenciamos datos de firebase
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Estudiantes = new ArrayList<Estudiante>();


        c1 = (CheckBox) findViewById(R.id.cBoxSin1);
        c2 = (CheckBox) findViewById(R.id.cBoxSin2);
        c3 = (CheckBox) findViewById(R.id.cBoxSin3);
        c4 = (CheckBox) findViewById(R.id.cBoxSin4);
        c5 = (CheckBox) findViewById(R.id.cBoxSin5);
        c6 = (CheckBox) findViewById(R.id.cBoxSin6);
        c7 = (CheckBox) findViewById(R.id.cBoxSin7);
        c8 = (CheckBox) findViewById(R.id.cBoxSin8);
        cns = (CheckBox) findViewById(R.id.cBoxNoSintoma);

        cns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprobar();
            }
        });

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cns.setChecked(false);
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cns.setChecked(false);
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cns.setChecked(false);
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cns.setChecked(false);
            }
        });
        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cns.setChecked(false);
            }
        });
        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cns.setChecked(false);
            }
        });
        c7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cns.setChecked(false);
            }
        });
        c8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cns.setChecked(false);
            }
        });
    }

    private void comprobar() {
        if(cns.isChecked()){
            c1.setChecked(false);
            c2.setChecked(false);
            c3.setChecked(false);
            c4.setChecked(false);
            c5.setChecked(false);
            c6.setChecked(false);
            c7.setChecked(false);
            c8.setChecked(false);
            Toast.makeText(AutoevaluacionEstudiantes.this, "No sintomas", Toast.LENGTH_LONG).show();
        }
    }

    public void onclick(View view){
        if (view.getId()==R.id.btnValidar){
            validar();
        }
    }

    private void validar(){
        String mensaje;
        String sintomasestudiante = null;
        if(cns.isChecked()==true){
            mensaje = "No presentas síntomas relacionados a COVID-19. Recuerda:";
            sintomasestudiante = "No";
            Intent i = new Intent(AutoevaluacionEstudiantes.this, ValidacionPositivaE.class);
            i.putExtra("email", email);
            i.putExtra("mensaje", mensaje);
            startActivity(i);
        }
        else if(c3.isChecked()==true && c1.isChecked()==false && c2.isChecked()==false && c4.isChecked()==false && c5.isChecked()==false && c6.isChecked()==false && c7.isChecked()==false){
            mensaje = "Presentas síntomas leves relacionados a COVID-19, como congestión nasal o dolor muscular. Tienes permitido ir al Campus UAO, si los sintomas empeoran, debes notificar y no asistir. Recuerda:";
            sintomasestudiante = "No";
            Intent i = new Intent(AutoevaluacionEstudiantes.this, ValidacionPositivaE.class);
            i.putExtra("email", email);
            i.putExtra("mensaje", mensaje);
            startActivity(i);
        }
        else if(c8.isChecked()==true && c1.isChecked()==false && c2.isChecked()==false && c4.isChecked()==false && c5.isChecked()==false && c6.isChecked()==false && c7.isChecked()==false){
            mensaje = "Presentas síntomas leves relacionados a COVID-19, como congestión nasal o dolor muscular. Tienes permitido ir al Campus UAO, si los sintomas empeoran, debes notificar y no asistir. Recuerda:";
            sintomasestudiante = "No";
            Intent i = new Intent(AutoevaluacionEstudiantes.this, ValidacionPositivaE.class);
            i.putExtra("email", email);
            i.putExtra("mensaje", mensaje);
            startActivity(i);
        }
        else if (c1.isChecked()==false && c2.isChecked()==false && c3.isChecked()==false && c4.isChecked()==false && c5.isChecked()==false && c6.isChecked()==false && c7.isChecked()==false && c8.isChecked()==false && cns.isChecked()==false){
            Toast.makeText(AutoevaluacionEstudiantes.this, "Seleccione los síntomas que presenta para continuar.", Toast.LENGTH_LONG).show();
        }else{
            sintomasestudiante = "Si";
            Intent i = new Intent(AutoevaluacionEstudiantes.this, ValidacionNegativaE.class);
            i.putExtra("email", email);
            startActivity(i);
        }
        SintomasEstudiante(sintomasestudiante);
        //SsintomasEstudiante(sintomasestudiante);
    }

    private void SsintomasEstudiante(String sintomasestudiante) {
        databaseReference.child("Estudiantes").orderByChild("correoestudiante");
        String key = databaseReference.child("Estudiantes").child("correoestudiante").child(email).push().getKey();
        databaseReference.child("Estudiantes").child(key).child("sintomasestudiante").setValue(sintomasestudiante);
    }

    private void SintomasEstudiante(final String sintomasestudiante) {

        databaseReference.child("Estudiantes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Borramos la lista previa
                Estudiantes.clear();
                String ide = null;
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        //obtenemos los usuarios de la consola de Firebase
                        Estudiante Estudiante = snapshot.getValue(Estudiante.class);
                        // agregamos usuarios a la lista
                        Estudiantes.add(Estudiante);
                    }
                    //comprobamos el correo de docente asociado para comprobar
                    for (int i = 0; i < Estudiantes.size(); i++) {
                        Estudiante Estudiante = Estudiantes.get(i);
                        if(Estudiante.getCorreoestudiante().equals(email)){
                            ide = Estudiante.getIdestudiante();
                        }
                    }
                    //databaseReference.child("Estudiantes").child("correoestudiante").child(email).getKey();
                    databaseReference.child("Estudiantes").child(ide).child("sintomasestudiante").setValue(sintomasestudiante);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

}