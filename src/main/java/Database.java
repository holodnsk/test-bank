import java.sql.*;

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

    public static int getMinUID() throws SQLException {
        String query =  "SELECT min(id) FROM accounts";

        return getUniqIntFromSQLQuery(query);
    }

    private static int getUniqIntFromSQLQuery(String query) throws SQLException {
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) return rs.getInt(1);
        throw new SQLException();
    }

    public static int getMaxUID() throws SQLException {
        String query  = "SELECT max(id) FROM accounts";
        return getUniqIntFromSQLQuery(query);
    }

    public static String getClientName(Integer uID) throws SQLException {
        String query ="SELECT name FROM clients WHERE id="+uID;

        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) return rs.getString(1);
        throw new SQLException();
    }
}
