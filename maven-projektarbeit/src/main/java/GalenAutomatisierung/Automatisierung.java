package GalenAutomatisierung;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Texteditor.ReplaceText;

public class Automatisierung {
	
	private static WebDriver driver;
	
	public static int[] getImageWidthAndHeight() {
		int[] imgWidthHeight = new int[2];

		WebElement img = driver.findElement(By.cssSelector("img"));

		// Get width of element.

		int ImageWidth = img.getSize().getWidth();
		imgWidthHeight[0] = ImageWidth;
		// System.out.println("Image width Is " + ImageWidth + " pixels");

		// Get height of element.
		int ImageHeight = img.getSize().getHeight();
		imgWidthHeight[1] = ImageHeight;
		// System.out.println("Image height Is " + ImageHeight + " pixels");
		return imgWidthHeight;

	}

	
	/*
	 * Methode, um den CSS-Selektor für ein WebElement zu generieren
	 * Stackoverlow, Get CSS selector string from WebElement in Selenium WebDriver - Java, in: 
	 * https://stackoverflow.com/questions/36546825/get-css-selector-string-from-webelement-in-selenium-webdriver-java/36547559#36547559,
	 * Stand:31.08.2019
	 */
	public static String CSS(WebElement element) {
		final String JS_BUILD_CSS_SELECTOR = "for(var e=arguments[0],n=[],i=function(e,n){if(!e||!n)return 0;f"
				+ "or(var i=0,a=e.length;a>i;i++)if(-1==n.indexOf(e[i]))return 0;re"
				+ "turn 1};e&&1==e.nodeType&&'HTML'!=e.nodeName;e=e.parentNode){if("
				+ "e.id){n.unshift('#'+e.id);break}for(var a=1,r=1,o=e.localName,l="
				+ "e.className&&e.className.trim().split(/[\\s,]+/g),t=e.previousSi"
				+ "bling;t;t=t.previousSibling)10!=t.nodeType&&t.nodeName==e.nodeNa"
				+ "me&&(i(l,t.className)&&(l=null),r=0,++a);for(var t=e.nextSibling"
				+ ";t;t=t.nextSibling)t.nodeName==e.nodeName&&(i(l,t.className)&&(l"
				+ "=null),r=0);n.unshift(r?o:o+(l?'.'+l.join('.'):':nth-child('+a+'" + ")'))}return n.join(' > ');";
		;

		String selector = "";
		JavascriptExecutor js = (JavascriptExecutor) driver;

		selector = (String) js.executeScript(JS_BUILD_CSS_SELECTOR, element);
		return selector;
	}

	public static String[] getImageWithRightFloat() {
        try {
		// Auffinden aller Bilder auf der Webseite
		List<WebElement> bilder = driver.findElements(By.cssSelector("img"));
		String xPath = "";
		String cssSelektor = "";

		// Schleife über alle Elemente, um ein Bild mit "float:right" zu finden
		for (WebElement webElement : bilder) {

			// Speichern der
			String bildRechterFloat = webElement.getCssValue("float");

			if (bildRechterFloat.equals("right")) {

				// Aufrufen der Funktion, die den absoluten xPath zurückliefert
				xPath = getAbsoluteXPath(webElement);

				// Auffinden des Elten-Elements -> könnte man sich theoretisch sparen, da das
				// Eltern-Element auch im absoluten Pfad vorhanden ist
				JavascriptExecutor js = (JavascriptExecutor) driver;
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				WebElement parentElement = (WebElement) executor.executeScript("return arguments[0].parentNode;",
						webElement);

				boolean rightParent = false;

				// Schleife, um den richtigen Container zu finden
				while (rightParent == false) {
					// Wenn das Element einer der erlaubten Inhaltselemente ist, wird der
					// CSS-Selektor für das Element gespeichert und die Schleife beendet
					System.out.println(parentElement.getCssValue("display") + parentElement.getTagName());
					if (parentElement.getTagName().contains("header") || parentElement.getTagName().contains("section")
							|| parentElement.getTagName().contains("article")
							|| parentElement.getTagName().contains("aside")
							|| parentElement.getTagName().contains("nav")
							|| parentElement.getTagName().contains("footer")) {

						cssSelektor = CSS(parentElement);

						rightParent = !false;
					} else {

						String path = getAbsoluteXPath(parentElement);
						WebElement element = driver.findElement(By.xpath(path));
						parentElement = (WebElement) executor.executeScript("return arguments[0].parentNode;", element);

					}

				}
				break;
			}
		}
		// Speichern der Strings in einem Array
		String[] childParent = new String[2];
		childParent[0] = xPath;
		childParent[1] = cssSelektor;
		return childParent;
		}catch (Exception e) {
			  System.out.println("Das Bild mit dem rechten Float ist nicht vorhanden");
			  return new String[2];
		}

	}

