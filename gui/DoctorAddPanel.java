package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JPanel;
import javax.swing.*;

import commands.AddDoctorCommand;

/**
 * The panel to obtain data for the creation of a patient, and to cause the patient to be created.
 */
public class DoctorAddPanel extends JPanel {
    /* Declare the components of the panel needed by inner classes. */

    /**
     * A text area to be used to display an error if one should occur.
     */
    JTextArea error = null;

    /**
     * A panel for the entry of the name of a new doctor.
     */
    ValueEntryPanel dNamePanel;
    /**
     * Buttons to see if Doctor is Surgeon or not.
     */
    JRadioButton docButton;
    JRadioButton surgButton;
    
    /**
     * Create the panel to obtain data for the creation of a patient, and to cause the patient to be
     * created.
     */
    public DoctorAddPanel() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(Box.createVerticalGlue());

        // add a label with a prompt to enter the patient data
        JLabel prompt = new JLabel("Enter Doctor Information");
        prompt.setMaximumSize(prompt.getPreferredSize());
        add(prompt);
        prompt.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalGlue());

        // add a panel with the field for the entry of the patient's name
        dNamePanel = new ValueEntryPanel("name");
        dNamePanel.setMaximumSize(dNamePanel.getPreferredSize());
        add(dNamePanel);
        dNamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalGlue());
        
        //Make 2 radio buttons and group them together.
        docButton = new JRadioButton("Doctor");
        docButton.setSelected(true);
         surgButton = new JRadioButton("Surgeon");
        ButtonGroup isSurgeon = new ButtonGroup();
        isSurgeon.add(docButton);
        isSurgeon.add(surgButton);
        
        //then, add them the our panel that will check to see
        //if a doctor is a surgeon or not.
        JPanel isSurgPanel = new JPanel();
        isSurgPanel.add(docButton);
        isSurgPanel.add(surgButton);  
        isSurgPanel.setBorder(new TitledBorder(new EtchedBorder(),"Doctor Type") );
        isSurgPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(isSurgPanel);
        add(Box.createVerticalGlue());
        
        // add a button to submit the information and create the patient
        JButton submitButton = new JButton("Submit");
        submitButton.setMaximumSize(submitButton.getPreferredSize());
        add(submitButton);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(new SubmitListener());
        add(Box.createVerticalGlue());
    }

    /**
     * The class listening for the press of the submit button. It accesses the name and health
     * number entered, and uses them to add a new Patient to the system.
     */
    private class SubmitListener implements ActionListener {
        /**
         * When the submit button is pressed, access the name, and check whether the Doctor is a surgeon or not.
         */
        public void actionPerformed(ActionEvent event) {
            if (error != null) {
                // remove error from the previous submission
                remove(error);
                error = null;
            }
            boolean isSurgeon;
            String name = dNamePanel.getValueAsString();
            if (docButton.isSelected()){
                isSurgeon = false;
            } else {
                isSurgeon = true;
            }
            AddDoctorCommand addDoctor = new AddDoctorCommand();
            addDoctor.addDoctor(name,isSurgeon);
            if (addDoctor.wasSuccessful()){
                getTopLevelAncestor().setVisible(false);
            } else {
                error = new JTextArea(SplitString.at(addDoctor.getErrorMessage(), 40));
                error.setMaximumSize(error.getPreferredSize());
                add(error);
                error.setAlignmentX(Component.CENTER_ALIGNMENT);
                add(Box.createVerticalGlue());
                revalidate(); // redraw the window as it has changed
            }
        }
    }

    public static final long serialVersionUID = 1;
}
