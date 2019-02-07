package daw.sergiorios.mispeliculas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;

import db.DataBaseManagerPelicula;

public class AgregarActivity extends AppCompatActivity {

    private DataBaseManagerPelicula managerPelicula;
    FloatingActionButton fabAceptar;
    EditText titulo, director, productora, duracion, anio, sinopsis, imagen;
    RatingBar valoracion;
    CheckBox vista;
    String txtTitulo, txtDirector, txtProductora, txtDuracion, txtAnio, txtValoracion, txtSinopsis, txtVista, txtImagen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nueva_pelicula);

        managerPelicula = new DataBaseManagerPelicula(this);
        fabAceptar = findViewById(R.id.fabAceptar);

        // Cuando pulsemos el boton aceptar
        fabAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDatos();
                Intent i = new Intent();

                if (!txtTitulo.matches("") && txtTitulo != null) {
                    managerPelicula.insertar(null, txtTitulo, txtDirector, txtProductora, txtDuracion, txtAnio, txtValoracion, txtSinopsis, txtVista, txtImagen);
                    i.putExtra("mensaje", "Pelicula añadida con éxito");
                    setResult(RESULT_OK, i);
                }
                else {
                    i.putExtra("mensaje", "No se pudo añadir la pelicula");
                    setResult(RESULT_OK, i);
                }

                finish();
            }
        });
    }

    @Override
    public void onDestroy() {
        setResult(RESULT_CANCELED);
        finish();
        super.onDestroy();
    }


    private void getDatos() {
        titulo = findViewById(R.id.etTitulo);
        txtTitulo = titulo.getText().toString();

        director = findViewById(R.id.etDirector);
        txtDirector = director.getText().toString();

        productora = findViewById(R.id.etProductora);
        txtProductora = productora.getText().toString();

        duracion = findViewById(R.id.etDuracion);
        txtDuracion = duracion.getText().toString();

        anio = findViewById(R.id.etAnio);
        txtAnio = anio.getText().toString();

        valoracion = findViewById(R.id.rbValoracion);
        txtValoracion = String.valueOf(valoracion.getRating());

        sinopsis = findViewById(R.id.etSinopsis);
        txtSinopsis = sinopsis.getText().toString();

        vista = findViewById(R.id.cbVista);
        if (vista.isChecked())
            txtVista = "1";
        else
            txtVista = "0";

        imagen = findViewById(R.id.etImagen);
        txtImagen = imagen.getText().toString();
    }
}
