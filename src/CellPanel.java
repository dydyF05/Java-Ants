import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class CellPanel extends JPanel implements MouseListener, Comparable<CellPanel>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int column;
	public int row;
	public int x;
	public int y;
	public int antsNumber = 0;
	public static int cellCount = 0;
	public static Color emptyColor = Color.WHITE;
	public static Color nestColor = Color.BLACK;
	public static Color foodColor = Color.RED;
	public static Color antColor = Color.BLUE;
	public static Color foodOnAnt = Color.GRAY;
	public static Color obstacleColor = Color.GRAY;
	public double currentPheromoneLev = 0;
	public double nestPheromonLev = 0;
	private boolean hasObstacle = false;
	private boolean hasNest = false;
	public int foodLeft = 0;
	Graphics g = null;
	private Set<Ant> visitingAntsSearchingFood = new HashSet<Ant>();
	private Set<Ant> visitingAntsSearchingNest = new HashSet<Ant>();
	
//	##### Cell environment parameters #####
	public static double rateEvaporation = .9;
	public static double pheromonDroppedOnVisit = 10.0;
	public static double maxLevPheromone = 100.0;
	public static double phLeftAfterMaxVisitAndHundredSteps = maxLevPheromone * (Math.pow(rateEvaporation, 50));
	public static double currentMaxPherLev = 1;
	public static double maxNestPheromon = 1000.0;
	public static double nestPhLeftAfterMaxVisitSteps = maxNestPheromon * (Math.pow(rateEvaporation, 50));
	public static double currentMaxNestPherLev = 1;

	
	CellPanel(int x, int y, int column, int row){
		this.addMouseListener(this);		
		cellCount++;
		this.column = column;
		this.row = row;
		this.x=x;
		this.y=y;
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		setLayout( new FlowLayout(1) );
		this.setBackground(emptyColor);
//		repaint();
	}
