package tictactoe;

import java.awt.*;
import javax.swing.*;

public class TicTacToeUI
{
	private JLabel infoLabel;
	private JLabel scoreLabel;
	private JButton buttons[][];

	public TicTacToeUI( final GameController controller, int rowsNumber, int columnsNumber )
	{
		JFrame frame = new JFrame( "TicTacToe by Anestis" );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		JPanel scorePanel = new JPanel();
		scorePanel.setLayout( new GridLayout( 1, 1 ) );
		scorePanel.setPreferredSize( new Dimension( 250, 150 ) );
		scoreLabel = new JLabel( "", SwingConstants.CENTER );
		scorePanel.add( scoreLabel );

		buttons = new JButton[ rowsNumber ][ columnsNumber ];
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout( new GridLayout( rowsNumber, columnsNumber ) );
		for( int i = 0; i < rowsNumber; ++i )
		{
			for( int j = 0; j < columnsNumber; ++j )
			{
				final int row = i;
				final int column = j;

				JButton button = buttons[ i ][ j ] = new JButton();
				button.setPreferredSize( new Dimension( 70, 70 ) );
				button.addActionListener( e -> controller.cellClicked( row, column ) );
				buttonPanel.add( button );
			}
			;
		}

		JPanel buttonPanel2 = new JPanel();
		buttonPanel2.setLayout( new GridBagLayout() );
		buttonPanel2.add( buttonPanel, new GridBagConstraints() );

		JPanel textPanel = new JPanel();
		textPanel.setLayout( new GridLayout( 1, 1 ) );
		textPanel.setPreferredSize( new Dimension( 450, 100 ) );
		infoLabel = new JLabel( "", SwingConstants.CENTER );
		textPanel.add( infoLabel );

		JPanel restartButtonPanel = new JPanel();
		restartButtonPanel.setLayout( new GridLayout( 1, 1 ) );
		JButton restartButton = new JButton( "Start New Round" );
		restartButton.addActionListener( e -> controller.restartGame() );
		restartButton.setPreferredSize( new Dimension( 0, 50 ) );
		restartButtonPanel.add( restartButton );

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout( new BoxLayout( mainPanel, BoxLayout.Y_AXIS ) );
		mainPanel.add( scorePanel );
		mainPanel.add( buttonPanel2 );
		mainPanel.add( textPanel );
		mainPanel.add( restartButtonPanel );

		frame.getContentPane().add( mainPanel );

		frame.pack();
		frame.setMinimumSize( frame.getSize() );
		frame.setLocationRelativeTo( null );
		frame.setVisible( true );
	}

	public void writeInfo( String text )
	{
		infoLabel.setText( text );
	}

	public void writeScores( String text )
	{
		scoreLabel.setText( text );
	}

	public void setCellText( int row, int column, String text )
	{
		buttons[ row ][ column ].setText( text );
	}
}
