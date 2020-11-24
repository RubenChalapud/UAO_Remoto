package com.example.uaoremoto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ValidacionPositivaD extends AppCompatActivity {
    private TextView msj;
    private Button btnIrInicioD;
    String email, idprofesor, user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validacion_positiva_d);

        msj = (TextView) findViewById(R.id.tVMsjValD);
        String mesj = getIntent().getStringExtra("mensaje");
        msj.setText(mesj);

        btnIrInicioD = (Button) findViewById(R.id.btnIrInicioD);
        email = getIntent().getStringExtra("email");
        user = getIntent().getStringExtra("user");
        idprofesor = getIntent().getStringExtra("idprofesor");

        btnIrInicioD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( ValidacionPositivaD.this, InicioDocente.class);
                i.putExtra("email", email);
                i.putExtra("user", user);
                i.putExtra("idprofesor", idprofesor);
                startActivity(i);
            }
        });
    }
}