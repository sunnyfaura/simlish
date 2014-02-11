import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;


public class SimlishApp implements ActionListener {
	private JFrame frame = new JFrame();
	private JPanel contentPane;
	private JButton btnLoadFile;
	private JFileChooser fc;
	private JTextArea textPane;
	private File candidateSimlish;
	private SimlishParser parser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimlishApp thing = new SimlishApp();
					thing.createGUI();			
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void createGUI() {
		frame.addWindowFocusListener(new WindowAdapter() {
		    public void windowGainedFocus(WindowEvent e) {
		        textPane.requestFocusInWindow();
		    }
		});
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public SimlishApp() {
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
		panel.add(tabbedPane, "name_12620652033253");
		
		textPane = new JTextArea();
		textPane.setEditable(false);
		tabbedPane.addTab("Interpreter", null, textPane, null);
		textPane.setWrapStyleWord(false);
		
		JTextArea textPane_1 = new JTextArea();
		textPane_1.setEditable(false);
		tabbedPane.addTab("Tokenizer", null, textPane_1, null);
		
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
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnLoadFile ) {
            int returnVal = fc.showOpenDialog(frame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                candidateSimlish = fc.getSelectedFile();
                parser.setFile(candidateSimlish);
            }
        } 
	}	
}
