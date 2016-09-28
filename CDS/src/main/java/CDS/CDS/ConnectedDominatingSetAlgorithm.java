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
import java.util.PriorityQueue;
import java.util.Set;
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
	PriorityQueue<PairCDS> pq;
	HashSet<Integer> activeset;
	Graph g;
	int p;
	public Object resizeArray (Object oldArray, int newSize) {
		   int oldSize = java.lang.reflect.Array.getLength(oldArray);
		   Class elementType = oldArray.getClass().getComponentType();
		   Object newArray = java.lang.reflect.Array.newInstance(
		         elementType, newSize);
		   int preserveLength = Math.min(oldSize, newSize);
		   if (preserveLength > 0)
		      System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
		   return newArray; 
    }
	public void incGraph(){
		graph = (int[][])Arrays.copyOf(graph,vertices);
		for (int i=0; i<graph.length; i++) {
		     if (graph[i] == null)
		        graph[i] = new int[vertices];
		     else 
		    	graph[i] = (int[])Arrays.copyOf(graph[i], vertices); }
	}
	public void incColor(){
		color = Arrays.copyOf(color,vertices);
		color[vertices-1]='w';
	}
	public void incVertices(){
		vertices++;
	}
	public void markEdge(int x){
		graph[vertices-1][x]=1;
		g.addEdge(""+p,""+(vertices-1),""+x);
		p++;
	}
	public void updateUIAdd(){
		Node node=g.addNode(""+(vertices-1));
		node.addAttribute("ui.label", node.getId());
	}
	public ConnectedDominatingSetAlgorithm(File filename, int vertex) throws IOException {
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
		//File file = new File(filename);
		filereader(filename);
		activeset=new HashSet<Integer>();
		for(int i=0;i<vertices;i++)
			activeset.add(new Integer(i));
	}
	public void filereader(File fin) throws IOException {
		FileInputStream fis = new FileInputStream(fin);

		//Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String line = null;
		p=0;
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
	public void setColorClass()
	{
		for(int i=0;i<vertices;i++)
        {
        	if(color[i]=='b')
        	{
        		Node n = g.getNode(""+i);
        	    n.setAttribute("ui.class", "included");
        	}
        	else if(color[i]=='y')
        	{
        		Node n = g.getNode(""+i);
        	    n.setAttribute("ui.class", "waiting");
        	}
        	else if(color[i]=='g')
        	{
        		Node n = g.getNode(""+i);
        	    n.setAttribute("ui.class", "covered");
        	}
        	else
        	{
        		Node n = g.getNode(""+i);
        	    n.setAttribute("ui.class", "started");
        	}
        }
	}
	public void execute2()
	{
		initialize();
		phaseOneAlgorithm();
		phaseTwoAlgorithm();
		setColorClass();
	    Viewer view=g.display();
	    view.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
		printcolor();
	}
	public void execute1()
	{
		initialize();
		phaseOneAlgorithm();
		setColorClass();
		Viewer view=g.display();
		view.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
		printcolor();
	}
	public void initialize() {
		// TODO Auto-generated method stub
        for(int i=0;i<vertices;i++)
        {
        	parr[i].index=i;
            parr[i].coveringnumber=finddegree(i);
        }
        Set<PairCDS> CDSCollection = new HashSet<PairCDS>(Arrays.asList(parr));
        pq= new PriorityQueue<PairCDS>(new Comparator<PairCDS>() {
            public int compare(PairCDS s1, PairCDS s2) {
                if(s1.coveringnumber < s2.coveringnumber)
                	return 1;
                else if(s1.coveringnumber == s2.coveringnumber)
                	return 0;
                else
                	return -1;
            }
        });
        pq.addAll(CDSCollection);
	}
	public void phaseOneAlgorithm() {
		// TODO Auto-generated method stub
		//phase-1 starts
        int current;
        int whites=vertices;
        while(whites!=0)
        {//System.out.println("index selected: " + (parr[current].index+1));
        	current=pq.poll().index; 
        	if(color[current]=='w') //if its white 
        	{   //System.out.println("its white and color changed to black");
        		color[current]='b';
        		whites--;
        		for(int i=0;i<vertices;i++)
        		{
        			if(graph[current][i]==1)
        			{
        				if(color[i]=='w')
        				{
        					color[i]='g';
        					whites--;
        				}
        			}
        		}
        	}
        	else if(color[current]=='g')
        	{//System.out.println("its grey");
        		boolean f=false;
        		for(int i=0;i<vertices;i++)
        		{
        			if(graph[current][i]==1)
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
        		 color[current]='b';
        		 for(int i=0;i<vertices;i++)
        		 {
        			 if(graph[current][i]==1)
         			{
         				if(color[i]=='w')
         				{
         					color[i]='g';
         					whites--;
         				}
         			}
        		 }
        		}
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
        	    HashSet<Integer> blackset=new HashSet<Integer>(); //constant time
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
	}
	public void addModifyAlgorithm(){
		
		for(int i=0;i<vertices;i++){
			if(graph[vertices-1][i]==1 && color[i]=='b'){
				color[vertices-1]='g';
				setColorClass();
				g.display();
				printcolor();
				return;
			}
		}
		for(int i=0;i<vertices;i++){
			if(graph[vertices-1][i]==1){
				for(int j=0;j<vertices;j++){
					if(graph[i][j]==1 && color[j]=='b'){
						color[i]='b';
						color[vertices-1]='g';
						setColorClass();
						g.display();
					    printcolor();
						return;
					}
				}
			}
		}
	}
}