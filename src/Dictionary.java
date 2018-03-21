
public class Dictionary {
	private char character;
	private int code;
	
	public Dictionary(char c1, int c2)
	{
		character=c1;
		code=c2;
	}
	
	public void setCharacter(char c)
	{
		character=c;
	}
	
	public void setCode(int c)
	{
		code=c;
	}
	
	public char getCharacter()
	{
		return character;
	}
	
	public int getCode()
	{
		return code;
	}
	
	public String toString()
	{
		return code+" ";
	}
	
}
