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

        System.out.println("Enter you login: \"admin\" or range of values of user ID "+
                Database.getMinUID() +"-"+ Database.getMaxUID() +
                " or type \"exit\" any time to exit");

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
            welcomeMenu();
        }

    }

    private static void accuracyWarning() {
        System.out.println("You will be too accuracy");
    }

    // TODO не смотря на то что в задании нет возможности клиенту делать транзакции это надо сделать
    private static void clientMenu() throws SQLException {
        System.out.println("welcome "+Database.getClientName(uID));
        System.out.println("you can: nothing");

    }

    // TODO убрать приветствное сообщение каждлый раз
    private static void rootAdminMenu() throws SQLException {
        System.out.println("welcome to the administration console");
        System.out.println(
                "enter:\n" +
                        "\"clients\" or \"c\" to view list of clients\n" +
                        "client ID to view list of accounts\n" +
                        "\"transactions\" or \"t\" to view transactions\n" +
                        "\"logout\" or \"l\" to change user\n" +
                        "\"exit\" or \"e\" to exit terminal\n" +
                        "");
        String userAnswer  =getUserAnswer();
        if ("exit".equals(userAnswer) || "e".equals(userAnswer)) exitProgramm();
        else if ("logout".equals(userAnswer) || "l".equals(userAnswer)) welcomeMenu();
        else if ("clients".equals(userAnswer) || "c".equals(userAnswer)) System.out.println(getListUser());
        else if ("transactions".equals(userAnswer) || "t".equals(userAnswer)) viewTransactions();
        else try {
                Integer parseduID = Integer.parseInt(userAnswer);
                System.out.println(getAccountsOfClient(parseduID));

            } catch (NumberFormatException e) {
                accuracyWarning();
            }

        rootAdminMenu();
    }

    private static void viewTransactions() throws SQLException {
        System.out.println("enter account ID to view transactions or" +
                        "\"logout\" or \"l\" to change user\n" +
                        "\"exit\" or \"e\" to exit terminal\n" +
                        "");
        String userAnswer  =getUserAnswer();
        if ("exit".equals(userAnswer) || "e".equals(userAnswer)) exitProgramm();
        else if ("logout".equals(userAnswer) || "l".equals(userAnswer)) welcomeMenu();
        else try {
                Integer parseduID = Integer.parseInt(userAnswer);
                System.out.println(getTransactionsOfAccount(parseduID));

            } catch (NumberFormatException e) {
                accuracyWarning();
            }
        rootAdminMenu();
    }

    private static String getTransactionsOfAccount(Integer accountID) throws SQLException {
        ResultSet rs = Database.getStatement().executeQuery("SELECT * FROM transactions WHERE id="+accountID);

        StringBuilder stringBuilder = new StringBuilder("Date\t\tfrom Account\tto Account\tamount\n");

        while (rs.next()) {
            stringBuilder.append(rs.getString(5));
            stringBuilder.append("\t");
            stringBuilder.append(rs.getInt(2));
            stringBuilder.append("\t\t\t\t");
            stringBuilder.append(rs.getInt(3));
            stringBuilder.append("\t\t\t");
            stringBuilder.append(rs.getInt(4));
            stringBuilder.append("\n");
        };
        stringBuilder.append("===========================================");

        return stringBuilder.toString();
    }

    private static String getAccountsOfClient(Integer uID) throws SQLException {
        ResultSet rs = Database.getStatement().executeQuery("SELECT * FROM accounts WHERE id="+uID);

        StringBuilder stringBuilder = new StringBuilder("Account\tAmount\n");

        while (rs.next()) {
            stringBuilder.append(rs.getInt(1));
            stringBuilder.append("\t");
            stringBuilder.append(rs.getInt(3));
            stringBuilder.append("\n");
        };
        stringBuilder.append("============================");

        return stringBuilder.toString();
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
        stringBuilder.append("============================");

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
