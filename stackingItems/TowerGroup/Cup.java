package TowerGroup;
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
        setSpecific();
        this.lid = null;
        this.isLid = false;
        cupsInside = new ArrayList<>();
    }
    
    /**
     * Verifica si la taza tiene tapa
     * @return regresa un boolean que indica el valor de verdad
     */
    public boolean hasLid() {
        return isLid;
    }
    
    /**
     * Retorna la tapa de la taza
     * @return regresa la taza
     */
    public Lid getLid() {
        return lid;
    }
    
    /**
     * Coloca una tapa en la taza
     * @param una tapa en especifico
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

    /**
     * Este metodo se encarga de asignar el tipo general de un item creado
     */
    @Override
    public final void setType(){
        type = "Cup";
    }
    
    /**
     * Este metodo se encarga de asignar el tipo especifico de un item creado
     */
    @Override
    public void setSpecific(){
        specificType = "Normal";
    }
}