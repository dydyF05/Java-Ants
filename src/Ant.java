import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

final public class Ant{
	public static int antNum = 1;
	CellPanel previousCell;
	CellPanel currentCell;
	public boolean carriesFood = false;
	private Random alea = new Random();
	public int foodCarried = 0;
	public int id;
	public static double maxtNestPheromon = 1000;
	public double nestPheromon = 0;
	public int numberFromNestToFood = 0;
	
	Ant(CellPanel startingCell){
		this.id = antNum;
		antNum++;
		this.currentCell = startingCell;
		this.previousCell = startingCell;
	}
	
	public boolean carriesFood(){
		if(this.foodCarried > 0)
			return true;
		this.foodCarried = 0;
		return false;
	}
	
	public void live(){
		this.pickNestPheromonsIfOnNest();
		if(this.carriesFood()){
//			System.out.println(this + "  et retourne au nid");
			this.returnToNest();
		}
		else{
//			System.out.println(this + " et cherche de la bouffe");
			CellPanel cell = this.getBestWander();
			if (cell !=  null)				
				this.moveToCell(cell.column, cell.row);
			else
				this.wanderAround();
			numberFromNestToFood++;
		}
	}
	public void pickNestPheromonsIfOnNest(){
		if(this.isNest(this.currentCell.column, this.currentCell.row))
			this.nestPheromon = maxtNestPheromon;
	}
	public double getNestPheromonsToDrop(){
		double toGive = 10;
		this.nestPheromon = this.nestPheromon - toGive;
		return toGive;
	}
	public boolean hasNestPheromons(){
		if (this.nestPheromon > 0)
			return true;
		return false;
	}
	private CellPanel getBestCellToReturnToNest() {
		ArrayList<CellPanel> accessibleNeighbors = new ArrayList<CellPanel>();
		CellPanel bestCell = null;
		for (int i_col = -1; i_col <= 1; i_col++){
			int vCol = this.currentCell.column + i_col;
			for (int i_row = -1; i_row <= 1; i_row++){				
				int vRow = this.currentCell.row + i_row;
				if( isCellAccessible(vCol, vRow)){
					if( MainPanel.cellArray[vCol][vRow].hasNestPheromons() )
						if(this.checkNestPheromonLevel(vCol, vRow))
							accessibleNeighbors.add(MainPanel.cellArray[vCol][vRow]);					
				}
			}
		}
//		Le pb d'algo de retour au nid se résoud ici .. :/
		if(accessibleNeighbors.size() > 0){
//			Collections.sort(accessibleNeighbors);
//			return accessibleNeighbors.get(accessibleNeighbors.size()-1);
//			System.out.println( this + " a trouvé des nest pheromones ds son voisinage au nb de " + accessibleNeighbors.size());
			int maxIndex = accessibleNeighbors.size()-1;
			if(maxIndex < 1)
				maxIndex = 1;
			Random rand = new Random();
			int randIndex = rand.nextInt(maxIndex);
			while(randIndex > accessibleNeighbors.size()-1){
				randIndex--;
			}
			bestCell = accessibleNeighbors.get(randIndex);
		}
		return bestCell;
	}
	private CellPanel getBestWander(){
		CellPanel bestCell = null;
		ArrayList<CellPanel> accessibleNeighbors = new ArrayList<CellPanel>();
		for (int i_col = -1; i_col <= 1; i_col++){
			int vCol = this.currentCell.column + i_col;
			for (int i_row = -1; i_row <= 1; i_row++){				
				int vRow = this.currentCell.row + i_row;
				if( isCellAccessible(vCol, vRow) && !this.isNest(vCol, vRow) && !this.isPreviousCell(vCol, vRow)){
					if( this.isFood(vCol, vRow) )
						return MainPanel.cellArray[vCol][vRow];
					if( this.checkPheromonLevel(vCol, vRow) )
						accessibleNeighbors.add(MainPanel.cellArray[vCol][vRow]);
				}
			}
		}
		if(accessibleNeighbors.size() > 0){
			int maxIndex = accessibleNeighbors.size()-1;
			Random rand = new Random();
			while(maxIndex < 1){
				maxIndex++;
			}
			int randIndex = rand.nextInt(maxIndex);
			while(randIndex > accessibleNeighbors.size()-1){
				randIndex--;
			}
			bestCell = accessibleNeighbors.get(randIndex);
		}
		return bestCell;
	}
	private boolean isPreviousCell(int col, int row){
		if(col == this.previousCell.column && row == this.previousCell.row)
			return true;
		return false;
	}
	private boolean isNest(int col, int row){
		if( Application.nestCell.column == col && Application.nestCell.row == row)
			return true;
		return false;
	}
	private boolean checkPheromonLevel(int col, int row){
		CellPanel bestCell = MainPanel.cellArray[col][row];
		Random rand = new Random();
		double randDouble = rand.nextDouble() / (CellPanel.currentMaxPherLev*Application.antsNumber);
		double levPhrmn = bestCell.currentPheromoneLev / CellPanel.currentMaxPherLev;
		if(previousCell.column == col && previousCell.row == row){
			levPhrmn = randDouble *levPhrmn;
		}
		if(levPhrmn > randDouble)
			return true;
		return false;
	}
	private boolean isFood(int col, int row){
		if(MainPanel.cellArray[col][row].hasFood())
			return true;
		return false;
	}
	private boolean checkNestPheromonLevel(int col, int row){
		if(MainPanel.cellArray[col][row].nestPheromonLev < CellPanel.nestPhLeftAfterMaxVisitSteps)
			return false;
		CellPanel c = MainPanel.cellArray[col][row];		
//		La fourmis ne va pas forcément prendre le chemin défini comme meilleur, 
//		il y a une marge proba trop importante pour que cette cell ne soit pas le meilleur choix
//		Moins la piste est fraiche plus il y a de chance qu'on random donc qu'on renvoie null
		Random rand = new Random();
		double randD = rand.nextDouble() / (CellPanel.currentMaxNestPherLev * Application.antsNumber);
		double levNestPhrmn = c.nestPheromonLev / CellPanel.currentMaxNestPherLev;
		if(previousCell.column == col && previousCell.row == row){
			levNestPhrmn = randD *levNestPhrmn;
		}
//		System.out.println("custom lev:" + levNestPhrmn + ", rand:" + randD);
		if(randD < levNestPhrmn)
			return true;
		return false;
	}
	public void wanderAround(){
		int currentRow = this.currentCell.row;
		int currentCol = this.currentCell.column;
		int randRow = alea.nextInt(3) - 1;
		int randCol = alea.nextInt(3) - 1 ;
		
		int nextCol = currentCol+randCol;
		int nextRow = currentRow+randRow;
		while(!this.isCellAccessible(nextCol, nextRow)){
			nextCol = currentCol+alea.nextInt(3) - 1;
			nextRow = currentRow+alea.nextInt(3) - 1;
		}
		this.moveToCell(nextCol, nextRow);
	}
	
