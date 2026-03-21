/**
 * Representa una tapa para una taza.
 * Cada tapa mide 1 cm de alto.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce 
 * @version 2.0 (14/02/2026)
 * 
 */
public class Lid extends Item{
    
    /**
     * Constructor de Lid
     * @param number numero de la taza a la que pertenece
     */
    public Lid(int number) {
        super(number, "Lid");
    }
        
    /**
     * Calcula el ancho de la tapa en cm
     * Es igual a la anchura de su taza: 2n - 1
     */
    public int getWidth() {
        return getHeight();
    }
}