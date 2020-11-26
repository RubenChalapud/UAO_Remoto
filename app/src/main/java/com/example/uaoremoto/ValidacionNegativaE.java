package com.example.uaoremoto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ValidacionNegativaE extends AppCompatActivity {
    Button btnAsistirVirtual, btnIrInicioNE;
    String email, idestudiante, user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validacion_negativa_e);

        btnAsistirVirtual = (Button) findViewById(R.id.btnAsistirVirtual);
        btnIrInicioNE = (Button) findViewById(R.id.btnIrInicioNE);

        email = getIntent().getStringExtra("email");
        idestudiante = getIntent().getStringExtra("idestudiante");
        user = getIntent().getStringExtra("user");

        btnIrInicioNE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( ValidacionNegativaE.this, InicioEstudiante.class);
                i.putExtra("email", email);
                i.putExtra("user", user);
                i.putExtra("idestudiante", idestudiante);
                startActivity(i);
            }
        });

        btnAsistirVirtual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( ValidacionNegativaE.this, MisCursosEstudiante.class);
                i.putExtra("email", email);
                i.putExtra("user", user);
                i.putExtra("idestudiante", idestudiante);
                startActivity(i);
            }
        });
    }
}