import java.util.ArrayList;
import java.util.List;
import java.sql.*;

class User extends Person {
    // private String name;
    private String startingLocation;
    private String destination;
    private String preferredTravelTime;
    private List<String> reviews;
    private boolean acceptedMatch;
    private String driverName;
    private Time carTime;
    private Date carDate;
    public User(String name, String startingLocation, String destination, String preferredTravelTime) {
        super(name);
        this.startingLocation = startingLocation;
        this.destination = destination;
        this.preferredTravelTime = preferredTravelTime;
        
        this.reviews = new ArrayList<>();
        this.acceptedMatch = false;
    }

   

    public String getStartingLocation() {
        return startingLocation;
    }

    public String getDestination() {
        return destination;
    }

    public String getPreferredTravelTime() {
        return preferredTravelTime;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Time getCarTime() {
        return carTime;
    }

    public void setCarTime(Time carTime) {
        this.carTime = carTime;
    }

    public Date getCarDate() {
        return carDate;
    }

    public void setCarDate(Date carDate) {
        this.carDate = carDate;
    }

    public boolean hasAcceptedMatch() {
        return acceptedMatch;
    }

    public void acceptMatch() {
        acceptedMatch = true;
    }

    public void displayReviews() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT review, rating FROM user_reviews WHERE name = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, name);
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<String> reviews = new ArrayList<>();
                    List<Integer> ratings = new ArrayList<>();

                    while (resultSet.next()) {
                        String review = resultSet.getString("review");
                        int rating = resultSet.getInt("rating");
                        reviews.add(review);
                        ratings.add(rating);
                    }

                    if (reviews.isEmpty()) {
                        System.out.println("No reviews available for " + name + ".");
                    } else {
                        System.out.println("Reviews for " + name + ":");
                        for (int i = 0; i < reviews.size(); i++) {
                            String review = reviews.get(i);
                            int rating = ratings.get(i);
                            System.out.println("- Review: " + review + ", Rating: " + rating);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to fetch reviews from the database.");
        }
    }

    private int rating;

    public void addReview(String review, int rating) {
        reviews.add(review);
        this.rating = rating;
    }

    public void rejectMatch() {
        acceptedMatch = false;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Starting Location: " + startingLocation +
                ", Destination: " + destination + ", Preferred Travel Time: " + preferredTravelTime;
    }
}