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

    public void render(Graphics2D g2d, Point renderTopLeft, int renderRectXSize, int renderRectYSize)
    {
        int numSquaresX = renderRectXSize/squareSize;
        int numSquaresY = renderRectYSize/squareSize;
        for(int i = Math.max((int)renderTopLeft.x/squareSize, 0); i < Math.min((int)renderTopLeft.x/squareSize + numSquaresX, numCols); i++)
        {
            for(int j = Math.max((int)renderTopLeft.y/squareSize, 0); j < Math.min((int)renderTopLeft.y/squareSize + numSquaresY, numRows); j++)
            {
                this.squares[i][j].render(g2d);
            }
        }
    }

    // The first time, we have to draw all squares
    public void renderEverything(Graphics2D g2d)
    {
        for(int i = 0; i < numCols; i++)
        {
            for(int j = 0; j < numRows; j++)
            {
                this.squares[i][j].render(g2d);
            }
        }
    }

    // ====================================
    //
    //              Getters
    //
    // ===================================
    public int getNumRows()
    {
        return this.numRows;
    }
    public int getNumCols()
    {
        return this.numCols;
    }


    public void updateSquares(Rectangle r, Point renderTopLeft, int renderRectXSize, int renderRectYSize)
    {
        int numSquaresX = renderRectXSize/squareSize;
        int numSquaresY = renderRectYSize/squareSize;
        for(int i = Math.max((int)renderTopLeft.x/squareSize, 0); i < Math.min((int)renderTopLeft.x/squareSize + numSquaresX, numCols); i++)
        {
            for(int j = Math.max((int)renderTopLeft.y/squareSize, 0); j < Math.min((int)renderTopLeft.y/squareSize + numSquaresY, numRows); j++)
            {
                if(r.isInside(squares[i][j]))
                {
                    squares[i][j].getZambonied();
                }
            }
        }
    }
}
