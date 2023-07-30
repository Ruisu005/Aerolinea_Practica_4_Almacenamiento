package com.example.practica_4_aerolnea;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MostrarVuelosActivity extends AppCompatActivity {

    private ArrayList<String> vuelosAerolineas;
    private ArrayAdapter<String> adapter;
    private String vueloSeleccionado;
    private String aerolineaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_vuelos);

        vuelosAerolineas = new ArrayList<>();
        cargarVuelosAerolineas();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, vuelosAerolineas);

        ListView listViewVuelosAerolineas = findViewById(R.id.listViewVuelosAerolineas);
        listViewVuelosAerolineas.setAdapter(adapter);

        listViewVuelosAerolineas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                String[] parts = item.split(", Aerolínea: ");
                vueloSeleccionado = parts[0].substring(parts[0].indexOf(":") + 2).trim();

                AlertDialog.Builder builder = new AlertDialog.Builder(MostrarVuelosActivity.this);
                builder.setTitle("Seleccionar Aerolínea")
                        .setItems(getAerolineasRegistradas(), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                aerolineaSeleccionada = getAerolineasRegistradas()[which];
                                guardarSeleccion();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        Button btnSeleccionarVueloAerolinea = findViewById(R.id.btnSeleccionarVueloAerolinea);
        btnSeleccionarVueloAerolinea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarSeleccion();
            }
        });
    }

    private String[] getAerolineasRegistradas() {
        ArrayList<String> aerolineas = new ArrayList<>();
        try {
            FileInputStream fileInputStream = openFileInput("aerolineas.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                aerolineas.add(line);
            }

            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return aerolineas.toArray(new String[0]);
    }


    private void cargarVuelosAerolineas() {
        try {
            FileInputStream fileInputStream = openFileInput("vuelos.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                vuelosAerolineas.add(line);
            }

            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarSeleccion() {
        if (vueloSeleccionado != null && aerolineaSeleccionada != null) {
            String seleccion = "Vuelo: " + vueloSeleccionado + ", Aerolínea: " + aerolineaSeleccionada + "\n";
            try {
                FileOutputStream fileOutputStream = openFileOutput("seleccion.txt", MODE_APPEND);
                fileOutputStream.write(seleccion.getBytes());
                fileOutputStream.close();
                Toast.makeText(this, "Selección guardada con éxito.", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al guardar la selección.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Primero seleccione un vuelo y una aerolínea.", Toast.LENGTH_SHORT).show();
        }
    }


}