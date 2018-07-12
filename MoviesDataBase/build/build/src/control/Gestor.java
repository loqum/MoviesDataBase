package control;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Gestor extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {

	Parent root = FXMLLoader.load(getClass().getResource("Pelicula.fxml"));
	Scene scene = new Scene(root);

	stage.setResizable(true);
	stage.setTitle("MoviesDataBase");
	stage.getIcons().add(new Image("/images/movie.png"));

	// al cerrar la ventana
	stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

	    @Override
	    public void handle(WindowEvent e) {
		Platform.exit();
		System.exit(0);
	    }
	});

	stage.setScene(scene);
	stage.show();
    }

    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
	launch(args);
    }

}


