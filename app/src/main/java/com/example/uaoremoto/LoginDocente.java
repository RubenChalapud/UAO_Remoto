package com.example.uaoremoto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginDocente extends AppCompatActivity implements View.OnClickListener {
    private EditText textCorreo, textContra;
    private Button btnIngresar;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_docente);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        textCorreo = (EditText) findViewById(R.id.editTextLogD);
        textContra = (EditText) findViewById(R.id.editTextPassD);

        btnIngresar = (Button) findViewById(R.id.btnLogD);

        progressDialog = new ProgressDialog(this);

        btnIngresar.setOnClickListener(this);
    }

    private void loguearUsuario() {
        //Obtenemos el email y la contraseña desde las cajas de texto
        final String email = textCorreo.getText().toString().trim();
        String password = textContra.getText().toString().trim();


        //Verificamos que las cajas de texto no esten vacías
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Realizando consulta en linea...");
        progressDialog.show();



        //loguear usuario
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            databaseReference.child("Profesores").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String idprofesor;
                                    if(dataSnapshot.exists()){
                                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                            if(snapshot.child("correoprofesor").getValue().toString().equals(email)){
                                                idprofesor = snapshot.child("idprofesor").getValue().toString();
                                                IrAInicio(idprofesor, email);
                                            }
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                                Toast.makeText(LoginDocente.this, "El usuario o contraseña es erroneo", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginDocente.this, "El usuario o contraseña es erroneo", Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    private void IrAInicio(String idprofesor, String email) {
        int pos = email.indexOf("@");
        String user = email.substring(0, pos);
        Toast.makeText(LoginDocente.this, "Bienvenido: " + textCorreo.getText(), Toast.LENGTH_LONG).show();
        Intent intencion = new Intent(LoginDocente.this, InicioDocente.class);
        intencion.putExtra("user", user);
        intencion.putExtra("idprofesor", idprofesor);
        intencion.putExtra("email", email);
        startActivity(intencion);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnLogD:
                //Invocamos al método:
                loguearUsuario();
                break;
        }
    }
}