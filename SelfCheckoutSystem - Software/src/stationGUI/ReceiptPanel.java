package stationGUI;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class ReceiptPanel extends JPanel {

	private void initComponents()
	{
		setBounds(0,0,1280,720);
		setLayout(new MigLayout("","[]","[]"));
		setVisible(false);
	}

}
