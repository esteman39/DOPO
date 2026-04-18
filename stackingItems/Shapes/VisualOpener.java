package Shapes;
import TowerGroup.*;
/**
 * Write a description of class VisualOpener here.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce
 * @version 1.0 (01/04/2026)
 */
public class VisualOpener extends CupVisual{
    
    public VisualOpener(Opener opener){
        super(opener);
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
        diference.changeSize(10, heightPx);
        
        // Posicionar
        int topY = y - heightPx + (int)scale;
        base.changePosition(x, y);
        diference.changePosition(x, y + ((int)scale/2));
        isLid.changePosition(x, y);
        leftWall.changePosition(x, topY);
        rightWall.changePosition(x + heightPx - thicknessPx, topY);
        
        // Mostrar
        base.makeVisible();
        leftWall.makeVisible();
        rightWall.makeVisible();
        diference.makeVisible();
        isVisible = true;
    }
    
    @Override
    public void isLided(){
        isLid.changeColor("white");
        isLid.makeVisible();
        diference.makeVisible();
    }
    
    @Override
    public void makeVisible() {
        if (!isVisible) {
            base.makeVisible();
            leftWall.makeVisible();
            rightWall.makeVisible();
            diference.makeVisible();
            isVisible = true;
        }
    }
}