import java.io.FileNotFoundException;

/**
 * Created by david on 8/5/15.
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Griddler myGriddler = new Griddler("input.txt");
        System.out.println(myGriddler);
    }
}
