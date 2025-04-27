import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import drivers.Driver;
import booking.FareCalculator;
import booking.Booking;


public class CarPoolingSystemUI {
    // Constants
    private static final String[] LOCATIONS = {"Bidholi", "Prem Nagar", "Clock Tower", "Bus Stop", "Train Station"};
    private static final int COST_PER_KM = 12;

    // Main components
    private JFrame frame;
    private JPanel currentPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CarPoolingSystemUI().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("Simple Car Pooling");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        showMainMenu();

        frame.setVisible(true);
    }

    private void showMainMenu() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Car Pooling System", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(title);

        JButton bookBtn = new JButton("Book a Ride");
        bookBtn.addActionListener(e -> showBookingScreen());
        panel.add(bookBtn);

        JButton driverBtn = new JButton("Driver Registration");
        driverBtn.addActionListener(e -> showDriverScreen());
        panel.add(driverBtn);

        switchPanel(panel);
    }

    private void showBookingScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Book Your Ride", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);

        JPanel locationPanel = new JPanel();
        locationPanel.setLayout(new GridLayout(2, 2, 10, 10));

        JLabel pickupLabel = new JLabel("Pickup Location:");
        locationPanel.add(pickupLabel);
        JComboBox<String> pickupCombo = new JComboBox<>(LOCATIONS);
        locationPanel.add(pickupCombo);

        JLabel dropLabel = new JLabel("Drop Location:");
        locationPanel.add(dropLabel);
        JComboBox<String> dropCombo = new JComboBox<>(LOCATIONS);
        locationPanel.add(dropCombo);

        panel.add(locationPanel);

        JButton bookBtn = new JButton("Book Now");
        bookBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
	bookBtn.addActionListener(e -> {
		String pickup = (String) pickupCombo.getSelectedItem();
		String drop = (String) dropCombo.getSelectedItem();
		if (pickup.equals(drop)) {
    			JOptionPane.showMessageDialog(frame, "C'mon you can walk that!");
    			return;
		}

		int distance = Math.abs(pickupCombo.getSelectedIndex() - dropCombo.getSelectedIndex()) * 10;  // 10 km between each index

		int fare = FareCalculator.calculateFare(distance);

		List<String> assignedDriver = Driver.assignDriver(pickup);
		if (assignedDriver == null || assignedDriver.isEmpty()) {
    			JOptionPane.showMessageDialog(frame, "No driver available at pickup location currently!");
    			return;
		}


		String driverId = assignedDriver.get(0);		
		String driverName = assignedDriver.get(1);

		Booking.bookRide(pickup, drop, distance, fare, 15, Integer.parseInt(driverId));
		Driver.updateDriverLocation(driverId, drop);

		JOptionPane.showMessageDialog(frame,"Ride Booked!\nDistance: " + distance + " km\nFare: ₹" + fare +"\nAssigned Driver: " + driverName + " (" + driverId + ")");
	});


        panel.add(bookBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.addActionListener(e -> showMainMenu());
        panel.add(backBtn);

        switchPanel(panel);
    }

    private void showDriverScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Driver Registration", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel nameLabel = new JLabel("Your Name:");
        namePanel.add(nameLabel);
        JTextField nameField = new JTextField(20);
        namePanel.add(nameField);
        panel.add(namePanel);

        JPanel locationPanel = new JPanel();
        locationPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel locationLabel = new JLabel("Current Location:");
        locationPanel.add(locationLabel);
        JComboBox<String> locationCombo = new JComboBox<>(LOCATIONS);
        locationPanel.add(locationCombo);
        panel.add(locationPanel);

        JButton registerBtn = new JButton("Register");
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
	registerBtn.addActionListener(e -> {
    		String name = nameField.getText().trim();
    		String location = (String) locationCombo.getSelectedItem();

    		if (name.isEmpty()) {
        		JOptionPane.showMessageDialog(frame, "Please enter your name!");
        		return;
    		}

    		Driver.registerDriver(name, location);
   		JOptionPane.showMessageDialog(frame, "Registration Successful!\nWelcome " + name);
    		nameField.setText("");
	});



        panel.add(registerBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.addActionListener(e -> showMainMenu());
        panel.add(backBtn);

        switchPanel(panel);
    }

    private void switchPanel(JPanel newPanel) {
        if (currentPanel != null) {
            frame.remove(currentPanel);
        }
        currentPanel = newPanel;
        frame.add(currentPanel);
        frame.revalidate();
        frame.repaint();
    }
}
