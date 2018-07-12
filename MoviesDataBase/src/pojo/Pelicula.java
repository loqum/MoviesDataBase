package pojo;

public class Pelicula {
    private long id;
    private String titulo;
    private String director;
    private String genero;
    private int year;
    private String soporte;
    private String ciclo;
    private String vista;

    public Pelicula(int id, String titulo, String director, String genero, int year, String soporte, String ciclo, String vista) {
	this.id = id;
	this.titulo = titulo;
	this.director = director;
	this.genero = genero;
	this.year = year;
	this.soporte = soporte;
	this.ciclo = ciclo;
	this.vista = vista;
    }

    public Pelicula(String titulo, String director, String genero, int year, String soporte, String ciclo, String vista) {
	this.titulo = titulo;
	this.director = director;
	this.genero = genero;
	this.year = year;
	this.soporte = soporte;
	this.ciclo = ciclo;
	this.vista = vista;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
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

    public String getGenero() {
	return genero;
    }

    public void setGenero(String genero) {
	this.genero = genero;
    }

    public int getYear() {
	return year;
    }

    public void setYear(int year) {
	this.year = year;
    }

    public String getSoporte() {
	return soporte;
    }

    public void setSoporte(String soporte) {
	this.soporte = soporte;
    }

    public String getCiclo() {
	return ciclo;
    }

    public void setCiclo(String ciclo) {
	this.ciclo = ciclo;
    }

    public String getVista() {
	return vista;
    }

    public void setVista(String vista) {
	this.vista = vista;
    }

    @Override
    public String toString() {
	return "Pelicula [id=" + id + ", titulo=" + titulo + ", director=" + director + ", genero=" + genero + ", year="
		+ year + ", soporte=" + soporte + ", ciclo=" + ciclo + ", vista=" + vista + "]";
    }    

}
