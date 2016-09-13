package CDS.CDS;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
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
                	return s1.index-s2.index;
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
        {System.out.println("current: "+current);
        	if(color[parr[current].index]=='w') //if its white 
        	{
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
        		current++;
        	}
        	else if(color[parr[current].index]=='g')
        	{
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
        		 color[current]='b';
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
        		current++;
        	}
        	else
        	{
        	  current++;
        	}
        }
        /*for(int i=0;i<10;i++)
          System.out.println("color["+(i+1)+"]: "+color[i]);
        */
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
        for(int i=0;i<10;i++)
            System.out.println("color["+(i+1)+"]: "+color[i]);
    }
}
