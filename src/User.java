public class User {
    public String name;
    public int id;

    public User(int id, String name){
        this.id=id;
        this.name=name;
    }
    public User(){
        this.id=0;
        this.name=null;
    }
    public int getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
}
