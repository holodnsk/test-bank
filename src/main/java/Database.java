import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static Statement statement;
    private static Connection connection;

    public static Statement getStatement() {
        // TODO пофиксить для многопоточности
        if (statement!= null) return statement;

        try {
            //Загружаем драйвер
            Class.forName("com.mysql.jdbc.Driver");

            // варим стэйтмент для отправки запросов нашей БД
            String url = "jdbc:mysql://localhost:3306/";
            connection = DriverManager.getConnection(url, "root", "root");
            statement = connection.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("can`t connect to DB localhost:3306 root:root");
        }

        return statement;
    }

    public static void closeStatement(){
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
