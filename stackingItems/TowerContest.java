import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Write a description of class TowerCOntest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TowerContest{
    
    
    public String solve(int n, int h){
        Solution newSolution = new Solution(n,(long)h);
    
        if (!newSolution.firstInstance()){
            return "impossible";
        }else{
            ArrayList<Integer> solutionIs = newSolution.getSolution();
            String solution = String.join(" ", solutionIs.stream().map(String::valueOf).collect(Collectors.toList()));
            return solution;
        }
        
    }
    
    public void simulate (int n, int h){
        
        String simulation = solve(n,h);
        
        if (simulation.equals("impossible")){
            System.out.println("No hay solución es imposible");
        }else{
            String[] partes = simulation.split(" ");
            int maxWidth = (2 * n) - 1;
            Tower tower = new Tower(maxWidth, maxWidth);
            tower.makeInvisible();
            
            for(String parte : partes){
                int altura = Integer.parseInt(parte);
                int numeroDeTaza = (altura + 1) / 2;
                tower.pushCup(numeroDeTaza);
            }
            
            tower.makeVisible();
            
        }
        
    }
}