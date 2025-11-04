package gr.aueb.cf.dpapp.viewcontroller;

import gr.aueb.cf.dpapp.Main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JSeparator;

/**
 * The {@code DoctorsMenuFrame} class represents the menu screen for doctor management.
 * <p>
 * From this menu the user may:
 * <ul>
 *     <li>View, update, or delete doctors</li>
 *     <li>Insert a new doctor</li>
 *     <li>Return to the main menu</li>
 * </ul>
 */
public class DoctorsMenuFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Constructs the Doctors Menu frame and initializes all UI components.
	 */
	public DoctorsMenuFrame() {

		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(Thread.currentThread().getContextClassLoader().getResource("medical.png")));
		setTitle("Doctors' Menu");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton doctorsViewBtn = new JButton("View Doctors");
		doctorsViewBtn.addActionListener(e -> {
			Main.getDoctorsUpdateDeleteFrame().setVisible(true);
			Main.getDoctorsMenuFrame().setEnabled(false);
		});
		doctorsViewBtn.setForeground(Color.BLUE);
		doctorsViewBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		doctorsViewBtn.setBounds(137, 33, 147, 50);
		contentPane.add(doctorsViewBtn);

		JButton doctorsInsertBtn = new JButton("Insert Doctor");
		doctorsInsertBtn.addActionListener(e -> {
			Main.getDoctorsInsertFrame().setVisible(true);
			Main.getDoctorsMenuFrame().setEnabled(false);
		});
		doctorsInsertBtn.setForeground(Color.BLUE);
		doctorsInsertBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		doctorsInsertBtn.setBounds(137, 105, 147, 50);
		contentPane.add(doctorsInsertBtn);

		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(e -> {
			Main.getMainMenuFrame().setEnabled(true);
			Main.getDoctorsMenuFrame().setVisible(false);
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		closeBtn.setBounds(324, 217, 102, 36);
		contentPane.add(closeBtn);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 185, 416, 1);
		contentPane.add(separator);
	}
}
