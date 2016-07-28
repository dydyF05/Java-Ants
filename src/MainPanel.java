import java.awt.GridLayout;

import javax.swing.JPanel;

public class MainPanel extends JPanel {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static CellPanel [][] cellArray = new CellPanel[Application.COLUMNS][Application.ROWS];
	
	MainPanel(){
//		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
/*		
 * 		###### On crée la grid panelée ici (des cell heritant de jpanel) #####
 */
		setLayout( new GridLayout( Application.ROWS, Application.COLUMNS ) );
		int curX = 0;
		int curRow = 0;
		while( curRow < Application.ROWS ){
			int curY = 0;
			int curCol = 0;
			while( curCol < Application.COLUMNS ){
				CellPanel cell = new CellPanel(curX, curY,  curCol, curRow);
				cell.setBounds(curX, curY, Application.CELL_WIDTH, Application.CELL_HEIGHT);
				add(cell);
				cellArray[curCol][curRow] = cell;
				curY += Application.CELL_HEIGHT;
				curCol += 1;
				Application.allCells.add(cell);
			}
			curX += Application.CELL_WIDTH;
			curRow += 1;
		}
		
//		Application.allCells.sort();
//		
//		repaint();
	}
}
