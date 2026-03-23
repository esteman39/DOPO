/**
 * Representa la visualizacion de una tapa en el canvas.
 * La tapa se dibuja como un rectangulo del mismo color que su taza.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce
 * @version 2.0 (23/03/2026)
 */
public class LidVisual extends Visual{
    private Rectangle diference;
    
    /**
     * Crea la representacion visual de una tapa.
     * El color se asigna segun el numero de la tapa, igual que su taza.
     * 
     * @param lid la tapa del modelo a visualizar
     */
    public LidVisual(Lid lid){
        super(lid);
        this.diference = new Rectangle();

        assignColor();

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
    @Override
    public void draw(int x, int y, double scale) {
        int widthPx  = (int)(item.getHeight() * scale);
        int heightPx = (int) scale; // la tapa mide 1 cm de alto
        
        base.changeSize(heightPx, widthPx);
        diference.changeSize(10, widthPx);
        base.changePosition(x, y);
        diference.changePosition(x, y);
        diference.changeColor("white");
        base.makeVisible();
        diference.makeVisible();
        isVisible = true;
    }
    
    /**
     * Oculta la tapa del canvas.
     */
    @Override
    public void erase() {
        if (isVisible) {
            base.makeInvisible();
            diference.makeInvisible();
            isVisible = false;
        }
    }
    
    /**
     * Hace visible la tapa si estaba oculta.
     */
    @Override
    public void makeVisible() {
        if (!isVisible) {
            base.makeVisible();
            diference.makeVisible();
            isVisible = true;
        }
    }
    
    /**
     * Hace invisible la tapa si estaba visible.
     */
    @Override
    public void makeInvisible() {
        if (isVisible) {
            base.makeInvisible();
            diference.makeInvisible();
            isVisible = false;
        }
    }

    /**
     * Asigna un color a la taza segun su numero
     */
    @Override
    protected void assignColor() {
        String color = defineColor();
        base.changeColor(color);
    }
}