import java.util.*;

/**
 * Representa una taza cilindrica del simulador.
 * La taza i tiene altura 2i - 1 cm.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce 
 * @version 3.0 (23/03/2026)
 */
public class Cup extends Item{
    private Lid lid;
    private boolean isLid;
    private ArrayList<Integer> cupsInside;
    
    /**
     * Constructor de Cup
     * @param number numero de la taza
     */
    public Cup(int number){
        super(number);
        setType();
        this.lid = null;
        this.isLid = false;
        cupsInside = new ArrayList<>();
    }
    
    /**
     * Verifica si la taza tiene tapa
     */
    public boolean hasLid() {
        return isLid;
    }
    
    /**
     * Retorna la tapa de la taza
     */
    public Lid getLid() {
        return lid;
    }
    
    /**
     * Coloca una tapa en la taza
     */
    public void setLid(Lid newLid) {
        this.lid = newLid;
    }
    
    public void addCupInside(int number) {
        cupsInside.add(number);
    }
    
    public ArrayList<Integer> getCupsInside() {
        return cupsInside;
    }
    
    public boolean hasCupsInside() {
        return !cupsInside.isEmpty();
    }
    
    public void clearCupsInside() {
        cupsInside.clear();
    }
    
    /**
     * Remueve la tapa de la taza
     */
    public void removeLid() {
        this.lid = null;
        this.isLid = false;
    }
    
    public void coverCup(){
        this.isLid = true;
    }
    
    @Override
    public void setType(){
        type = "Cup";
    }
}