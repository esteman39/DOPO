package Shapes;
import TowerGroup.*;

/**
 * Write a description of class Visual here.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce
 * @version 1.0 (23/03/2026)
 */
public abstract class Visual{
    protected Item item;
    protected Rectangle base;
    public boolean isVisible;
    
    public Visual(Item item){
        this.item = item;
        this.base = new Rectangle();
        this.isVisible = false;
    }
    
    public abstract void draw(int x, int y, double scale);
    
    public abstract void erase();
    
    public abstract void makeVisible();
    
    public abstract void makeInvisible();
    
    protected abstract void assignColor();
    
    public String defineColor(){
        String[] colors = {"red", "blue", "yellow", "magenta", "green"};
        String color = colors[(item.getNumber() - 1) % colors.length];
        
        return color;
    }
    
    public Item getItem(){
        return this.item;
    }
}