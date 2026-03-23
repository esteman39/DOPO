
/**
 * Write a description of class Item here.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce
 * @version 2.0 (23/03/2026)
 */
public abstract class Item{
    protected int number;
    protected String type;
    
    /**
     * Inicializador de la clase Item que representa todos los elementos que son considerados elementos en el simunador
     */
    public Item(int number){
        this.number = number;
    }
    
    public abstract void setType();
    
    /**
     * Retorna el numero del elemento.
     * @return: retorna un entero el cual representa el numero del elemento.
     */
    public final int getNumber(){
        return this.number;
    }
    
    /**
     * Retorna el tamaño, ya sea la anchura o la altura que es la misma, de un elemento.
     * @return: retorna un entero que representa ya sea la altura o la anchura de un elemento.
     */
    public final int getHeight(){
        return (2 * number) - 1;
    }
    
    public String getType(){
        return this.type;
    }
}