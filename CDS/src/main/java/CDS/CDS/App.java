package CDS.CDS;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import scala.collection.parallel.ParIterableLike.Find;
public class App 
{
    public static int finddegree(int arr[][],int index)
    {
    	int degree=0;
    	for(int i=0;i<10;i++)
    	{
    		if(arr[index][i]==1)
    			degree++;
    	}
    	return degree;
    }
    /*
    public static boolean findcover(int id1,int id2,int id3)
    {
    	//does id1 is covered by both id2 and id3 if yes return true else false
    	
    }
    */
    public static void printcolor(char color[])
    {
    	for(int i=0;i<10;i++)
    	 System.out.print("color["+(i+1)+"]="+color[i]+", ");
    }
    public static void main( String[] args )
    {               //1,2,3,4,5,6,7,8,9,10
  final int arr[][]={{0,1,1,1,0,1,1,0,1,0},
                /*2*/{1,0,0,1,0,1,1,0,0,0},
                /*3*/{1,0,0,0,0,1,0,0,0,1},
                /*4*/{1,1,0,0,1,1,1,0,1,0},
                /*5*/{0,0,0,1,0,0,1,1,1,0},
                /*6*/{1,1,1,1,0,0,0,0,0,0},
                /*7*/{1,1,0,1,1,0,0,0,1,0},
                /*8*/{0,0,0,0,1,0,0,0,1,0},
                /*9*/{1,0,0,1,1,0,1,1,0,0},
               /*10*/{0,0,1,0,0,0,0,0,0,0}};
        char color[]=new char[10];
        for(int i=0;i<10;i++)
           color[i]='w';
        PairCDS parr[]=new PairCDS[10];
        final HashMap<Integer,Integer> hm=new HashMap<Integer, Integer>();
        for(int i=0;i<10;i++)
        {
        	//System.out.println(i);
            parr[i]=new PairCDS();
        	parr[i].index=i;
            parr[i].coveringnumber=finddegree(arr,i);
            hm.put(new Integer(parr[i].index),new Integer(parr[i].coveringnumber));
        }
       Arrays.sort(parr, new Comparator<PairCDS>() {
            public int compare(PairCDS s1, PairCDS s2) {
                if(s1.coveringnumber < s2.coveringnumber)
                	return 1;
                else if(s1.coveringnumber == s2.coveringnumber)
                {
                	//logic for MANET
                	/*
                	int deg1=0;
                	int deg2=0;
                	for(int i=0;i<10;i++)
                	{
                		if(arr[s1.index][i]==1)
                			deg1=deg1+hm.get(new Integer(i)).intValue();
                		if(arr[s2.index][i]==1)
                			deg2=deg2+hm.get(new Integer(i)).intValue();
                	}
                	if(deg1 < deg2)
                		return -1;
                	else if(deg1==deg2)
                		return s1.index-s2.index;
                	else
                		return 1;
                	*/
                	return 0;
                }
                else
                	return -1;
            }
        });
        for(int i=0;i<10;i++)
        	System.out.println("index: "+ (parr[i].index+1) + "covering number: " + parr[i].coveringnumber);
        //phase-1 starts
        int current=0;
        int whites=10;
        while(whites!=0)
        {System.out.println("index selected: " + (parr[current].index+1));
        	if(color[parr[current].index]=='w') //if its white 
        	{   //System.out.println("its white and color changed to black");
        		color[parr[current].index]='b';
        		whites--;
        		for(int i=0;i<10;i++)
        		{
        			if(arr[parr[current].index][i]==1)
        			{
        				if(color[i]=='w')
        				{
        					color[i]='g';
        					whites--;
        				}
        			}
        		}
        		//printcolor(color);
        		current++;
        	}
        	else if(color[parr[current].index]=='g')
        	{//System.out.println("its grey");
        		boolean f=false;
        		for(int i=0;i<10;i++)
        		{
        			if(arr[parr[current].index][i]==1)
        			{
        				if(color[i]=='w')
        				{
        					f=true;
        					break;
        				}
        			}
        		}
        		if(f)
        		{
        		// System.out.println("and its changed to black");
        		 color[parr[current].index]='b';
        		 for(int i=0;i<10;i++)
        		 {
        			 if(arr[parr[current].index][i]==1)
         			{
         				if(color[i]=='w')
         				{
         					color[i]='g';
         					whites--;
         				}
         			}
        		 }
        		}
        		//printcolor(color);
        		current++;
        	}
        	else
        	{
        	  //System.out.println("its black skip!!!");
        	  current++;
        	}
        }      
        
        for(int i=0;i<10;i++)
        {
        	int blackcount=0;
        	for(int j=0;j<10;j++)
        	{
        		if(arr[i][j]==1 && color[j]=='b' && color[i]=='g')
        			blackcount++;
        	}
        	if(blackcount>=2)
        		color[i]='y';
        }
        //phase-1 over phase 2 starts
        for(int i=0;i<10;i++)
        {
        	if(color[i]=='y')
        	{
        	    HashSet<Integer> blackset=new HashSet<Integer>();
        	    for(int j=0;j<10;j++)
        	    {
        	    	if(arr[i][j]==1 && color[j]=='b')
        	    	{
        	    		for(int k=0;k<10;k++)
        	    		{
        	    			if(arr[j][k]==1)
        	    				blackset.add(new Integer(k));
        	    		}
        	    	}
        	    }
        	    boolean iscover=true;
        	    for(int j=0;j<10;j++)
        	    {
        	    	if(arr[i][j]==1 && !blackset.contains(new Integer(j)))
        	    	{
        	    		iscover=false;
        	    		break;
        	    	}
        	    }
        	    if(!iscover)
        	    {
        	    	color[i]='b';
        	    }
        	    else if(!blackset.contains(new Integer(i)))
        	    {
        	    	color[i]='b';
        	    }
        	    else
        	    {
        	    	color[i]='g';
        	    }
        	}
        }
        for(int i=0;i<10;i++)
        {
        	if(color[i]=='b')
        	{
        	    HashSet<Integer> blackset=new HashSet<Integer>();
        	    for(int j=0;j<10;j++)
        	    {
        	    	if(arr[i][j]==1 && color[j]=='b')
        	    	{
        	    		for(int k=0;k<10;k++)
        	    		{
        	    			if(arr[j][k]==1)
        	    				blackset.add(new Integer(k));
        	    		}
        	    	}
        	    }
        	    boolean iscover=true;
        	    for(int j=0;j<10;j++)
        	    {
        	    	if(arr[i][j]==1 && !blackset.contains(new Integer(j)))
        	    	{
        	    		iscover=false;
        	    		break;
        	    	}
        	    }
        	    if(!iscover)
        	    {
        	    	color[i]='b';
        	    }
        	    else if(!blackset.contains(new Integer(i)))
        	    {
        	    	color[i]='b';
        	    }
        	    else
        	    {
        	    	color[i]='g';
        	    }
        	}
        }
       printcolor(color);
    }
}
