package CDS.CDS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

public class ConnectedDominatingSetAlgorithm {
   
	int vertices;
	int graph[][];
	PairCDS parr[];
	char color[];
	Graph g;
	public ConnectedDominatingSetAlgorithm(String filename, int vertex) throws IOException {
		// TODO Auto-generated constructor stub
		vertices=vertex;
		g=new SingleGraph("CDSGraph");
		graph=new int[vertices][vertices];
		parr=new PairCDS[vertices];
		for(int i=0;i<vertices;i++)
			parr[i]=new PairCDS();
		color=new char[vertices];
		for(int i=0;i<vertices;i++)
	           color[i]='w';
		for(int i=0;i<vertices;i++)
			g.addNode("" + i);
		for (Node node : g) {
	        node.addAttribute("ui.label", node.getId());
	    }
		g.addAttribute("ui.stylesheet", "url('file:///E:/ThesisProject/ThesisProject/CDS/src/main/java/CDS/CDS/stylinggraph.css')");
		File file = new File(filename);
		filereader(file);
	}
	public void filereader(File fin) throws IOException {
		FileInputStream fis = new FileInputStream(fin);

		//Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String line = null;
		int p=0;
		while ((line = br.readLine()) != null){
			//System.out.println(line);
			StringTokenizer st=new StringTokenizer(line);
			int k=0;
			String starr[]=new String[2];
			while(st.hasMoreTokens())
			{
				starr[k++]=st.nextToken();
			}
		    int a=Integer.parseInt(starr[0]);
		    int b=Integer.parseInt(starr[1]);
		    graph[a][b]=1;
		    graph[b][a]=1;
		    g.addEdge("" + p,"" + a,"" + b);
		    p++;
		}
		br.close();
	}
	public int finddegree(int index)
    {
    	int degree=0;
    	for(int i=0;i<vertices;i++)
    	{
    		if(graph[index][i]==1)
    			degree++;
    	}
    	return degree;
    }
	public void printcolor()
    {
    	for(int i=0;i<vertices;i++)
    	 System.out.print("color["+i+"]="+color[i]+", ");
    }
	public void execute()
	{
		initialize();
		phaseOneAlgorithm();
		phaseTwoAlgorithm();
		printcolor();
	}
	public void initialize() {
		// TODO Auto-generated method stub
		HashMap<Integer,Integer> hm=new HashMap<Integer, Integer>();
        for(int i=0;i<vertices;i++)
        {
        	//System.out.println(i);
        	parr[i].index=i;
            parr[i].coveringnumber=finddegree(i);
            hm.put(new Integer(parr[i].index),new Integer(parr[i].coveringnumber));
        }
       Arrays.sort(parr, new Comparator<PairCDS>() {
            public int compare(PairCDS s1, PairCDS s2) {
                if(s1.coveringnumber < s2.coveringnumber)
                	return 1;
                else if(s1.coveringnumber == s2.coveringnumber)
                	return 0;
                else
                	return -1;
            }
        });
	}
	public void phaseOneAlgorithm() {
		// TODO Auto-generated method stub
		//phase-1 starts
        int current=0;
        int whites=vertices;
        while(whites!=0)
        {//System.out.println("index selected: " + (parr[current].index+1));
        	if(color[parr[current].index]=='w') //if its white 
        	{   //System.out.println("its white and color changed to black");
        		color[parr[current].index]='b';
        		whites--;
        		for(int i=0;i<vertices;i++)
        		{
        			if(graph[parr[current].index][i]==1)
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
        	{//System.out.println("its grey");
        		boolean f=false;
        		for(int i=0;i<vertices;i++)
        		{
        			if(graph[parr[current].index][i]==1)
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
        		 for(int i=0;i<vertices;i++)
        		 {
        			 if(graph[parr[current].index][i]==1)
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
        
        for(int i=0;i<vertices;i++)
        {
        	int blackcount=0;
        	for(int j=0;j<vertices;j++)
        	{
        		if(graph[i][j]==1 && color[j]=='b' && color[i]=='g')
        			blackcount++;
        	}
        	if(blackcount>=2)
        		color[i]='y';
        }
        /*
        for(int i=0;i<vertices;i++)
        {
        	if(color[i]=='b')
        	{
        		Node n = g.getNode(""+i);
        	    n.setAttribute("ui.class", "included");
        	}
        	if(color[i]=='y')
        	{
        		Node n = g.getNode(""+i);
        	    n.setAttribute("ui.class", "waiting");
        	}
        }
        */
        //g.display();
	}
	public void phaseTwoAlgorithm() {
		// TODO Auto-generated method stub
		//phase-1 over phase 2 starts
        for(int i=0;i<vertices;i++)
        {
        	if(color[i]=='y')
        	{
        	    HashSet<Integer> blackset=new HashSet<Integer>();
        	    for(int j=0;j<vertices;j++)
        	    {
        	    	if(graph[i][j]==1 && color[j]=='b')
        	    	{
        	    		for(int k=0;k<vertices;k++)
        	    		{
        	    			if(graph[j][k]==1)
        	    				blackset.add(new Integer(k));
        	    		}
        	    	}
        	    }
        	    boolean iscover=true;
        	    for(int j=0;j<vertices;j++)
        	    {
        	    	if(graph[i][j]==1 && !blackset.contains(new Integer(j)))
        	    	{
        	    		iscover=false;
        	    		break;
        	    	}
        	    }
        	    if(!iscover)
        	    {
        	    	color[i]='b';
        	    }
        	    else
        	    {
        	    	color[i]='g';
        	    }
        	}
        }
        for(int i=0;i<vertices;i++)
        {
        	if(color[i]=='b')
        	{
        	    HashSet<Integer> blackset=new HashSet<Integer>();
        	    for(int j=0;j<vertices;j++)
        	    {
        	    	if(graph[i][j]==1 && color[j]=='b')
        	    	{
        	    		for(int k=0;k<vertices;k++)
        	    		{
        	    			if(graph[j][k]==1)
        	    				blackset.add(new Integer(k));
        	    		}
        	    	}
        	    }
        	    boolean iscover=true;
        	    for(int j=0;j<vertices;j++)
        	    {
        	    	if(graph[i][j]==1 && !blackset.contains(new Integer(j)))
        	    	{
        	    		iscover=false;
        	    		break;
        	    	}
        	    }
        	    if(!iscover)
        	    {
        	    	color[i]='b';
        	    }
        	    else
        	    {
        	    	color[i]='g';
        	    }
        	}
        }
        for(int i=0;i<vertices;i++)
        {
        	if(color[i]=='b')
        	{
        		Node n = g.getNode(""+i);
        	    n.setAttribute("ui.class", "included");
        	}
        	if(color[i]=='y')
        	{
        		Node n = g.getNode(""+i);
        	    n.setAttribute("ui.class", "waiting");
        	}
        }
        g.display();
	}
}