package tictactoe;

public class Player
{
	private String id;
	private char mark;
	private int points = 0;

	public Player( String id, char mark )
	{
		this.id = id;
		this.mark = mark;
	}

	public String getId()
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

	public char getMark()
	{
		return mark;
	}

	public void addPoints( int winPoints )
	{
		this.points += winPoints;
	}

	public int getPoints()
	{
		return points;
	}
}
