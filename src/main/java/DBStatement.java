import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBStatement {
    private static Statement statement;

    public static Statement getStatement() {
        // TODO ��������� ��� ���������������
        if (statement!= null) return statement;
        Connection connection = null;
        Statement statement = null;
        try {
            //��������� �������
            Class.forName("com.mysql.jdbc.Driver");

            // ����� ��������� ��� �������� �������� ����� ��
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
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
