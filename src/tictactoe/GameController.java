package tictactoe;

import java.util.ArrayList;

public class GameController
{
	final int BOARD_ROWS = 8;
	final int BOARD_COLUMNS = 12;
	final int LINE_LEN_TO_WIN = 4;

	final int WIN_POINTS = LINE_LEN_TO_WIN;

	private TicTacToeUI ui;
	private Board board;

	private ArrayList<Player> players = new ArrayList<>();

	public GameController()
	{
		board = new Board( BOARD_ROWS, BOARD_COLUMNS, LINE_LEN_TO_WIN );

		players.add( new Player( "1", 'X' ) );
		players.add( new Player( "2", 'O' ) );
		// players.add( new Player( "3", 'M' ) );

		for( Player player : players )
			board.addPlayer( player );

		ui = new TicTacToeUI( this, BOARD_ROWS, BOARD_COLUMNS );

		writePlayerScores();

		restartGame();
	}

	private void writePlayerScores()
	{
		StringBuilder str = new StringBuilder();

		str.append( "<html>" );
		str.append( "<h2><center>Scores</center></h2>" );
		for( Player player : players )
		{
			str.append( "<p>Player '" + player.getId() + "' ( " + player.getMark() + " ) :  " + player.getPoints() + "</p>" );
		}
		str.append( "</html>" );

		ui.writeScores( str.toString() );
	}

	private void writeNextPlayerInfo()
	{
		Player curPlayer = board.getCurPlayer();
		ui.writeInfo( "Player '" + curPlayer.getId() + "' ( " + curPlayer.getMark() + " ) turn" );
	}

	public void restartGame()
	{
		for( int i = 0; i < BOARD_ROWS; ++i )
			for( int j = 0; j < BOARD_COLUMNS; ++j )
				ui.setCellText( i, j, "" );

		board.startGame();
		writeNextPlayerInfo();
	}

	public void cellClicked( int row, int column )
	{
		if( board.isGameOver() )
			return;

		String playerMark = "" + board.getCurPlayer().getMark();
		if( board.makeMove( row, column ) )
		{
			ui.setCellText( row, column, playerMark );
		}

		if( board.isGameOver() )
		{
			Player winner = board.getWinner();
			if( winner != null )
			{
				winner.addPoints( WIN_POINTS );

				for( int[] cell : board.getWinLine() )
					ui.setCellText( cell[ 0 ], cell[ 1 ], "<html><p style=\"color:#FF0000\">" + playerMark + "</p><html>" );

				ui.writeInfo( "<html><center><h1 style=\"color:#FF0000\">Game Over</h1>" + "Player '" + winner.getId() + "' ( " + winner.getMark() + " ) wins</center></html>" );
				writePlayerScores();
			}
			else
			{
				// Game over, no winner
				ui.writeInfo( "<html><center><h1 style=\"color:#FF0000\">Game Over</h1></center></html>" );
			}
		}
		else
		{
			// Waiting next turn
			writeNextPlayerInfo();
		}
	}
}
