/**
 * Representa una tapa para una taza.
 * Cada tapa mide 1 cm de alto.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce 
 * @version 3.0 (23/03/2026)
 * 
 */
public class Lid extends Item{
    /**
     * Constructor de Lid
     * @param number numero de la taza a la que pertenece
     */
    public Lid(int number) {
        super(number);
        setType();
    }
    
    @Override
    public void setType(){
        type = "Lid";
    }
}