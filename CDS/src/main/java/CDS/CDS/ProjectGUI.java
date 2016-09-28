package CDS.CDS;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class ProjectGUI{

	JFrame jf;
	JPanel jp;
	JLabel jl1, jl2, jl3,error, addl1,dell1;
	ConnectedDominatingSetAlgorithm cds;
	JTextArea jt1,jaddt1,jdelt1;
	File file;
	JRadioButton jr1,jr2;
	JButton jb1,add,del,modify;
	ButtonGroup bg;
	public ProjectGUI() {
		// TODO Auto-generated constructor stub
		//initialize
		jf = new JFrame("Connected Dominating Set");
		jp = new JPanel();
		jl1 = new JLabel("Upload your graph file:"); 
		jl2 = new JLabel("Enter the number of vertices:");
		jl3 = new JLabel("Select part of algorithm for CDS:");
		addl1 = new JLabel("New Node Edges");
		dell1 = new JLabel("Node Delete");
		add=new JButton("Add Node (+)");
		del=new JButton("Delete Node (-)");
		error = new JLabel("");
		modify=new JButton("Modify");
		jt1 = new JTextArea();
		Border border=BorderFactory.createLineBorder(Color.BLACK,2);
		jt1.setBorder(border);
		jaddt1=new JTextArea();
		jaddt1.setText("a,b,c,d");
		jdelt1=new JTextArea();
		jaddt1.setBorder(border);
		jdelt1.setBorder(border);
		jr1 = new JRadioButton("Phase1",true);
		jr1.setActionCommand("Phase1");
		jr2 = new JRadioButton("Phase2",true);
		jr2.setActionCommand("Phase2");
		jb1 =new JButton("Generate");
		bg = new ButtonGroup();
		file=new File("");
		bg.add(jr1);
		bg.add(jr2);
		//settings
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		JButton button = new JButton("Upload");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					//System.out.println(selectedFile.getAbsolutePath());
					file=selectedFile;
					error.setForeground(Color.BLUE);
					error.setText("File Upload Successfully");
				}
			}
		});
		jb1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(!file.isFile()){
				   error.setForeground(Color.RED);
                   error.setText("Please Upload The Graph File");
				}
				else if(jt1.getText().toString().isEmpty()){
				   error.setForeground(Color.RED);
                   error.setText("Please Enter Vertices");
				}
				else{
					String str = bg.getSelection().getActionCommand();
					//System.out.println(str);
					String jtext = jt1.getText().toString();
					int vertices=Integer.parseInt(jtext);
					try {
						cds = new ConnectedDominatingSetAlgorithm(file,vertices);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						//System.out.println(" file error");
						error.setForeground(Color.RED);
					    error.setText("File Format Error/ Vertex Error");
					}
					if(str.contentEquals("Phase1"))
					{
						System.out.println("Phase1");
						try {
							cds = new ConnectedDominatingSetAlgorithm(file,vertices);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							System.out.println(" file error");
						}
						cds.execute1();
					}
					else 
					{
						System.out.println("Phase2");
						try {
							cds = new ConnectedDominatingSetAlgorithm(file,vertices);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							System.out.println(" file error");
						}
						cds.execute2();
					}
				}
			}	
		});
		add.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				cds.incVertices();
				cds.incGraph();
				cds.incColor();
				cds.updateUIAdd();
				String edges=jaddt1.getText().toString();
				StringTokenizer st=new StringTokenizer(edges,",");
				while(st.hasMoreTokens()){
					 int x=Integer.parseInt(st.nextToken());
			         cds.markEdge(x);
				}
				cds.addModifyAlgorithm();
			}
		});
		modify.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		button.setBounds(250, 20, 100, 20);
		jt1.setBounds(250, 50, 100, 20);
		jl1.setBounds(60, 20, 140, 20);
		jl2.setBounds(60, 50, 220, 20);
		jr1.setBounds(300, 80, 70, 20);
		jr2.setBounds(400, 80, 70, 20);
		jl3.setBounds(60, 80, 240, 20);
		jb1.setBounds(60, 110, 300,40);
		addl1.setBounds(60,160,100,20);
		jaddt1.setBounds(60,190,100,20);
		dell1.setBounds(60,220,100,20);
		jdelt1.setBounds(60,250,100,20);
		error.setBounds(400,20,200,40);
		add.setBounds(180,190,150,20);
		del.setBounds(180,250,150,20);
		modify.setBounds(60,280,300,40);
		//jl1.setForeground(Color.WHITE);
		jp.add(jl1);
		jp.add(jl2);
		jp.add(jl3);
		jp.add(jt1);
		jp.add(jaddt1);
		jp.add(addl1);
		jp.add(dell1);
		jp.add(jdelt1);
		jp.add(add);
		jp.add(del);
		jp.add(button);
		jp.add(jr1);
		jp.add(jb1);
		jp.add(jr2);
		jp.add(error);
		//jp.add(modify);
		jp.setLayout(null);
		//jp.setBackground(Color.DARK_GRAY);
		//jp.setBackground(Color.BLUE);

		//JFrame specs
		jf.add(jp);
		jf.setResizable(false);
		jf.setSize(600, 400);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
