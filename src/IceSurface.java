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
        for(int i = 0; i < numCols; i++)
        {
            for(int j = 0; j < numRows; j++)
                this.squares[i][j] = new IceSquare(i * squareSize, j * squareSize, squareSize, Color.white);
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
                //if (r.isInside(squares[i][j]))
                if(r.getCenter().distanceToOtherPoint(squares[i][j].getTopLeft()) < r.getYWidth()/2 + squareSize)
                {
                    squares[i][j].getZambonied();
                }
            }
        }
    }
}
