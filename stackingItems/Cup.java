/**
 * Representa una taza cilindrica del simulador.
 * La taza i tiene altura 2i - 1 cm.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce 
 * @version 1.0 (14/02/2026)
 */
public class Cup {
    private int number;
    private Lid lid;
    
    /**
     * Constructor de Cup
     * @param number numero de la taza
     */
    public Cup(int number) {
        this.number = number;
        this.lid = null;
    }
    
    /**
     * Retorna el numero de la taza
     */
    public int getNumber() {
        return number;
    }
    
    /**
     * Calcula la altura de la taza en cm
     * Formula: altura = 2n - 1
     */
    public int getHeight() {
        return (2 * number) - 1;
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
    public void setLid(Lid lid) {
        this.lid = lid;
    }
    
    /**
     * Remueve la tapa de la taza
     */
    public void removeLid() {
        this.lid = null;
    }
}