package repo;

import java.sql.ResultSet;

import connection.JdbcHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import pojo.Pelicula;

public class PeliculaRepo {

    private boolean insertar(Pelicula pelicula) {
	String query = "INSERT INTO peliculas (titulo, director, genero, year, soporte, ciclo) " + "VALUES ('"
		+ pelicula.getTitulo() + "','" + pelicula.getDirector() + "','" + pelicula.getGenero() + "','"
		+ pelicula.getYear() + "','" + pelicula.getSoporte() + "','" + pelicula.getCiclo() + "')";
	JdbcHelper jdbc = new JdbcHelper();
	boolean exito = jdbc.ejecutarQuery(query);
	return exito;
    }

    private boolean modificar(Pelicula pelicula) {
	String query = "UPDATE peliculas SET " + "titulo = '" + pelicula.getTitulo() + "'," + "director = '"
		+ pelicula.getDirector() + "'," + "genero = '" + pelicula.getGenero() + "'," + "year = '"
		+ pelicula.getYear() + "'," + "soporte = '" + pelicula.getSoporte() + "'," + "ciclo = '"
		+ pelicula.getCiclo() + "' WHERE idpeliculas = " + pelicula.getId();
	JdbcHelper jdbc = new JdbcHelper();
	boolean exito = jdbc.ejecutarQuery(query);
	return exito;
    }

    public boolean eliminar(int id) {
	String query = "DELETE FROM peliculas WHERE idpeliculas = " + id;
	JdbcHelper jdbc = new JdbcHelper();
	boolean exito = jdbc.ejecutarQuery(query);
	return exito;
    }

    public boolean validar(Pelicula pelicula) {
	StringBuilder sb = new StringBuilder();
	boolean esValido = true;
	if (pelicula == null) {
	    esValido = false;
	    sb.append("*No existe la película\n");
	}
	if (pelicula.getTitulo().trim().equals("")) {
	    esValido = false;
	    sb.append("*Título requerido\n");
	}
	if (pelicula.getTitulo().trim().length() > 100) {
	    esValido = false;
	    sb.append("*Título debe ser menor a 100 caracteres\n");
	}
	if (pelicula.getDirector().trim().equals("")) {
	    esValido = false;
	    sb.append("*Director requerido\n");
	}
	if (pelicula.getDirector().trim().length() > 100) {
	    esValido = false;
	    sb.append("*Director debe ser menor a 100 caracteres\n");
	}
	if (pelicula.getGenero().trim().equals("")) {
	    esValido = false;
	    sb.append("*Género requerido\n");
	}
	if (pelicula.getYear() <= 0) {
	    esValido = false;
	    sb.append("*El año debe ser mayor a cero\n");
	}
	if (pelicula.getSoporte().trim().equals("")) {
	    esValido = false;
	    sb.append("*Soporte requerido\n");
	}

	if (!esValido) {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Error de validación");
	    alert.setHeaderText("Se han encontrado los siguientes errores: ");
	    alert.setContentText(sb.toString());

	    alert.showAndWait();

	}
	return esValido;
    }

    public boolean guardar(Pelicula pelicula) {
	if (validar(pelicula) == false) {
	    return false;
	}
	boolean exito;
	if (pelicula.getId() == 0)
	    exito = insertar(pelicula);
	else
	    exito = modificar(pelicula);
	return exito;
    }

    public Pelicula buscarPelicula(int idBusqueda) {
	String query = "SELECT * FROM peliculas WHERE idpeliculas = " + idBusqueda;
	JdbcHelper jdbc = new JdbcHelper();
	ResultSet rs = jdbc.realizarConsulta(query);

	Pelicula pelicula = null;

	try {
	    if (rs.next()) {
		int id = idBusqueda;
		String titulo = rs.getString("titulo");
		String director = rs.getString("director");
		String genero = rs.getString("genero");
		int year = rs.getInt("year");
		String soporte = rs.getString("soporte");
		String ciclo = rs.getString("ciclo");
		pelicula = new Pelicula(id, titulo, director, genero, year, soporte, ciclo);
	    }
	} catch (Exception ex) {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Error de búsqueda");
	    alert.setHeaderText("Error al buscar película:\n");
	    alert.setContentText(ex.toString());

	    alert.showAndWait();

	}
	return pelicula;
    }

