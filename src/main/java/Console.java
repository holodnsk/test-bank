import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class Console {
    private static Integer uID;

    public static void run() throws SQLException {
        welcomeMenu();
    }


    public static void welcomeMenu() throws SQLException {
        // TODO надо дать реальный диапазон возможных ID из БД
        System.out.println("Enter you login: \"admin\" or range of values of user ID 1-"+SQLDBInit.NUM_CLIENTS);

        String userAnswer = getUserAnswer();
        if ("admin".equals(userAnswer)) adminMenu();
        else {
            try {
                uID = Integer.parseInt(userAnswer);
            } catch (NumberFormatException e) {
                System.out.println("try again");
                welcomeMenu();
            }
            // TODO проверить что введенный uID есть в БД
            userMenu();
        }

    }

    private static void userMenu() {
        System.out.println("welcome ");

    }

    private static void adminMenu() {
        System.out.println("welcome to the administration console");
    }

    private static String getUserAnswer() {
        Scanner userAnswer = new Scanner(new InputStreamReader(System.in));
        if (userAnswer.hasNext()) return userAnswer.nextLine();
        return "try again";
    }

}
