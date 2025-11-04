package gr.aueb.cf.dpapp;

import gr.aueb.cf.dpapp.viewcontroller.*;

import java.awt.EventQueue;

/**
 * Main class for launching the Desktop Application.
 * <p>
 * This class initializes all the frames of the application and
 * manages their visibility and location on the screen.
 * </p>
 */
public class Main {

	// Application frames
	private final static MainMenuFrame mainMenuFrame = new MainMenuFrame();
	private final static DoctorsMenuFrame doctorsMenuFrame = new DoctorsMenuFrame();
	private final static DoctorsInsertFrame doctorsInsertFrame = new DoctorsInsertFrame();
	private final static DoctorsUpdateDeleteFrame doctorsUpdateDeleteFrame = new DoctorsUpdateDeleteFrame();
	private final static PatientsMenuFrame patientsMenuFrame = new PatientsMenuFrame();
	private final static PatientsInsertFrame patientsInsertFrame = new PatientsInsertFrame();
	private final static PatientsUpdateDeleteFrame patientsUpdateDeleteFrame = new PatientsUpdateDeleteFrame();
	private final static DoctorsPatientsFrame doctorsPatientsFrame = new DoctorsPatientsFrame();

	/**
	 * Launches the application.
	 * All frames are initialized, positioned at the center of the screen,
	 * and only the main menu frame is made visible.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				mainMenuFrame.setLocationRelativeTo(null);
				mainMenuFrame.setVisible(true);

				doctorsMenuFrame.setLocationRelativeTo(null);
				doctorsMenuFrame.setVisible(false);

				doctorsInsertFrame.setLocationRelativeTo(null);
				doctorsInsertFrame.setVisible(false);

				doctorsUpdateDeleteFrame.setLocationRelativeTo(null);
				doctorsUpdateDeleteFrame.setVisible(false);

				patientsMenuFrame.setLocationRelativeTo(null);
				patientsMenuFrame.setVisible(false);

				patientsInsertFrame.setLocationRelativeTo(null);
				patientsInsertFrame.setVisible(false);

				patientsUpdateDeleteFrame.setLocationRelativeTo(null);
				patientsUpdateDeleteFrame.setVisible(false);

				doctorsPatientsFrame.setLocationRelativeTo(null);
				doctorsPatientsFrame.setVisible(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/** Returns the main menu frame. */
	public static MainMenuFrame getMainMenuFrame() {
		return mainMenuFrame;
	}

	/** Returns the doctors menu frame. */
	public static DoctorsMenuFrame getDoctorsMenuFrame() {
		return doctorsMenuFrame;
	}

	/** Returns the doctors insert frame. */
	public static DoctorsInsertFrame getDoctorsInsertFrame() {
		return doctorsInsertFrame;
	}

	/** Returns the doctors update/delete frame. */
	public static DoctorsUpdateDeleteFrame getDoctorsUpdateDeleteFrame() {
		return doctorsUpdateDeleteFrame;
	}

	/** Returns the patients menu frame. */
	public static PatientsMenuFrame getPatientsMenuFrame() {
		return patientsMenuFrame;
	}

	/** Returns the patients insert frame. */
	public static PatientsInsertFrame getPatientsInsertFrame() {
		return patientsInsertFrame;
	}

	/** Returns the patients update/delete frame. */
	public static PatientsUpdateDeleteFrame getPatientsUpdateDeleteFrame() {
		return patientsUpdateDeleteFrame;
	}

	/** Returns the doctors-patients link frame. */
	public static DoctorsPatientsFrame getDoctorsPatientsFrame() {
		return doctorsPatientsFrame;
	}
}
