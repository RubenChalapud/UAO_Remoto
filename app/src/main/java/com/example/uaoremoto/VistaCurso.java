package com.example.uaoremoto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class VistaCurso extends AppCompatActivity {
    private TextView textNombre, textAula, TextHorario, TextNumEst;
    private Button btnListarEstudiantes;

    //Inicializar menu
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_curso);

        //Menu
        drawerLayout = findViewById(R.id.drawer_layout);

        //Boton Listar estudiantes
        btnListarEstudiantes = (Button) findViewById(R.id.btnListaEstudiantes);

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
        String idaulac = getIntent().getStringExtra("idaulac");
        textAula.setText(idaulac);

        final String idcurso = getIntent().getStringExtra("idcurso");

        btnListarEstudiantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(VistaCurso.this, ActivityListaEstudiantes.class);
                i.putExtra("idcurso", idcurso);
                i.putExtra("nombrecurso", nombrecurso);
                startActivity(i);
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
        Intent i = new Intent(VistaCurso.this, InicioDocente.class);
        startActivity(i);
    }

    private static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickAutoMenu(View view){
        Intent i = new Intent(VistaCurso.this, AutoevaluacionDocente.class);
        startActivity(i);
    }

    public void ClickCursosMenu(View view){
        closeDrawer(drawerLayout);
    }

    public void ClickSalir(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(VistaCurso.this);
        builder.setTitle("Salir");
        builder.setMessage("¿Deseas salir de UAO Remoto?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                VistaCurso.this.finishAffinity();
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