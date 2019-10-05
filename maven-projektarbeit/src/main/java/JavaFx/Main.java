package JavaFx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	
	@Override
	public void start(Stage stage) throws Exception {
	
		 Parent root= FXMLLoader.load(getClass().getResource("test.fxml"));
		 stage.setTitle("Projektarbeit Johannes Schwegler");
		 Scene scene = new Scene(root);
		 scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
	     stage.setScene(scene);
	  
	     stage.show();
	}

	
	public static void main(String[] args) {
		
		launch(args);
		
	}
}