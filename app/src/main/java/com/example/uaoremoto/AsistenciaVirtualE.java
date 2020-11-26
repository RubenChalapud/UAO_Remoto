package com.example.uaoremoto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class AsistenciaVirtualE extends AppCompatActivity{
    private TextView textNombre, textViewAsist;
    Button btnValidar, btnIrStream;
    String email, user, idestudiante, idclase;

    //Inicializar menu
    DrawerLayout drawerLayout;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia_virtual_e);

        //Menu
        drawerLayout = findViewById(R.id.drawer_layout);

        //  referenciamos datos de firebase
        databaseReference = FirebaseDatabase.getInstance().getReference();

        textNombre =(TextView)findViewById(R.id.textViewNombreCurso);
        textViewAsist = (TextView) findViewById(R.id.textViewAsistVir);

        btnValidar = (Button) findViewById(R.id.btnValAsisVir);
        btnIrStream = (Button) findViewById(R.id.btnIrStream);

        final String nombrecurso = getIntent().getStringExtra("nombrecurso");
        textNombre.setText(nombrecurso);
        email = getIntent().getStringExtra("email");
        user = getIntent().getStringExtra("user");
        idestudiante = getIntent().getStringExtra("idestudiante");
        idclase = getIntent().getStringExtra("idclase");


        btnValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActualizarDatos(idclase);
            }
        });

        btnIrStream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AsistenciaVirtualE.this, PlayerActivity.class);
                i.putExtra("email", email);
                i.putExtra("user", user);
                i.putExtra("idestudiante", idestudiante);
                startActivity(i);
            }
        });

    }

    private void ActualizarDatos(final String idclase) {
        final Map<String, Object> estMap = new HashMap<>();
        estMap.put("modoclase", "Virtual");

        final Map<String, Object> est2Map = new HashMap<>();
        est2Map.put("asistenciaclase", "Si");

        databaseReference.child("Clases").child(idclase).updateChildren(estMap);
        databaseReference.child("Clases").child(idclase).updateChildren(est2Map);

        textViewAsist.setText("Se validó asistencia virtual");
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
        Intent i = new Intent(AsistenciaVirtualE.this, AutoevaluacionEstudiantes.class);
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
        Intent i = new Intent(AsistenciaVirtualE.this, MisCursosEstudiante.class);
        i.putExtra("email", email);
        i.putExtra("user", user);
        i.putExtra("idestudiante", idestudiante);
        //    closeDrawer(drawerLayout);
        startActivity(i);
    }

    public void ClickSalir(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(AsistenciaVirtualE.this);
        builder.setTitle("Salir");
        builder.setMessage("¿Deseas salir de UAO Remoto?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AsistenciaVirtualE.this.finishAffinity();
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