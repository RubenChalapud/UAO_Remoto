package com.example.uaoremoto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public Button btnEstudiante, btnDocente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEstudiante =(Button) findViewById(R.id.btnInicioEst);
        btnDocente =(Button) findViewById(R.id.btnInicioDoc);

        btnDocente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginDocente.class);
                startActivity(i);
            }
        });

        btnEstudiante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginEstudiante.class);
                startActivity(i);
            }
        });
    }
}