import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CarpoolingService implements CarPoolingServiceInterface{
   
    public void registerUser(String name, String locality) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO knownusers (name, locality) VALUES (?, ? )")) {
    
            statement.setString(1, name);
            statement.setString(2, locality);
            
            statement.executeUpdate();
    
            System.out.println("User " + name + " registered successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCar(String driverName, String locality, String source, String destination, String date, String time, String userName) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT user_id FROM knownusers WHERE name = ?")) {
    
            statement.setString(1, userName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt("user_id");
                     System.out.println(userId);
                    
                        try (PreparedStatement carStatement = connection.prepareStatement("INSERT INTO cars (driver_name, locality, source, destination, date, time, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
                            carStatement.setString(1, driverName);
                            carStatement.setString(2, locality);
                            carStatement.setString(3, source);
                            carStatement.setString(4, destination);
                            carStatement.setDate(5, Date.valueOf(date));
                            carStatement.setTime(6, Time.valueOf(time));
                            carStatement.setInt(7, userId);
                            carStatement.executeUpdate();
    
                            System.out.println("Car added successfully by " + driverName + ".");
                        }
                     
                } else {
                    System.out.println("Error: User not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<String> getCarsByLocality(String locality) {
        List<String> carsByLocality = new ArrayList<>();
    
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT knownusers.name AS username, cars.driver_name, cars.source, cars.destination, cars.date, cars.time " +
                     "FROM cars " +
                     "INNER JOIN knownusers ON cars.user_id = knownusers.user_id " +
                     "WHERE knownusers.locality = ?")) {
    
            statement.setString(1, locality);
    
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String driverName = resultSet.getString("driver_name");
                    String source = resultSet.getString("source");
                    String destination = resultSet.getString("destination");
                    String carDate = resultSet.getDate("date").toString();
                    String carTime = resultSet.getTime("time").toString();
                    carsByLocality.add("Username: " + username + ", Driver: " + driverName + ", Source: " + source + ", Destination: " + destination + ", Date: " + carDate + ", Time: " + carTime);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return carsByLocality;
    }

    public List<String> searchCars(String source, String destination, String date,String ulocality) {
        List<String> matchedCars = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT driver_name, date, time FROM cars WHERE source = ? AND destination = ? AND date = ? AND locality=?" )) {

            statement.setString(1, source);
            statement.setString(2, destination);
            statement.setDate(3, Date.valueOf(date));
            statement.setString(4, ulocality);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String driverName = resultSet.getString("driver_name");
                    String carDate = resultSet.getDate("date").toString();
                    String carTime = resultSet.getTime("time").toString();
                    matchedCars.add("Driver: " + driverName + ", Date: " + carDate+", Time: "+carTime);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matchedCars;
    }

}
