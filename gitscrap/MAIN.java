package gitscrap;

import java.util.Scanner;

class abcd
{
	int element = 1;
	int i;
	abcd next;
	 abcd current = null;
	 abcd start;
	 abcd end;
	void enter()
	{
		
		Scanner input = new Scanner(System.in);
		int j = input.nextInt();
		abcd a = new abcd();
		a.i=j;
		a.next=null;
		if(element==1)
		{
			current=a;
			start = a;
		}
		else
		{
		current.next=a;
		current = a;
		}
		element++;
	}
	void reverse()
	{
		abcd prev;
		abcd current;
		abcd next;
		current = start;
		prev=null;
		while(current!=null)
		{
			next = current.next;
			current.next=prev;
			prev=current;
			current=next;
			
		}
		end=prev;
	}
}

public class MAIN {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		abcd a = new abcd();
		a.enter();
		a.enter();
		a.enter();
		abcd curr = a.start;
		while(curr!=null)
		{
			System.out.println(curr.i);
			curr = curr.next;
		}
		a.reverse();
		abcd rev = a.end;
		while(rev!=null)
		{
			System.out.println(rev.i);
			rev=rev.next;
		}
	}

}
