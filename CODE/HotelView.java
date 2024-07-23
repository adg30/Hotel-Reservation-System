import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HotelView extends JFrame {
    private JTextField hotelNameField;
    private JTextField numRoomsField;
    private JButton createHotelButton;
    private JTextArea outputArea;

    public HotelView() {
        // Set up the frame
        this.setTitle("Hotel Reservation System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setLayout(new BorderLayout());

        // Panel for input
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        inputPanel.add(new JLabel("Hotel Name:"));
        hotelNameField = new JTextField();
        inputPanel.add(hotelNameField);

        inputPanel.add(new JLabel("Number of Rooms:"));
        numRoomsField = new JTextField();
        inputPanel.add(numRoomsField);

        createHotelButton = new JButton("Create Hotel");
        inputPanel.add(createHotelButton);

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        this.add(inputPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
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

    public void setOutputText(String text) {
        outputArea.setText(text);
    }

    public void addCreateHotelListener(ActionListener listenForCreateHotelButton) {
        createHotelButton.addActionListener(listenForCreateHotelButton);
    }
}
