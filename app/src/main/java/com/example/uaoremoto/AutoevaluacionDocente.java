package com.example.uaoremoto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class AutoevaluacionDocente extends AppCompatActivity {
    private CheckBox c1, c2, c3, c4, c5, c6, c7, c8, cns;
    private Button btnValidar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autoevaluacion_docente);

        btnValidar = (Button) findViewById(R.id.btnValidar);

        c1 = (CheckBox) findViewById(R.id.cBoxSin1);
        c2 = (CheckBox) findViewById(R.id.cBoxSin2);
        c3 = (CheckBox) findViewById(R.id.cBoxSin3);
        c4 = (CheckBox) findViewById(R.id.cBoxSin4);
        c5 = (CheckBox) findViewById(R.id.cBoxSin5);
        c6 = (CheckBox) findViewById(R.id.cBoxSin6);
        c7 = (CheckBox) findViewById(R.id.cBoxSin7);
        c8 = (CheckBox) findViewById(R.id.cBoxSin8);
        cns = (CheckBox) findViewById(R.id.cBoxNoSintoma);

        cns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprobar();
            }
        });

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cns.setChecked(false);
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cns.setChecked(false);
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cns.setChecked(false);
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cns.setChecked(false);
            }
        });
        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cns.setChecked(false);
            }
        });
        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cns.setChecked(false);
            }
        });
        c7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cns.setChecked(false);
            }
        });
        c8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cns.setChecked(false);
            }
        });
    }

    private void comprobar() {
        if(cns.isChecked()){
            c1.setChecked(false);
            c2.setChecked(false);
            c3.setChecked(false);
            c4.setChecked(false);
            c5.setChecked(false);
            c6.setChecked(false);
            c7.setChecked(false);
            c8.setChecked(false);
            Toast.makeText(AutoevaluacionDocente.this, "No sintomas", Toast.LENGTH_LONG).show();
        }
    }

    public void onclick(View view){
        if (view.getId()==R.id.btnValidar){
            validar();
        }
    }

    private void validar(){
        String mensaje;
        if(cns.isChecked()==true){
            mensaje = "No presentas síntomas relacionados a COVID-19. Recuerda:";
            Intent i = new Intent(AutoevaluacionDocente.this, ValidacionPositivaD.class);
            i.putExtra("mensaje", mensaje);
            startActivity(i);
        }
        else if(c3.isChecked()==true && c1.isChecked()==false && c2.isChecked()==false && c4.isChecked()==false && c5.isChecked()==false && c6.isChecked()==false && c7.isChecked()==false){
            mensaje = "Presentas síntomas leves relacionados a COVID-19, como congestión nasal o dolor muscular. Tienes permitido ir al Campus UAO, si los sintomas empeoran, debes notificar y no asistir. Recuerda:";
            Intent i = new Intent(AutoevaluacionDocente.this, ValidacionPositivaD.class);
            i.putExtra("mensaje", mensaje);
            startActivity(i);
        }
        else if(c8.isChecked()==true && c1.isChecked()==false && c2.isChecked()==false && c4.isChecked()==false && c5.isChecked()==false && c6.isChecked()==false && c7.isChecked()==false){
            mensaje = "Presentas síntomas leves relacionados a COVID-19, como congestión nasal o dolor muscular. Tienes permitido ir al Campus UAO, si los sintomas empeoran, debes notificar y no asistir. Recuerda:";
            Intent i = new Intent(AutoevaluacionDocente.this, ValidacionPositivaD.class);
            i.putExtra("mensaje", mensaje);
            startActivity(i);
        }
        else if (c1.isChecked()==false && c2.isChecked()==false && c3.isChecked()==false && c4.isChecked()==false && c5.isChecked()==false && c6.isChecked()==false && c7.isChecked()==false && c8.isChecked()==false && cns.isChecked()==false){
            Toast.makeText(AutoevaluacionDocente.this, "Seleccione los síntomas que presenta para continuar.", Toast.LENGTH_LONG).show();
        }else{
            Intent i = new Intent(AutoevaluacionDocente.this, ValidacionNegativaD.class);
            startActivity(i);
        }
    }
}