package GalenFramework;

import com.galenframework.api.Galen;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;
import GalenAutomatisierung.Automatisierung;
import JavaFx.Main;
import JavaFx.MainController;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
/**
 * Johannes Schwegler.
 */
public class GalenTest {
	
	private WebDriver driver;
	
	String filePath;
	String destinationPath;
	
	@Before
	public void setUp() throws IOException {

	    String[] arguments = new String[] {"123"};
	    //Öffnen des JavaFX-Fensters
	    Main.main(arguments);
	    filePath=MainController.filePath;
	    destinationPath=MainController.destinationPath;
	 
	    
	    // Falls das Fenster geschlossen wird, bricht der Test ab.
		if(filePath=="" || destinationPath=="") {
			return;
		}
		
		
		System.setProperty("webdriver.chrome.driver",
				".\\chromedriver.exe");
		// Create a Chrome Driver
	
		driver = new ChromeDriver();
		
		// Set the browser size for desktop
		driver.manage().window().setSize(new Dimension(1920, 1080));
		// Go to swtestacademy.com
		driver.get(filePath);
		
	
		Automatisierung.generierungSpecsDatei();

	
	}

    /*
     * Methode, um aus jedem / ein // zu machen, dann sonst der Zielpfad nicht angenommen wird. Alternativ kann man das Ganze auch über die Methode replace() lösen
     */
    public String duplicateChars(String path) {
		
    	String newPath="";
    	for(int i=0; i<path.length();i++) {
    		if(path.charAt(i)=='\\') {
    			newPath+='\\';
    			
    		}
    		
    		newPath+=path.charAt(i);
    	}
    	return newPath;
    }
	
	
	@Test
	public void homePageLayoutTest() throws IOException {

		String gspecLocation = ".\\src\\test\\resources\\Automatisierung WS1819\\Layouttest.gspec";

		// Erstellen eines LayoutReport Objektes
		// Die checkLayout-Funktion überprüft das Layout und gibt ein
		// Layoutreport-Objekt zurück

		LayoutReport layoutReport = Galen.checkLayout(driver, gspecLocation, Arrays.asList("desktop"));

		// Erstellt eine Liste mit GalenTestInfos
		List<GalenTestInfo> tests = new LinkedList<GalenTestInfo>();

		// Erstellen eines GalenTestInfo-Objektes
		GalenTestInfo test = GalenTestInfo.fromString("Automatisierte Ergebnisse");

		// // Holt den Layoutreport und ordnet es einem Test Objekt zu
		test.getReport().layout(layoutReport, "Check Gridlayout");

		// Fügt ein Objekt zur Testliste hinzu
		tests.add(test);

		// Erstellen eines htmlReportbuilderObjektes
		HtmlReportBuilder htmlReportBuilder = new HtmlReportBuilder();

		// Erstellt einen Bericht(report) im /ws1819target Ordner
		try {
		String destination=duplicateChars(destinationPath);
	
		
		htmlReportBuilder.build(tests, destination);
		

		}

		catch(Exception e) {
			System.out.println(e.getMessage());
		
		}

		// Wenn Fehler im Test auftreten, schlägt dieser fehl
		if (layoutReport.errors() > 0) {
			Assert.fail("Der Layouttest ist fehlgeschlagen");
		}
	
	}

	@After
	public void tearDown() {
		driver.quit();
	}

}