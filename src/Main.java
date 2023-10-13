import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws SQLException {
        ExecutorService executor = Executors.newFixedThreadPool(30);
        long start=System.currentTimeMillis();
        String jdbcUrl = "jdbc:mysql://localhost:3306/airlinebooking?useSSL=false";
        String username = "root";
        String password = "sushmabadam";
        String query = "SELECT * FROM users";
        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<User> userList = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            System.out.println("ID: " + id + ", Name: " + name);
            userList.add(new User(id,name));
        }
        for(User user: userList){
            BookSeat task = new BookSeat(user);
            executor.execute(task);
        }
        shutdownAndAwaitTermination(executor);
        System.out.println("Time taken: "+(System.currentTimeMillis()-start)+" ms");
        String seats= "SELECT id,name,trip_id,user_id from seats where trip_id=1 ORDER BY id";
        resultSet=statement.executeQuery(seats);
        while(resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int trip_id = resultSet.getInt("trip_id");
            int user_id=resultSet.getInt("user_id");
            System.out.println("ID: " + id + ", Name: " + name+", trip_id: "+ trip_id +", user_id: "+ user_id);
        }

    }
    static void shutdownAndAwaitTermination(ExecutorService pool) {
        pool.shutdown();
        try {
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow();
                if (!pool.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ex) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}