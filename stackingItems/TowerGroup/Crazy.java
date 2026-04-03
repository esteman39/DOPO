package TowerGroup;

/**
 * Write a description of class Crazy here.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce
 * @version 1.0 (01/04/2026)
 */
public class Crazy extends Lid{
    
    public Crazy(int number){
        super(number);
        setSpecific();   
    }
    
    @Override
    public void setSpecific(){
        specificType = "Crazy";
    }
}
