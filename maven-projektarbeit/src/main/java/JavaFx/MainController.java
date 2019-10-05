package JavaFx;

import java.io.File;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class MainController {



	@FXML
	private Button button__choose, button__destination, submit;

	@FXML
	private ListView<String> listView, listViewDestination;

	@FXML
	private Text responseText, correctFile, correctDestination;

	boolean fileAvailable = false;
	boolean destinationAvailable = false;

	public static String filePath = "";
	public static String destinationPath = "";

	/*
	 * Klasse zur Initialisierung. Diese wird ausgeführt, bevor die Anwendung
	 * startet.
	 */
	@FXML
	public void initialize() {
		correctFile.setVisible(false);
		correctDestination.setVisible(false);
		submit.setDisable(true);
	}

	/*
	 * Event, der bei Klick auf "Datei wählen" ausgeführt wird.
	 */
	public void buttonChooseAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(null);
		correctFile.setVisible(false);
		checkSubmit();

		if (selectedFile != null) {

			String name = selectedFile.getName();

			if (!name.contains("html")) {
				fileAvailable = false;
				responseText.setText("Diese Datei ist keine HTML-Datei. Bitte wählen Sie erneut.");
				listView.getItems().clear();
				return;
			}

			fileAvailable = true;
			responseText.setText("");
			correctFile.setVisible(true);
			listView.getItems().add(selectedFile.getAbsolutePath());

		} else {

		}

		checkSubmit();

	}

	/*
	 * Event, der bei Klick auf "Speicherort wählen" ausgeführt wird.
	 */
	public void buttonChooseDestination(ActionEvent event) {
		DirectoryChooser chooser = new DirectoryChooser();
		File selectedDirectory = chooser.showDialog(null);
		correctDestination.setVisible(false);

		if (selectedDirectory != null) {
			destinationAvailable = true;
			correctDestination.setVisible(true);
			listViewDestination.getItems().add(selectedDirectory.getPath());
		}

		else {
			destinationAvailable = false;
			listViewDestination.getItems().clear();
			return;

		}
		checkSubmit();

	}

	/*
	 * Wenn Datei und Speicherziel vorhanden sind, wird der Button aktiviert
	 */
	public void checkSubmit() {
		if (fileAvailable == true && destinationAvailable == true) {
			submit.setDisable(false);

		}
	}

	/*
	 * Event, der bei Klick auf den unteren Button ausgeführt wird. Hierbei werden
	 * die Pfade gespeichert und das Fenster geschlossen.
	 */
	public void setPath(ActionEvent event) {
		filePath = (String) listView.getItems().get(0);
		destinationPath = (String) listViewDestination.getItems().get(0);
		
		Platform.exit();

	}

	public String getFilePath() {
		return filePath;
	}

	
	public String getDestinationPath() {
		return destinationPath;
	}



}
