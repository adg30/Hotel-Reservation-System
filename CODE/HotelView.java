import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class HotelView extends JFrame {
    private JTextField hotelNameField, numRoomsField, roomPriceField, hotelTypeField, searchField;
    private JButton createButton, manageButton, viewButton, bookButton, searchButton, exitButton, modifyDatePriceButton;
    private JTextArea displayArea;

    public HotelView() {
        setTitle("Hotel Management System");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));

        inputPanel.add(new JLabel("Hotel Name:"));
        hotelNameField = new JTextField();
        inputPanel.add(hotelNameField);

        inputPanel.add(new JLabel("Number of Rooms:"));
        numRoomsField = new JTextField();
        inputPanel.add(numRoomsField);

        inputPanel.add(new JLabel("Room Price:"));
        roomPriceField = new JTextField();
        inputPanel.add(roomPriceField);

        inputPanel.add(new JLabel("Hotel Type (0-Standard, 1-Resort, 2-Suite):"));
        hotelTypeField = new JTextField();
        inputPanel.add(hotelTypeField);

        inputPanel.add(new JLabel("Search Hotel:"));
        searchField = new JTextField();
        inputPanel.add(searchField);

        createButton = new JButton("Create Hotel");
        manageButton = new JButton("Manage Hotel");
        viewButton = new JButton("View Hotels");
        bookButton = new JButton("Book Room");
        searchButton = new JButton("Search Hotel");
        exitButton = new JButton("Exit");
        modifyDatePriceButton = new JButton("Modify Date Price");

        inputPanel.add(createButton);
        inputPanel.add(manageButton);
        inputPanel.add(viewButton);
        inputPanel.add(bookButton);
        inputPanel.add(searchButton);
        inputPanel.add(exitButton);
        inputPanel.add(modifyDatePriceButton);

        displayArea = new JTextArea();
        displayArea.setEditable(false);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
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
    
    public String getSearchQuery() {
      return searchField.getText();
    }

    public void setDisplayText(String text) {
        displayArea.setText(text);
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

    public void addSearchButtonListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public void addExitButtonListener(ActionListener listener) {
        exitButton.addActionListener(listener);
    }

    public void addModifyDatePriceButtonListener(ActionListener listener) {
        modifyDatePriceButton.addActionListener(listener);
    }
}
