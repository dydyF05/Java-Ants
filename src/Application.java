import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Application {
	public static final int WINDOW_WIDTH = 600;
	public static final int WINDOW_HEIGHT = 600;
	public static final int ANT_PANEL_WIDTH = 600 - 100;
	public static final int ANT_PANEL_HEIGHT = WINDOW_HEIGHT - 100;
	public static final int ROWS = 25;
	public static final int COLUMNS = 25;
	public static int CELL_WIDTH = ANT_PANEL_WIDTH / COLUMNS;
	public static int CELL_HEIGHT = ANT_PANEL_HEIGHT / ROWS;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame ("Fourmis");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setResizable(false);

		JPanel container = new JPanel();		
		container.setLayout(new BorderLayout());

		MainPanel panel = new MainPanel();
		panel.setPreferredSize(new Dimension(Application.ANT_PANEL_WIDTH, Application.ANT_PANEL_HEIGHT));
		
		ConfigurationPanel confPanel = new ConfigurationPanel();
		
		container.add(panel);
		container.add(confPanel, BorderLayout.SOUTH);
		confPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT - ANT_PANEL_HEIGHT));
		
		frame.add(container);

		frame.pack();
		frame.setVisible(true);
	}

}
