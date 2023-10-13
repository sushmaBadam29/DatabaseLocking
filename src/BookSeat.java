import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookSeat implements Runnable {
    String jdbcUrl = "jdbc:mysql://localhost:3306/airlinebooking?useSSL=false";
    String username = "root";
    String password = "sushmabadam";

    User user= null;

    public BookSeat(User user){
        this.user=user;
    }

    @Override
    public void run(){
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet resultSet=null;
//            String sql="SELECT id,name,trip_id,user_id from seats where trip_id=1 and user_id IS NULL ORDER BY id LIMIT 1";//approach1
//            resultSet=statement.executeQuery(sql);
//            String sql1="SELECT id,name,trip_id,user_id from seats where trip_id=1 and user_id IS NULL ORDER BY id LIMIT 1 FOR UPDATE";
//            resultSet=statement.executeQuery(sql1);
            String sql2="SELECT id,name,trip_id,user_id from seats where trip_id=1 and user_id IS NULL ORDER BY id LIMIT 1 FOR UPDATE SKIP LOCKED";
            resultSet=statement.executeQuery(sql2);
            Seat seat=null;
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int trip_id = resultSet.getInt("trip_id");
                int user_id=resultSet.getInt("user_id");
                seat=new Seat(id,name,trip_id,user_id);
            }
            String update= "UPDATE seats set user_id = "+String.valueOf(user.getId())+" where id="+String.valueOf(seat.getId());
            PreparedStatement p = connection.prepareStatement(update);
            p.execute();
            resultSet.close();
            statement.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
