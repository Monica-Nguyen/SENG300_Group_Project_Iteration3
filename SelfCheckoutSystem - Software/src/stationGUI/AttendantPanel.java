package stationGUI;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class AttendantPanel extends JPanel {

	private MainFrame mainFrame;
	private JFrame frame;
	private JLabel lblStationStatus;

	/**
	 * Creates the Attendent Panel
	 * 
	 * @param mainFrame The main frame used in the checkout system
	 */
	public AttendantPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		initComponents();
	}

	/**
	 * Creates the different components of the Attendant Panel
	 */
	private void initComponents() {
		setBounds(0, 0, 1280, 720);
		setLayout(new MigLayout("",
				"[517.00][252.00][136.00,grow][grow][101.00][131.00,grow][17.00][10.00][][][][][][][][][][][][][][][38.00][36.00,grow]",
				"[139.00,grow][129.00,grow][138.00,grow][134.00,grow][135.00,grow][124.00,grow]"));
		setVisible(false);


		JLabel lblSubTotal = new JLabel("Sub Total:");
		lblSubTotal.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblSubTotal.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblSubTotal, "cell 1 0");

		JLabel lblPrinterStatus = new JLabel("Printer Status:");
		lblPrinterStatus.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblPrinterStatus, "cell 4 0");

		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotal.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblTotal, "cell 1 1");

		//Creates the label to display the station status
		lblStationStatus = new JLabel("Station Status: ON");
		lblStationStatus.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblStationStatus, "cell 1 2");

		JLabel lblPaymentMethod = new JLabel("Payment Method:");
		lblPaymentMethod.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblPaymentMethod, "cell 2 3");

		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblStatus, "cell 2 4");

		JLabel lblChange = new JLabel("Change:");
		lblChange.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(lblChange, "cell 2 5");

		createButtons();

	}

	/**
	 * Creates all the buttons needed in the attendant station
	 */
	private void createButtons() {
		// Block Station button
		JButton btnBlock = new JButton("Block Station");
		btnBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.station.baggingArea.disable();
				mainFrame.station.mainScanner.disable();
				mainFrame.station.handheldScanner.disable();
			}
		});
		btnBlock.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnBlock, "cell 5 3");

		// Unlock Station button
		JButton btnUnblock = new JButton("Unblock Station");
		btnUnblock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mainFrame.station.baggingArea.isDisabled() && mainFrame.station.mainScanner.isDisabled()
						&& mainFrame.station.handheldScanner.isDisabled()) {
					mainFrame.station.baggingArea.enable();
					mainFrame.station.mainScanner.enable();
					mainFrame.station.handheldScanner.enable();
				}
			}
		});
		btnUnblock.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnUnblock, "cell 5 4");

		// Refill button
		JButton btnRefill = new JButton("Refill");
		btnRefill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createFrame();
				// Creating new Refill Panel
				RefillPanel dispenserPanel = new RefillPanel(mainFrame, frame);
				frame.getContentPane().add(dispenserPanel);
				frame.pack();
				frame.setVisible(true);
			}
		});
		btnRefill.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnRefill, "cell 0 0");

		// Empty Dispenser
		JButton btnEmpty = new JButton("Empty Storage");
		btnEmpty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createFrame();
				// Creating new Refill Panel
				EmptyPanel panel = new EmptyPanel(mainFrame, frame);
				frame.getContentPane().add(panel);
				frame.pack();
				frame.setVisible(true);
			}
		});
		btnEmpty.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnEmpty, "cell 0 1");

		// Creating the start up button
		JButton btnStartUp = new JButton("Start Up");

		// Creating the shut down button
		JButton btnShutDown = new JButton("Shut Down");

		// Creating an action listener for start up button and formatting
		btnStartUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.maintenance.startUp();
				btnStartUp.setVisible(false);
				btnShutDown.setVisible(true);
				lblStationStatus.setText("Station Status: ON");
			}
		});
		btnStartUp.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnStartUp, "cell 0 2");

		// Creating an action listener for shut down button and formatting
		btnShutDown.setVisible(true);
		btnShutDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.maintenance.shutDown();
				btnShutDown.setVisible(false);
				btnStartUp.setVisible(true);
				lblStationStatus.setText("Station Status: OFF");
			}
		});
		btnShutDown.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnShutDown, "cell 0 2");
		btnStartUp.setVisible(false);

		// Logout button
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.attendantPanel.setVisible(false);
				mainFrame.scanningPanel.setVisible(true);
			}
		});
		btnLogout.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(btnLogout, "cell 5 5");

	}
	
	/**
	 * Creates a new JFrame
	 */
	private void createFrame() {
		// Creating new frame for option panels
		frame = new JFrame("Option Frame");
		frame.setBounds(0, 0, 250, 370);
		frame.setResizable(false);
	}

}
