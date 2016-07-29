import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class saveAsXml {
	public static void init(){
		if(!isAppliSavable())
			return;
		try {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		
			// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("fourmis");
		doc.appendChild(rootElement);
		Attr attr_speed = doc.createAttribute("speed");
		attr_speed.setValue(Double.toString(Application.attente));
		Attr attr_maxFood = doc.createAttribute("maxFood");
		attr_maxFood.setValue(Integer.toString(Application.maxFoodPerCell));
		Attr attr_evap = doc.createAttribute("evaporation");
		attr_evap.setValue(Double.toString(CellPanel.rateEvaporation));
		Attr attr_maxAnts = doc.createAttribute("maxAnts");
		attr_maxAnts.setValue(Integer.toString(Application.antsNumber));
		rootElement.setAttributeNode(attr_speed);
		rootElement.setAttributeNode(attr_maxFood);
		rootElement.setAttributeNode(attr_evap);
		rootElement.setAttributeNode(attr_maxAnts);
		
		
		int curCol = 0;
		int curRow = 0;
		while( curCol < Application.COLUMNS ){
			Element col = doc.createElement("col");
			Attr attr_line = doc.createAttribute("line");
			attr_line.setValue(Integer.toString(curCol));
			col.setAttributeNode(attr_line);
			curRow = 0;
			while( curRow < Application.ROWS ){
				if(doesCellExist(curCol, curRow)){
					Element cellElement = doc.createElement("cell");
					Attr status = doc.createAttribute("status");
					if(MainPanel.cellArray[curCol][curRow].hasNest())						
						status.setValue("nest");
					else if(MainPanel.cellArray[curCol][curRow].hasFood())
						status.setValue("food");
					else if(MainPanel.cellArray[curCol][curRow].hasObstacle())
						status.setValue("obstacle");
					cellElement.setAttributeNode(status);
					col.appendChild(cellElement);
				}
				curRow += 1;
		    }
			curCol += 1;
			rootElement.appendChild(col);
		}
			

		
		

		
		
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("").getAbsolutePath()+"\\save.xml");
		
		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);
		
		transformer.transform(source, result);
		
		System.out.println("Map saved!");
		
		  } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
	}
	public static boolean doesCellExist(int curCol, int curRow){
		try{
			if(MainPanel.cellArray[curCol][curRow] == null)
				return false;
			return true;
		}catch (ArrayIndexOutOfBoundsException e) {
			return false;
	     }
	}
	private static boolean isAppliSavable(){
		if(MainPanel.cellArray[0][0] == null)
			return false;
		return true;
	}
}
