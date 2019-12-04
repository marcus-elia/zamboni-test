import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

public class IceSquare
{
    private Point topLeft;
    private int size;
    private Color iceColor; // The RBG color of this square
    private Rectangle2D rect;

    // The RGB color with alpha
    private Color renderColor;

    public IceSquare(double x, double y, int inputSize, Color inputIceColor)
    {
        this.topLeft = new Point(x,y);
        this.size = inputSize;
        this.iceColor = inputIceColor;
        this.renderColor = new Color(iceColor.getRed()/255.0f, iceColor.getGreen()/255.0f, iceColor.getBlue()/255.0f, 0.5f);
        this.rect = new Rectangle2D.Double(topLeft.x, topLeft.y, size, size);
    }

    public void render(Graphics2D g2d)
    {
        g2d.setColor(this.renderColor);
        g2d.fill(this.rect);
    }





    public Point getTopLeft()
    {
        return this.topLeft;
    }
    public Point getBottomRight()
    {
        return new Point(this.topLeft.x + this.size, this.topLeft.y + this.size);
    }

    // Set the alpha to 1
    public void getZambonied()
    {
        this.renderColor = new Color(iceColor.getRed()/255.0f, iceColor.getGreen()/255.0f, iceColor.getBlue()/255.0f, 1f);
    }
}
