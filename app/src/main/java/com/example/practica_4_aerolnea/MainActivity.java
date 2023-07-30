package com.example.practica_4_aerolnea;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> vuelosReservados;
    private ArrayAdapter<String> adapterMisVuelos;
    private ArrayList<String> datosSeleccionados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vuelosReservados = new ArrayList<>();
        adapterMisVuelos = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, vuelosReservados);
        datosSeleccionados = new ArrayList<>();

        ListView listViewMisVuelos = findViewById(R.id.listViewMisVuelos);
        listViewMisVuelos.setAdapter(adapterMisVuelos);

        // Manejador para el botón "Mostrar Vuelos"
        Button btnMostrarVuelos = findViewById(R.id.btnMostrarVuelos);
        btnMostrarVuelos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarVuelos();
            }
        });

        // Manejador para el botón "Registrar Vuelo"
        Button btnRegistrarVuelo = findViewById(R.id.btnRegistrarVuelo);
        btnRegistrarVuelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarVuelo();
            }
        });

        // Manejador para el botón "Registrar Aerolínea"
        Button btnRegistrarAerolinea = findViewById(R.id.btnRegistrarAerolinea);
        btnRegistrarAerolinea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarAerolinea();
            }
        });

        Button btnBorrarRegistros = findViewById(R.id.btnBorrarRegistros);
        btnBorrarRegistros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarRegistros();
            }
        });
    }

    // Método para mostrar los vuelos reservados en la lista
    private void mostrarVuelos() {
        Intent intent = new Intent(this, MostrarVuelosActivity.class);
        startActivity(intent);
    }

    // Método para registrar un nuevo vuelo
    private void registrarVuelo() {
        Intent intent = new Intent(this, RegistrarVueloActivity.class);
        startActivity(intent);
    }

    // Método para registrar una nueva aerolínea
    private void registrarAerolinea() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Registrar Aerolínea");

        View view = getLayoutInflater().inflate(R.layout.dialog_registrar_aerolinea, null);
        final EditText editTextAerolinea = view.findViewById(R.id.editTextAerolinea);
        builder.setView(view);

        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String aerolinea = editTextAerolinea.getText().toString().trim();
                if (!aerolinea.isEmpty()) {
                    guardarAerolinea(aerolinea);
                } else {
                    Toast.makeText(MainActivity.this, "Ingrese el nombre de la aerolínea", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void guardarAerolinea(String aerolinea) {
        String aerolineaTexto = "Aerolínea: " + aerolinea + "\n";

        try {
            FileOutputStream fileOutputStream = openFileOutput("aerolineas.txt", MODE_APPEND);
            fileOutputStream.write(aerolineaTexto.getBytes());
            fileOutputStream.close();
            Toast.makeText(this, "Aerolínea registrada con éxito.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar la aerolínea.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mostrarDatosSeleccionados();
    }

    private void cargarVuelosReservados() {
        vuelosReservados.clear();
        try {
            FileInputStream fileInputStream = openFileInput("seleccion.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                vuelosReservados.add(line);
            }

            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        adapterMisVuelos.notifyDataSetChanged();
    }

    private void mostrarDatosSeleccionados() {
        try {
            FileInputStream fileInputStream = openFileInput("seleccion.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            datosSeleccionados.clear();
            while ((line = bufferedReader.readLine()) != null) {
                datosSeleccionados.add(line);
            }

            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datosSeleccionados);
        ListView listViewDatosSeleccionados = findViewById(R.id.listViewMisVuelos);
        listViewDatosSeleccionados.setAdapter(adapter);
    }

    private void borrarRegistros() {
        boolean archivosBorrados = deleteFile("vuelos.txt") &&
                deleteFile("aerolineas.txt") &&
                deleteFile("seleccion.txt");
        if (archivosBorrados) {
            Toast.makeText(this, "Registros borrados con éxito.", Toast.LENGTH_SHORT).show();
            datosSeleccionados.clear();
            mostrarDatosSeleccionados(); // Actualiza la lista para reflejar que los registros fueron borrados
        } else {
            Toast.makeText(this, "Error al borrar registros.", Toast.LENGTH_SHORT).show();
        }
    }
}