package TowerGroup;

/**
 * Esta nueva clase de taza tiene la particularidad de estar al del reves, se siguen comportando como una taza normal, la diferencia es que esta
 * dada la vuelta, en caso de que pase la altura maxima se saca de la torre como una copa normal.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce
 * @version 1.0 (01/04/2026)
 */
public class Reverse extends Cup{
    public Reverse(int number){
        super(number);
    }
    
    @Override
    public void setSpecific(){
        specificType = "Reverse";
    }
}