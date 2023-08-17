import java.util.*;
// class "User" start
class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
}

// class "User" end
// class "ParkingSpot" start

class ParkingSpot {
    private int spotNumber;
    private boolean isAvailable;

    public ParkingSpot(int spotNumber) {
        this.spotNumber = spotNumber;
        this.isAvailable = true;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void book() {
        isAvailable = false;
    }

    public void release() {
        isAvailable = true;
    }
}

// class "ParkingSpot" end
// class "ParkingSystem" start

class ParkingSystem {
    private List<ParkingSpot> parkingSpots;
    private Map<String, List<ParkingSpot>> bookings;

    public ParkingSystem(int totalSpots) {
        parkingSpots = new ArrayList<>();
        bookings = new HashMap<>();
        for (int i = 1; i <= totalSpots; i++) {
            parkingSpots.add(new ParkingSpot(i));
        }
    }

    public void displayBookings(String username) {
        List<ParkingSpot> userBookings = bookings.getOrDefault(username, new ArrayList<>());
        if (userBookings.isEmpty()) {
            System.out.println("You have no booked slots.");
        } else {
            System.out.println("Your Booked Slots:");
            for (ParkingSpot spot : userBookings) {
                System.out.println("Spot Number: " + spot.getSpotNumber());
            }
        }
    }

    public void showAvailableParkingSpots() {
        System.out.println("Available Parking Spots:");
        for (ParkingSpot spot : parkingSpots) {
            if (spot.isAvailable()) {
                System.out.println("Spot Number: " + spot.getSpotNumber());
            }
        }
    }

    public boolean bookParkingSpot(String username, int spotNumber) {
        ParkingSpot spotToBook = null;
        for (ParkingSpot spot : parkingSpots) {
            if (spot.getSpotNumber() == spotNumber) {
                spotToBook = spot;
                break;
            }
        }

        if (spotToBook == null) {
            System.out.println("Invalid spot number. Please try again.");
            return false;
        }

        if (!spotToBook.isAvailable()) {
            System.out.println("Spot " + spotNumber + " is already booked. Please choose another spot.");
            return false;
        }

        spotToBook.book();
        bookings.computeIfAbsent(username, k -> new ArrayList<>()).add(spotToBook);

        System.out.println("Booking successful! You have booked spot number " + spotNumber + ".");
        return true;
    }

    public boolean releaseParkingSpot(String username, int spotNumber) {
        List<ParkingSpot> userBookings = bookings.get(username);
        if (userBookings == null || userBookings.isEmpty()) {
            System.out.println("You have no booked slots to release.");
            return false;
        }

        ParkingSpot spotToRelease = null;
        for (ParkingSpot spot : userBookings) {
            if (spot.getSpotNumber() == spotNumber) {
                spotToRelease = spot;
                break;
            }
        }

        if (spotToRelease == null) {
            System.out.println("You have not booked the specified spot.");
            return false;
        }

        spotToRelease.release();
        userBookings.remove(spotToRelease);

        System.out.println("Slot released successfully.");
        return true;
    }

    public boolean login(String username, String password) {
        // In a real implementation, you'd compare against stored users' data.
        // For simplicity, we assume any username and password is valid.
        return true;
    }
}

// class "ParkingSystem" end
// class "Main" start and main method is in this class

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ParkingSystem parkingSystem = new ParkingSystem(10); // 10 parking spots available

        System.out.println("Welcome to the Online Parking System!");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (parkingSystem.login(username, password)) {
            System.out.println("Login successful!");

            boolean running = true;
            while (running) {
                System.out.println("\n------ Dashboard ------");
                System.out.println("1. My Slot");
                System.out.println("2. Book New Slot");
                System.out.println("3. Remove Slot");
                System.out.println("4. Logout");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        parkingSystem.displayBookings(username);
                        break;
                    case 2:
                        parkingSystem.showAvailableParkingSpots();
                        System.out.print("Enter the spot number to book: ");
                        int spotNumber = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character
                        boolean bookingStatus = parkingSystem.bookParkingSpot(username, spotNumber);
                        if (bookingStatus) {
                            System.out.println("Booking successful!");
                        } else {
                            System.out.println("Failed to book the spot. Please try again.");
                        }
                        break;
                    case 3:
                        parkingSystem.displayBookings(username);
                        System.out.print("Enter the spot number to release: ");
                        int spotToRelease = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character
                        boolean releaseStatus = parkingSystem.releaseParkingSpot(username, spotToRelease);
                        if (releaseStatus) {
                            System.out.println("Slot released successfully!");
                        } else {
                            System.out.println("Failed to release the slot. Please try again.");
                        }
                        break;
                    case 4:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }

        scanner.close();
    }
}
