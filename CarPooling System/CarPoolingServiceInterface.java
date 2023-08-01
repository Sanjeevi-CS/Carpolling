import java.util.List;

public interface CarPoolingServiceInterface {
    public void registerUser(String name, String locality);
    public void addCar(String driverName, String locality, String source, String destination, String date, String time, String userName);
     public List<String> getCarsByLocality(String locality);
     public List<String> searchCars(String source, String destination, String date,String ulocality);
}
