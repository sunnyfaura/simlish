import java.awt.EventQueue;


public class SimlishDriver {
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
}
