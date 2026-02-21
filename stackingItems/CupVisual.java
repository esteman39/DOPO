package stackingItems;

/**
 * Representa la visualizacion de una taza en el canvas.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce 
 * @version 1.0 (14/02/2026)
 */
public class CupVisual {
    private Cup cup;
    private Rectangle leftWall;
    private Rectangle rightWall;
    private Rectangle base;
    private LidVisual lidVisual;
    private boolean isVisible;
    
    /**
     * Constructor de CupVisual
     * @param cup la taza a visualizar
     */
    public CupVisual(Cup cup) {
        this.cup = cup;
        this.leftWall = new Rectangle();
        this.rightWall = new Rectangle();
        this.base = new Rectangle();
        this.lidVisual = null;
        this.isVisible = false;
        
        // Asignar color
        assignColor();
    }
    
    /**
     * Dibuja la taza en la posicion especificada
     * @param x posicion X
     * @param y posicion Y (base de la taza)
     * @param scale escala de pixeles por cm
     */
    public void draw(int x, int y, double scale) {
        int heightCm = cup.getHeight();
        int heightPx = (int)(heightCm * scale);
        int thicknessPx = (int)scale;
        
        // Configurar paredes
        leftWall.changeSize(heightPx, thicknessPx);
        rightWall.changeSize(heightPx, thicknessPx);
        base.changeSize(thicknessPx, heightPx);
        
        // Posicionar
        int topY = y - heightPx + (int)scale;
        base.changePosition(x, y);
        leftWall.changePosition(x, topY);
        rightWall.changePosition(x + heightPx - thicknessPx, topY);
        
        // Mostrar
        base.makeVisible();
        leftWall.makeVisible();
        rightWall.makeVisible();
        isVisible = true;
        
        // Dibujar tapa si existe
        if (cup.hasLid()) {
            if (lidVisual == null) {
                String cupColor = getColor();
                lidVisual = new LidVisual(cup.getLid(), cupColor);
            }
            lidVisual.draw(x, topY - thicknessPx, scale);
        }
    }
    
    /**
     * Oculta la taza
     */
    public void erase() {
        if (isVisible) {
            base.makeInvisible();
            leftWall.makeInvisible();
            rightWall.makeInvisible();
            
            if (lidVisual != null) {
                lidVisual.erase();
            }
            
            isVisible = false;
        }
    }
    
    public void makeVisible() {
        if (!isVisible) {
            base.makeVisible();
            leftWall.makeVisible();
            rightWall.makeVisible();
            
            if (lidVisual != null) {
                lidVisual.makeVisible();
            }
            
            isVisible = true;
        }
    }
    
    public void makeInvisible() {
        if (isVisible) {
            base.makeInvisible();
            leftWall.makeInvisible();
            rightWall.makeInvisible();
            
            if (lidVisual != null) {
                lidVisual.makeInvisible();
            }
            
            isVisible = false;
        }
    }
    
    /**
     * Actualiza la visualizacion de la tapa
     */
    public void updateLid(int x, int y, double scale) {
        if (cup.hasLid()) {
            if (lidVisual == null) {
                String cupColor = getColor();
                lidVisual = new LidVisual(cup.getLid(), cupColor);
            }
            lidVisual.draw(x, y, scale);
        } else {
            if (lidVisual != null) {
                lidVisual.erase();
                lidVisual = null;
            }
        }
    }
    
    /**
     * Asigna un color a la taza segun su numero
     */
    private void assignColor() {
        String[] colors = {"red", "blue", "yellow", "magenta", "green"};
        String color = colors[(cup.getNumber() - 1) % colors.length];
        
        base.changeColor(color);
        leftWall.changeColor(color);
        rightWall.changeColor(color);
    }
    
    /**
     * Obtiene el color de la taza
     */
    private String getColor() {
        String[] colors = {"red", "blue", "yellow", "magenta", "green"};
        return colors[(cup.getNumber() - 1) % colors.length];
    }
    
    public Cup getCup() {
        return cup;
    }
}
