import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class Console {
    private static Integer uID;

    public static void run() throws SQLException {
        welcomeMenu();
    }


    public static void welcomeMenu() throws SQLException {
        // TODO надо дать реальный диапазон возможных uID из БД
        int minUID = Database.getMinUID();
        int maxUID = Database.getMaxUID();
        System.out.println("Enter you login: \"admin\" or range of values of user ID "+minUID+"-"+maxUID+ " or type \"exit\" any time to exit");

        String userAnswer = getUserAnswer();
        if ("exit".equals(userAnswer)) return;
        if ("admin".equals(userAnswer)) adminMenu();
        else {
            try {
                uID = Integer.parseInt(userAnswer);
                if (uID>= Database.getMinUID() && uID<=Database.getMaxUID()) {
                    clientMenu();
                    welcomeMenu();
                } else {
                    System.out.println("You will be too accuracy");
                }
            } catch (NumberFormatException e) {
                System.out.println("You will be too accuracy");
            }
            // TODO проверить что введенный uID есть в БД

            welcomeMenu();
        }

    }

    private static void clientMenu() throws SQLException {
        System.out.println("welcome "+Database.getClientName(uID));
        System.out.println("you can: nothing");

    }

    private static void adminMenu() {
        System.out.println("welcome to the administration console");
        System.out.println(
                "enter:\n" +
                "\"list\" to view list of clients\n" +
                "client ID to select the client for the next operations\n" +
                "");
    }

    private static String getUserAnswer() {
        Scanner userAnswer = new Scanner(new InputStreamReader(System.in));
        if (userAnswer.hasNext()) return userAnswer.nextLine();
        return "try again";
    }

}
