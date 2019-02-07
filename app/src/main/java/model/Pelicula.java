package model;

public class Pelicula {
    String id;
    String titulo;
    String director;
    String productora;
    String duracion;
    String anio;
    String valoracion;
    String sinopsis;
    String vista;
    String imagen_url;



    public Pelicula() {
    }

    public Pelicula(String id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public Pelicula(String id, String titulo, String director, String productora, String duracion, String anio, String valoracion, String sinopsis, String vista, String imagen) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.productora = productora;
        this.duracion = duracion;
        this.anio = anio;
        this.valoracion = valoracion;
        this.sinopsis = sinopsis;
        this.vista = vista;
        this.imagen_url = imagen;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getProductora() {
        return productora;
    }

    public void setProductora(String productora) {
        this.productora = productora;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getVista() {
        return vista;
    }

    public void setVista(String vista) {
        this.vista = vista;
    }

    public String getImagen() {
        return imagen_url;
    }

    public void setImagen(String imagen_url) {
        this.imagen_url = imagen_url;
    }
}


