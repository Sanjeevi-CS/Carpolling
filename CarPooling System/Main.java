
import java.util.List;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CarpoolingApp carpoolingApp = new CarpoolingApp();
        CarpoolingService carpoolingService = new CarpoolingService();
        int choice;
        do {
            System.out.println("\n---------- Carpooling App Menu ----------");
            System.out.println("1. Ridesharing");
            System.out.println("2. Carpooling");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1/2/3): ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    int ridechoice;
                    do {
                        System.out.println("\n---------- Ridesharing Menu ----------");
                        System.out.println("1. Find Matches for Your Trip");
                        System.out.println("2. Display All User Profiles");
                        System.out.println("3. Add a Review for a User");
                        System.out.println("4. Register");
                        System.out.println("5. Delete User");
                        System.out.println("6. Go back to the main menu");
                        System.out.print("Enter your choice (1/2/3/4/5/6): ");
                        ridechoice = scanner.nextInt();
                        scanner.nextLine();
                        switch (ridechoice) {
                            case 1:
                                System.out.print("Enter your name: ");
                                String name = scanner.nextLine();

                                System.out.print("Enter your starting location: ");
                                String startingLocation = scanner.nextLine();

                                System.out.print("Enter your destination: ");
                                String destination = scanner.nextLine();

                                System.out.print("Enter your preferred travel time: ");
                                String preferredTravelTime = scanner.nextLine();

                                User currentUser = new User(name, startingLocation, destination, preferredTravelTime);
                                carpoolingApp.findMatches(currentUser);
                                break;

                            case 2:
                                List<User> users = carpoolingApp.fetchAllUsers();
                                carpoolingApp.displayUserProfiles(users);
                                break;

                            case 3:
                                if (!carpoolingApp.users.isEmpty()) {
                                    System.out.print("Select a user to review (enter their name): ");
                                    String selectedUserName = scanner.nextLine();
                                    User selectedUser = null;

                                    for (User user : carpoolingApp.users) {
                                        if (user.getName().equalsIgnoreCase(selectedUserName)) {
                                            selectedUser = user;
                                            break;
                                        }
                                    }

                                    if (selectedUser != null) {
                                        System.out.print("Write a review for " + selectedUser.getName() + ": ");
                                        String review = scanner.nextLine();

                                        System.out.print("Rate " + selectedUser.getName() + " (1 to 5): ");
                                        int rating = scanner.nextInt();
                                        scanner.nextLine();

                                        selectedUser.addReview(review, rating);

                                        carpoolingApp.addReviewToDatabase(selectedUser, review, rating);

                                    } else {
                                        System.out.println("User not found.");
                                    }
                                } else {
                                    System.out.println("No users available for review.");
                                }
                                break;

                            case 4:
                                System.out.print("Enter your name: ");
                                String uname = scanner.nextLine();

                                System.out.print("Enter your starting location: ");
                                String ustartingLocation = scanner.nextLine();

                                System.out.print("Enter your destination: ");
                                String udestination = scanner.nextLine();

                                System.out.print("Enter your preferred travel time: ");
                                String upreferredTravelTime = scanner.nextLine();

                                User newUser = new User(uname, ustartingLocation, udestination, upreferredTravelTime);
                                carpoolingApp.registerUser(newUser);
                                break;
                            case 5:
                                System.out.print("Enter your name: ");
                                String dname = scanner.nextLine();
                                carpoolingApp.deleteUser(dname);
                            break;
                            case 6:
                                System.out.println("Exiting the Carpooling App. Goodbye!");
                                break;

                            default:
                                System.out.println("Invalid choice. Please try again.");
                        }

                    } while (ridechoice != 6);
                    break;
                case 2:

                    int carpoolchoice;
                    do {

                        System.out.println("\n---------- CarPooling Menu ----------");
                        System.out.println("1. Register User");
                        System.out.println("2. Add Car");
                        System.out.println("3. Search Cars");
                        System.out.println("4. Search with locality");
                        System.out.println("5. Exit to main menu");
                        System.out.print("Enter your choice (1/2/3/4/5): ");
                        carpoolchoice = scanner.nextInt();
                        scanner.nextLine();
                        switch (carpoolchoice) {
                            case 1:
                                System.out.println("Enter your name: ");
                                String name = scanner.nextLine();
                                System.out.println("Enter your locality: ");
                                String locality = scanner.nextLine();
                                carpoolingService.registerUser(name, locality);

                                break;
                            case 2:
                                System.out.println("Enter your username: ");
                                String userName = scanner.nextLine();
                                System.out.println("Enter driver's name: ");
                                String driverName = scanner.nextLine();
                                System.out.println("Enter source location: ");
                                String carSource = scanner.nextLine();
                                System.out.println("Enter destination location: ");
                                String carDestination = scanner.nextLine();
                                System.out.println("Enter date (YYYY-MM-DD): ");
                                String date = scanner.nextLine();
                                System.out.println("Enter time (HH:MM): ");
                                String time = scanner.nextLine();
                                System.out.println("Enter your locality: ");
                                String clocality = scanner.nextLine();
                                carpoolingService.addCar(driverName, clocality, carSource, carDestination, date, time,
                                        userName);
                                break;
                            case 3:
                                System.out.println("Enter source location: ");
                                String searchSource = scanner.nextLine();
                                System.out.println("Enter destination location: ");
                                String searchDestination = scanner.nextLine();
                                System.out.println("Enter date (YYYY-MM-DD): ");
                                String searchDate = scanner.nextLine();
                                System.out.println("Enter your locality: ");
                                String ulocality = scanner.nextLine();
                                List<String> matchedCars = carpoolingService.searchCars(searchSource, searchDestination,
                                        searchDate, ulocality);
                                if (!matchedCars.isEmpty()) {
                                    System.out.println("Matched Cars:");
                                    for (String car : matchedCars) {
                                        System.out.println(car);
                                    }
                                } else {
                                    System.out.println("No cars found matching the criteria.");
                                }
                                break;
                            case 4:
                                System.out.println("Enter your locality:");
                                String local = scanner.nextLine();
                                List<String> carsInLocality = carpoolingService.getCarsByLocality(local);
                                if (carsInLocality.isEmpty()) {
                                    System.out.println("There are no cars in your locality.");
                                } else {
                                    for (String carInfo : carsInLocality) {
                                        System.out.println(carInfo);
                                    }
                                }
                                break;
                            case 5:
                                System.out.println("Thank you for using Carpooling Service. Goodbye!");
                                break;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                        }
                    } while (carpoolchoice != 5);
                    break;
                case 3:
                    System.out.println("Exiting the Carpooling App. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
        scanner.close();
    }
}
