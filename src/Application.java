import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Application {
	public static final int WINDOW_WIDTH = 600;
	public static final int WINDOW_HEIGHT = 600;
	public static final int ANT_PANEL_WIDTH = 600 - 100;
	public static final int ANT_PANEL_HEIGHT = WINDOW_HEIGHT - 100;
	public static final int ROWS = 32;
	public static final int COLUMNS = 40;
	public static int CELL_WIDTH = ANT_PANEL_WIDTH / COLUMNS;
	public static int CELL_HEIGHT = ANT_PANEL_HEIGHT / ROWS;
	public static int maxFoodPerCell = 100;
	public static boolean paused = false;
	public static boolean firstLaunch = true;
	public static int totalFoodInEnvironment = 0;
	public static double attente = 10.0;
	public static int pasSimulation = 0;
	public static double totalNestPheromons = 0.0;
	public static double totalFoodPheromons = 0.0;
	private static boolean configDone = false;
	
	public static int antsNumber = 100;


	public static CellPanel nestCell = null;
	public static Set<CellPanel> allCells = new HashSet<CellPanel>();
	public static Set<CellPanel> foodSet = new HashSet<CellPanel>();
	public static Set<CellPanel> obstacleSet = new HashSet<CellPanel>();
	public static Set<Ant> allAnts = new HashSet<Ant>();


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new InfosFrame("parametres");
		
	}
	public static void createAntFrame(){
		if(configDone)
			return;
		configDone = true;
		System.out.println("evap: "+CellPanel.rateEvaporation);
		JFrame frame = new JFrame ("Fourmis");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setResizable(false);

		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());

		MainPanel panel = new MainPanel();
		panel.setPreferredSize(new Dimension(ANT_PANEL_WIDTH, ANT_PANEL_HEIGHT));
		
		ConfigurationPanel confPanel = new ConfigurationPanel();
		
		container.add(panel);
		container.add(confPanel, BorderLayout.SOUTH);
		confPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT - ANT_PANEL_HEIGHT));
		
		ControlPanel controlPanel = new ControlPanel();
		container.add(controlPanel, BorderLayout.NORTH);
		controlPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT - ANT_PANEL_HEIGHT));
		
		frame.add(container);

		frame.pack();
		frame.setVisible(true);
	}
	public static void moveOneStep(){
		try{
			do{
				pasSimulation++;
//				System.out.println("#### start step ####");
				if(firstLaunch){
					firstLaunch = false;
					for(int i = 0; i < antsNumber; i++){
						allAnts.add(new Ant(nestCell));
					}
				}
				
				Iterator<Ant> iterator = allAnts.iterator();
				while (iterator.hasNext()){
					Ant ant = iterator.next();
					ant.live();
				}
				Iterator<CellPanel> cellIte = allCells.iterator();
				while (cellIte.hasNext()){
					CellPanel cell = cellIte.next();
					cell.evaporate();
				}
				Application.totalNestPheromons = Application.totalNestPheromons*CellPanel.rateEvaporation;
				Application.totalFoodPheromons = Application.totalFoodPheromons*CellPanel.rateEvaporation;
				
				ControlPanel.udpateSteps();
//				System.out.println("#### end step ####");
			} while ( Application.paused == false && totalFoodInEnvironment > 0);			
			Thread.sleep((int) attente);
//			ControlPanel.udpateSteps();
		}catch(InterruptedException e){
			e.printStackTrace();
//			ControlPanel.udpateSteps();
		}		
	}
}
