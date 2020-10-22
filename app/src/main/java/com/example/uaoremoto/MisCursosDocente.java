package com.example.uaoremoto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.util.ArrayList;

public class MisCursosDocente extends AppCompatActivity {
    //Inicializar menu
    DrawerLayout drawerLayout;

    //Cursos del Docente (Realizar la busuqeda en la base de datos para determinar el numero de cursos)
    static int numCursos = 4;
    //LinearLayout que contiene los botones
    LinearLayout botonesCursos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_cursos_docente);

        //Obtener el correo del Docente
        String email = getIntent().getStringExtra("correo");

        //Llamar a LinearLayout que contiene botones
        botonesCursos = (LinearLayout) findViewById(R.id.CursosDBotones);

        //Arraylist para la creacion de los botones de cursos
        ArrayList<boton> cursos = new ArrayList<boton>();
        cursos.add(new boton(1, "Calculo"));
        cursos.add(new boton(2, "Fisica"));
        cursos.add(new boton(3, "Algebra"));
        cursos.add(new boton(4, "Ecuaciones"));
        cursos.add(new boton(5, "Calculo 2"));

        //recorremos Arraylist para asignar los botones a cada curso
        for (boton c:cursos){
            final String ncurso = c.nombreCurso;
            Button btn = new Button(getApplicationContext());
            btn.setText(c.nombreCurso);
            btn.setId(c.cod);
            btn.setTextColor(Color.BLACK);
            botonesCursos.addView(btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Curso:" + ncurso, Toast.LENGTH_SHORT).show();
                }
            });
        }




        //Menu
        drawerLayout = findViewById(R.id.drawer_layout);
    }

    //Botones para el array list
    class boton{
        public int cod;
        public String nombreCurso;

        public boton(int cod, String nombreCurso){
            this.cod = cod;
            this.nombreCurso = nombreCurso;
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