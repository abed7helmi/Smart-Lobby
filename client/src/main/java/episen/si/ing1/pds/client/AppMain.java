package episen.si.ing1.pds.client;

import java.sql.*;

public class AppMain {

    private static DataSource d = new DataSource();




    public static void main(String[] args) throws Exception {

        Client a = new Client();
        Client b = new Client();
        Client z = new Client();
        Client l = new Client();
        Client e = new Client();
        Client f = new Client();

        a.test(d);
        b.test(d);
        z.test(d);
        l.test(d);
        e.test(d);

    }
}
