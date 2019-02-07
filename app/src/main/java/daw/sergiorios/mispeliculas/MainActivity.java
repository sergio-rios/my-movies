package daw.sergiorios.mispeliculas;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addViews();
        managerPelicula = new DataBaseManagerPelicula(this);
        listaPeliculas = managerPelicula.getListaPeliculas();
        inicializarRecycler();
        recargarRecycler();
    }

    @Override
    public void onResume(){
        super.onResume();
        recargarRecycler();
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

        recargarRecycler();
    }












    private void recargarRecycler() {
        listaPeliculas = managerPelicula.getListaPeliculas();

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
}
