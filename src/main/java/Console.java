import java.io.InputStreamReader;
import java.sql.ResultSet;
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
        if ("admin".equals(userAnswer)) rootAdminMenu();
        else {
            try {
                uID = Integer.parseInt(userAnswer);
                if (uID>= Database.getMinUID() && uID<=Database.getMaxUID()) {
                    clientMenu();
                    welcomeMenu();
                } else {
                    accuracyWarning();
                }
            } catch (NumberFormatException e) {
                accuracyWarning();
            }
            // TODO проверить что введенный uID есть в БД

            welcomeMenu();
        }

    }

    private static void accuracyWarning() {
        System.out.println("You will be too accuracy");
    }

    private static void clientMenu() throws SQLException {
        System.out.println("welcome "+Database.getClientName(uID));
        System.out.println("you can: nothing");

    }

    // TODO убрать приветсвие каждлый раз
    private static void rootAdminMenu() throws SQLException {
        System.out.println("welcome to the administration console");
        System.out.println(
                "enter:\n" +
                        "\"list\" to view list of clients\n" +
                        "client ID to select the client for the next operations\n" +
                        "\"logout\" to change user\n" +
                        "\"exit\" to exit terminal\n" +
                        "");
        String userAnswer  =getUserAnswer();
        if ("exit".equals(userAnswer)) exitProgramm();
        else if ("logout".equals(userAnswer)) welcomeMenu();
        else if ("list".equals(userAnswer)) System.out.println(getListUser());
        else try {
                Integer parseduID = Integer.parseInt(userAnswer);

            } catch (NumberFormatException e) {
                accuracyWarning();
            }

        rootAdminMenu();
    }

    private static String getListUser() throws SQLException {
        ResultSet rs = Database.getStatement().executeQuery("SELECT * FROM clients");
        StringBuilder stringBuilder = new StringBuilder("ID\tNAME\n");

        while (rs.next()) {
            stringBuilder.append(rs.getInt(1));
            stringBuilder.append("\t");
            stringBuilder.append(rs.getString(2));
            stringBuilder.append("\n");
        };
        stringBuilder.deleteCharAt(stringBuilder.length()-1);

        return stringBuilder.toString();
    }

    private static void exitProgramm() {
        Database.closeStatement();
        System.exit(0);
    }

    private static String getUserAnswer() {
        Scanner userAnswer = new Scanner(new InputStreamReader(System.in));
        if (userAnswer.hasNext()) return userAnswer.nextLine();
        return "try again";
    }

}
