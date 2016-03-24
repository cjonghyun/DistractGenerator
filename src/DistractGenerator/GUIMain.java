package DistractGenerator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import javax.swing.JFrame;

public class GUIMain extends JPanel implements ActionListener{
	protected JButton search;
	protected JTextArea text;
	protected JLabel label;
	protected JTextArea output; 
	protected JButton export;
	protected JScrollPane scroll;
	protected JScrollPane inputScroll;
	DistractGenerator dg;
	public GUIMain(){
		label = new JLabel("<html>Input text <br!> or AVL code</html>");
		text = new JTextArea();
		search = new JButton("Search");
		export = new JButton("Export");
		search.addActionListener(this);
		export.addActionListener(this);
		output = new JTextArea();
		output.setEditable(false);
		inputScroll = new JScrollPane(text);
		scroll = new JScrollPane(output);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    inputScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		this.setLayout(null);
		label.setBounds(10,10, 150, 60);
		inputScroll.setBounds(100,10,150,60);
		search.setBounds(250, 10, 80, 30);
		export.setBounds(300,900, 200, 30);
		scroll.setBounds(10, 80, 800, 800);
		add(label);
		add(inputScroll);
		add(search);
		add(export);
		add(scroll);
	}
	
	private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Distractor Generator");
        GUIMain gui = new GUIMain();
        gui.setPreferredSize(new Dimension(830,950));
        frame.setContentPane(gui);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == search){
			scroll.getVerticalScrollBar().setValue(100);
			scroll.getHorizontalScrollBar().setValue(0);
			String input = text.getText();
			dg = new DistractGenerator();
			dg.read();
			dg.generate(input);
			if(dg.getOutput() == null){
				output.setText("Can not find the word");
			}
			else{
				String result = dg.getOutput();				
				output.setText(result);
			}
	       output.setCaretPosition(0);
		}
		if(e.getSource() == export){
            JFileChooser exportFile = new JFileChooser();
            exportFile.showSaveDialog(this);
            int returnVal = exportFile.showSaveDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION){
            	String filePath = exportFile.getSelectedFile().getPath();
            	if(!filePath.endsWith(".csv"))
            		filePath += ".csv";
            	dg.exportToCSV(filePath);
            }

		}
	}
}
