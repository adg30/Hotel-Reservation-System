import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HotelView extends JFrame {
  private JTextField hotelNameField;
  private JTextField numRoomsField;
  private JTextField roomPriceField;
  private JTextField searchField;
  private JTextArea displayArea;
  private JButton addButton;
  private JButton manageButton;
  private JButton viewButton;
  private JButton bookButton;
  private JButton searchButton;
  private JButton exitButton;

  public HotelView() {
    setTitle("Hotel Management System");
    setSize(600, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    JPanel inputPanel = new JPanel(new GridLayout(5, 2)); // Adjusted layout to fit search field and button
    inputPanel.add(new JLabel("Hotel Name:"));
    hotelNameField = new JTextField();
    inputPanel.add(hotelNameField);

    inputPanel.add(new JLabel("Number of Rooms:"));
    numRoomsField = new JTextField();
    inputPanel.add(numRoomsField);

    inputPanel.add(new JLabel("Room Price:"));
    roomPriceField = new JTextField();
    inputPanel.add(roomPriceField);

    inputPanel.add(new JLabel("Search Hotel:")); // Added search field
    searchField = new JTextField();
    inputPanel.add(searchField);

    addButton = new JButton("Add Hotel");
    inputPanel.add(addButton);

    displayArea = new JTextArea();
    displayArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(displayArea);

    JPanel buttonPanel = new JPanel();
    manageButton = new JButton("Manage Hotel");
    viewButton = new JButton("View Hotel");
    bookButton = new JButton("Book Room");
    searchButton = new JButton("Search"); // Added search button
    exitButton = new JButton("Exit");
    buttonPanel.add(manageButton);
    buttonPanel.add(viewButton);
    buttonPanel.add(bookButton);
    buttonPanel.add(searchButton); // Added search button to panel
    buttonPanel.add(exitButton);

    add(inputPanel, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);
  }

  public String getHotelName() {
    return hotelNameField.getText();
  }

  public int getNumRooms() {
    try {
      return Integer.parseInt(numRoomsField.getText());
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  public double getRoomPrice() {
    try {
      return Double.parseDouble(roomPriceField.getText());
    } catch (NumberFormatException e) {
      return 0.0;
    }
  }

  public String getSearchQuery() {
    return searchField.getText();
  }

  public void setDisplayText(String text) {
    displayArea.setText(text);
  }

  public void appendDisplayText(String text) {
    displayArea.append(text);
  }

  public void addAddButtonListener(ActionListener listener) {
    addButton.addActionListener(listener);
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
}
