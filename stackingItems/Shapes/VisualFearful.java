package Shapes;
import TowerGroup.*;
/**
 * Write a description of class VisualFearful here.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce
 * @version 1.0 (01/04/2026)
 */
public class VisualFearful extends LidVisual{
    
    public VisualFearful(Fearful fearful){
        super(fearful);
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
        diference.changeSize(heightPx, 10);
        base.changePosition(x, y);
        diference.changePosition(x, y);
        diference.changeColor("white");
        base.makeVisible();
        diference.makeVisible();
        isVisible = true;
    }
}