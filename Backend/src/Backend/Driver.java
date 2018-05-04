package Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Hashtable;

@SpringBootApplication
public class Driver {

    static Hashtable<String, Data> data;

    public static void main(String[] args) {
        Driver.data = new Hashtable<>();

        SpringApplication.run(Driver.class, args);
    }
}
