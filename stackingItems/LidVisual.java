/**
 * Representa la visualizacion de una tapa en el canvas.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce 
 * @version 1.0 (14/02/2026)
 */
public class LidVisual {
    private Lid lid;
    private Rectangle rectangle;
    private boolean isVisible;
    private String color;
    
    /**
     * Constructor de LidVisual
     * @param lid la tapa a visualizar
     * @param color color de la taza a la que pertenece
     */
    public LidVisual(Lid lid, String color) {
        this.lid = lid;
        this.color = color;
        this.rectangle = new Rectangle();
        this.rectangle.changeColor(color);
        this.isVisible = false;
    }
    
    /**
     * Dibuja la tapa en la posicion especificada
     * @param x posicion X
     * @param y posicion Y
     * @param scale escala de pixeles por cm
     */
    public void draw(int x, int y, double scale) {
        int widthPx = (int)(lid.getWidth() * scale);
        int heightPx = (int)scale;
        
        rectangle.changeSize(heightPx, widthPx);
        rectangle.changePosition(x, y);
        rectangle.makeVisible();
        isVisible = true;
    }
    
    /**
     * Oculta la tapa
     */
    public void erase() {
        if (isVisible) {
            rectangle.makeInvisible();
            isVisible = false;
        }
    }
    
    public void makeVisible() {
        if (!isVisible) {
            rectangle.makeVisible();
            isVisible = true;
        }
    }
    
    public void makeInvisible() {
        if (isVisible) {
            rectangle.makeInvisible();
            isVisible = false;
        }
    }
}