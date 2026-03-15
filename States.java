import java.util.ArrayList;
import java.util.Arrays;

/**
 * Write a description of class States here.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce 
 * @version 2.0 (14/02/2026)
 */
public class States{
    private int currentHeight;
    private int currentLevel;
    private ArrayList<Integer> space;
    
    public States(int height, int level, ArrayList<Integer> spaceNow){
        this.currentHeight = height;
        this.currentLevel = level;
        this.space = spaceNow;
    }
    
    public int getCurrentHeight(){
        return currentHeight;
    }
    
    public int getCurrentLevel(){
        return currentLevel;
    }
    
    public ArrayList<Integer> getSpace(){
        return space;
    }
}