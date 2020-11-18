package com.example.uaoremoto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InicioDocente extends AppCompatActivity {
    public static final String user="names";
    public static final String email="email";
    TextView txtUser;
    TextView txtCorreo;

    Button btnRealizarAutoE;

    //Inicializar menu
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_docente);

        //Boton para evaluar covid
        btnRealizarAutoE = (Button) findViewById(R.id.btnRealizarEvaluacion);

        txtUser =(TextView)findViewById(R.id.textViewNombreD);
        String user = getIntent().getStringExtra("names");
        txtUser.setText(user);

        txtCorreo = (TextView) findViewById(R.id.textViewCorreoD);
        final String email = getIntent().getStringExtra("email");
        txtCorreo.setText(email);

        //Menu
        drawerLayout = findViewById(R.id.drawer_layout);

        //Ir a evaluar covid
        btnRealizarAutoE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(InicioDocente.this, AutoevaluacionDocente.class);
                i.putExtra("email", email);
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
        closeDrawer(drawerLayout);
    }

    private static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickAutoMenu(View view){
        String email = getIntent().getStringExtra("email");
        Intent i = new Intent(InicioDocente.this, AutoevaluacionDocente.class);
        i.putExtra("email", email);
        startActivity(i);
    }

    public void ClickCursosMenu(View view){
        String email = getIntent().getStringExtra("email");
        Intent i = new Intent(InicioDocente.this, MisCursosDocente.class);
        i.putExtra("email", email);
        startActivity(i);
    }

    public void ClickSalir(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(InicioDocente.this);
        builder.setTitle("Salir");
        builder.setMessage("¿Deseas salir de UAO Remoto?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                InicioDocente.this.finishAffinity();
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