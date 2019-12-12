import java.awt.*;
import java.awt.geom.Path2D;

// A button shaped like a zamboni. Click on it to start the game
public class ZamboniButton
{
    private int x;
    private int y;

    private Path2D snowTank;
    private Path2D body;

    public ZamboniButton(int inputX, int inputY)
    {
        this.x = inputX;
        this.y = inputY;

        this.makeSnowTank();
        this.makeBody();
    }

    // =====================================
    //
    //             Helper Functions
    //           for the Constructor
    //
    // =====================================
    public void makeSnowTank()
    {
        snowTank = new Path2D.Double();
        snowTank.moveTo(x,y);
        snowTank.lineTo(x - 120, y - 6);
        snowTank.lineTo(x - 120, y - 30);
        snowTank.lineTo(x, y - 48);
        snowTank.lineTo(x,y);
    }
    private void makeBody()
    {
        body = new Path2D.Double();
        body.moveTo(x,y);
        body.lineTo(x - 120, y - 6);
        body.lineTo(x - 120, y + 30);
        body.lineTo(x - 114, y + 30);

        // Front wheel
        body.curveTo(x - 114, y + 20.0655, x - 105.93347, y + 12, x - 96, y + 12);
        body.curveTo(x - 86.0655, y + 12, x - 78, y + 20.0655, x - 78, y + 30);
        body.lineTo(x - 18, y + 30);

        // Back Wheel
        body.curveTo(x - 18, y + 20.0655, x - 9.93447, y + 12, x, y + 12);
        body.lineTo(x + 42, y + 12);

        // Seat
        body.lineTo(x + 54, y);
        body.lineTo(x + 54, y - 18);
        body.lineTo(x + 42, y - 18);
        body.lineTo(x + 42, y);
        body.lineTo(x + 18, y);

        // Dashboard
        body.lineTo(x + 18, y - 24);
        body.lineTo(x + 24, y - 24);
        body.lineTo(x + 24, y - 48);
        body.lineTo(x, y - 48);
        body.lineTo(x, y);
    }






    public void render(Graphics2D g2d)
    {
        g2d.setColor(Color.white);
        g2d.fill(this.snowTank);

        g2d.setColor(Color.blue);
        g2d.fill(this.body);
    }
}
