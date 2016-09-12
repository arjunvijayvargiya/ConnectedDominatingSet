package CDS.CDS;

import java.util.Arrays;
import java.util.Comparator;

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
        int arr[][]={{0,1,1,1,0,1,1,0,1,0},
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
        for(int i=0;i<10;i++)
        {
        	//System.out.println(i);
            parr[i]=new PairCDS();
        	parr[i].index=i;
            parr[i].coveringnumber=finddegree(arr,i);
        }
       Arrays.sort(parr, new Comparator<PairCDS>() {
            public int compare(PairCDS s1, PairCDS s2) {
                if(s1.coveringnumber <= s2.coveringnumber)
                	return 1;
                else
                	return -1;
            }
        });
        for(int i=0;i<10;i++)
        	System.out.println("index: "+ (parr[i].index+1) + "covering number: " + parr[i].coveringnumber);
        
    }
}