	private boolean isCellAccessible(int col, int row){
		 try {
//			On check l'existence de la cell 
			if(MainPanel.cellArray[col][row] == null){
				return false;
			}
			if(MainPanel.cellArray.length < col || col < 0){
				return false;
			}
			if(MainPanel.cellArray[col].length < row || row < 0){
				return false;
			}
//			On check si elle a un obstacle
			if(MainPanel.cellArray[col][row].hasObstacle()){
				return false;
			}	
//			On check si la fourmis n'est pas déjà sur cett cellule
			if(col == this.currentCell.column && row == this.currentCell.row){
				return false;
			}
//				La fourmi ne doit pas rentrer au nid tant qu'elle n'a pas trouvé de bouffe 
//				(peut poser pb si le nid est en plein milieu du seul passage vers la bouffe
//				if(MainPanel.cellArray[col][row].hasNest())
//					return false;
			return true;
		 }catch (ArrayIndexOutOfBoundsException e) {
				return false;
	     }
	}
	
	private void moveToCell(int col, int row){
		this.previousCell = this.currentCell;
		CellPanel nextCell = MainPanel.cellArray[col][row];
		this.currentCell.leave(this);
		nextCell.visit(this);
		this.currentCell = nextCell;
		this.scavenge();
	}
	
	private void scavenge(){
//		La fourmis n'est pas censée se rappeler d'où elle vient donc bon... on oublie cette ligne
//		cellsVisitedUntilFromFoodToNest.add(this.currentCell);
		this.currentCell.antSearch(this);
	}
	private boolean isNestInNeighborhood(){
		double colDiff = Math.abs(this.currentCell.column - Application.nestCell.column);
		double rowDiff = Math.abs(this.currentCell.row - Application.nestCell.row);
		if( colDiff == 0 || colDiff == 1)
			if( rowDiff == 0 || rowDiff == 1)
				return true;
		return false;
	}
	public void returnToNest(){	
//		La fourmis cherche à retourner au nid pr déposer la bouffe
//		Le nid est à portée
		if(isNestInNeighborhood()){
			this.previousCell = this.currentCell;
			this.currentCell = Application.nestCell;
			this.previousCell.leave(this);
			this.currentCell.visit(this);
			this.currentCell.antSearch(this);
		}
//		Il va falloir chercher
		else{			
			CellPanel cell = getBestCellToReturnToNest();
			if(cell == null){
				this.wanderAround();
			}
			else{
				this.previousCell = this.currentCell;
				this.previousCell.leave(this);
				this.currentCell = cell;
				this.currentCell.visit(this);
				this.currentCell.antSearch(this);
			}
		}
		
	}
	
	
	public String toString(){
		if(this.carriesFood())
			return "la fourmis " + this.id + " transporte "+ foodCarried;
		return "la fourmis " + this.id;
	}

}
