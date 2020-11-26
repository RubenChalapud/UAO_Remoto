package com.example.uaoremoto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.BitSet;

public class VistaCursoE extends AppCompatActivity {
    private TextView textNombre, textAula, TextHorario, TextNumEst;
    private Button btnListarEstudiantes, btnUbicacion, btnAsistencia, btnAsistirVirtual;

    String lat, lon;
    double latitud = 0;
    double longitud = 0;
    DatabaseReference databaseReference;


    //Inicializar menu
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_curso_e);

        //Menu
        drawerLayout = findViewById(R.id.drawer_layout);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        //Boton Listar estudiantes
        btnListarEstudiantes = (Button) findViewById(R.id.btnListaEstudiantes);
        btnUbicacion = (Button) findViewById(R.id.btnUbicacionAula);
        btnAsistencia = (Button) findViewById(R.id.btnRegAsistencia);
        btnAsistirVirtual = (Button) findViewById(R.id.btnAsistirVirtualCurso);

        textNombre =(TextView)findViewById(R.id.textViewNombreCurso);
        final String nombrecurso = getIntent().getStringExtra("nombrecurso");
        textNombre.setText(nombrecurso);

        TextHorario = (TextView) findViewById(R.id.tvHorario);
        String horariocurso = getIntent().getStringExtra("horariocurso");
        TextHorario.setText(horariocurso);

        TextNumEst = (TextView) findViewById(R.id.tvNumEst);
        String numeroestudiantes = getIntent().getStringExtra("numeroestudiantes");
        TextNumEst.setText(numeroestudiantes+" estudiantes");

        textAula = (TextView) findViewById(R.id.tvAula);
        final String idaulac = getIntent().getStringExtra("idaulac");
        textAula.setText(idaulac);

        final String idcurso = getIntent().getStringExtra("idcurso");

        final String idestu = getIntent().getStringExtra("idestudiante");

        final String idclase = getIntent().getStringExtra("idclase");

        btnListarEstudiantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(VistaCursoE.this, ActivityListaEstudiantes.class);
                i.putExtra("idcurso", idcurso);
                i.putExtra("nombrecurso", nombrecurso);
                startActivity(i);
            }
        });

        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                traerDatosAulaEnviar(idaulac);
            }
        });

        btnAsistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(VistaCursoE.this, AsistenciaEstudiante.class);
                i.putExtra("idcurso", idcurso);
                i.putExtra("idclase", idclase);
                i.putExtra("idestudiante", idestu);
                i.putExtra("nombrecurso", nombrecurso);
                startActivity(i);
            }
        });

        btnAsistirVirtual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(VistaCursoE.this, AsistenciaVirtualE.class);
                i.putExtra("idcurso", idcurso);
                i.putExtra("idclase", idclase);
                i.putExtra("idestudiante", idestu);
                i.putExtra("nombrecurso", nombrecurso);
                startActivity(i);
            }
        });
    }

    private void traerDatosAulaEnviar(final String idaula) {
        databaseReference.child("Aulas").child(idaula).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    lat = dataSnapshot.child("latitud").getValue(String.class);
                    lon = dataSnapshot.child("longitud").getValue(String.class);
                    Intent i = new Intent(VistaCursoE.this, UbicacionAula.class);
                    i.putExtra("idaula", idaula);
                    i.putExtra("latitud", lat);
                    i.putExtra("longitud", lon);
                    Log.i("latitudV", ""+lat);
                    Log.i("longitudV", ""+lon);
                    startActivity(i);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
//        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
//            drawerLayout.closeDrawer(GravityCompat.START);
//        }
    }

    public void ClickAutoMenu(View view){
        String email = getIntent().getStringExtra("email");
        String user = getIntent().getStringExtra("user");
        String idestudiante = getIntent().getStringExtra("idestudiante");
        Intent i = new Intent(VistaCursoE.this, AutoevaluacionEstudiantes.class);
        i.putExtra("email", email);
        i.putExtra("user", user);
        i.putExtra("idestudiante", idestudiante);
 //       closeDrawer(drawerLayout);
        startActivity(i);
    }

    public void ClickCursosMenu(View view){
        String email = getIntent().getStringExtra("email");
        String user = getIntent().getStringExtra("user");
        String idestudiante = getIntent().getStringExtra("idestudiante");
        Intent i = new Intent(VistaCursoE.this, MisCursosEstudiante.class);
        i.putExtra("email", email);
        i.putExtra("user", user);
        i.putExtra("idestudiante", idestudiante);
    //    closeDrawer(drawerLayout);
        startActivity(i);
    }

    public void ClickSalir(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(VistaCursoE.this);
        builder.setTitle("Salir");
        builder.setMessage("¿Deseas salir de UAO Remoto?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                VistaCursoE.this.finishAffinity();
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
//        closeDrawer(drawerLayout);
    }
}