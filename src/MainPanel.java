import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class MainPanel extends JPanel {	
	Cell [][] cellArray = new Cell[Application.COLUMNS][Application.ROWS];
	
	MainPanel(){
//		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
/*		
 * 		###### On crée la grid panelée ici (des celle heritant de jpanel) #####
 */
		setLayout( new GridLayout( Application.ROWS, Application.COLUMNS ) );
		int curX = 0;
		int curRow = 0;
		while( curRow < Application.ROWS ){
			int curY = 0;
			int curCol = 0;
			while( curCol < Application.COLUMNS ){
				Cell cell = new Cell(curY, curX, curRow, curCol);
				cell.setBounds(curY, curX, Application.CELL_WIDTH, Application.CELL_HEIGHT);
				add(cell);
				cellArray[curCol][curRow] = cell;
				curY += Application.CELL_HEIGHT;
				curCol += 1;
			}
			curX += Application.CELL_WIDTH;
			curRow += 1;
		}
//		
//		repaint();
	}
}
