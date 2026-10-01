package es.dam1.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
	
	

	public static void main(String[] args) {

		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		
		FXMLLoader loader =
                new FXMLLoader(getClass().getResource("/tareas.fxml"));

		//el método load te carga el nodo con el panel, que sea el
		//que sea heredará de Parent:
        Scene scene = new Scene(loader.load());
        
        String css = this.getClass().getResource("/estilos.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setTitle("GESTOR TAREAS");
        stage.setScene(scene);
        stage.show();
		
	}

}
