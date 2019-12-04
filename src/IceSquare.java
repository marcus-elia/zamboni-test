import java.awt.*;

public class IceSquare
{
    private Point topLeft;
    private int size;
    private Color iceColor; // The RBG color of this square

    // The RGB color with alpha
    private Color renderColor;

    public IceSquare(double x, double y, int inputSize, Color inputIceColor)
    {
        this.topLeft = new Point(x,y);
        this.size = inputSize;
        this.iceColor = inputIceColor;
        this.renderColor = new Color(iceColor.getRed(), iceColor.getGreen(), iceColor.getBlue(), 0.5f);
    }

    public Point getTopLeft()
    {
        return this.topLeft;
    }

    // Set the alpha to 1
    public void getZambonied()
    {
        this.renderColor = new Color(iceColor.getRed(), iceColor.getGreen(), iceColor.getBlue(), 1f);
    }
}
