import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class Cell extends JPanel{
	public int column;
	public int row;
	public int x;
	public int y;
	
	Cell(int x, int y, int column, int row){
		column = column;
		row = row;
		x=x;
		y=y;
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		setLayout( new FlowLayout(1) );
		this.setBackground(Color.white);
		System.out.println("Instanciated col: "+ column + ", row: "+ row+"    x: "+x+", y:"+y);
//		repaint();
	}
}
