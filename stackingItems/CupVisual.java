/**
 * Representa la visualizacion de una taza en el canvas.
 * 
 * @author Thomas Sebastian Garcia Gomez & Esteban Muñoz Arce 
 * @version 2.0 (23/03/2026)
 */
public class CupVisual extends Visual{
    private Rectangle leftWall;
    private Rectangle rightWall;
    private Rectangle diference;
    private int x;
    private int y;
    
    /**
     * Constructor de CupVisual
     * @param cup la taza a visualizar
     */
    public CupVisual(Cup cup){
        super(cup);
        this.leftWall = new Rectangle();
        this.rightWall = new Rectangle();
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
        diference.changeSize(thicknessPx, heightPx);
        
        // Posicionar
        int topY = y - heightPx + (int)scale;
        base.changePosition(x, y);
        diference.changePosition(x, y);
        leftWall.changePosition(x, topY);
        rightWall.changePosition(x + heightPx - thicknessPx, topY);
        
        // Mostrar
        base.makeVisible();
        leftWall.makeVisible();
        rightWall.makeVisible();
        isVisible = true;
    }
    
    public void isLided(){
        diference.changeColor("white");
        diference.makeVisible();
    }
    
    public void notLided(){
        diference.makeInvisible();
    }
    
    /**
     * Oculta la taza
     */
    @Override
    public void erase() {
        if (isVisible) {
            base.makeInvisible();
            diference.makeInvisible();
            leftWall.makeInvisible();
            rightWall.makeInvisible();
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
            diference.makeInvisible();
            isVisible = false;
        }
    }
    
    /**
     * Asigna un color a la taza segun su numero
     */
    @Override
    protected void assignColor(){
        String color = defineColor();
        base.changeColor(color);
        leftWall.changeColor(color);
        rightWall.changeColor(color);
    }
}