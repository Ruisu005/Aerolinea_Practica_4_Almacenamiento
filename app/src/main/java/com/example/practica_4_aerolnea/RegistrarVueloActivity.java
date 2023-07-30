package com.example.practica_4_aerolnea;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;

public class RegistrarVueloActivity extends AppCompatActivity {

    private EditText editTextOrigen;
    private EditText editTextDestino;
    private EditText editTextFecha;
    private EditText editTextHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_vuelo);

        editTextOrigen = findViewById(R.id.editTextOrigen);
        editTextDestino = findViewById(R.id.editTextDestino);
        editTextFecha = findViewById(R.id.editTextFecha);
        editTextHora = findViewById(R.id.editTextHora);

        Button btnGuardarVuelo = findViewById(R.id.btnGuardarVuelo);
        btnGuardarVuelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarVuelo();
            }
        });
    }

    private void guardarVuelo() {
        String origen = editTextOrigen.getText().toString();
        String destino = editTextDestino.getText().toString();
        String fecha = editTextFecha.getText().toString();
        String hora = editTextHora.getText().toString();

        // Validar que los campos no estén vacíos
        if (origen.isEmpty() || destino.isEmpty() || fecha.isEmpty() || hora.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        String vuelo = "Origen: " + origen + "\n" +
                "Destino: " + destino + "\n" +
                "Fecha: " + fecha + "\n" +
                "Hora: " + hora + "\n\n";

        try {
            FileOutputStream fileOutputStream = openFileOutput("vuelos.txt", MODE_APPEND);
            fileOutputStream.write(vuelo.getBytes());
            fileOutputStream.close();
            Toast.makeText(this, "Vuelo guardado con éxito.", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar el vuelo.", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCampos() {
        editTextOrigen.setText("");
        editTextDestino.setText("");
        editTextFecha.setText("");
        editTextHora.setText("");
    }
}