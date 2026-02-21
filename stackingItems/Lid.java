/**
 * Representa una tapa para una taza.
 * Cada tapa mide 1 cm de alto.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce 
 * @version 1.0 (14/02/2026)
 * 
 */
public class Lid {
    private int number;
    
    /**
     * Constructor de Lid
     * @param number numero de la taza a la que pertenece
     */
    public Lid(int number) {
        this.number = number;
    }
    
    /**
     * Retorna el numero de la tapa
     */
    public int getNumber() {
        return number;
    }
    
    /**
     * Calcula el ancho de la tapa en cm
     * Es igual a la altura de su taza: 2n - 1
     */
    public int getWidth() {
        return (2 * number) - 1;
    }
}