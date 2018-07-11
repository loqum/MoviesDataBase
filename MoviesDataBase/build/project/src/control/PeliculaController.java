package control;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import pojo.Pelicula;
import javax.swing.JOptionPane;
import repo.PeliculaRepo;
import reportes.Reporte;

public class PeliculaController implements Initializable {

    public ObservableList<Pelicula> dataPelicula = FXCollections.observableArrayList();
    @FXML
    private Tab tabEntrada;
    @FXML
    private Label labResultados;
    @FXML
    private TabPane tabPanePelicula;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtTitulo;
    @FXML
    private TextField txtDirector;
    @FXML
    private TextField txtSoporte;
    @FXML
    private TextField txtCiclo;
    @FXML
    private ChoiceBox<String> choiceGenero;
    @FXML
    private TextField txtYear;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Tab tabSalida;
    @FXML
    private TextField txtBuscar;
    @FXML
    private ChoiceBox<String> choiceBuscar;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnRefrescar;
    @FXML
    private TableView<Pelicula> tablaPelicula;
    @FXML
    private TableColumn<Pelicula, Long> colId;
    @FXML
    private TableColumn<Pelicula, String> colTitulo;
    @FXML
    private TableColumn<Pelicula, String> colDirector;
    @FXML
    private TableColumn<Pelicula, String> colGenero;
    @FXML
    private TableColumn<Pelicula, String> colSoporte;
    @FXML
    private TableColumn<Pelicula, Integer> colYear;
    @FXML
    private TableColumn<Pelicula, String> colCiclo;
    @FXML
    private MenuItem btnReporte;
    @FXML
    private MenuItem handleExit;
    @FXML
    private MenuItem handleAbout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	configurarTabla();
	rellenarTablaPelicula();
	vaciarCampos();
	deshabilitarCampos();

	colocarImagenBotones();
	btnNuevo.setDisable(false);
	btnGuardar.setDisable(true);
	btnEliminar.setDisable(true);
	btnCancelar.setDisable(true);

