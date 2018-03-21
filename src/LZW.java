import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Scanner;


public class LZW {

	private Hashtable<Integer, String> h=new Hashtable<Integer, String>();
	private int ind=256;
	
	public Integer getKey(String st)
	{
		for(int i=0; i<ind;i++)
		{
			if (h.get(i).equals(st)) return new Integer(i);
		}
		
		return null;
	}
	
	
	public ArrayList<Integer> lzw_compressor(String text)
	{
		ArrayList<Integer> result=new ArrayList<Integer>();

		for(int i=0; i<=255; i++)
		{
			h.put( i,(char)i+"");
		}

		String s=new String(text.charAt(0)+"");
		
		for(int i=1; i<text.length();i++)
		{
			char c=text.charAt(i);
			if(h.contains(s+c+"")) s=s+c;
				else
				{
					result.add(getKey(s));
					h.put(ind, s+c);
					ind++;
					s=c+"";
				}
		}
		result.add(getKey(s));
		return result;
	}
	
	public String LZW_decompressor(ArrayList<Integer> res)
	{
		h=new Hashtable<Integer, String>();
		ind=256;
		String tex;
		String s=new String();
		
		for(int i=0; i<=255; i++)
		{
			h.put( i,(char)i+"");
		}
		Object[] indices=res.toArray();
		Integer current=(Integer)indices[0];
		Integer previous;
		tex=new String(h.get(current));
		for(int i=1; i<res.size();i++)
		{
			previous=current;
			current=(Integer)indices[i];
			if(h.containsKey(current))
			{
				s=h.get(current);
				tex=tex+s;
				h.put(ind++, h.get(previous)+s.charAt(0));
			}
			else
			{
				s=h.get(previous)+h.get(current).charAt(0);
				tex=tex+s;
				h.put(ind++,  s);
			}
		}
		return tex;
	}
	

	public String resToString(ArrayList<Integer> res)
	{
		String s=new String();
		Iterator<Integer> it=res.iterator();
		while(it.hasNext())
		{
			s=s+" "+it.next().toString();
		}
		return s;
	}
	
	public byte[] strToByte(String s)
	{
		byte[] b=new byte[1000];
		for(int i=0;i<s.length();i++)
		{
			b[i]=(byte)s.charAt(i);
		}
		return b;
	}
	
	public String readFromFile(String fileName) throws IOException
	{
		DataInputStream d = new DataInputStream(new FileInputStream(fileName));
		String count;
		String continut=new String();
		while((count=d.readLine())!=null)
		{
			continut=continut+count+"\r\n";
		}
		d.close();
		return continut;
	}
	
	public void writeToFile(String fileName, String s) throws IOException
	{
		DataOutputStream out = new DataOutputStream(new FileOutputStream(fileName));
		out.writeBytes(s);
		out.close();
	}
	
	public ArrayList<Integer> strToArray(String s)
	{
		ArrayList<Integer> list=new ArrayList<Integer>();
		
		 Scanner fi = new Scanner(s);
		    fi.useDelimiter("[^\\p{Alnum},\\.-]"); 
		    while (true) {
		        if (fi.hasNextInt())
		            list.add(fi.nextInt());
		        else
		            break;
		    }
		
		return list;
	}
	
	public static void main(String[] args) throws IOException
	{
		
		if(args.length!=3)
			{
				System.out.println("Wrong number of arguments");
				System.exit(1);
			}
		
		String text=new String();

		ArrayList<Integer> res=new ArrayList<Integer>();
		LZW lzw=new LZW();
		
		text=lzw.readFromFile(args[1]);
		
		if(((String)args[0]).equals("-c"))
			{
				res=lzw.lzw_compressor(text);
				lzw.writeToFile(args[2], lzw.resToString(res));
			}
		
		else if(((String)args[0]).equals("-d"))
		{
			res=lzw.strToArray(text);
			text=lzw.LZW_decompressor(res);
			lzw.writeToFile(args[2],  text);
		}

	}
	
}
