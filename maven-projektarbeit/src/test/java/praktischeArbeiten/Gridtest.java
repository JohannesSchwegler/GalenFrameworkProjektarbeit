package praktischeArbeiten;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.lang.String;
import com.galenframework.api.Galen;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;


public class Gridtest {

	private WebDriver driver;

	@Before
	public void setUp() throws IOException {

		String pathDriver = "chromedriver.exe";
		
	
		String URL =  "\\src\\test\\resources\\grid\\layouttest.html";
		String path = System.getProperty("user.dir");
	
		System.setProperty("webdriver.chrome.driver", pathDriver);

		// Erstellen eines neuen Drivers
		driver = new ChromeDriver();
		// Gr��e des Drivers festlegen
		driver.manage().window().setSize(new Dimension(1920, 1080));
		// index.html
		driver.get(path+URL);

	}

	@Test
	public void homePageLayoutTest() throws IOException {

		String gspecLocation = ".\\src\\test\\resources\\Grid\\layouttest.gspec";

		// Erstellen eines LayoutReport Objektes
		// Die checkLayout-Funktion �berpr�ft das Layout und gibt ein
		// Layoutreport-Objekt zur�ck

		LayoutReport layoutReport = Galen.checkLayout(driver, gspecLocation, Arrays.asList("desktop"));

		// Erstellt eine Liste mit GalenTestInfos
		List<GalenTestInfo> tests = new LinkedList<GalenTestInfo>();

		// Erstellen eines GalenTestInfo-Objektes
		GalenTestInfo test = GalenTestInfo.fromString("Gridlayout-Test Ergebnisse");

		// // Holt den Layoutreport und ordnet es einem Test Objekt zu
		test.getReport().layout(layoutReport, "Check Gridlayout");

		// F�gt ein Objekt zur Testliste hinzu
		tests.add(test);

		// Erstellen eines htmlReportbuilderObjektes
		HtmlReportBuilder htmlReportBuilder = new HtmlReportBuilder();

		// Erstellt einen Bericht(report) im /ws1819target Ordner
		htmlReportBuilder.build(tests, ".\\src\\test\\resources\\Grid\\ErgebnisseDesGridLayouts");

		// Wenn Fehler im Test auftreten, schl�gt dieser fehl
		if (layoutReport.errors() > 0) {
			Assert.fail("Der Layouttest ist fehlgeschlagen");
		}
		openFile(true);
	}

	public void openFile(boolean oeffnen) throws IOException {
		if (oeffnen) {
			File datei = new File(
					".\\src\\test\\resources\\Grid\\ErgebnisseDesGridLayouts\\report.html");

			// Checkt ob der Desktop unterst�tzt wird
			if (!Desktop.isDesktopSupported()) {
				System.out.println("Desktop is not supported");
				return;
			}

			Desktop desktop = Desktop.getDesktop();
			if (datei.exists())
				desktop.open(datei);

		} else {
			return;
		}

	}

	@After
	public void tearDown() {
		driver.quit();
	}

}