package TowerGroup;

/**
 * Write a description of class Fearful here.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce
 * @version 1.0 (01/04/2026)
 */
public class Fearful extends Lid{
    
    public Fearful(int number){
        super(number);
        setSpecific();
    }

    @Override
    public void setSpecific(){
        specificType = "Fearful";
    }
}
