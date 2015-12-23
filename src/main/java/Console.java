import java.sql.*;

public class Console {


    public static void welcomeMenu() throws SQLException {

        // TODO надо дать реальный диапазон возможных ID
        System.out.println("Enter you login: \"admin\" or range of values of user ID 1-"+SQLDBInit.NUM_CLIENTS);

    }

}
