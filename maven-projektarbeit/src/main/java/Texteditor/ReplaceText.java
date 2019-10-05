package Texteditor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ReplaceText {

	/*
	 * Methode, die eine neue .specs-Datei erstellt
	 * 
	 * @param cssContainerRechtesBild Container für das Bild, welches rechts fließt
	 * 
	 * @param xPathImageRightFloat xPfad zum Bild, welches rechts fließt
	 * 
	 * @param List<int[]> margins List mit den Außenabständen
	 */

	public static void createFile(String cssContainerRechtesBild, String xPathImageRightFloat, List<int[]> margins)
			throws IOException {

		if (cssContainerRechtesBild == null || xPathImageRightFloat == null) {

			cssContainerRechtesBild = "Nicht vorhanden";
			xPathImageRightFloat = "Nicht vorhanden";
		}

		// Erstellen einer neuen Datei
		File file = new File(".\\src\\test\\resources\\Automatisierung WS1819\\Layouttest.gspec");

		int[] marginHeader = new int[4];
		int[] marginNavigation = new int[4];
		int[] marginArticle = new int[4];
		int[] marginAside = new int[4];
		int[] marginFooter = new int[4];
		try {
			marginHeader = margins.get(0);
			marginNavigation = margins.get(1);
			marginArticle = margins.get(2);
			marginAside = margins.get(3);
			marginFooter = margins.get(4);
		} catch (Exception e) {
			// Falls die Seite keine Margins aufweist, werden die Werte auf 0 gesetzt, damit
			// es nicht zu Problemen für den Writer kommt

			for (int i = 0; i < 4; i++) {
				marginHeader[i] = 0;
				marginNavigation[i] = 0;
				marginArticle[i] = 0;
				marginAside[i] = 0;
				marginFooter[i] = 0;

			}

		}

		// Erstellen einer neuen Datei. Falls bereits eine Datei mit dem gleichen Name
		// vorhanden ist, wird diese gelöscht
		if (file.createNewFile()) {
			System.out.println("File is created!");
		} else {
			deleteFile(Paths.get(".\\src\\test\\resources\\Automatisierung WS1819\\Layouttest.gspec"));
			file.createNewFile();

		}

		File fileToBeModified = new File(".\\src\\test\\resources\\Automatisierung WS1819\\Layouttest.gspec");

		// Template für die Fallstudie
		String oldContent = "@objects\r\n" + 
				"    body          xpath      /html/body\r\n" + 
				"    header        xpath      /html/body/header\r\n" + 
				"    nav           xpath      /html/body/nav\r\n" + 
				"    article       xpath      /html/body/article\r\n" + 
				"    aside         xpath      /html/body/aside\r\n" + 
				"    footer        xpath      /html/body/footer\r\n" + 
				"    img           css        img\r\n" + 
				"    Container     css        ContainerDesRechtenBildes\r\n" + 
				"    imgRight      xpath      RechtesBild\r\n" + 
				"    li-*          css        .navigation li\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"= Main section =\r\n" + 
				"     \r\n" + 
				"    header:\r\n" + 
				"       width <<${viewport.width() - marginHeader} px>>    \r\n" + 
				"       \r\n" + 
				"        \r\n" + 
				"    nav:\r\n" + 
				"       below header\r\n" + 
				"       inside body <<marginNavigationLeft>>px left\r\n" + 
				"       width ~ 32% of viewport/width\r\n" + 
				"\r\n" + 
				"    article:\r\n" + 
				"       below nav\r\n" + 
				"       inside body <<marginArticleLeft>>px left\r\n" + 
				"       width ~ 65% of viewport/width\r\n" + 
				"\r\n" + 
				"    aside:\r\n" + 
				"       below nav\r\n" + 
				"       inside body <<marginAsideRight>>px right \r\n" + 
				"       width ~ 32% of viewport/width  \r\n" + 
				"\r\n" + 
				"     \r\n" + 
				"    footer:\r\n" + 
				"       width <<${viewport.width() - marginFooter} px>>\r\n" + 
				"       below header\r\n" + 
				"       below nav\r\n" + 
				"       below article\r\n" + 
				"       below aside\r\n" + 
				"\r\n" + 
				"    img:\r\n" + 
				"       width 450px\r\n" + 
				"       height 450px  \r\n" + 
				"\r\n" + 
				"    imgRight:\r\n" + 
				"       inside Container 0px right  \r\n" + 
				"\r\n" + 
				"    @for [1-5] as index\r\n" + 
				"        li-1:\r\n" + 
				"            aligned horizontally all li-${index + 1}   \r\n" + 
				"";
		BufferedReader reader = null;

		FileWriter writer = null;

		try {
			reader = new BufferedReader(new FileReader(fileToBeModified));

			// Daten werden gelesen

			String line = reader.readLine();

			while (line != null) {
				oldContent = oldContent + line + System.lineSeparator();

				line = reader.readLine();
			}

			// Das Template wird um die übergebenen Werte ergänzt.

			String newContent = oldContent.replaceAll("ContainerDesRechtenBildes", cssContainerRechtesBild)
					.replaceAll("RechtesBild", xPathImageRightFloat)
					.replaceAll("marginHeader", Integer.toString(marginHeader[1] + marginHeader[3]))
					.replaceAll("marginNavigationLeft", Integer.toString(marginNavigation[1]))
					.replaceAll("marginArticleLeft", Integer.toString(marginArticle[1]))
					.replaceAll("marginAsideRight", Integer.toString(marginAside[3]))
					.replaceAll("marginFooter", Integer.toString(marginFooter[1] + marginFooter[3]))
					.replaceAll("<<", "").replaceAll(">>", "");

			writer = new FileWriter(fileToBeModified);

			writer.write(newContent);
		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			try {

				reader.close();

				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	/*
	 * Methode zum Löschen einer Datei
	 */
	public static void deleteFile(Path url) throws IOException {

		try {
			Files.deleteIfExists(url);
		} catch (NoSuchFileException e) {
			System.out.println("Die Datei oder das Verzeichnis existieren nicht");
		} catch (DirectoryNotEmptyException e) {
			System.out.println("Das Verzechnis ist nicht leer");
		} catch (IOException e) {
			System.out.println("Keine Berechtigungen");
		}

	}

}