	public static List<int[]> getMarginsOfElements() {
		List<int[]> marginList = new ArrayList<int[]>();
		try {
		marginList.add(getMarginOfElement("/html/body/header"));
		marginList.add(getMarginOfElement("/html/body/nav"));
		marginList.add(getMarginOfElement("/html/body/article"));
		marginList.add(getMarginOfElement("/html/body/aside"));
		marginList.add(getMarginOfElement("/html/body/footer"));
         
		return marginList;
		}catch (Exception e) {
			
			System.out.println("Es fehlt eines der Elemente. Somit ist kein Test möglich");
			return marginList;
		}

	}

	/*
	 * Liefert die Außenabstände eines Elementes zurück
	 */
	public static int[] getMarginOfElement(String xpath) {
		int[] margin = new int[4];

		WebElement element = driver.findElement(By.xpath(xpath));
		String marginTop = element.getCssValue("margin-top");

		margin[0] = (int) Double.parseDouble(marginTop.replaceAll("px", ""));

		String marginLeft = element.getCssValue("margin-left");
		margin[1] = (int) Double.parseDouble(marginLeft.replaceAll("px", ""));

		String marginBottom = element.getCssValue("margin-bottom");
		margin[2] = (int) Double.parseDouble(marginBottom.replaceAll("px", ""));

		String marginRight = element.getCssValue("margin-right");
		margin[3] = (int) Double.parseDouble(marginRight.replaceAll("px", ""));

		return margin;

	}


   /*
    * Methode, um den absoluten xPfad zu einem WebElement zu erhalten
    * Stackoverflow, Want to Retrieve Xpath of Given WebElement, in:
    * https://stackoverflow.com/questions/47069382/want-to-retrieve-xpath-of-given-webelement,
    * Stand 25.08.2019
    */

	public static String getAbsoluteXPath(WebElement element) {
		return (String) ((JavascriptExecutor) driver)
				.executeScript("function absoluteXPath(element) {" + "var comp, comps = [];" + "var parent = null;"
						+ "var xpath = '';" + "var getPos = function(element) {" + "var position = 1, curNode;"
						+ "if (element.nodeType == Node.ATTRIBUTE_NODE) {" + "return null;" + "}"
						+ "for (curNode = element.previousSibling; curNode; curNode = curNode.previousSibling) {"
						+ "if (curNode.nodeName == element.nodeName) {" + "++position;" + "}" + "}" + "return position;"
						+ "};" +

						"if (element instanceof Document) {" + "return '/';" + "}" +

						"for (; element && !(element instanceof Document); element = element.nodeType == Node.ATTRIBUTE_NODE ? element.ownerElement : element.parentNode) {"
						+ "comp = comps[comps.length] = {};" + "switch (element.nodeType) {" + "case Node.TEXT_NODE:"
						+ "comp.name = 'text()';" + "break;" + "case Node.ATTRIBUTE_NODE:"
						+ "comp.name = '@' + element.nodeName;" + "break;" + "case Node.PROCESSING_INSTRUCTION_NODE:"
						+ "comp.name = 'processing-instruction()';" + "break;" + "case Node.COMMENT_NODE:"
						+ "comp.name = 'comment()';" + "break;" + "case Node.ELEMENT_NODE:"
						+ "comp.name = element.nodeName;" + "break;" + "}" + "comp.position = getPos(element);" + "}" +

						"for (var i = comps.length - 1; i >= 0; i--) {" + "comp = comps[i];"
						+ "xpath += '/' + comp.name.toLowerCase();" + "if (comp.position !== null) {"
						+ "xpath += '[' + comp.position + ']';" + "}" + "}" +

						"return xpath;" +

						"} return absoluteXPath(arguments[0]);", element);
	}
	
	
	
      /*
       * Aufruf der Klasse, die die .specs-Datei erstellt. 
       */
	public static void generierungSpecsDatei() throws IOException {
		String[] childAndContainer = getImageWithRightFloat();
		String xPathImageRightFloat = childAndContainer[0];
		String cssParentImageRightFloat = childAndContainer[1];

		List<int[]> margins = getMarginsOfElements();
		ReplaceText.createFile(cssParentImageRightFloat, xPathImageRightFloat, margins);

	}
	
}
