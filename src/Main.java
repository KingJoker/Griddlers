import java.io.FileNotFoundException;

/**
 * Created by david on 8/5/15.
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Griddler myGriddler = new Griddler("rabbit.txt");
        System.out.println(myGriddler);
        myGriddler.solve();
        System.out.println("Solved:");
        System.out.println(myGriddler);
    }
}
