package com.example.uaoremoto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.util.ArrayList;
import java.util.List;


public class MisCursosDocente extends AppCompatActivity {
    //Inicializar menu
    DrawerLayout drawerLayout;

    //LinearLayout que contiene los botones
    LinearLayout botonesCursos;

    //Prueba
    ListView listViewUsers;

    //lista que almacena todos los usuarios de la base de datos de Firebase
    List<Curso> Cursos;
    //lista que almacena todos los usuarios de la base de datos de Firebase
    List<Profesor> Profesores;

    DatabaseReference databaseReference;
    DatabaseReference dbCursos;
    DatabaseReference dbProfesores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_cursos_docente);

        //Obtener el correo del Docente
        String email = getIntent().getStringExtra("correo");

        //Llamar a LinearLayout que contiene botones
        botonesCursos = (LinearLayout) findViewById(R.id.CursosDBotones);

        //prueba
        //  referenciamos datos de firebase
        databaseReference = FirebaseDatabase.getInstance().getReference();
        dbCursos = FirebaseDatabase.getInstance().getReference("Cursos");
        dbProfesores = FirebaseDatabase.getInstance().getReference("Profesores");

        econtrarIdProfesor(email);

        //Menu
        drawerLayout = findViewById(R.id.drawer_layout);
    }

    private void econtrarIdProfesor(String email) {
        // listado de objetos almacenados (usuarios creados)
        Profesores = new ArrayList<>();
        final String ema = email;

        databaseReference.child("Profesores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Borramos la lista previa
                Profesores.clear();

                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        //Log.e("Profesores: ", ""+ snapshot.getValue());
                        //obtenemos los usuarios de la consola de Firebase
                        Profesor Profesor = snapshot.getValue(Profesor.class);
                        // agregamos usuarios a la lista
                        Profesores.add(Profesor);
                    }
                    //comprobamos el correo y asociamos a un id
                    for (int i = 0; i < Profesores.size(); i++) {
                        Profesor Profesor = Profesores.get(i);
                        if(Profesor.getCorreoprofesor().equals(ema)){
                            System.out.println(Profesor.getIdprofesor());
                            String idpro = Profesor.getIdprofesor();
                            encontrarCursosconId(idpro);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void encontrarCursosconId(String idpro) {
        // listado de objetos almacenados (usuarios creados)
        Cursos = new ArrayList<>();
        final String idp = idpro;
        //Arraylist para la creacion de los botones de cursos
        final ArrayList<boton> cursos = new ArrayList<boton>();

        databaseReference.child("Cursos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Borramos la lista previa de Cursos
                Cursos.clear();
                cursos.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        //Log.e("Profesores: ", ""+ snapshot.getValue());
                        //obtenemos los usuarios de la consola de Firebase
                        Curso Curso = snapshot.getValue(Curso.class);
                        // agregamos usuarios a la lista
                        Cursos.add(Curso);
                    }

                    for (int i = 0; i < Cursos.size(); i++) {
                        Curso Curso = Cursos.get(i);
                        if(Curso.getIdprofesor().equals(idp)){
                            cursos.add(new boton(Curso.getIdcurso(), Curso.getNombrecurso(), Curso.getNumestudiantes(), Curso.getHorariocurso(), Curso.getIdprofesor(), Curso.getIdaula()));
                        }else{
                            Toast.makeText(getApplicationContext(), "El profesor no tiene cursos asociados", Toast.LENGTH_SHORT).show();
                        }
                    }

                    //recorremos Arraylist para asignar los botones a cada curso
                    for (boton c:cursos){
                        final String ncurso = c.nombreCurso;
                        final String idcur = c.cod;
                        final String numest = c.numestudiantes;
                        final String horario = c.horariocurso;
                        final String idpro = c.idprofesor;
                        final String idaul = c.idaula;
                        Button btn = new Button(getApplicationContext());
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(1000, 150);
                        params.setMargins(0, 30, 0, 0);//left, top, right, bottom
                        btn.setLayoutParams(params);
                        btn.setText(c.nombreCurso);
                        btn.setTextColor(Color.WHITE);
                        btn.setBackgroundColor(Color.rgb(96,108,129));
                        botonesCursos.addView(btn);
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getApplicationContext(), "Curso: " + ncurso, Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(MisCursosDocente.this, VistaCurso.class);
                                i.putExtra("idcurso", idcur);
                                i.putExtra("nombrecurso", ncurso);
                                i.putExtra("numeroestudiantes", numest);
                                i.putExtra("horariocurso", horario);
                                i.putExtra("idprofesorc", idpro);
                                i.putExtra("idaulac", idaul);
                                startActivity(i);
                            }
                        });
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    //Botones para el array list
    class boton{
        public String cod;
        public String nombreCurso;
        public String numestudiantes;
        public String horariocurso;
        public String idprofesor;
        public String idaula;

        public boton(String cod, String nombreCurso, String numestudiantes, String horariocurso, String idprofesor, String idaula){
            this.cod = cod;
            this.nombreCurso = nombreCurso;
            this.numestudiantes = numestudiantes;
            this.horariocurso = horariocurso;
            this.idprofesor = idprofesor;
            this.idaula = idaula;
        }
    }


    //Metodos para Menu
    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    private static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickInicio(View view){
        Intent i = new Intent(MisCursosDocente.this, InicioDocente.class);
        startActivity(i);
    }

    private static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickAutoMenu(View view){
        Intent i = new Intent(MisCursosDocente.this, AutoevaluacionDocente.class);
        startActivity(i);
    }

    public void ClickCursosMenu(View view){
        closeDrawer(drawerLayout);
    }

    public void ClickSalir(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(MisCursosDocente.this);
        builder.setTitle("Salir");
        builder.setMessage("¿Deseas salir de UAO Remoto?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MisCursosDocente.this.finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}