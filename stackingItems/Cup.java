/**
 * Representa una taza cilindrica del simulador.
 * La taza i tiene altura 2i - 1 cm.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce 
 * @version 1.0 (14/02/2026)
 */
public class Cup extends Item{
    private Lid lid;
    
    /**
     * Constructor de Cup
     * @param number numero de la taza
     */
    public Cup(int number){
        super(number, "Cup");
        this.lid = null;
    }
    
    /**
     * Verifica si la taza tiene tapa
     */
    public boolean hasLid() {
        return lid != null;
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
    
    /**
     * Remueve la tapa de la taza
     */
    public void removeLid() {
        this.lid = null;
    }
}