package CDS.CDS;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class ProjectGUI{

	JFrame jf;
	JPanel jp;
	JLabel jl1, jl2, jl3,error;
	ConnectedDominatingSetAlgorithm cds;
	JTextArea jt1;
	//String filepath="";
	File file;
	JRadioButton jr1,jr2;
	JButton jb1;
	ButtonGroup bg;
	public ProjectGUI() {
		// TODO Auto-generated constructor stub
		//initialize
		jf = new JFrame("Connected Dominating Set");
		jp = new JPanel();
		jl1 = new JLabel("Upload your graph file:"); 
		jl2 = new JLabel("Enter the number of vertices:");
		jl3 = new JLabel("Select part of algorithm for CDS:");
		error = new JLabel("");
		jt1 = new JTextArea();
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
		button.setBounds(250, 20, 100, 20);
		jt1.setBounds(250, 50, 100, 20);
		jl1.setBounds(60, 20, 140, 20);
		jl2.setBounds(60, 50, 220, 20);
		jr1.setBounds(300, 80, 70, 20);
		jr2.setBounds(400, 80, 70, 20);
		jl3.setBounds(60, 80, 240, 20);
		jb1.setBounds(60, 110, 300,40);
		error.setBounds(400,20,200,40);
		//jl1.setForeground(Color.WHITE);
		jp.add(jl1);
		jp.add(jl2);
		jp.add(jl3);
		jp.add(jt1);
		jp.add(button);
		jp.add(jr1);
		jp.add(jb1);
		jp.add(jr2);
		jp.add(error);
		jp.setLayout(null);
		//jp.setBackground(Color.DARK_GRAY);
		//jp.setBackground(Color.BLUE);

		//JFrame specs
		jf.add(jp);
		jf.setResizable(false);
		jf.setSize(600, 200);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
