/**
 * Representa la visualizacion de una tapa en el canvas.
 * La tapa se dibuja como un rectangulo del mismo color que su taza.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce
 * @version 2.0 (28/02/2026)
 */
public class LidVisual {
    
    /** La tapa del modelo que esta siendo visualizada. */
    private Lid lid;
    
    /** Rectangulo que representa visualmente la tapa. */
    private Rectangle rectangle;
    
    /** Indica si la tapa esta actualmente visible en el canvas. */
    private boolean isVisible;
    
    /**
     * Crea la representacion visual de una tapa.
     * El color se asigna segun el numero de la tapa, igual que su taza.
     * 
     * @param lid la tapa del modelo a visualizar
     */
    public LidVisual(Lid lid) {
        this.lid = lid;
        this.rectangle = new Rectangle();
        this.isVisible = false;
        assignColor();
    }
    
    /**
     * Dibuja la tapa en la posicion especificada del canvas.
     * 
     * @param x     posicion X en pixeles (esquina superior izquierda)
     * @param y     posicion Y en pixeles (esquina superior izquierda)
     * @param scale escala de pixeles por centimetro
     */
    public void draw(int x, int y, double scale) {
        int widthPx  = (int)(lid.getWidth() * scale);
        int heightPx = (int) scale; // la tapa mide 1 cm de alto
        
        rectangle.changeSize(heightPx, widthPx);
        rectangle.changePosition(x, y);
        rectangle.makeVisible();
        isVisible = true;
    }
    
    /**
     * Retorna la tapa del modelo asociada a este visual.
     * 
     * @return la tapa Lid
     */
    public Lid getLid() {
        return lid;
    }
    
    /**
     * Oculta la tapa del canvas.
     */
    public void erase() {
        if (isVisible) {
            rectangle.makeInvisible();
            isVisible = false;
        }
    }
    
    /**
     * Hace visible la tapa si estaba oculta.
     */
    public void makeVisible() {
        if (!isVisible) {
            rectangle.makeVisible();
            isVisible = true;
        }
    }
    
    /**
     * Hace invisible la tapa si estaba visible.
     */
    public void makeInvisible() {
        if (isVisible) {
            rectangle.makeInvisible();
            isVisible = false;
        }
    }
    
    /**
     * Asigna el color al rectangulo segun el numero de la tapa.
     * El color coincide con el de la taza del mismo numero.
     */
    private void assignColor() {
        String[] colors = {"red", "blue", "yellow", "magenta", "green"};
        String color = colors[(lid.getNumber() - 1) % colors.length];
        rectangle.changeColor(color);
    }
}