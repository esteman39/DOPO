package Shapes;
import TowerGroup.*;
/**
 * Write a description of class VisualCrazy here.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce
 * @version 1.0 (01/04/2026)
 */
public class VisualCrazy extends LidVisual{
    
    public VisualCrazy(Crazy crazy){
        super(crazy);
    }
    
    /**
     * Dibuja la tapa en la posicion especificada del canvas.
     * @param x     posicion X en pixeles (esquina superior izquierda)
     * @param y     posicion Y en pixeles (esquina superior izquierda)
     * @param scale escala de pixeles por centimetro
     */
    @Override
    public void draw(int x, int y, double scale) {
        int widthPx  = (int)(item.getHeight() * scale);
        int heightPx = (int) scale; // la tapa mide 1 cm de alto
        
        base.changeSize(heightPx, widthPx);
        diference.changeSize(heightPx - 40, widthPx);
        base.changePosition(x, y);
        diference.changePosition(x, y);
        diference.changeColor("white");
        base.makeVisible();
        diference.makeVisible();
        isVisible = true;
    }
}