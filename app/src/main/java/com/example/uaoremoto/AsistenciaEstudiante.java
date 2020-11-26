package com.example.uaoremoto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;

public class AsistenciaEstudiante extends AppCompatActivity  implements View.OnClickListener{
    //Inicializar menu
    DrawerLayout drawerLayout;
    Button btnEscan, btnValidar;
    TextView txMensaje, texRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia_estudiante);

        //Menu
        drawerLayout = findViewById(R.id.drawer_layout);

        btnEscan = (Button) findViewById(R.id.btnEscanearQR);
        btnValidar = (Button) findViewById(R.id.btnRegistrarAsistencia);

        txMensaje = (TextView) findViewById(R.id.textViewMsgVali);
        texRegistro = (TextView) findViewById(R.id.textViewAsistQR);

        btnEscan.setOnClickListener(this);
        btnValidar.setOnClickListener(this);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {

            String scanContent = scanningResult.getContents();

            StringTokenizer t = new StringTokenizer(scanContent, "*");
            final String msj = t.nextToken();
            final String asis = t.nextToken();

            txMensaje.setText("" + msj);
            texRegistro.setText("" + asis);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "No se recibieron datos del escaneo", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btnEscanearQR){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
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
//        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
//            drawerLayout.closeDrawer(GravityCompat.START);
        //       }
    }

    public void ClickAutoMenu(View view){
        String email = getIntent().getStringExtra("email");
        String user = getIntent().getStringExtra("user");
        String idestudiante = getIntent().getStringExtra("idestudiante");
        Intent i = new Intent(AsistenciaEstudiante.this, AutoevaluacionEstudiantes.class);
        i.putExtra("email", email);
        i.putExtra("user", user);
        i.putExtra("idestudiante", idestudiante);
        //closeDrawer(drawerLayout);
        startActivity(i);
    }

    public void ClickCursosMenu(View view){
        String email = getIntent().getStringExtra("email");
        String user = getIntent().getStringExtra("user");
        String idestudiante = getIntent().getStringExtra("idestudiante");
        Intent i = new Intent(AsistenciaEstudiante.this, MisCursosEstudiante.class);
        i.putExtra("email", email);
        i.putExtra("user", user);
        i.putExtra("idestudiante", idestudiante);
        //closeDrawer(drawerLayout);
        startActivity(i);
    }

    public void ClickSalir(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(AsistenciaEstudiante.this);
        builder.setTitle("Salir");
        builder.setMessage("¿Deseas salir de UAO Remoto?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AsistenciaEstudiante.this.finishAffinity();
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