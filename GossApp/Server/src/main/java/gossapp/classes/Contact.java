package gossapp.classes;

public class Contact {
    private String name;

    public Contact(String name){
        this.name = name;
    }

    public void modifyName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