    public ObservableList<Pelicula> buscarTodos() {
	String query = "SELECT * FROM peliculas";
	JdbcHelper jdbc = new JdbcHelper();
	ResultSet rs = jdbc.realizarConsulta(query);

	ObservableList<Pelicula> peliculas = FXCollections.observableArrayList();

	try {
	    while (rs.next()) {
		int id = rs.getInt("idpeliculas");
		String titulo = rs.getString("titulo");
		String director = rs.getString("director");
		String genero = rs.getString("genero");
		int year = rs.getInt("year");
		String soporte = rs.getString("soporte");
		String ciclo = rs.getString("ciclo");
		peliculas.add(new Pelicula(id, titulo, director, genero, year, soporte, ciclo));
	    }
	} catch (Exception ex) {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Error de búsqueda");
	    alert.setHeaderText("Error al buscar las películas:\n");
	    alert.setContentText(ex.toString());

	    alert.showAndWait();
	}
	return peliculas;
    }

    public ObservableList<Pelicula> buscarPorNombre(String tituloBusqueda) {
	String query = "SELECT * FROM peliculas WHERE titulo LIKE '%" + tituloBusqueda + "%'";
	JdbcHelper jdbc = new JdbcHelper();
	ResultSet rs = jdbc.realizarConsulta(query);

	ObservableList<Pelicula> peliculas = FXCollections.observableArrayList();

	try {
	    while (rs.next()) {
		int id = rs.getInt("idpeliculas");
		String titulo = rs.getString("titulo");
		String director = rs.getString("director");
		String genero = rs.getString("genero");
		int year = rs.getInt("year");
		String soporte = rs.getString("soporte");
		String ciclo = rs.getString("ciclo");
		peliculas.add(new Pelicula(id, titulo, director, genero, year, soporte, ciclo));
	    }
	} catch (Exception ex) {

	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Error de búsqueda");
	    alert.setHeaderText("Error al buscar película por título:\n");
	    alert.setContentText(ex.toString());

	    alert.showAndWait();

	}
	return peliculas;
    }

    public ObservableList<Pelicula> buscarPorAutor(String directorBusqueda) {
	String query = "SELECT * FROM peliculas WHERE director LIKE '%" + directorBusqueda + "%'";
	JdbcHelper jdbc = new JdbcHelper();
	ResultSet rs = jdbc.realizarConsulta(query);

	ObservableList<Pelicula> peliculas = FXCollections.observableArrayList();

	try {
	    while (rs.next()) {
		int id = rs.getInt("idpeliculas");
		String titulo = rs.getString("titulo");
		String director = rs.getString("director");
		String genero = rs.getString("genero");
		int year = rs.getInt("year");
		String soporte = rs.getString("soporte");
		String ciclo = rs.getString("ciclo");
		peliculas.add(new Pelicula(id, titulo, director, genero, year, soporte, ciclo));
	    }
	} catch (Exception ex) {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Error de búsqueda");
	    alert.setHeaderText("Error al buscar película por director:\n");
	    alert.setContentText(ex.toString());

	    alert.showAndWait();

	}
	return peliculas;
    }

    public ObservableList<Pelicula> buscarPorGenero(String generoBusqueda) {
	String query = "SELECT * FROM peliculas WHERE genero LIKE '%" + generoBusqueda + "%'";
	JdbcHelper jdbc = new JdbcHelper();
	ResultSet rs = jdbc.realizarConsulta(query);

	ObservableList<Pelicula> peliculas = FXCollections.observableArrayList();

	try {
	    while (rs.next()) {
		int id = rs.getInt("idpeliculas");
		String titulo = rs.getString("titulo");
		String director = rs.getString("director");
		String genero = rs.getString("genero");
		int year = rs.getInt("year");
		String soporte = rs.getString("soporte");
		String ciclo = rs.getString("ciclo");
		peliculas.add(new Pelicula(id, titulo, director, genero, year, soporte, ciclo));
	    }
	} catch (Exception ex) {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Error de búsqueda");
	    alert.setHeaderText("Error al buscar película por género:\n");
	    alert.setContentText(ex.toString());

	    alert.showAndWait();
	}
	return peliculas;
    }

    public ObservableList<Pelicula> buscarPorCiclo(String cicloBusqueda) {
	String query = "SELECT * FROM peliculas WHERE ciclo LIKE '%" + cicloBusqueda + "%'";
	JdbcHelper jdbc = new JdbcHelper();
	ResultSet rs = jdbc.realizarConsulta(query);

	ObservableList<Pelicula> peliculas = FXCollections.observableArrayList();

	try {
	    while (rs.next()) {
		int id = rs.getInt("idpeliculas");
		String titulo = rs.getString("titulo");
		String director = rs.getString("director");
		String genero = rs.getString("genero");
		int year = rs.getInt("year");
		String soporte = rs.getString("soporte");
		String ciclo = rs.getString("ciclo");
		peliculas.add(new Pelicula(id, titulo, director, genero, year, soporte, ciclo));
	    }
	} catch (Exception ex) {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Error de búsqueda");
	    alert.setHeaderText("Error al buscar película por ciclo:\n");
	    alert.setContentText(ex.toString());

	    alert.showAndWait();
	}
	return peliculas;
    }

}
