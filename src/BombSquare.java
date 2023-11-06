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

	public boolean bombCheck(int x, int y)
	{
		BombSquare square = (BombSquare) board.getSquareAt(x, y);
		return (square.thisSquareHasBomb);
	}


	public int[][] bombBomb(int x, int y)
	{
		int[][] relativeCoordinates = {
				{-1, -1}, {-1, 0}, {-1, 1},
				{0, -1},           {0, 1},
				{1, -1}, {1, 0}, {1, 1}
		};

		List<int[]> nonBombCoordinatesList = new ArrayList<>();

		for (int[] relativeCoord : relativeCoordinates) {
			int newX = x + relativeCoord[0];
			int newY = y + relativeCoord[1];
			if (board.getSquareAt(newX, newY) != null && board.getSquareAt(newX, newY) instanceof BombSquare && thisSquareHasBomb) {
				nonBombCoordinatesList.add(relativeCoord);
			}
		}

		int[][] nonBombCoordinates = new int[nonBombCoordinatesList.size()][2];
		for (int i = 0; i < nonBombCoordinatesList.size(); i++) {
			nonBombCoordinates[i] = nonBombCoordinatesList.get(i);
		}

		return nonBombCoordinates;
	}


	public int expand(int x, int y)
	{
		if (board.getSquareAt(x, y) != null) {
			if (checkSurr(x, y) > 0) {
				if (checkSurr(x, y) == 9) {
					board.getSquareAt(x, y).setImage("C:/Users/JoeCe/OneDrive/Documents/Udemy Projects/MineSweeper/src/images/9.png");
				} else {
					board.getSquareAt(x ,y).setImage("C:/Users/JoeCe/OneDrive/Documents/Udemy Projects/MineSweeper/src/images/x.png".replace("x", Integer.toString(checkSurr(x, y))));
					int[][] co_ords = bombBomb(x, y);
					for (int[] coordinates : co_ords) {
						int fist = coordinates[0];
						int second = coordinates[1];
						return expand(x + fist,y + second);
					}
				}
			}
		} else {
			board.getSquareAt(x, y).setImage("C:/Users/JoeCe/OneDrive/Documents/Udemy Projects/MineSweeper/src/images/0.png");
			return expand(x-1, y-1) + expand(x, y-1) + expand(x + 1, y-1) + expand(x-1, y) + expand(x+1, y) + expand(x-1, y+1) + expand(x, y+1) + expand(x+1, y+1);
		}
		return -1;
	}

	public int checkSurr(int xPos, int yPos)
	{
		int[] xs = {-1, 0, 1};
		int[] xy = {-1, 0, 1};
		int bombCount = 0;

		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				if (board.getSquareAt(xPos + xs[y], yPos + xy[x]) != null) {
					if (bombCheck(xPos + xs[y], yPos + xy[x])) {
						bombCount++;
					}
				}
			}
		}
		return bombCount;
	}

	public void clicked()
	{
		if (this.thisSquareHasBomb) {
			this.setImage("C:/Users/JoeCe/OneDrive/Documents/Udemy Projects/MineSweeper/src/images/bomb.png");
			System.out.println("bomb");
		}

		//expand(xLocation, yLocation);
	}
}
