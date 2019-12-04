import java.awt.*;

public class IceSurface
{
    private int squareSize;

    private int numRows;
    private int numCols;


    private IceSquare[][] squares;

    public IceSurface(int inputSquareSize, int inputWidth, int inputHeight)
    {
        this.squareSize = inputSquareSize;

        this.numRows = inputHeight / squareSize;
        this.numCols = inputWidth / squareSize;

        this.squares = new IceSquare[numCols][numRows];
        this.initializeSquares();
    }

    // Helper function for constructor. Fills the array with white squares
    public void initializeSquares()
    {
        Color curColor;
        for(int i = 0; i < numCols; i++)
        {
            for(int j = 0; j < numRows; j++)
            {
                // Goal lines
                if(j == numRows / 16 || j == 15*numRows/16)
                {
                    curColor = Color.red;
                }
                // Blue lines
                else if(j == numRows/3 || j == numRows/3 + 1 || j == 2*numRows/3 || j == 2*numRows/3 - 1)
                {
                    curColor = Color.blue;
                }
                // Center line
                else if(j == numRows/2)
                {
                    curColor = Color.red;
                }
                else
                {
                    curColor = Color.white;
                }
                this.squares[i][j] = new IceSquare(i * squareSize, j * squareSize, squareSize, curColor);
            }
        }
    }

    public void tick()
    {

    }

    public void render(Graphics2D g2d)
    {
        for(int i = 0; i < numCols; i++)
        {
            for(int j = 0; j < numRows; j++)
            {
                this.squares[i][j].render(g2d);
            }
        }
    }


    public void updateSquares(Rectangle r)
    {
        for(int i = 0; i < numCols; i++)
        {
            for(int j = 0; j < numRows; j++)
            {

                if(r.getCenter().distanceToOtherPoint(squares[i][j].getTopLeft()) < r.getXWidth()/2 + squareSize &&
                   r.isInside(squares[i][j]))
                {
                    squares[i][j].getZambonied();
                }
            }
        }
    }
}
