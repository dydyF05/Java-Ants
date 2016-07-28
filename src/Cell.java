import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

public class Cell extends Observable {
//	##### Cell environment parameters #####
	public static double rateEvaporation = .9;
	public static double pheromonDroppedOnVisit = 10.0;
	public static double maxLevPheromone = 100.0;
	public static double phLeftAfterMaxVisitAndHundredSteps = maxLevPheromone * (Math.pow(rateEvaporation, 50));
	public static double currentMaxPherLev = 1;
	public static double maxNestPheromon = 1000.0;
	public static double nestPhLeftAfterMaxVisitSteps = maxNestPheromon * (Math.pow(rateEvaporation, 100));
	public static double currentMaxNestPherLev = 1;
	
//	##### OwnCell's properties #####
	public int antsNumber = 0;
	private String status = "empty";	
	private Set<Ant> ants = new HashSet<Ant>();
	
	Cell(){
		this.addObserver(new CellObserver());
	}
	
	
}
