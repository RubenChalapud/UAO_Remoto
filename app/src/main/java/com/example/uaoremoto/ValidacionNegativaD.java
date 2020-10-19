package com.example.uaoremoto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ValidacionNegativaD extends AppCompatActivity {
    private Button btnIrInicio, btnNotifFac, btnNotifEst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validacion_negativa_d);

        btnIrInicio = (Button) findViewById(R.id.btnIrInicioND);
        btnNotifEst = (Button) findViewById(R.id.btnNotiEst);
        btnNotifFac = (Button) findViewById(R.id.btnNotiFac);

        btnIrInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( ValidacionNegativaD.this, InicioDocente.class);
                startActivity(i);
            }
        });

        btnNotifEst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ValidacionNegativaD.this, "Se envió email de notificación a Estudiantes relacionados con tus cursos.", Toast.LENGTH_LONG).show();
            }
        });

        btnNotifFac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ValidacionNegativaD.this, "Se envió email de notificación a la Facultad correspondiente.", Toast.LENGTH_LONG).show();
            }
        });
    }
}