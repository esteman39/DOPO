package Shapes;
import TowerGroup.*;
/**
 * Representa la visualizacion de una taza en el canvas.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce 
 * @version 2.0 (23/03/2026)
 */
public class CupVisual extends Visual{
    protected Rectangle leftWall;
    protected Rectangle rightWall;
    protected Rectangle isLid;
    protected Rectangle diference;
    protected int x;
    protected int y;
    
    /**
     * Constructor de CupVisual
     * @param cup la taza a visualizar
     */
    public CupVisual(Cup cup){
        super(cup);
        this.leftWall = new Rectangle();
        this.rightWall = new Rectangle();
        this.isLid = new Rectangle();
        this.diference = new Rectangle();
        
        // Asignar color
        assignColor();
    }
    
    /**
     * Dibuja la taza en la posicion especificada
     * @param x posicion X
     * @param y posicion Y (base de la taza)
     * @param scale escala de pixeles por cm
     */
    @Override
    public void draw(int x, int y, double scale) {
        this.x = x;
        this.y = y;
        int heightCm = item.getHeight();
        int heightPx = (int)(heightCm * scale);
        int thicknessPx = (int)scale;
        
        // Configurar paredes
        leftWall.changeSize(heightPx, thicknessPx);
        rightWall.changeSize(heightPx, thicknessPx);
        base.changeSize(thicknessPx, heightPx);
        isLid.changeSize(thicknessPx, heightPx);
        
        // Posicionar
        int topY = y - heightPx + (int)scale;
        base.changePosition(x, y);
        isLid.changePosition(x, y);
        leftWall.changePosition(x, topY);
        rightWall.changePosition(x + heightPx - thicknessPx, topY);
        
        // Mostrar
        base.makeVisible();
        leftWall.makeVisible();
        rightWall.makeVisible();
        isVisible = true;
    }
    
    public void isLided(){
        isLid.changeColor("white");
        isLid.makeVisible();
    }
    
    public void notLided(){
        isLid.makeInvisible();
    }
    
    /**
     * Oculta la taza
     */
    @Override
    public void erase() {
        if (isVisible) {
            base.makeInvisible();
            isLid.makeInvisible();
            leftWall.makeInvisible();
            rightWall.makeInvisible();
            diference.makeInvisible();
            isVisible = false;
        }
    }
    
    @Override
    public void makeVisible() {
        if (!isVisible) {
            base.makeVisible();
            leftWall.makeVisible();
            rightWall.makeVisible();
            isVisible = true;
        }
    }
    
    @Override
    public void makeInvisible() {
        if (isVisible) {
            base.makeInvisible();
            leftWall.makeInvisible();
            rightWall.makeInvisible();
            isLid.makeInvisible();
            diference.makeInvisible();
            isVisible = false;
        }
    }
    
    /**
     * Asigna un color a la taza segun su numero
     */
    @Override
    protected void assignColor(){
        diference.changeColor("black");
        String color = defineColor();
        base.changeColor(color);
        leftWall.changeColor(color);
        rightWall.changeColor(color);
    }
}