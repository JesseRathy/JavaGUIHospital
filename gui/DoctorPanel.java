package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
// import javax.swing.JTextField;
import javax.swing.JFrame;

import commands.AssignDoctorCommand;
import commands.DropAssocCommand;
//import containers.PatientSetAccess;
// import commands.AssignCommand;
//import entities.Patient;
import entities.Surgeon;
// import entities.BasicDoctor;
import entities.Doctor;

/**
 * The panel to display the information for a patient, and accept operations on the patient. The
 * panel gives the patient's name and health number. If the patient has bed in the ward, it is given
 * and the user has the option to remove the patient from the bed. If the patient does not have a
 * bed, a create is created for the ward information, so that the patient can be added to an empty
 * bed. The doctors of the patient are given, and the user has the option to add another doctor or
 * remove a doctor.
 */
public class DoctorPanel extends JPanel {
    /**
     * Create the panel to display the doctor's information and accept operations on the doctor.
     * 
     * @param doctor the doctor whose information is to be displayed and on whom operations can be
     *        done
     */
    public DoctorPanel(Doctor doctor) {
        /*
         * The creation of the panel is placed in another method as it needs to be invoked whenever
         * the patient information of the doctor is changed.
         */
        build(doctor);
    }

    /**
     * Fill in the panel to display the patient's information and accept operations on the patient.
     * 
     * @param doctor the doctor whose information is to be displayed and on whom operations can be
     *        done
     */
    private void build(Doctor doctor) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(new JLabel("Name: " + doctor.getName()));

        if (doctor instanceof Surgeon) {
            //add(new JLabel(""));
            add(new JLabel("Specialty: Surgeon"));
        }


        add(Box.createVerticalGlue());
        JPanel accessPatientPanel = accessPatient(doctor);
        add(accessPatientPanel);
        accessPatientPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        accessPatientPanel.setMaximumSize(accessPatientPanel.getPreferredSize());
        
        add(Box.createVerticalGlue());
        JPanel addPatientPanel = addPatientPanel(doctor);
        add(addPatientPanel);
        addPatientPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        addPatientPanel.setMaximumSize(addPatientPanel.getPreferredSize());

        add(new JLabel(" ")); // blank line in the panel for spacing
        add(Box.createVerticalGlue());
        JPanel rmvPatientPanel = removePatientPanel(doctor);
        add(rmvPatientPanel);
        rmvPatientPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rmvPatientPanel.setMaximumSize(rmvPatientPanel.getPreferredSize());

        // add an empty panel to force the exit component to the bottom
        JPanel emptyPanel = new JPanel();
        add(emptyPanel);
        emptyPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        final JButton exitButton = new JButton("Exit");
        add(exitButton);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                exitButton.getTopLevelAncestor().setVisible(false);
            }

        });
        //add(Box.createVerticalGlue());
    }

    private JPanel accessPatient(final Doctor doctor) {
        JPanel accessPatientPanel = new JPanel();
        accessPatientPanel.add(new JLabel("Find Patient of doctor"));
        final JTextField textField = new JTextField(10);
        accessPatientPanel.add(textField);
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String valueAsString = textField.getText();
                int healthNum = -1;
                if (valueAsString != null && valueAsString.length() > 0) {
                    try {
                        healthNum = Integer.parseInt(valueAsString);
                    } catch (NumberFormatException e) {
                        textField.setText("Not int: " + textField.getText());
                        textField.revalidate();
                        return;
                    }
                    PatientFrame frame = null;
                    if (doctor.hasPatient(healthNum)) {

                        try {
                            frame = new PatientFrame(healthNum);
                        } catch (RuntimeException e) {
                            textField.setText("Invalid id: " + textField.getText());
                            textField.revalidate();
                            return;
                        }
                        frame.setLocation(300, 300);
                        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                        frame.setVisible(true);
                        textField.revalidate();
                    } else {
                        JOptionPane.showMessageDialog(DoctorPanel.this,
                                "Patient is not associated with this doctor!");
                        // textField.setText("invalid Patient: " + textField.getText());
                        textField.revalidate();
                    }
                } else {
                    textField.setText("Empty field: " + textField.getText());
                    textField.revalidate();
                }

            }
        });
        return accessPatientPanel;
    }

    /**
     * A panel to add a doctor-patient association for this doctor. The panel as a prompt to enter
     * the doctor's name, and a field to enter the name.
     *
     * @param patient the current patient
     * @return a panel to associate a new doctor with this patient
     */
    private JPanel removePatientPanel(final Doctor doctor) {
        JPanel removePatientPanel = new JPanel();
        removePatientPanel.add(new JLabel("Remove patient from doctor"));
        final JTextField textField = new JTextField(10);
        removePatientPanel.add(textField);
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String valueAsString = textField.getText();
                int healthNum = -1;
                if (valueAsString != null && valueAsString.length() > 0) {
                    try {
                        healthNum = Integer.parseInt(valueAsString);
                    } catch (NumberFormatException e) {
                        textField.setText("Not int: " + textField.getText());
                        textField.revalidate();
                        return;
                    }
                    DropAssocCommand rmvAssoc = new DropAssocCommand();
                    rmvAssoc.dropAssociation(doctor.getName(), healthNum);
                    if (rmvAssoc.wasSuccessful()) {
                        // recreate the panel as it has changed
                        removeAll();
                        build(doctor);
                        revalidate();
                        JOptionPane.showMessageDialog(null, "Success! Patient with health number "
                                + healthNum + " Was removed!");
                    } else {
                        JOptionPane.showMessageDialog(DoctorPanel.this, rmvAssoc.getErrorMessage());
                        textField.revalidate();
                    }
                } else {
                    textField.setText("Empty field: " + textField.getText());
                    textField.revalidate();
                }
                
            }
            
        });

        return removePatientPanel;
    }

    /**
     * A panel to add a doctor-patient association for this doctor. The panel as a prompt to enter
     * the doctor's name, and a field to enter the name.
     *
     * @param patient the current patient
     * @return a panel to associate a new doctor with this patient
     */
    private JPanel addPatientPanel(final Doctor doctor) {
        //JPanel emptyPanel = new JPanel();
        //add(emptyPanel);
        //emptyPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel addPatientPanel = new JPanel();
        addPatientPanel.add(new JLabel("Add patient from doctor"));
        final JTextField textField = new JTextField(10);
        addPatientPanel.add(textField);
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String valueAsString = textField.getText();
                int healthNum = -1;
                if (valueAsString != null && valueAsString.length() > 0) {
                    try {
                        healthNum = Integer.parseInt(valueAsString);
                    } catch (NumberFormatException e) {
                        textField.setText("Not int: " + textField.getText());
                        textField.revalidate();
                        return;
                    }
                    AssignDoctorCommand addAssoc = new AssignDoctorCommand();
                    addAssoc.assignDoctor(doctor.getName(), healthNum);
                    if (addAssoc.wasSuccessful()) {
                        // recreate the panel as it has changed
                        removeAll();
                        build(doctor);
                        revalidate();
                        JOptionPane.showMessageDialog(null,
                                "Success! Patient with health number " + healthNum + " Was added!");
                    } else {
                        JOptionPane.showMessageDialog(DoctorPanel.this, addAssoc.getErrorMessage());
                        textField.revalidate();
                    }
                } else {
                    textField.setText("Empty field: " + textField.getText());
                    textField.revalidate();
                }
            }
        });
        return addPatientPanel;
    }

    public static final long serialVersionUID = 1;
}

// to assign the patient to a panel, we can just look up the patient, see if they exist, then call
// AssignDoctorCommand on that patient
