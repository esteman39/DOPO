package TowerGroup;
import java.util.ArrayList;

/**
 * Write a description of class Item here.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce
 * @version 2.0 (23/03/2026)
 */
public abstract class Item{
    protected int number;
    protected String type;
    protected String specificType;
    
    /**
     * Inicializador de la clase Item que representa todos los elementos que son considerados elementos en el simunador
     */
    public Item(int number){
        this.number = number;
    }
    
    /**
     * Este metodo se encarga de asignar el tipo general de un item creado
     */
    public abstract void setType(); 
    
    /**
     * Este metodo se encarga de asignar el tipo especifico de un item creado
     */
    public abstract void setSpecific();
    
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
    
    /**
     * Retorna el tipo de item que se ha creado, en este caso se hace referencia al tipo general.
     * @return Retorna una cadena de texto que representa este aspecto.
     */
    public String getType(){
        return this.type;
    }
    
    /**
     * Retorna el tipo de item que se ha creado, en este caso se hace referencia al tipo especifico.
     * @return Retorna una cadena de texto que representa este aspecto.
     */
    public String getSpecific(){
        return this.specificType;
    }
}