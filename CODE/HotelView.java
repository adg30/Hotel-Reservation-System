import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The HotelView class is responsible for creating and displaying the GUI for the Hotel Management System.
 */
public class HotelView extends JFrame {
  // GUI components
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

  /**
   * Constructor for the HotelView class. Initializes the GUI components and layout.
   */
  public HotelView() {
    // Set the title of the window
    setTitle("Hotel Management System");

    // Set the size of the window
    setSize(600, 400);

    // Ensure the application exits when the window is closed
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Set the layout of the window
    setLayout(new BorderLayout());

    // Create the input panel with a grid layout
    JPanel inputPanel = new JPanel(new GridLayout(5, 2));

    // Add hotel name label and text field to the input panel
    inputPanel.add(new JLabel("Hotel Name:"));
    hotelNameField = new JTextField();
    inputPanel.add(hotelNameField);

    // Add number of rooms label and text field to the input panel
    inputPanel.add(new JLabel("Number of Rooms:"));
    numRoomsField = new JTextField();
    inputPanel.add(numRoomsField);

    // Add room price label and text field to the input panel
    inputPanel.add(new JLabel("Room Price:"));
    roomPriceField = new JTextField();
    inputPanel.add(roomPriceField);

    // Add search hotel label and text field to the input panel
    inputPanel.add(new JLabel("Search Hotel:"));
    searchField = new JTextField();
    inputPanel.add(searchField);

    // Add the add hotel button to the input panel
    addButton = new JButton("Add Hotel");
    inputPanel.add(addButton);

    // Create the display area with a scroll pane
    displayArea = new JTextArea();
    displayArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(displayArea);

    // Create the button panel
    JPanel buttonPanel = new JPanel();
    manageButton = new JButton("Manage Hotel");
    viewButton = new JButton("View Hotel");
    bookButton = new JButton("Book Room");
    searchButton = new JButton("Search");
    exitButton = new JButton("Exit");

    // Add buttons to the button panel
    buttonPanel.add(manageButton);
    buttonPanel.add(viewButton);
    buttonPanel.add(bookButton);
    buttonPanel.add(searchButton);
    buttonPanel.add(exitButton);

    // Add the input panel, scroll pane, and button panel to the window
    add(inputPanel, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);
  }

  /**
   * Gets the hotel name entered by the user.
   * @return the hotel name as a String
   */
  public String getHotelName() {
    return hotelNameField.getText();
  }

  /**
   * Gets the number of rooms entered by the user.
   * @return the number of rooms as an int, or 0 if invalid
   */
  public int getNumRooms() {
    try {
      return Integer.parseInt(numRoomsField.getText());
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  /**
   * Gets the room price entered by the user.
   * @return the room price as a double, or 0.0 if invalid
   */
  public double getRoomPrice() {
    try {
      return Double.parseDouble(roomPriceField.getText());
    } catch (NumberFormatException e) {
      return 0.0;
    }
  }

  /**
   * Gets the search query entered by the user.
   * @return the search query as a String
   */
  public String getSearchQuery() {
    return searchField.getText();
  }

  /**
   * Sets the display text in the display area.
   * @param text the text to display
   */
  public void setDisplayText(String text) {
    displayArea.setText(text);
  }

  /**
   * Appends text to the display area.
   * @param text the text to append
   */
  public void appendDisplayText(String text) {
    displayArea.append(text);
  }

  /**
   * Adds an ActionListener to the add hotel button.
   * @param listener the ActionListener to add
   */
  public void addAddButtonListener(ActionListener listener) {
    addButton.addActionListener(listener);
  }

  /**
   * Adds an ActionListener to the manage hotel button.
   * @param listener the ActionListener to add
   */
  public void addManageButtonListener(ActionListener listener) {
    manageButton.addActionListener(listener);
  }

  /**
   * Adds an ActionListener to the view hotel button.
   * @param listener the ActionListener to add
   */
  public void addViewButtonListener(ActionListener listener) {
    viewButton.addActionListener(listener);
  }

  /**
   * Adds an ActionListener to the book room button.
   * @param listener the ActionListener to add
   */
  public void addBookButtonListener(ActionListener listener) {
    bookButton.addActionListener(listener);
  }

  /**
   * Adds an ActionListener to the search button.
   * @param listener the ActionListener to add
   */
  public void addSearchButtonListener(ActionListener listener) {
    searchButton.addActionListener(listener);
  }

  /**
   * Adds an ActionListener to the exit button.
   * @param listener the ActionListener to add
   */
  public void addExitButtonListener(ActionListener listener) {
    exitButton.addActionListener(listener);
  }
}
