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
        valoresDefecto();
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
    public void onRestart(){
        super.onRestart();
        recargarRecycler("titulo ASC");
        asc_titulo = false;
        OrdenarTitulo();
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
        ResetOrdenar("titulo");
    }

    private void OrdenarDuracion() {
        asc_duracion = !asc_duracion;
        String orden = asc_duracion ? "duracion DESC" : "duracion ASC";
        String texto = asc_duracion ? "Duración ↓" : "Duración ↑";

        MenuItem item = menuOpciones.findItem(R.id.duracion);
        item.setTitle(texto);

        recargarRecycler(orden);
        ResetOrdenar("duracion");
    }

    private void OrdenarAnio() {
        asc_anio = !asc_anio;
        String orden = asc_anio ? "anio DESC" : "anio ASC";
        String texto = asc_anio ? "Año ↓" : "Año ↑";

        MenuItem item = menuOpciones.findItem(R.id.anio);
        item.setTitle(texto);

        recargarRecycler(orden);
        ResetOrdenar("anio");
    }

    private void OrdenarValoracion() {
        asc_valoracion = !asc_valoracion;
        String orden = asc_valoracion ? "valoracion DESC" : "valoracion ASC";
        String texto = asc_valoracion ? "Valoración ↓" : "Valoración ↑";

        MenuItem item = menuOpciones.findItem(R.id.valoracion);
        item.setTitle(texto);

        recargarRecycler(orden);
        ResetOrdenar("valoracion");
    }

    private void ResetOrdenar(String no_actualizar) {
        String texto;
        MenuItem item;

        if (!no_actualizar.equals("titulo")) {
            asc_titulo = false;
            texto = "Titulo ↑";
            item = menuOpciones.findItem(R.id.titulo);
            item.setTitle(texto);
        }

        if (!no_actualizar.equals("duracion")) {
            asc_duracion = false;
            texto = "Duración ↑";
            item = menuOpciones.findItem(R.id.duracion);
            item.setTitle(texto);
        }

        if (!no_actualizar.equals("anio")) {
            asc_anio = false;
            texto = "Año ↑";
            item = menuOpciones.findItem(R.id.anio);
            item.setTitle(texto);
        }

        if (!no_actualizar.equals("valoracion")) {
            asc_valoracion = false;
            texto = "Valoración ↑";
            item = menuOpciones.findItem(R.id.valoracion);
            item.setTitle(texto);
        }
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

    private void valoresDefecto() {
        Log.d("NA", "hola");
        if (managerPelicula.count() == 0) {
            Log.d("NA", "De na");
            managerPelicula.insertar(null, "Coherence", "James Ward", "Bellanova Films / Ugly Duckling Films", "89", "2013", "3.5", "En Finlandia, en 1923, el paso de un cometa hizo que los habitantes de un pueblo quedaran completamente desorientados; incluso una mujer llegó a llamar a la policía denunciando que el hombre que estaba en su casa no era su marido. Décadas más tarde, un grupo de amigos recuerda este caso mientras cenan, brindan y se preparan para ver pasar un cometa... ", "1", "https://www.culturamas.es/wp-content/uploads/2014/10/coherence1.jpg");
            managerPelicula.insertar(null, "Mystic River", "Clint Eastwood", "Warner Bros", "137", "2003", "4", "Cuando Jimmy Markum (Sean Penn), Dave Boyle (Tim Robbins) y Sean Devine (Kevin Bacon) eran unos niños que crecían juntos en un peligroso barrio obrero de Boston, pasaban los días jugando al hockey en la calle. Pero, un día, a Dave le ocurrió algo que marcó para siempre su vida y la de sus amigos. Veinticinco años más tarde, otra tragedia los vuelve a unir: el asesinato de Katie (Emmy Rossum), la hija de 19 años de Jimmy. A Sean, que es policía, le asignan el caso; pero también tiene que estar muy pendiente de Jimmy porque, en su desesperación, está intentando tomarse la justicia por su mano.", "1", "https://www.eldiario.es/fotos/Cartel-Mystic-River_EDIIMA20180926_0835_5.jpg");
            managerPelicula.insertar(null, "Reservoir Dogs", "Quentin Tarantino", "Live Entertainment / Dog Eat Dog Productions", "99", "1992", "4", "Una banda organizada es contratada para atracar una empresa y llevarse unos diamantes. Sin embargo, antes de que suene la alarma, la policía ya está allí. Algunos miembros de la banda mueren en el enfrentamiento con las fuerzas del orden, y los demás se reúnen en el lugar convenido.", "1", "https://www.ecartelera.com/carteles/4300/4308/001.jpg");
            managerPelicula.insertar(null, "El hombre que conocía el infinito", "Matt Brown", "", "114", "2015", "0", "Narra la historia de Srinivasa Ramanujan, un matemático indio que hizo importantes contribuciones al mundo de las matemáticas como la teoría de los números, las series y las fracciones continuas. Con su arduo trabajo, Srinivasa consiguió entrar en la Universidad de Cambridge durante la Primera Guerra Mundial, donde continuó trabajando en sus teorías con la ayuda del profesor británico G. H. Hardy, a pesar de todos los impedimentos que su origen indio suponían para los estándares sociales de aquella época.", "0", "https://images-na.ssl-images-amazon.com/images/I/A1X%2Bu9vdXtL._SY445_.jpg");
            managerPelicula.insertar(null, "Exam", "Stuart Hazeldine", "", "101", "2009", "2.5", "Cinta de tensión psicológica que nos sitúa en un escenario opresivo donde un grupo de candidatos competirán por hacerse con un jugoso puesto de trabajo. Las reglas son simples: nada de preguntas, nada de salir de la habitación y, por último, nada de estropear el folio del examen (un folio que, por otra parte, está en blanco). Tienen 80 minutos...", "1", "https://i.pinimg.com/originals/51/4c/5a/514c5a319028efeb3836933dc996f040.jpg");
        }
    }
}
