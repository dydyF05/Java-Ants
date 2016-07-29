import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class loadXml {
	public static void init(){
		if(!isMapUnplayed()){
			System.out.println("impossible d'écraser une map en train d'etre joué");
			return;
		}
		try {
			String path = new File("").getAbsolutePath()+"\\save.xml";
			File fXmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			doc.getDocumentElement().normalize();

//			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
//			On récupère les infos de base de la config.
			double evap = Double.parseDouble(doc.getDocumentElement().getAttribute("evaporation"));
			double speed = Double.parseDouble(doc.getDocumentElement().getAttribute("speed"));
			double maxAnts = Integer.parseInt(doc.getDocumentElement().getAttribute("maxAnts"));
			double maxFood = Integer.parseInt(doc.getDocumentElement().getAttribute("maxFood"));
			
			
//			On récupère le contenu de base de chaque cellule
			
			NodeList nCols = doc.getElementsByTagName("col");
			for (int col_i = 0; col_i < nCols.getLength(); col_i++) {
				Node nCol = nCols.item(col_i);
				NodeList nCells = ((Element) nCol).getElementsByTagName("cell");
				for (int row_i = 0; row_i < nCells.getLength(); row_i++) {
					Node nCell = nCells.item(row_i);
					if(saveAsXml.doesCellExist(col_i, row_i)){
						String status = ((Element) nCell).getAttribute("status");
						switch(status){
							case "nest":
								MainPanel.cellArray[col_i][row_i].setAsNest();
								break;
							case "food":
//								System.out.println("FOOD FOUND");
								MainPanel.cellArray[col_i][row_i].setAsFood();
								break;
							case "obstacle":
								MainPanel.cellArray[col_i][row_i].setAsObstacle();
								break;
							default:
								MainPanel.cellArray[col_i][row_i].emptyCell();
								break;
						}
						
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static boolean isMapUnplayed(){
		return Application.firstLaunch;
	}
}
