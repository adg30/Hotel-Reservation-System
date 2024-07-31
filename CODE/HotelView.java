import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
/**
 * HotelView class represents the graphical user interface for the hotel management system.
 * More specifically, it is the "View" in its MVC architecture
 */
public class HotelView extends JFrame {
    private JCheckBox customPriceCheckbox;
    private JRadioButton standardHotel, dividedHotel;
    private JTextField hotelNameField, numRoomsField, roomPriceField;
    private JButton createButton, manageButton, viewButton, bookButton, exitButton, orderFoodButton;
    private int selectedHotelType;

    /**
     * Constructor for HotelView.
     */
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

        //custom price checkbox actionlistener
        customPriceCheckbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               handleCustomPriceCheckboxAction();
            }
        });


        inputPanel.add(new JLabel("Base Hotel Type:"));
        ButtonGroup hotelTypeButtonGroup = new ButtonGroup();
        standardHotel = new JRadioButton("Standard");
        dividedHotel = new JRadioButton("Divided");
        hotelTypeButtonGroup.add(standardHotel);
        hotelTypeButtonGroup.add(dividedHotel);
        inputPanel.add(standardHotel);
        inputPanel.add(dividedHotel);

        //radiobutton listeners
        standardHotel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedHotelType = 1;//turn it into 1 for standard 2 for divided
            }
        });

        dividedHotel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedHotelType = 2;
            }
        });

        


        // Create a button panel with GridLayout
        JPanel buttonPanel = new JPanel(new GridLayout(4, 2, 10, 10)); // 4 rows, 2 columns, 10px padding

        createButton = new JButton("Create Hotel");
        manageButton = new JButton("Manage Hotel");
        viewButton = new JButton("View Hotels");
        bookButton = new JButton("Simulate Booking");
        orderFoodButton = new JButton("Order Food");
        exitButton = new JButton("Exit");

        // Add buttons to the button panel
        buttonPanel.add(createButton);
        buttonPanel.add(manageButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(bookButton);
        buttonPanel.add(orderFoodButton);
        buttonPanel.add(exitButton);

        // Add input and button panels to the main panel
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);



        // Add the main panel to the frame 
        add(mainPanel, BorderLayout.NORTH);
  
    }
    /**
     * Handles the action event when the custom price checkbox is selected.
     */
    private void handleCustomPriceCheckboxAction() {
        if (customPriceCheckbox.isSelected()) {
            String customPrice = JOptionPane.showInputDialog("Enter custom price:");
            if (customPrice != null) {
                try {
                    int price = Integer.parseInt(customPrice);
                    if (price >= 100) {
                        customPriceCheckbox.setText(customPrice);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid price! Setting default price to 100.", "Error", JOptionPane.ERROR_MESSAGE);
                        customPriceCheckbox.setText("100");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input! Setting default price to 100.", "Error", JOptionPane.ERROR_MESSAGE);
                    customPriceCheckbox.setText("100");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid input! Setting default price to 100.", "Error", JOptionPane.ERROR_MESSAGE);
                customPriceCheckbox.setText("100");
            }
        }
    }
    
    /**
     * Gets the hotel name entered by the user.
     *
     * @return the hotel name.
     */
    public String getHotelName() {
        return hotelNameField.getText();
    }

    /**
     * Gets the number of rooms entered by the user.
     *
     * @return the number of rooms.
     */
    public int getNumRooms() {
        try {
            return Integer.parseInt(numRoomsField.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Gets the room price entered by the user.
     *
     * @return the room price.
     */
    public double getRoomPrice() {
        try {
            return Double.parseDouble(roomPriceField.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Gets the selected hotel type.
     *
     * @return the selected hotel type (1 for standard, 2 for divided).
     */
    public int getSelectedHotelType() {
        return selectedHotelType;
    }

    /**
     * Checks if the custom price checkbox is selected.
     *
     * @return true if the custom price checkbox is selected, false otherwise.
     */
    public boolean isCustomPriceSelected() {
        return customPriceCheckbox.isSelected();
    }

    /**
     * Gets the custom price entered by the user.
     *
     * @return the custom price.
     */
    public double getCustomPrice() {
        try {
            return Double.parseDouble(customPriceCheckbox.getText());
        } catch (NumberFormatException e) {
            return 1299; // Return default price if parsing fails
        }
    }

    /**
     * Sets the display text in a non-editable text area.
     *
     * @param text the text to display.
     */
    public void setDisplayText(String text) {
        JTextArea displayArea = new JTextArea(text);
        displayArea.setEditable(false);
        displayArea.setLineWrap(true);
        displayArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JOptionPane.showMessageDialog(this, scrollPane, "Display", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Adds an action listener to the create button.
     *
     * @param listener the action listener.
     */
    public void addCreateButtonListener(ActionListener listener) {
        createButton.addActionListener(listener);
    }

    /**
     * Adds an action listener to the manage button.
     *
     * @param listener the action listener.
     */
    public void addManageButtonListener(ActionListener listener) {
        manageButton.addActionListener(listener);
    }

    /**
     * Adds an action listener to the view button.
     *
     * @param listener the action listener.
     */
    public void addViewButtonListener(ActionListener listener) {
        viewButton.addActionListener(listener);
    }

    /**
     * Adds an action listener to the book button.
     *
     * @param listener the action listener.
     */
    public void addBookButtonListener(ActionListener listener) {
        bookButton.addActionListener(listener);
    }

    /**
     * Adds an action listener to the order food button.
     *
     * @param listener the action listener.
     */
    public void addOrderFoodButtonListener(ActionListener listener) {
        orderFoodButton.addActionListener(listener);
    }

    /**
     * Adds an action listener to the exit button.
     *
     * @param listener the action listener.
     */
    public void addExitButtonListener(ActionListener listener) {
        exitButton.addActionListener(listener);
    }

}