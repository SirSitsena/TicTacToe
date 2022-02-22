package tictactoe;

import java.util.ArrayList;

public class Board
{
	private char board[][];

	private final int rowsNumber;
	private final int columnsNumber;
	private final int lineLenToWin;

	private int freeCells = 0;
	private boolean gameover;

	private ArrayList<Player> players = new ArrayList<>();
	private int curPlayer = 0;
	private Player winner;
	private ArrayList<int[]> winLine = new ArrayList<>();

	public Board( int rows, int columns, int lineLenToWin )
	{
		this.rowsNumber = rows;
		this.columnsNumber = columns;
		this.lineLenToWin = lineLenToWin;
		gameover = true;
	}

	public void addPlayer( Player player )
	{
		players.add( player );
	}

	public Player getCurPlayer()
	{
		return players.get( curPlayer );
	}

	public void startGame()
	{
		board = new char[ rowsNumber ][ columnsNumber ];
		curPlayer = 0;
		freeCells = rowsNumber * columnsNumber;
		winner = null;
		winLine.clear();
		gameover = false;
	}

	/**
	 * Current player tries to put his next mark on board.
	 * @param row - from zero.
	 * @param column - from zero.
	 * @return false - if can't put mark on board (cell busy or game is over).
	 */
	public boolean makeMove( int row, int column )
	{
		// Check if we can put mark on board
		if( (board[ row ][ column ] != 0) || gameover )
			return false;

		char mark = players.get( curPlayer ).getMark();
		board[ row ][ column ] = mark;
		--freeCells;

		if( isWin( row, column, mark ) )
		{
			winner = players.get( curPlayer );
			gameover = true;
		}

		if( freeCells == 0 )
		{
			gameover = true;
		}
		// If success switch to next player
		++curPlayer;
		curPlayer %= players.size();

		return true;
	}

	public boolean isGameOver()
	{
		return gameover;
	}

	/**
	 * @return null if no winner
	 */
	public Player getWinner()
	{
		return winner;
	}

	public ArrayList<int[]> getWinLine()
	{
		return winLine;
	}

	private class WinLineCounter
	{
		int lineLen = 0;
		char mark;

		WinLineCounter( char mark )
		{
			this.mark = mark;
			winLine.clear();
		}

		/**
		 * @return true if we have win line
		 */
		boolean countMark( int row, int column )
		{
			if( board[ row ][ column ] == mark )
			{
				++lineLen;
				winLine.add( new int[] { row, column } );
				if( lineLen == lineLenToWin )
					return true;
			}
			else
			{
				lineLen = 0;
				winLine.clear();
			}
			return false;
		}
	}

	// Check current row for win line
	private boolean isRowWin( int row, char mark )
	{
		WinLineCounter lineCounter = new WinLineCounter( mark );
		for( int j = 0; j < columnsNumber; ++j )
		{
			if( lineCounter.countMark( row, j ) )
				return true;
		}
		winLine.clear();
		return false;
	}

	// Check current column for win line
	private boolean isColumnWin( int column, char mark )
	{
		WinLineCounter lineCounter = new WinLineCounter( mark );
		for( int i = 0; i < rowsNumber; ++i )
		{
			if( lineCounter.countMark( i, column ) )
				return true;
		}
		winLine.clear();
		return false;
	}

	// Check up-down diagonal
	private boolean isUpDownDiagWin( int row, int column, char mark )
	{
		WinLineCounter lineCounter = new WinLineCounter( mark );
		int min = Math.min( row, column );
		for( int i = row - min, j = column - min; (i < rowsNumber) && (j < columnsNumber); ++i, ++j )
		{
			if( lineCounter.countMark( i, j ) )
				return true;
		}
		winLine.clear();
		return false;
	}

	// Check down-up diagonal
	private boolean isDownUpDiagWin( int row, int column, char mark )
	{
		WinLineCounter lineCounter = new WinLineCounter( mark );
		int min = Math.min( rowsNumber - row - 1, column );
		for( int i = row + min, j = column - min; (i >= 0) && (j < columnsNumber); --i, ++j )
		{
			if( lineCounter.countMark( i, j ) )
				return true;
		}
		winLine.clear();
		return false;
	}

	private boolean isWin( int row, int column, char mark )
	{
		return isRowWin( row, mark ) || isColumnWin( column, mark ) || isUpDownDiagWin( row, column, mark ) || isDownUpDiagWin( row, column, mark );
	}
}
