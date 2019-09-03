package gui;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * The panel for the access of a specific patient. It has a prompt label, and a text field for the
 * entry of the patient's health number. When the Enter key is pressed, the health number entered is
 * used to create a new window with the patient's data and operations on the patient.
 */
public class DoctorAccessPanel extends JPanel {
    /**
     * The text field for the entry of the patient's health number.
     */
    JTextField textField;

    /**
     * Create the panel with the prompt label and text field. If data is entered into the text field
     * that is not a valid int value, a brief error message is entered at the front of the text
     * field. Otherwise, a new window is created with the patient's data and operations on the
     * patient.
     */
    public DoctorAccessPanel() {
        JLabel docPromptLabel = new JLabel("Access doctor");
        add(docPromptLabel);
        textField = new JTextField(10);
        add(textField);
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String valueAsString = textField.getText();
                if (valueAsString != null && valueAsString.length() > 0) {
                   // try {
                         String name = valueAsString;
                   // } catch (NumberFormatException e) {
                   //     textField.setText("Not int: " + textField.getText());
                   //     textField.revalidate();
                   //     return;
                   // }
                    DoctorFrame frame = null;
                    try {
                        frame = new DoctorFrame(name);
                    } catch (RuntimeException e) {
                        textField.setText("Invalid id: " + textField.getText());
                        textField.revalidate();
                        return;
                    }
                    frame.setLocation(300, 300);
                    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    frame.setVisible(true);
                    textField.setText("");
                    textField.revalidate();
                } else {
                    textField.setText("Empty field: " + textField.getText());
                    textField.revalidate();
                }
            }
        });
    }

    public static final long serialVersionUID = 1;
}
