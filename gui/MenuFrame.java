package gui;
import javax.swing.JFrame;
import startup.HospitalSystem;

    /**
     * The frame for the window to display the operations that involve patients.
     */
    public class MenuFrame extends JFrame {
        /** The standard width for the frame. */
        public static final int DEFAULT_WIDTH = 500;

        /** The standard height for the frame. */
        public static final int DEFAULT_HEIGHT = 300;

        /**
         * Create the frame for the operations that involve patients.
         */
        public MenuFrame() {
            setTitle("Main Menu");
            setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
            MenuPanel panel = new MenuPanel();
            add(panel);
        }

        /**
         * A main to run the GUI version of the hospital system that only involves patient operations
         * and the ward.
         */
        public static void main(String[] args) {
            HospitalSystem system = new HospitalSystem();
            system.initialize();
            MenuFrame frame = new MenuFrame();
            frame.setLocation(300, 300);
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            frame.setVisible(true);
        }
        public static final long serialVersionUID = 1;

}

