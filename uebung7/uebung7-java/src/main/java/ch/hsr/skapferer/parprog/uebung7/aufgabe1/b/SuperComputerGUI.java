package ch.hsr.skapferer.parprog.uebung7.aufgabe1.b;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SuperComputerGUI extends JPanel implements ActionListener {
	private static final long serialVersionUID = 4998293627753886206L;
	private JLabel resultLabel;
	private final TheSupercomputer theSupercomputer;

	public SuperComputerGUI() {
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);

		addImg();
		addLabel("Confused? google the result.");
		addStartButton();

		resultLabel = addLabel("Result: ?");
		add(Box.createRigidArea(new Dimension(0, 5)));

		this.theSupercomputer = new TheSupercomputer(resultLabel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		theSupercomputer.execute();
	}

	private void addStartButton() {
		add(Box.createRigidArea(new Dimension(0, 5)));
		JButton startButton = new JButton("Start");
		startButton.setToolTipText("Starts the calculation to find the answer to the Ultimate Question of Life, the Universe, and Everything");
		startButton.addActionListener(this);
		add(startButton);
	}

	private JLabel addLabel(String text) {
		add(Box.createRigidArea(new Dimension(0, 5)));
		JLabel label = new JLabel(text);
		add(label);
		return label;
	}

	private void addImg() {
		java.net.URL imgURL = getClass().getClassLoader().getResource("supercomputer.jpg");
		if (imgURL != null) {
			add(new JLabel(new ImageIcon(imgURL)));
		}
	}

	private static void createAndShowGUI() {
		JFrame frame = new JFrame("The Supercomputer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SuperComputerGUI contentPane = new SuperComputerGUI();
		frame.setContentPane(contentPane);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}
}