import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SystemUI	 {
	private static final String[] LOCATIONS = {"Bidholi", "Prem Nagar", "Clock Tower", "Bus Stop", "Train Station"};
	private static final String CUSTOMER_FILE = "customers.txt";
	private static final String DRIVER_FILE = "drivers.txt";
	
	private JFrame frame;
	private JPanel currentPanel;
	private Map<String, String> customerCredentials = new HashMap<>();
	private Map<String, String> driverCredentials = new HashMap<>();

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new SystemUI	().initialize());
	}

	private void initialize() {
		frame = new JFrame("Car Pooling System");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 400);
		frame.setLocationRelativeTo(null);
		
		loadCredentials();
		showWelcomeScreen();
		
		frame.setVisible(true);
	}

	private void loadCredentials() {
		// Load customer credentials
		try (BufferedReader br = new BufferedReader(new FileReader(CUSTOMER_FILE))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length >= 2) {
					customerCredentials.put(parts[0], parts[1]);
				}
			}
		} catch (IOException e) {
			// File doesn't exist yet, will be created on first registration
		}

		// Load driver credentials
		try (BufferedReader br = new BufferedReader(new FileReader(DRIVER_FILE))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length >= 2) {
					driverCredentials.put(parts[1], parts[0]); // name -> id
				}
			}
		} catch (IOException e) {
			// File doesn't exist yet, will be created on first registration
		}
	}

	private void showWelcomeScreen() {
		JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel title = new JLabel("Welcome to Car Pooling System", JLabel.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 20));
		panel.add(title);

		JButton loginBtn = new JButton("Login");
		loginBtn.addActionListener(e -> showLoginTypeScreen());
		panel.add(loginBtn);

		JButton registerBtn = new JButton("Register");
		registerBtn.addActionListener(e -> showRegisterTypeScreen());
		panel.add(registerBtn);

		switchPanel(panel);
	}

	private void showLoginTypeScreen() {
		JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel title = new JLabel("Login As", JLabel.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 18));
		panel.add(title);

		JButton customerBtn = new JButton("Customer");
		customerBtn.addActionListener(e -> showCustomerLoginScreen());
		panel.add(customerBtn);

		JButton driverBtn = new JButton("Driver");
		driverBtn.addActionListener(e -> showDriverLoginScreen());
		panel.add(driverBtn);

		JButton backBtn = new JButton("Back");
		backBtn.addActionListener(e -> showWelcomeScreen());
		panel.add(backBtn);

		switchPanel(panel);
	}

	private void showRegisterTypeScreen() {
		JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel title = new JLabel("Register As", JLabel.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 18));
		panel.add(title);

		JButton customerBtn = new JButton("Customer");
		customerBtn.addActionListener(e -> showCustomerRegisterScreen());
		panel.add(customerBtn);

		JButton driverBtn = new JButton("Driver");
		driverBtn.addActionListener(e -> showDriverRegisterScreen());
		panel.add(driverBtn);

		JButton backBtn = new JButton("Back");
		backBtn.addActionListener(e -> showWelcomeScreen());
		panel.add(backBtn);

		switchPanel(panel);
	}

	private void showCustomerLoginScreen() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel title = new JLabel("Customer Login", JLabel.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 18));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(title);

		JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel nameLabel = new JLabel("Name:");
		namePanel.add(nameLabel);
		JTextField nameField = new JTextField(20);
		namePanel.add(nameField);
		panel.add(namePanel);

		JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel phoneLabel = new JLabel("Phone:");
		phonePanel.add(phoneLabel);
		JTextField phoneField = new JTextField(20);
		phonePanel.add(phoneField);
		panel.add(phonePanel);

		JButton loginBtn = new JButton("Login");
		loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		loginBtn.addActionListener(e -> {
			String name = nameField.getText().trim();
			String phone = phoneField.getText().trim();

			if (name.isEmpty() || phone.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Please enter both name and phone number!");
				return;
			}

			if (customerCredentials.containsKey(name) && customerCredentials.get(name).equals(phone)) {
				JOptionPane.showMessageDialog(frame, "Login successful!");
				showCustomerBookingScreen(name);
			} else {
				int option = JOptionPane.showConfirmDialog(frame, 
					"Customer not found or credentials incorrect. Register as new customer?", 
					"Register", JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					registerCustomer(name, phone);
					showCustomerBookingScreen(name);
				}
			}
		});
		panel.add(loginBtn);

		JButton backBtn = new JButton("Back");
		backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		backBtn.addActionListener(e -> showLoginTypeScreen());
		panel.add(backBtn);

		switchPanel(panel);
	}

	private void showDriverLoginScreen() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel title = new JLabel("Driver Login", JLabel.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 18));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(title);

		JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel nameLabel = new JLabel("Name:");
		namePanel.add(nameLabel);
		JTextField nameField = new JTextField(20);
		namePanel.add(nameField);
		panel.add(namePanel);

		JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel phoneLabel = new JLabel("Phone:");
		phonePanel.add(phoneLabel);
		JTextField phoneField = new JTextField(20);
		phonePanel.add(phoneField);
		panel.add(phonePanel);

		JButton loginBtn = new JButton("Login");
		loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		loginBtn.addActionListener(e -> {
			String name = nameField.getText().trim();
			String phone = phoneField.getText().trim();

			if (name.isEmpty() || phone.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Please enter both name and phone number!");
				return;
			}

			if (driverCredentials.containsKey(name)) {
				// Verify phone number if needed
				JOptionPane.showMessageDialog(frame, "Login successful!");
				showDriverDashboard(name);
			} else {
				int option = JOptionPane.showConfirmDialog(frame, 
					"Driver not found or credentials incorrect. Register as new driver?", 
					"Register", JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					showDriverRegisterScreen(name, phone);
				}
			}
		});
		panel.add(loginBtn);

		JButton backBtn = new JButton("Back");
		backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		backBtn.addActionListener(e -> showLoginTypeScreen());
		panel.add(backBtn);

		switchPanel(panel);
	}

	private void showCustomerRegisterScreen() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel title = new JLabel("Customer Registration", JLabel.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 18));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(title);

		JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel nameLabel = new JLabel("Name:");
		namePanel.add(nameLabel);
		JTextField nameField = new JTextField(20);
		namePanel.add(nameField);
		panel.add(namePanel);

		JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel phoneLabel = new JLabel("Phone:");
		phonePanel.add(phoneLabel);
		JTextField phoneField = new JTextField(20);
		phonePanel.add(phoneField);
		panel.add(phonePanel);

		JButton registerBtn = new JButton("Register");
		registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		registerBtn.addActionListener(e -> {
			String name = nameField.getText().trim();
			String phone = phoneField.getText().trim();

			if (name.isEmpty() || phone.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Please enter both name and phone number!");
				return;
			}

			registerCustomer(name, phone);
			showCustomerBookingScreen(name);
		});
		panel.add(registerBtn);

		JButton backBtn = new JButton("Back");
		backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		backBtn.addActionListener(e -> showRegisterTypeScreen());
		panel.add(backBtn);

		switchPanel(panel);
	}

	private void showDriverRegisterScreen() {
		showDriverRegisterScreen("", "");
	}

	private void showDriverRegisterScreen(String name, String phone) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel title = new JLabel("Driver Registration", JLabel.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 18));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(title);

		JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel nameLabel = new JLabel("Name:");
		namePanel.add(nameLabel);
		JTextField nameField = new JTextField(20);
		nameField.setText(name);
		namePanel.add(nameField);
		panel.add(namePanel);

		JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel phoneLabel = new JLabel("Phone:");
		phonePanel.add(phoneLabel);
		JTextField phoneField = new JTextField(20);
		phoneField.setText(phone);
		phonePanel.add(phoneField);
		panel.add(phonePanel);

		JPanel locationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel locationLabel = new JLabel("Location:");
		locationPanel.add(locationLabel);
		JComboBox<String> locationCombo = new JComboBox<>(LOCATIONS);
		locationPanel.add(locationCombo);
		panel.add(locationPanel);

		JButton registerBtn = new JButton("Register");
		registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		registerBtn.addActionListener(e -> {
			String driverName = nameField.getText().trim();
			String driverPhone = phoneField.getText().trim();
			String location = (String) locationCombo.getSelectedItem();

			if (driverName.isEmpty() || driverPhone.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Please enter both name and phone number!");
				return;
			}

			registerDriver(driverName, driverPhone, location);
			showDriverDashboard(driverName);
		});
		panel.add(registerBtn);

		JButton backBtn = new JButton("Back");
		backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		backBtn.addActionListener(e -> showRegisterTypeScreen());
		panel.add(backBtn);

		switchPanel(panel);
	}

	private void registerCustomer(String name, String phone) {
		customerCredentials.put(name, phone);
		try (FileWriter fw = new FileWriter(CUSTOMER_FILE, true)) {
			fw.write(name + "," + phone + "\n");
			JOptionPane.showMessageDialog(frame, "Registration successful! Customer ID: C" + customerCredentials.size());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame, "Error saving customer data.");
		}
	}

	private void registerDriver(String name, String phone, String location) {
		int driverId = driverCredentials.size() + 1;
		driverCredentials.put(name, "D" + driverId);
		try (FileWriter fw = new FileWriter(DRIVER_FILE, true)) {
			fw.write("D" + driverId + "," + name + "," + phone + "," + location + "\n");
			JOptionPane.showMessageDialog(frame, "Registration successful! Driver ID: D" + driverId);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame, "Error saving driver data.");
		}
	}

	private void showCustomerBookingScreen(String customerName) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel title = new JLabel("Book Your Ride", JLabel.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 18));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(title);

		JPanel pickupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel pickupLabel = new JLabel("Pickup Location:");
		pickupPanel.add(pickupLabel);
		JComboBox<String> pickupCombo = new JComboBox<>(LOCATIONS);
		pickupPanel.add(pickupCombo);
		panel.add(pickupPanel);

		JPanel dropPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel dropLabel = new JLabel("Drop Location:");
		dropPanel.add(dropLabel);
		JComboBox<String> dropCombo = new JComboBox<>(LOCATIONS);
		dropPanel.add(dropCombo);
		panel.add(dropPanel);

		JButton bookBtn = new JButton("Book Ride");
		bookBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		bookBtn.addActionListener(e -> {
			String pickup = (String) pickupCombo.getSelectedItem();
			String drop = (String) dropCombo.getSelectedItem();

			if (pickup.equals(drop)) {
				JOptionPane.showMessageDialog(frame, "Pickup and drop locations cannot be the same!");
				return;
			}

			String[] driverInfo = assignDriver(pickup);
			if (driverInfo == null) {
				JOptionPane.showMessageDialog(frame, "No available drivers at your location!");
				return;
			}

			int distance = calculateDistance(pickup, drop);
			int fare = booking.FareCalculator.calculateFare(distance);
			int eta = booking.FareCalculator.calculateETA(distance);

			String message = "Ride booked successfully!\n" +
							"Driver: " + driverInfo[1] + " (ID: " + driverInfo[0] + ")\n" +
							"Pickup: " + pickup + "\n" +
							"Drop: " + drop + "\n" +
							"Distance: " + distance + " km\n" +
							"Fare: ₹" + fare + "\n" +
							"ETA: " + eta + " minutes";

			JOptionPane.showMessageDialog(frame, message);
		});
		panel.add(bookBtn);

		JButton logoutBtn = new JButton("Logout");
		logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		logoutBtn.addActionListener(e -> showWelcomeScreen());
		panel.add(logoutBtn);

		switchPanel(panel);
	}

	private void showDriverDashboard(String driverName) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel title = new JLabel("Driver Dashboard - " + driverName, JLabel.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 18));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(title);

		// Display current location
		String currentLocation = getDriverLocation(driverName);
		JLabel locationLabel = new JLabel("Current Location: " + currentLocation);
		locationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(locationLabel);

		// Update location
		JPanel updatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel updateLabel = new JLabel("Update Location:");
		updatePanel.add(updateLabel);
		JComboBox<String> locationCombo = new JComboBox<>(LOCATIONS);
		locationCombo.setSelectedItem(currentLocation);
		updatePanel.add(locationCombo);
		panel.add(updatePanel);

		JButton updateBtn = new JButton("Update Location");
		updateBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		updateBtn.addActionListener(e -> {
			String newLocation = (String) locationCombo.getSelectedItem();
			updateDriverLocation(driverName, newLocation);
			locationLabel.setText("Current Location: " + newLocation);
			JOptionPane.showMessageDialog(frame, "Location updated successfully!");
		});
		panel.add(updateBtn);

		JButton logoutBtn = new JButton("Logout");
		logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		logoutBtn.addActionListener(e -> showWelcomeScreen());
		panel.add(logoutBtn);

		switchPanel(panel);
	}

	private String[] assignDriver(String pickupLocation) {
		try (BufferedReader br = new BufferedReader(new FileReader(DRIVER_FILE))) {
			String line;
			String[] closestDriver = null;
			int minDistance = Integer.MAX_VALUE;

			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length >= 4) {
					String driverLocation = parts[3];
					int distance = calculateDistance(driverLocation, pickupLocation);
					if (distance < minDistance) {
						minDistance = distance;
						closestDriver = parts;
					}
				}
			}

			return closestDriver;
		} catch (IOException e) {
			return null;
		}
	}

	private int calculateDistance(String loc1, String loc2) {
		int index1 = -1, index2 = -1;
		for (int i = 0; i < LOCATIONS.length; i++) {
			if (LOCATIONS[i].equals(loc1)) index1 = i;
			if (LOCATIONS[i].equals(loc2)) index2 = i;
		}

		if (index1 == -1 || index2 == -1) return 0;

		int[] distances = {9, 11, 7, 13}; // Distances between consecutive locations
		int start = Math.min(index1, index2);
		int end = Math.max(index1, index2);
		int totalDistance = 0;

		for (int i = start; i < end; i++) {
			totalDistance += distances[i];
		}

		return totalDistance;
	}

	private String getDriverLocation(String driverName) {
		try (BufferedReader br = new BufferedReader(new FileReader(DRIVER_FILE))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length >= 4 && parts[1].equals(driverName)) {
					return parts[3];
				}
			}
		} catch (IOException e) {
			return "Unknown";
		}
		return "Unknown";
	}

	private void updateDriverLocation(String driverName, String newLocation) {
		try {
			File inputFile = new File(DRIVER_FILE);
			File tempFile = new File("temp_drivers.txt");

			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length >= 4 && parts[1].equals(driverName)) {
					writer.write(parts[0] + "," + parts[1] + "," + parts[2] + "," + newLocation + "\n");
				} else {
					writer.write(line + "\n");
				}
			}

			writer.close();
			reader.close();

			if (inputFile.delete()) {
				tempFile.renameTo(inputFile);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame, "Error updating driver location.");
		}
	}

	private void switchPanel(JPanel panel) {
		if (currentPanel != null) {
			frame.remove(currentPanel);
		}
		currentPanel = panel;
		frame.add(panel);
		frame.revalidate();
		frame.repaint();
	}
}