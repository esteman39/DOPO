package Shapes;
import TowerGroup.*;
/**
 * Write a description of class VisualReverse here.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce
 * @version 1.0 (01/04/2026)
 */
public class VisualReverse extends CupVisual{
    
    public VisualReverse(Reverse reverse){
        super(reverse);
    }
    
    /**
     * Dibuja la taza en la posicion especificada
     * @param x posicion X
     * @param y posicion Y (base de la taza)
     * @param scale escala de pixeles por cm
     */
    @Override
    public void draw(int x, int y, double scale){
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
        base.changePosition(x, y);
        isLid.changePosition(x, y);
        leftWall.changePosition(x, y);
        rightWall.changePosition(x + heightPx - thicknessPx, y);
        
        // Mostrar
        base.makeVisible();
        leftWall.makeVisible();
        rightWall.makeVisible();
        isVisible = true;
    }
    
    @Override
    public void isLided(){
        isLid.changeColor("white");
        isLid.makeVisible();
        diference.makeVisible();
    }
}