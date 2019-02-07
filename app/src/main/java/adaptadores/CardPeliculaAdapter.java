package adaptadores;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import daw.sergiorios.mispeliculas.PeliculaActivity;
import daw.sergiorios.mispeliculas.R;
import model.Pelicula;

public class CardPeliculaAdapter extends RecyclerView.Adapter<CardPeliculaAdapter.CardViewHolder> {

    private final Context mainContext;
    private final List<Pelicula> listaPeliculas;

    public CardPeliculaAdapter(List<Pelicula> listaPeliculas, Context mainContext) {
        this.mainContext = mainContext;
        this.listaPeliculas = listaPeliculas;
    }


    static class CardViewHolder extends RecyclerView.ViewHolder {
        protected TextView titulo;
        protected TextView director;
        protected TextView duracion;
        protected TextView anio;
        protected RatingBar valoracion;
        protected ImageView imagen;


        public CardViewHolder(View v) {
            super(v);

            this.titulo = v.findViewById(R.id.tvTitulo);
            this.director = v.findViewById(R.id.tvDirector);
            this.duracion = v.findViewById(R.id.tvDuracion);
            this.anio = v.findViewById(R.id.tvAnio);
            this.valoracion = v.findViewById(R.id.rbValoracion);
            this.imagen = v.findViewById(R.id.imgPelicula);
        }
    }



    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_pelicula, viewGroup, false);

        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewHolder cardViewHolder, int index) {
        final Pelicula pelicula = listaPeliculas.get(index);
        cardViewHolder.itemView.setTag(pelicula);

        cardViewHolder.titulo.setText(pelicula.getTitulo());
        cardViewHolder.director.setText(pelicula.getDirector());
        cardViewHolder.duracion.setText(pelicula.getDuracion() + " min");
        cardViewHolder.anio.setText(pelicula.getAnio());
        cardViewHolder.valoracion.setRating(Float.parseFloat(pelicula.getValoracion()));

        Glide.with(mainContext)
             .load(pelicula.getImagen())
             .apply(new RequestOptions()
             .placeholder(R.drawable.picture))
             .into(cardViewHolder.imagen);

        cardViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainContext, PeliculaActivity.class);

                intent.putExtra("id", pelicula.getId());
                intent.putExtra("titulo", pelicula.getTitulo());
                intent.putExtra("director", pelicula.getDirector());
                intent.putExtra("productora", pelicula.getProductora());
                intent.putExtra("duracion", pelicula.getDuracion());
                intent.putExtra("anio", pelicula.getAnio());
                intent.putExtra("valoracion", pelicula.getValoracion());
                intent.putExtra("sinopsis", pelicula.getSinopsis());
                intent.putExtra("vista", pelicula.getVista());
                intent.putExtra("imagen", pelicula.getImagen());


                mainContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPeliculas.size();
    }
}
