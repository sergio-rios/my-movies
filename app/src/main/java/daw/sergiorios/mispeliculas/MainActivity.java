package daw.sergiorios.mispeliculas;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import adaptadores.CardPeliculaAdapter;
import db.DataBaseManagerPelicula;
import model.Pelicula;

public class MainActivity extends AppCompatActivity {

    private Button btnConsultar, btnInsertar;
    private DataBaseManagerPelicula managerPelicula;
    private RecyclerView recyclerView;
    private CardPeliculaAdapter adapter;
    private RecyclerView.LayoutManager lManager;
    private List<Pelicula> listaPeliculas;
    private Menu menuOpciones;

    private boolean asc_titulo = true;
    private boolean asc_duracion = false;
    private boolean asc_anio = false;
    private boolean asc_valoracion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addViews();
        managerPelicula = new DataBaseManagerPelicula(this);
        listaPeliculas = managerPelicula.getListaPeliculas("titulo ASC");
        inicializarRecycler();
        recargarRecycler("titulo ASC");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.lista_peliculas, menu);
        this.menuOpciones = menu;

        return true;
    }

    @Override
    public void onResume(){
        super.onResume();
        recargarRecycler("titulo ASC");
        Log.d("RESUME", "Resume");
    }


    private void addViews() {
        FloatingActionButton fabInsertar = findViewById(R.id.fabInsertar);
        fabInsertar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AgregarActivity.class);

                startActivityForResult(intent, 1);
            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Respuesta de actividad crear pelicua
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Toast.makeText(this, data.getStringExtra("mensaje"), Toast.LENGTH_LONG).show();
        }

        // Respuesta de actividad

        recargarRecycler("titulo ASC");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.titulo:
                OrdenarTitulo();
                break;
            case R.id.duracion:
                OrdenarDuracion();
                break;
            case R.id.anio:
                OrdenarAnio();
                break;
            case R.id.valoracion:
                OrdenarValoracion();
                break;
            case R.id.salir:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void OrdenarTitulo() {
        asc_titulo = !asc_titulo;
        String orden = asc_titulo ? "titulo ASC" : "titulo DESC";
        String texto = asc_titulo ? "Título ↓" : "Título ↑";

        MenuItem item = menuOpciones.findItem(R.id.titulo);
        item.setTitle(texto);

        recargarRecycler(orden);
    }

    private void OrdenarDuracion() {
        asc_duracion = !asc_duracion;
        String orden = asc_duracion ? "duracion ASC" : "duracion DESC";
        String texto = asc_titulo ? "Duración ↓" : "Duración ↑";

        MenuItem item = menuOpciones.findItem(R.id.duracion);
        item.setTitle(texto);

        recargarRecycler(orden);
    }

    private void OrdenarAnio() {
        asc_anio = !asc_anio;
        String orden = asc_anio ? "anio ASC" : "anio DESC";
        String texto = asc_titulo ? "Año ↓" : "Año ↑";

        MenuItem item = menuOpciones.findItem(R.id.anio);
        item.setTitle(texto);

        recargarRecycler(orden);
    }

    private void OrdenarValoracion() {
        asc_valoracion = !asc_valoracion;
        String orden = asc_valoracion ? "valoracion ASC" : "valoracion DESC";
        String texto = asc_titulo ? "Valoración ↓" : "Valoración ↑";

        MenuItem item = menuOpciones.findItem(R.id.valoracion);
        item.setTitle(texto);

        recargarRecycler(orden);
    }


    @Override
    public void onBackPressed() {
        AlertDialog alert = mostrarConfirmacion();
        alert.show();
        Log.d("Salir", "Saliendo...");
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    private void recargarRecycler(String orden) {
        listaPeliculas = managerPelicula.getListaPeliculas(orden);

        adapter = new CardPeliculaAdapter(listaPeliculas, this);
        recyclerView.setAdapter(adapter);
    }

    public void inicializarRecycler() {
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        lManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lManager);

        adapter = new CardPeliculaAdapter(listaPeliculas, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    public AlertDialog mostrarConfirmacion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("¿Seguro que sea salir?")
                .setPositiveButton("Salir",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                .setNegativeButton("Cancelar", null);

        return builder.create();
    }
}
