package daw.sergiorios.mispeliculas;

import android.content.DialogInterface;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import db.DataBaseManagerPelicula;

public class PeliculaActivity extends AppCompatActivity {

    DataBaseManagerPelicula managerPelicula;
    EditText etTitulo, etDirector, etProductora, etDuracion, etAnio, etSinopsis, etImagen;
    RatingBar rbValoracion;
    CheckBox cbVista;
    String id = null, titulo = null, director = null, productora = null, duracion = null, anio = null, valoracion = null, sinopsis = null, vista = null, imagen = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_pelicula);

        managerPelicula = new DataBaseManagerPelicula(this);

        etTitulo = findViewById(R.id.etTitulo);
        etDirector = findViewById(R.id.etDirector);
        etProductora = findViewById(R.id.etProductora);
        etDuracion = findViewById(R.id.etDuracion);
        etAnio = findViewById(R.id.etAnio);
        rbValoracion = findViewById(R.id.rbValoracion);
        etSinopsis = findViewById(R.id.etSinopsis);
        cbVista = findViewById(R.id.cbVista);
        etImagen = findViewById(R.id.etImagen);

        getDatosIntent();
    }
    
    private void getDatosIntent() {
        if (getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");
        }

        if (getIntent().hasExtra("titulo")) {
            titulo = getIntent().getStringExtra("titulo");
        }

        if (getIntent().hasExtra("director")) {
            director = getIntent().getStringExtra("director");
        }

        if (getIntent().hasExtra("productora")) {
            productora = getIntent().getStringExtra("productora");
        }

        if (getIntent().hasExtra("duracion")) {
            duracion = getIntent().getStringExtra("duracion");
        }

        if (getIntent().hasExtra("anio")) {
            anio = getIntent().getStringExtra("anio");
        }

        if (getIntent().hasExtra("valoracion")) {
            valoracion = getIntent().getStringExtra("valoracion");
        }

        if (getIntent().hasExtra("sinopsis")) {
            sinopsis = getIntent().getStringExtra("sinopsis");
        }

        if (getIntent().hasExtra("vista")) {
            vista = getIntent().getStringExtra("vista");
        }

        if (getIntent().hasExtra("imagen")) {
            imagen = getIntent().getStringExtra("imagen");
        }

        setDatos(titulo, director, productora, duracion, anio, valoracion, sinopsis, vista, imagen);
    }

    private void setDatos(String titulo, String director, String productora, String duracion, String anio, String valoracion, String sinopsis, String vista, String imagen) {
        etTitulo.setText(titulo);
        etDirector.setText(director);
        etProductora.setText(productora);
        etDuracion.setText(duracion);
        etAnio.setText(anio);
        rbValoracion.setRating(Float.parseFloat(valoracion));
        etSinopsis.setText(sinopsis);
        etImagen.setText(imagen);

        if (vista.equals("1")) {
            cbVista.setChecked(true);
        }
        Log.d("vista", vista);
    }

    public void manejarDatos(View v) {
        switch (v.getId()) {
            case R.id.btnBorrar:
                Log.d("BORRAR", id);
                AlertDialog alert = mostrarConfirmacion(titulo);
                alert.show();
                break;

            case R.id.btnGuardar:
                Log.d("ACTUALIZAR", id);
                getDatosActualizar();
                managerPelicula.actualizar(id, titulo, director, productora, duracion, anio, valoracion, sinopsis, vista, imagen);
                Toast.makeText(this, "Pelicula guardada", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void getDatosActualizar() {
        titulo = etTitulo.getText().toString();
        director = etDirector.getText().toString();
        productora = etProductora.getText().toString();
        duracion = etDuracion.getText().toString();
        anio = etAnio.getText().toString();
        valoracion = String.valueOf(rbValoracion.getRating());
        sinopsis = etSinopsis.getText().toString();
        imagen = etImagen.getText().toString();

        if (cbVista.isChecked())
            vista = "1";
        else
            vista = "0";
    }



    public AlertDialog mostrarConfirmacion(String titulo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(titulo)
            .setMessage("¿Seguro que sea eliminar?")
            .setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        managerPelicula.eliminar(id);
                        finish();
                    }
                })
            .setNegativeButton("CANCELAR", null);

        return builder.create();
    }
}
