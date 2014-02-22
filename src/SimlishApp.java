import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;


public class SimlishApp implements ActionListener {
	private JFrame frame = new JFrame();
	private JPanel contentPane;
	private JButton btnLoadFile;
	private JFileChooser fc;
	private JTextArea textArea, textArea_1;
	private File candidateSimlish;
	private SimlishParser parser;
	private SimlishTokenizer tokenizer;
	
	public void createGUI() {
		frame.addWindowFocusListener(new WindowAdapter() {
			public void windowGainedFocus(WindowEvent e) {
		        textArea.requestFocusInWindow();
		    }
		});
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public SimlishApp() throws Exception {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 600, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new CardLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel.add(tabbedPane, "name_25154387111841");
		
		JScrollPane scrollPane = new JScrollPane();
		tabbedPane.addTab("Interpreter", null, scrollPane, null);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		tabbedPane.addTab("Tokenizer", null, scrollPane_1, null);
		
		textArea_1 = new JTextArea();
		textArea_1.setEditable(false);
		scrollPane_1.setViewportView(textArea_1);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setRequestFocusEnabled(false);
		toolBar.setBorderPainted(false);
		toolBar.setOpaque(false);
		toolBar.setFloatable(false);
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		fc = new JFileChooser();
		
		btnLoadFile = new JButton("Load and Interpret File..");
		toolBar.add(btnLoadFile);
		
		btnLoadFile.addActionListener(this);
		
		parser = new SimlishParser();
		tokenizer = new SimlishTokenizer();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnLoadFile ) {
            int returnVal = fc.showOpenDialog(frame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                candidateSimlish = fc.getSelectedFile();       
                textArea.setText("");
                textArea_1.setText("");
                textArea.append("Interpreting "+candidateSimlish.getName()+"...\n");
                
                LinkedList<Token> list;
                
                try {
                	final String EoL = System.getProperty("line.separator");
                	List<String> lines = Files.readAllLines(candidateSimlish.toPath(),
                	        Charset.defaultCharset());

                	StringBuilder sb = new StringBuilder();
                	for (String line : lines) {
                	    sb.append(line).append(EoL);
                	}
                	final String content = sb.toString();
                	tokenizer.tokenize(content);
                	list = tokenizer.getTokens();
                	
                	textArea_1.append("\t=LEXEME=\t\t\t=TOKEN=\n");
                	for ( int i = 0; i < list.size(); i++ ) {
                		textArea_1.append("("+(i+1)+")\t" + list.get(i).lexeme + "\t\t\t" + list.get(i).token+"\n");
                	}
                	
                	parser.parse(list);
                	textArea_1.append(parser.getOutput());
                	
                	
                } catch (ParserException p) {
                	textArea.append("CHEE WAGA CHOO CHOO! "+p.getMessage());
                } catch (NumberFormatException pp) {
                	textArea.append("UROCKA LALALA KONDO! "+pp.getMessage());
                } 
                catch (Exception ppp) {
                	textArea.append("PODERNO WEEBEE! "+ppp.getMessage());
                }
            }
        } 
	}		
}
