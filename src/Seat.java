public class Seat {
    public int id;
    public String name;
    public int trip_id;
    public int user_id;
    public Seat(int id, String name, int trip_id, int user_id){
        this.id=id;
        this.name=name;
        this.trip_id=trip_id;
        this.user_id=user_id;
    }
    public int getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public int getTrip_id(){
        return this.trip_id;
    }
    public int getUser_id(){
        return this.user_id;
    }
}
