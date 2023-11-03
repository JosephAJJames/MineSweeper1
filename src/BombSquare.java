import java.util.*;

public class BombSquare extends GameSquare
{
	private boolean thisSquareHasBomb = false;
	public static final int MINE_PROBABILITY = 10;

	public BombSquare(int x, int y, GameBoard board)
	{
		super(x, y, "images/blank.png", board);

		Random r = new Random();
		thisSquareHasBomb = (r.nextInt(MINE_PROBABILITY) == 0);
	}

	public void squareChecker(int xPos, int yPos, int[] xs, int[] xy)
	{
		if (board.getSquareAt(xPos, yPos) != null) {
			int numOfBombs = 0;

			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					if (board.getSquareAt(xPos + xs[x], yPos + xy[y]) != null) {
						if (board.getSquareAt(xPos + xs[x], yPos + xy[y]) instanceof BombSquare && thisSquareHasBomb) {
							numOfBombs++;
						}
					}
				}
			}
			board.getSquareAt(xPos, yPos).setImage("C:/Users/JoeCe/OneDrive/Documents/Udemy Projects/MineSweeper/src/images/x.png".replace("x", Integer.toString(numOfBombs))); //change back to relative paths
		}
	}

	public void clicked()
	{
		if (this.thisSquareHasBomb) {
			this.setImage("C:/Users/JoeCe/Downloads/Resources/images/bomb.png"); //change back to relative paths
		}
		int[] xs = {-1 , 0, 1};
		int[] xy = {-1, 0, 1};
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				if (!(x == 0) && !(y == 0)) {
					squareChecker(this.xLocation + xs[x], this.yLocation + xy[y], xs, xy);
				}
			}
		}
	}
}