//	##### Cell status #####	
	public boolean hasAnt(){
		return (this.antsNumber > 0) ? true: false;
	}
	public boolean hasObstacle() {
		return hasObstacle;
	}
	public boolean hasNest() {
		return hasNest;
	}
	public boolean hasFood() {
		if(this.foodLeft > 0)
			return true;
		this.foodLeft = 0;
		return false;
	}
	public boolean hasPheromons(){
		if(this.currentPheromoneLev > 0.0)
			return true;
		return false;
	}
	public boolean hasNestPheromons(){
		if(this.nestPheromonLev > 0.0)
			return true;
		return false;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
//		System.out.println("Clicked on cell: "+ column + ", row: "+ row+"    x: "+x+", y:"+y);
		
	}
	
	private void emptyCell(){
		if(this.hasNest()){
			Application.nestCell = null;
		};
		if(this.hasFood()){
			Application.foodSet.remove(this);
			Application.totalFoodInEnvironment -= this.foodLeft;
		};
		if(this.hasObstacle()){
			Application.obstacleSet.remove(this);
		}
		this.hasObstacle = false;
		this.hasNest = false;
		this.foodLeft = 0;
		this.setBackground(emptyColor);
	}
	private void setAsObstacle(){
		this.emptyCell();
		this.hasObstacle = true;
		this.setBackground(Color.GRAY);
		
		Application.obstacleSet.add(this);
	}
	private void setAsFood(){
		this.emptyCell();
		this.foodLeft = Application.maxFoodPerCell;
		Application.totalFoodInEnvironment += this.foodLeft;
		Application.foodSet.add(this);
		this.setBackground(Color.RED);
	}
	private void colorFood(){
		int red = 255;
		int green = 255 - ( ( (int) ( (this.foodLeft)*2.55 ) ) );
		int blue = 255 - ( ( (int) ( (this.foodLeft)*2.55 ) ) );
		Color c = new Color(red, green, blue);
		this.clearAntDrawing(c);
		this.setBackground(c);
	}
	public void recolor(){
		if(this.hasAnt()){
//			System.out.println("la fourmis a bien une ant");
//			this.g.setColor(antColor);
			this.drawAnt();
			return;
		};
		if(this.hasNest()){
//			System.out.println(this + " est bien recolorié comme nid");
			this.g.setColor(nestColor);
			this.clearAntDrawing(nestColor);
			this.setBackground(nestColor);
			return;
		};
		if(this.hasFood()){
			this.colorFood();			
			return;
		};
		if(this.hasObstacle()){
			this.g.setColor(obstacleColor);
			this.clearAntDrawing(obstacleColor);
			this.setBackground(obstacleColor);
//			this.drawAnt();
			return;
		}		
//		On est dans le cas où la cell n'a ni nid, ni obstacle ni bouffe il faut checker 
//		si elle a des phéromones et pas de fourmis
		if(this.hasPheromons() && !this.hasAnt()){
			this.colorWithPheromons();
//			this.drawAnt();
			return;
		}
		if(this.hasNestPheromons() && !this.hasAnt()){
			this.colorWithNestPheromons();
//			this.drawAnt();
			return;
		}
		this.g.setColor(emptyColor);
		this.setBackground(emptyColor);
//		this.drawAnt();
//		super.repaint();
//		return;
	}
	private void drawAnt(){
//		this.setBackground(antColor);
//		if(this.visitingAntsSearchingFood.size() > 0){
//			this.g.fillOval(0, 0, _x, _y);
//			this.g.drawOval(x, y, width, height);
//		}
		Color firstC = this.g.getColor();
		int _w = (int) (this.getSize().width * (9.2/10)) ;
		int _h = (int) (this.getSize().height * (9.2/10)) ;
		int _x = (int) (this.getSize().width - _w);
		int _y = (int) (this.getSize().height - _h);
		this.g.setColor(antColor);
		this.g.fillOval(_x, _y, _w, _h);
		if (visitingAntsSearchingNest.size() > 0){
//			System.out.println("bouuuuffe");
			int _w_pher = (int) (_w * .5);
			int _h_pher = (int) (_h * .5);
			int _x_pher = (int) (this.getSize().width - _w_pher)+3;
			int _y_pher = (int) (this.getSize().height - _h_pher)+3;
			this.g.setColor(foodColor);
			this.g.fillOval((int)_x_pher/2, (int)_y_pher/2, _w_pher, _h_pher);
		};
		this.g.setColor(firstC);
	}
	private void clearAntDrawing(Color fillColor){
		this.g.setColor(fillColor);
		int _w = (int) (this.getSize().width * (9.2/10)) ;
		int _h = (int) (this.getSize().height * (9.2/10)) ;
		int _x = (int) (this.getSize().width - _w);
		int _y = (int) (this.getSize().height - _h);
		this.g.fillOval(_x, _y, _w, _h);
	}
	private void colorWithNestPheromons(){
		if(this.hasNestPheromons()){
			int red = 255 / ( 1 + (int) this.nestPheromonLev);
			int green = 255 / ( 1 + (int) this.nestPheromonLev);
			int blue = 255 / ( 1 + (int) this.nestPheromonLev);
			Color c = new Color(red, green, blue);
			this.clearAntDrawing(c);
			this.setBackground(c);
		}
	}
	private void colorWithPheromons(){
		if(this.hasPheromons()){
			int red = 255;
			int green = 255;
			int blue = 255 / ( 1 + (int) this.currentPheromoneLev);
			Color c = new Color(red, green, blue);
			this.clearAntDrawing(c);
			this.setBackground(c);
		}
	}
	private void setAsNest(){
		if(Application.nestCell != null){
			Application.nestCell.emptyCell();
		};
		Application.nestCell = this;
		this.emptyCell();
		this.hasNest = true;
		this.currentPheromoneLev = 100;
		this.setBackground(nestColor);
	}
	private void addPheromon(){
		this.currentPheromoneLev += pheromonDroppedOnVisit;
		if(this.currentPheromoneLev > maxLevPheromone)
			this.currentPheromoneLev = maxLevPheromone;
		if(this.currentPheromoneLev > currentMaxPherLev)
			currentMaxPherLev = this.currentPheromoneLev;
	}
	private void addNestPheromon(Ant ant){
		this.nestPheromonLev += ant.getNestPheromonsToDrop();
		if(this.nestPheromonLev > maxNestPheromon)
			this.nestPheromonLev = maxNestPheromon;
		if(this.nestPheromonLev > currentMaxNestPherLev)
			currentMaxNestPherLev = this.nestPheromonLev;
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(!Application.firstLaunch)
			return;
		switch(ConfigurationPanel.el){
			case OBSTACLE:
				this.setAsObstacle();
				break;
			case FOOD:
				this.setAsFood();
				break;
			case NEST:
				this.setAsNest();
				break;
			case CLEAR:
				this.emptyCell();
			default:
				break;
		}
		ControlPanel.udpateSteps();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void visit(Ant ant){
		if(this.antsNumber < 0)
			this.antsNumber = 0;		
		this.antsNumber++;
		if(ant.carriesFood())
			this.visitingAntsSearchingNest.add(ant);
		else
			this.visitingAntsSearchingFood.add(ant);
//		System.out.println(this + " est bien visitée et a désormais "+this.antsNumber + " fourmis sur elle");
	}
	public void antSearch(Ant ant){
		if(this.hasObstacle()){
//			System.out.println(ant + " a trouvé le moyen d'aller sur un obstacle");
			return;
		}
//		Si la fourmis vient déposer sa bouffe sur le nid
		if(this.hasNest() && ant.carriesFood()){
//			System.out.println(ant + " depose sa bouffe au nid");
			this.foodLeft += ant.foodCarried;
			ant.foodCarried = 0;
			ant.carriesFood = false;
			ant.numberFromNestToFood = 0;
		}
//		La fourmis porte de la bouffe et retourne au nid
		if(!this.hasNest() && ant.carriesFood()){
//			System.out.println(ant + " bouffe sur cell " + this);
			this.addPheromon();
		}
//		La fourmis n'a pas de bouffe mais a du nestPheromon à déposer
		else if(!this.hasFood() && !this.hasNest() && ant.hasNestPheromons()){
			this.addNestPheromon(ant);
		}
//		La fourmis cherche de la bouffe et cette cellule en a
		if(!this.hasNest() && this.hasFood() && !ant.carriesFood()){
			this.takeFood(ant);
		}
	}
	
	public void leave(Ant ant){
		this.antsNumber--;
		if(this.antsNumber < 0)
			this.antsNumber = 0;
		if(ant.carriesFood())
			this.visitingAntsSearchingNest.remove(ant);
		else
			this.visitingAntsSearchingFood.remove(ant);
//		System.out.println(this + " a été quittée");
	}
	public void takeFood(Ant ant){
		int foodTook = 5;
		this.foodLeft -= foodTook;		
		ant.foodCarried = foodTook;
		ant.carriesFood = true;
		if(this.foodLeft < 0)
			this.foodLeft = 0;
		Application.totalFoodInEnvironment -= foodTook;
	}
	public void evaporate(){
		if(this.g== null)
			this.g = this.getGraphics();
		if(this.antsNumber < 0)
			this.antsNumber = 0;
		this.currentPheromoneLev = this.currentPheromoneLev*rateEvaporation;
		this.nestPheromonLev = this.nestPheromonLev*rateEvaporation;
		this.recolor();
//		this.repaint();
//		if(this.hasAnt())
//			System.out.println(this + " a une fourmis sur elle");
//		this.delayOnlyIfNeedBe();
	}

//	private void delayOnlyIfNeedBe(){
//		if(this.currentPheromoneLev < 0.001)
//			return;
//		if(this.hasFood())
//			return;
//		if(this.hasAnt())
//			return;
//		delay();
//		this.recolor();
//		this.repaint();
//	}
	
	public String toString(){
		return "coordonnees " + this.column + ":" + this.row;
	}

	@Override
	public int compareTo(CellPanel cell) {
//		Uniquement utile pour les fourmis recherchant de la bouffe
		if(this.currentPheromoneLev > cell.currentPheromoneLev)
			return -1;
		else if(this.currentPheromoneLev < cell.currentPheromoneLev)
			return 1;
		return 0;
	}

	public static void delay ()
	{
		double ms = Application.attente;
		long time = System.currentTimeMillis();
		while (System.currentTimeMillis() - time<ms);
	}	
}