	choiceGenero.getItems().addAll("Acción", "Ciencia Ficción", "Comedia", "Drama", "Histórica", "Romántica",
		"Suspense", "Terror");
	choiceGenero.setValue("");
	choiceBuscar.getItems().addAll("Id", "Título", "Director", "Género", "Ciclo");
	choiceBuscar.setValue("Id");
    }

    public void configurarTabla() {
	colId.setCellValueFactory(new PropertyValueFactory<>("id"));
	colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
	colDirector.setCellValueFactory(new PropertyValueFactory<>("director"));
	colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
	colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
	colSoporte.setCellValueFactory(new PropertyValueFactory<>("soporte"));
	colCiclo.setCellValueFactory(new PropertyValueFactory<>("ciclo"));
	tablaPelicula.setItems(dataPelicula);
    }

    public void rellenarTablaPelicula() {
	dataPelicula.clear();
	PeliculaRepo peliculaRepo = new PeliculaRepo();
	ObservableList<Pelicula> peliculas = peliculaRepo.buscarTodos();
	dataPelicula.setAll(peliculas);
	int resultados = peliculas.size();
	labResultados.setText("Resultados: " + resultados);
    }

    private void vaciarCampos() {
	txtId.setText("");
	txtTitulo.setText("");
	txtDirector.setText("");
	txtYear.setText("0");
	txtSoporte.setText("");
	txtCiclo.setText("");
    }

    private void deshabilitarCampos() {
	txtId.setDisable(true);
	txtTitulo.setDisable(true);
	txtDirector.setDisable(true);
	choiceGenero.setDisable(true);
	txtYear.setDisable(true);
	txtSoporte.setDisable(true);
	txtCiclo.setDisable(true);
    }

    private void habilitarCampos() {
	txtId.setDisable(true); // siempre ira deshabilitado
	txtTitulo.setDisable(false);
	txtDirector.setDisable(false);
	choiceGenero.setDisable(false);
	txtYear.setDisable(false);
	txtSoporte.setDisable(false);
	txtCiclo.setDisable(false);
    }

    @FXML
    private void btnNuevo_click(ActionEvent event) {
	vaciarCampos();
	habilitarCampos();
	btnNuevo.setDisable(true);
	btnGuardar.setDisable(false);
	btnEliminar.setDisable(true);
	btnCancelar.setDisable(false);
    }

    @FXML
    private void btnGuardar_click(ActionEvent event) {
	int id;
	String titulo = txtTitulo.getText();
	String director = txtDirector.getText();
	String genero = choiceGenero.getValue();
	String soporte = txtSoporte.getText();
	int year;
	String ciclo = txtCiclo.getText();
	try {
	    year = Integer.parseInt(txtYear.getText());
	} catch (Exception ex) {

	    Alert alert = new Alert(AlertType.WARNING);
	    alert.setTitle("MoviesDataBase");
	    alert.setHeaderText("Error de validación:");
	    alert.setContentText("El campo año debe ser numérico.");

	    alert.showAndWait();

	    return;
	}

	Pelicula pelicula;
	if (txtId.getText().trim().equals(""))
	    pelicula = new Pelicula(titulo, director, genero, year, soporte, ciclo);
	else {
	    id = Integer.parseInt(txtId.getText());
	    pelicula = new Pelicula(id, titulo, director, genero, year, soporte, ciclo);
	}
	PeliculaRepo peliculaRepo = new PeliculaRepo();
	if (peliculaRepo.guardar(pelicula)) {
	    vaciarCampos();
	    deshabilitarCampos();
	    btnNuevo.setDisable(false);
	    btnGuardar.setDisable(true);
	    btnEliminar.setDisable(true);
	    btnCancelar.setDisable(true);

	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle("MoviesDataBase");
	    alert.setHeaderText("¡La operación ha sido un éxito!");
	    alert.setContentText("Película registrada correctamente.");

	    alert.showAndWait();

	    rellenarTablaPelicula();
	}
    }

    @FXML
    private void btnEliminar_click(ActionEvent event) {
	int opcion = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas eliminar esta película?", "Confirmación",
		JOptionPane.YES_NO_OPTION, 2);
	if (opcion == JOptionPane.YES_OPTION) {
	    int id = Integer.parseInt(txtId.getText());
	    PeliculaRepo peliculaRepo = new PeliculaRepo();
	    peliculaRepo.eliminar(id);

	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle("MoviesDataBase");
	    alert.setHeaderText("¡La operación ha sido un éxito!");
	    alert.setContentText("Película eliminada correctamente.");

	    alert.showAndWait();

	    vaciarCampos();
	    deshabilitarCampos();
	    btnNuevo.setDisable(false);
	    btnGuardar.setDisable(true);
	    btnEliminar.setDisable(true);
	    btnCancelar.setDisable(true);
	    rellenarTablaPelicula();

	}
    }

    @FXML
    private void btnCancelar_click(ActionEvent event) {
	vaciarCampos();
	deshabilitarCampos();
	btnNuevo.setDisable(false);
	btnGuardar.setDisable(true);
	btnEliminar.setDisable(true);
	btnCancelar.setDisable(true);
    }

    @FXML
    private void btnBuscar_click(ActionEvent event) {
	dataPelicula.clear();
	String modoBusqueda = choiceBuscar.getValue();
	if (modoBusqueda.equals("Id")) {
	    int id = 0;
	    try {
		id = Integer.parseInt(txtBuscar.getText());
	    } catch (Exception ex) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("MoviesDataBase");
		alert.setHeaderText("Se ha producido un error en la búsqueda:");
		alert.setContentText("Debes ingresar un id válido.");

		alert.showAndWait();
		return;
	    }

	    PeliculaRepo peliculaRepo = new PeliculaRepo();
	    Pelicula pelicula = peliculaRepo.buscarPelicula(id);
	    if (pelicula != null) {
		dataPelicula.add(pelicula);
		labResultados.setText("Resultados: 1");
	    } else {
		labResultados.setText("Resultados: 0");
	    }
	    return;
	}

	PeliculaRepo peliculaRepo = new PeliculaRepo();
	ObservableList<Pelicula> peliculas = FXCollections.observableArrayList();

	switch (modoBusqueda) {
	case "Título":
	    String tituloBusqueda = txtBuscar.getText();
	    peliculas = peliculaRepo.buscarPorNombre(tituloBusqueda);
	    break;
	case "Director":
	    String directorBusqueda = txtBuscar.getText();
	    peliculas = peliculaRepo.buscarPorAutor(directorBusqueda);
	    break;
	case "Género":
	    String generoBusqueda = txtBuscar.getText();
	    peliculas = peliculaRepo.buscarPorGenero(generoBusqueda);
	    break;
	case "Ciclo":
	    String cicloBusqueda = txtBuscar.getText();
	    peliculas = peliculaRepo.buscarPorCiclo(cicloBusqueda);
	    break;
	default:
	    Alert alert = new Alert(AlertType.WARNING);
	    alert.setTitle("MoviesDataBase");
	    alert.setHeaderText("Se ha producido un error en la búsqueda:");
	    alert.setContentText("Modo de búsqueda incorrecto.");

	    alert.showAndWait();
	    return;
	}
	dataPelicula.setAll(peliculas);
	int resultados = peliculas.size();
	labResultados.setText("Resultados: " + resultados);
    }

    @FXML
    private void btnRefrescar_click(ActionEvent event) {
	rellenarTablaPelicula();
    }

    @FXML
    private void contextMenu_click(ContextMenuEvent event) {
	ContextMenu menu = new ContextMenu();
	MenuItem itemModificar = new MenuItem("Modificar");
	itemModificar.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
		Pelicula pelicula = tablaPelicula.getSelectionModel().getSelectedItem();
		if (pelicula == null) {
		    Alert alert = new Alert(AlertType.WARNING);
		    alert.setTitle("MoviesDataBase");
		    alert.setHeaderText("Se ha producido un error: ");
		    alert.setContentText("Debes seleccionar una película.");

		    alert.showAndWait();

		    return;
		}
		txtId.setText(String.valueOf(pelicula.getId()));
		txtTitulo.setText(pelicula.getTitulo());
		txtDirector.setText(pelicula.getDirector());
		choiceGenero.setValue(pelicula.getGenero());
		txtSoporte.setText(pelicula.getSoporte());
		txtYear.setText(String.valueOf(pelicula.getYear()));
		txtCiclo.setText(pelicula.getCiclo());

		habilitarCampos();
		btnNuevo.setDisable(true);
		btnGuardar.setDisable(false);
		btnEliminar.setDisable(false);
		btnCancelar.setDisable(false);

		// cambiar a la pestaÃ±a 1
		SingleSelectionModel<Tab> selectionModel = tabPanePelicula.getSelectionModel();
		selectionModel.select(0);

	    }
	});
	menu.getItems().add(itemModificar);
	tablaPelicula.setContextMenu(menu);
    }

    @FXML
    private void btnReporte_click(ActionEvent event) {
	Reporte reporte = new Reporte("peliculas");
	reporte.generarReporte();
    }

    private void colocarImagenBotones() {
	URL linkNuevo = getClass().getResource("/images/nuevo.png");
	URL linkGuardar = getClass().getResource("/images/guardar.png");
	URL linkEliminar = getClass().getResource("/images/eliminar.png");
	URL linkCancelar = getClass().getResource("/images/cancelar.png");
	URL linkBuscar = getClass().getResource("/images/buscar.png");
	URL linkRefrescar = getClass().getResource("/images/refrescar.png");
	URL linkReporte = getClass().getResource("/images/reporte.png");

	Image imagenNuevo = new Image(linkNuevo.toString(), 24, 24, false, true);
	Image imagenGuardar = new Image(linkGuardar.toString(), 24, 24, false, true);
	Image imagenEliminar = new Image(linkEliminar.toString(), 24, 24, false, true);
	Image imagenCancelar = new Image(linkCancelar.toString(), 24, 24, false, true);
	Image imagenBuscar = new Image(linkBuscar.toString(), 16, 16, false, true);
	Image imagenRefrescar = new Image(linkRefrescar.toString(), 16, 16, false, true);
	Image imagenReporte = new Image(linkReporte.toString(), 16, 16, false, true);

	btnNuevo.setGraphic((new ImageView(imagenNuevo)));
	btnGuardar.setGraphic((new ImageView(imagenGuardar)));
	btnEliminar.setGraphic((new ImageView(imagenEliminar)));
	btnCancelar.setGraphic((new ImageView(imagenCancelar)));
	btnBuscar.setGraphic((new ImageView(imagenBuscar)));
	btnRefrescar.setGraphic((new ImageView(imagenRefrescar)));
	btnReporte.setGraphic((new ImageView(imagenReporte)));

    }

    @FXML
    private void handleExit() {
	System.exit(0);
    }

    @FXML
    private void handleAbout() {
	Alert alert = new Alert(AlertType.INFORMATION);
	alert.setTitle("MoviesDataBase");
	alert.setHeaderText("Acerca de");
	alert.setContentText("Autor: Rubén Fernández \nhttps://github.com/loqum");

	alert.showAndWait();
    }
}
