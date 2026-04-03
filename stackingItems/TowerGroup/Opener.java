package TowerGroup;

/**
 * Write a description of class Opener here.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce
 * @version 1.0 (01/04/2026)
 */
public class Opener extends Cup{
    
    public Opener(int number){
        super(number);
        setSpecific();
    }
    
    @Override
    public void setSpecific(){
        specificType = "Opener";
    }
}
