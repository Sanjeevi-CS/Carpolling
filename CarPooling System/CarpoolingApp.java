
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Scanner;
    import java.sql.*;
    class CarpoolingApp {
        List<User> users;
        
        public void registerUser(User user) {
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "INSERT INTO user_reviews (name, starting_location, destination, preferred_travel_time) " +
                        "VALUES (?, ?, ?, ?)";
                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setString(1, user.getName());
                    statement.setString(2, user.getStartingLocation());
                    statement.setString(3, user.getDestination());
                    statement.setString(4, user.getPreferredTravelTime());
                    statement.executeUpdate();
                }
                System.out.println("Registration successful! Welcome to the Carpooling App.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to register user in the database.");
            }
        }

    public void addReviewToDatabase(User user, String review, int rating) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE user_reviews SET review = ?, rating = ? WHERE name = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, review);
                statement.setInt(2, rating);
                statement.setString(3, user.getName());
                statement.executeUpdate();
            }
            System.out.println("Review and rating added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to add review and rating to the database.");
        }
    }
    
        public CarpoolingApp() {
            this.users = new ArrayList<>();
        }

        public void addUser(User user) {
            users.add(user);
        }

        
        public void findMatches(User currentUser) {
            List<User> potentialMatches = new ArrayList<>();

            List<User> users = fetchAllUsers();

            for (User user : users) {
                if (user != currentUser && user.getDestination().equals(currentUser.getDestination()) &&
                        user.getPreferredTravelTime().equals(currentUser.getPreferredTravelTime())) {
                    potentialMatches.add(user);
                }
            }

            if (potentialMatches.isEmpty()) {
                System.out.println("No matches found for your trip.");
            } else {
                System.out.println("Potential matches for your trip:");
                for (int i = 0; i < potentialMatches.size(); i++) {
                    User user = potentialMatches.get(i);
                    System.out.println((i + 1) + ". " + user);
                    user.displayReviews();
                }
                System.out.print("Enter the number of the co-rider you want to choose (0 to reject all): ");
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                if (choice >= 1 && choice <= potentialMatches.size()) {
                    
                    User chosenUser = potentialMatches.get(choice - 1);
                    chosenUser.acceptMatch();
                    System.out.println("You have accepted the match with: " + chosenUser.getName());

                    
                    for (User user : potentialMatches) {
                        if (user != chosenUser) {
                            user.rejectMatch();
                        }
                    }
                } else {
                
                    for (User user : potentialMatches) {
                        user.rejectMatch();
                    }
                    System.out.println("You have rejected all potential matches.");
                    scanner.close();
                }
            }
        }

        public List<User> fetchAllUsers() {
            List<User> users = new ArrayList<>();
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "SELECT name, starting_location, destination, preferred_travel_time FROM user_reviews";
                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            String name = resultSet.getString("name");
                            String startingLocation = resultSet.getString("starting_location");
                            String destination = resultSet.getString("destination");
                            String preferredTravelTime = resultSet.getString("preferred_travel_time");

                            User user = new User(name, startingLocation, destination, preferredTravelTime);
                            users.add(user);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to fetch user profiles from the database.");
            }

            this.users = users;

            return users;
        }

        public void displayUserProfiles(List<User> users) {
            System.out.println("All user profiles:");
            for (User user : users) {
                System.out.println(user);
            }
        }

        
    public void deleteUser(String name) {
    
        if (name != null) {
           
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "DELETE FROM user_reviews WHERE name = ?";
                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setString(1, name);
                    int rowsAffected = statement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("User " + name + " has been deleted from the database.");
                    } else {
                        System.out.println("User " + name + " not found in the database.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to delete user from the database.");
            }
        } else {
            System.out.println("User " + name + " not found in the Carpooling App.");
        }
    }

        
    }