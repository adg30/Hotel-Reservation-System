import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class HotelView extends JFrame {
    private JCheckBox customPriceCheckbox;
    private JRadioButton standardHotel, dividedHotel;
    private JTextField hotelNameField, numRoomsField, roomPriceField, hotelTypeField;
    private JButton createButton, manageButton, viewButton, bookButton, exitButton;

    public HotelView() {
        setTitle("Hotel Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window

        // Create a main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10)); // 10px padding
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 10px padding around the panel

        // Create an input panel with GridLayout
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10)); // 10px padding between components

        // Add labels and text fields to the input panel
        inputPanel.add(new JLabel("Hotel Name:"));
        hotelNameField = new JTextField();
        inputPanel.add(hotelNameField);

        inputPanel.add(new JLabel("Number of Rooms:"));
        numRoomsField = new JTextField();
        inputPanel.add(numRoomsField);

        inputPanel.add(new JLabel("Custom Price:"));
        customPriceCheckbox = new JCheckBox();
        inputPanel.add(customPriceCheckbox);

        customPriceCheckbox.addActionListener(new ActionListener() {//checkbox popup experimenting
            public void actionPerformed(ActionEvent e) {
                if (customPriceCheckbox.isSelected()) {
                    String customPrice = JOptionPane.showInputDialog("Enter custom price:");
                    if (customPrice != null) {
                        customPriceCheckbox.setText(customPrice);
                    }
                }
            }
        });

        //experimenting with radiobuttons
        inputPanel.add(new JLabel("Hotel Type:"));
        ButtonGroup hotelTypeButtonGroup = new ButtonGroup();
        standardHotel = new JRadioButton("Standard");
        dividedHotel = new JRadioButton("Divided");
        hotelTypeButtonGroup.add(standardHotel);
        hotelTypeButtonGroup.add(dividedHotel);
        inputPanel.add(standardHotel);
        inputPanel.add(dividedHotel);


        // Create a button panel with GridLayout
        JPanel buttonPanel = new JPanel(new GridLayout(4, 2, 10, 10)); // 4 rows, 2 columns, 10px padding

        createButton = new JButton("Create Hotel");
        manageButton = new JButton("Manage Hotel");
        viewButton = new JButton("View Hotels");
        bookButton = new JButton("Book Room");
        exitButton = new JButton("Exit");

        // Add buttons to the button panel
        buttonPanel.add(createButton);
        buttonPanel.add(manageButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(bookButton);
        buttonPanel.add(exitButton);

        // Add input and button panels to the main panel
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);



        // Add the main panel to the frame 
        add(mainPanel, BorderLayout.NORTH);
  
    }

    public String getHotelName() {
        return hotelNameField.getText();
    }

    public int getNumRooms() {
        try {
            return Integer.parseInt(numRoomsField.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public double getRoomPrice() {
        try {
            return Double.parseDouble(roomPriceField.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public int getHotelType() {
        try {
            return Integer.parseInt(hotelTypeField.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void setDisplayText(String text) {
        JTextArea displayArea = new JTextArea(text);
        displayArea.setEditable(false);
        displayArea.setLineWrap(true);
        displayArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JOptionPane.showMessageDialog(this, scrollPane, "Display", JOptionPane.INFORMATION_MESSAGE);
    }

    public void addCreateButtonListener(ActionListener listener) {
        createButton.addActionListener(listener);
    }

    public void addManageButtonListener(ActionListener listener) {
        manageButton.addActionListener(listener);
    }

    public void addViewButtonListener(ActionListener listener) {
        viewButton.addActionListener(listener);
    }

    public void addBookButtonListener(ActionListener listener) {
        bookButton.addActionListener(listener);
    }

    public void addExitButtonListener(ActionListener listener) {
        exitButton.addActionListener(listener);
    }


}