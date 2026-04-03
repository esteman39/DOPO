package TowerGroup;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

/**
 * Write a description of class TowerCOntest here.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce
 * @version 1.0 (28/02/2026)
 */
public class TowerContest{
    private int numCups;
    private int maxHeight;
    private int maxWidth;
    private Tower tower;
    
    public TowerContest(int numberCups, int height){
        numCups = numberCups;
        maxHeight = height;
        maxWidth = (2 * numberCups) - 1;
        tower = new Tower(height, maxWidth);
    }
    
    public ArrayList<Integer> solve(){
        ArrayList<Integer> solution;
        Solution newSolution = new Solution(numCups, (long)maxHeight);
        solution = newSolution.getSolution();
        return solution;
    }
    
    public void simulate (){
        ArrayList<Integer> simulateSolution = solve();
        if(simulateSolution.size() == 0){
            JOptionPane.showMessageDialog(null, "IMPOSSIBLE", "stackingItems", JOptionPane.ERROR_MESSAGE);
        }
        else{
            for(int elemento : simulateSolution){
                int cup = (elemento + 1) / 2;
                tower.pushCup(cup);
            }
        }
    }
    
    public void changeCase(int newNumCups, int newHeight){
        numCups = newNumCups;
        maxHeight = newHeight;
        maxWidth = (2 * newNumCups) - 1;
        tower = new Tower(newHeight, maxWidth);
    }
    
    public void exit(){
        tower.exit();
    }
}