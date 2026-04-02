
/**
 * Write a description of class Hierarchical here.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce
 * @version 1.0 (01/04/2026)
 */
public class Hierarchical extends Cup{
    
    public Hierarchical(int number){
        super(number);
        setSpecific();
    }
    
    @Override
    public void setSpecific(){
        specificType = "Hierarchical";
    }
}
