package com.example.uaoremoto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class MisCursosEstudiante extends AppCompatActivity {
    //Inicializar menu
    DrawerLayout drawerLayout;

    String email, user, idestudiante;

    //LinearLayout que contiene los botones
    LinearLayout botonesCursos;

    //Prueba
    ListView listViewUsers;

    //lista que almacena todos los usuarios de la base de datos de Firebase
    List<Clase> Clases;
    //lista que almacena todos los usuarios de la base de datos de Firebase
    List<Estudiante> Estudiantes;
    //lista que almacena todos los usuarios de la base de datos de Firebase
    List<Curso> Cursos;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_cursos_estudiante);

        //Obtener el correo del Docente
        email = getIntent().getStringExtra("email");
        user = getIntent().getStringExtra("user");
        idestudiante = getIntent().getStringExtra("idestudiante");

        //Llamar a LinearLayout que contiene botones
        botonesCursos = (LinearLayout) findViewById(R.id.CursosDBotones);

        //prueba
        //  referenciamos datos de firebase
        databaseReference = FirebaseDatabase.getInstance().getReference();

        encontrarClasesconId(idestudiante);

        //Menu
        drawerLayout = findViewById(R.id.drawer_layout);
    }

    private void encontrarClasesconId(final String idest) {
        // listado de objetos almacenados (usuarios creados)
        Clases = new ArrayList<>();
        final String ide = idest;

        databaseReference.child("Clases").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Borramos la lista previa de Cursos
                Clases.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        //obtenemos los usuarios de la consola de Firebase
                        Clase Clase = snapshot.getValue(Clase.class);
                        // agregamos usuarios a la lista
                        Clases.add(Clase);
                    }

                    for (int i = 0; i < Clases.size(); i++) {
                        Clase Clase = Clases.get(i);
                        if(Clase.getIdestudiante().equals(ide)){
                            String idcurso = Clase.getIdcurso();
                            encontrarCursoconId(idcurso, ide, Clase.getIdclase());
                        }
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void encontrarCursoconId(String idcurso, final String idest, final String idclase) {
        // listado de objetos almacenados (usuarios creados)
        Cursos = new ArrayList<>();
        final String idc = idcurso;

        //Arraylist para la creacion de los botones de cursos
        final ArrayList<MisCursosEstudiante.boton> cursos = new ArrayList<MisCursosEstudiante.boton>();

        databaseReference.child("Cursos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Borramos la lista previa de Cursos
                Cursos.clear();
                cursos.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        //obtenemos los usuarios de la consola de Firebase
                        Curso Curso = snapshot.getValue(Curso.class);
                        // agregamos usuarios a la lista
                        Cursos.add(Curso);
                    }

                    for (int i = 0; i < Cursos.size(); i++) {
                        Curso Curso = Cursos.get(i);
                        if(Curso.getIdcurso().equals(idc)){
                            cursos.add(new MisCursosEstudiante.boton(Curso.getIdcurso(), Curso.getNombrecurso(), Curso.getNumestudiantes(), Curso.getHorariocurso(), Curso.getIdprofesor(), Curso.getIdaula(), idest));
                        }else{
                            Toast.makeText(getApplicationContext(), "El estudiante no tiene cursos asociados", Toast.LENGTH_SHORT).show();
                        }
                    }

                    //recorremos Arraylist para asignar los botones a cada curso
                    for (MisCursosEstudiante.boton c:cursos){
                        final String ncurso = c.nombreCurso;
                        final String idcur = c.cod;
                        final String numest = c.numestudiantes;
                        final String horario = c.horariocurso;
                        final String idpro = c.idprofesor;
                        final String idaul = c.idaula;
                        final String idest = c.idestudiante;
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
                                Intent i = new Intent(MisCursosEstudiante.this, VistaCursoE.class);
                                i.putExtra("idcurso", idcur);
                                i.putExtra("nombrecurso", ncurso);
                                i.putExtra("numeroestudiantes", numest);
                                i.putExtra("horariocurso", horario);
                                i.putExtra("idprofesorc", idpro);
                                i.putExtra("idaulac", idaul);
                                i.putExtra("idestudiante", idest);
                                i.putExtra("idclase", idclase);
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
        public  String idestudiante;

        public boton(String cod, String nombreCurso, String numestudiantes, String horariocurso, String idprofesor, String idaula, String idestudiante){
            this.cod = cod;
            this.nombreCurso = nombreCurso;
            this.numestudiantes = numestudiantes;
            this.horariocurso = horariocurso;
            this.idprofesor = idprofesor;
            this.idaula = idaula;
            this.idestudiante = idestudiante;
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
        closeDrawer(drawerLayout);
    }

    private static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickAutoMenu(View view){
        String email = getIntent().getStringExtra("email");
        String user = getIntent().getStringExtra("user");
        String idestudiante = getIntent().getStringExtra("idestudiante");
        Intent i = new Intent(MisCursosEstudiante.this, AutoevaluacionEstudiantes.class);
        i.putExtra("email", email);
        i.putExtra("user", user);
        i.putExtra("idestudiante", idestudiante);
        startActivity(i);
    }

    public void ClickCursosMenu(View view){
        String email = getIntent().getStringExtra("email");
        String user = getIntent().getStringExtra("user");
        String idestudiante = getIntent().getStringExtra("idestudiante");
        Intent i = new Intent(MisCursosEstudiante.this, MisCursosEstudiante.class);
        i.putExtra("email", email);
        i.putExtra("user", user);
        i.putExtra("idestudiante", idestudiante);
        startActivity(i);
    }

    public void ClickSalir(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(MisCursosEstudiante.this);
        builder.setTitle("Salir");
        builder.setMessage("¿Deseas salir de UAO Remoto?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MisCursosEstudiante.this.finishAffinity();
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