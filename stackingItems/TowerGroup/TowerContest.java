package TowerGroup;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

/**
 * ============================================================
 * CICLO 3 - Requisitos 14 al 15 (solucion del simulador)
 * ============================================================
 * 14. Crear una clase especial la cual pueda imprimir la solucion del problema
 * 15. Se muestra la solucion del problema
 * 
 * Esta clase se encarga de realizar la simulacion de la solucion al problema del simulador
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce
 * @version 1.0 (28/02/2026)
 */
public class TowerContest{
    private int numCups;
    private int maxHeight;
    private int maxWidth;
    private Tower tower;
    
    /**
     * El inicializador de TowerContest crea una torre comodin la cual será utilizada para simular la solucion
     * @param: numberCups es la cantidad de copas que pueden estar en la torre
     * @param: height es la altura maxima de la torre.
     */
    public TowerContest(int numberCups, int height){
        numCups = numberCups;
        maxHeight = height;
        maxWidth = (2 * numberCups) - 1;
        tower = new Tower(height, maxWidth);
    }
    
    /**
     * Este metodo se encarga de encontrar la solucion al problema planteado en el simulador
     * @return: retorna la lista de las tazas que logran generar la solucion del problema planteado
     */
    public ArrayList<Integer> solve(){
        ArrayList<Integer> solution;
        Solution newSolution = new Solution(numCups, (long)maxHeight);
        solution = newSolution.getSolution();
        return solution;
    }
    
    /**
     * Este metodo se encarga de simular la solucion en la torre comodin que se ha creado.
     */
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
    
    /**
     * Este metodo se encarga de generar otros casos, cambiando la torre y los datos relacionados a esta
     * @param: newNumCups es el nuevo numero de copas en el caso
     * @param: newHeigh es la nueva altura maxima que tendra la nueva torre comodin
     */
    public void changeCase(int newNumCups, int newHeight){
        numCups = newNumCups;
        maxHeight = newHeight;
        maxWidth = (2 * newNumCups) - 1;
        tower = new Tower(newHeight, maxWidth);
    }
    
    /**
     * Este metodo se encarga de cerrar TowerContest, borrando todos los elementos relacionados
     */
    public void exit(){
        tower.exit();
    }
}