package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MenuPanel extends JPanel {
    public MenuPanel() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(Box.createVerticalGlue());

        // add a button to do Patient Operations
        JButton PatientOpsButton = new JButton("Patient Operations");
        PatientOpsButton.setMaximumSize(PatientOpsButton.getPreferredSize());
        add(PatientOpsButton);
        PatientOpsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        PatientOpsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                PatientOpsFrame frame = new PatientOpsFrame();
                frame.setLocation(300, 300);
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
        add(Box.createVerticalGlue());

        // add a panel with a field to access a specific patient
        JButton DoctorOpsButton = new JButton("Doctor Operations");
        DoctorOpsButton.setMaximumSize(DoctorOpsButton.getPreferredSize());
        add(DoctorOpsButton);
        DoctorOpsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        DoctorOpsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                DoctorOpsFrame frame = new DoctorOpsFrame();
                frame.setLocation(300, 300);
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
        add(Box.createVerticalGlue());

        // add a button to display all the patients
        JButton WardListButton = new JButton("List Ward Information");
        WardListButton.setMaximumSize(WardListButton.getPreferredSize());
        add(WardListButton);
        WardListButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        WardListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
            WardFrame wardList = new WardFrame();
            wardList.setLocation(300,300);
            wardList.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            wardList.setVisible(true);
        }
        });
        add(Box.createVerticalGlue());

        // add a button to exit from the window containing this panel
        final JButton exitButton = new JButton("Exit");
        exitButton.setMaximumSize(exitButton.getPreferredSize());
        add(exitButton);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                exitButton.getTopLevelAncestor().setVisible(false);
                System.exit(0);
            }
        });
        add(Box.createVerticalGlue());
    }

    public static final long serialVersionUID = 1;
}
