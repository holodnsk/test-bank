import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Transaction {

    int fromAccountID;
    int toAccountID;
    int transactionAmount;
    String date;
    // TODO заменить Date
    // TODO научить записывать неудавшиеся транзакции
    // TODO научить работать с наличкой
    // TODO исправить: может размножать/испарять деньги при выполнении в несколько потоков
    public Transaction(int fromAccountID, int toAccountID, int transactionAmount, Statement statement, String date) throws SQLException {
        this.fromAccountID = fromAccountID;
        this.toAccountID = toAccountID;
        this.transactionAmount=transactionAmount;
        this.date=date;

        int amountOfFromAccount = getAmountOfAccount(fromAccountID, statement);
        getQueryTransactionFromAccount(fromAccountID, transactionAmount, statement, amountOfFromAccount);

        int amountOfToAccount = getAmountOfToAccount(toAccountID, statement);
        String transactionToAccount =
                "UPDATE accounts SET  amount = " + (amountOfToAccount+transactionAmount) + " WHERE id = " +toAccountID;
        statement.executeUpdate(transactionToAccount);


        String query = "insert into transactions (fromAccountId, toAccountId, amount, date) values ('"+
                fromAccountID+  "', '"+
                toAccountID+  "', '"+
                transactionAmount+  "', '"+
                date+
                "');";
        statement.executeUpdate(query);
    }

    private int getAmountOfToAccount(int toAccountID, Statement statement) throws SQLException {
        int amountOfToAccount=0;
        ResultSet rsTo = statement.executeQuery("SELECT amount FROM accounts WHERE clientId=" + toAccountID);
        while(rsTo.next()) {
            amountOfToAccount = rsTo.getInt("amount");
        }
        return amountOfToAccount;
    }

    private void getQueryTransactionFromAccount(int fromAccountID, int transactionAmount, Statement statement, int amountOfFromAccount) throws SQLException {
        String queryTransactionFromAccount =
                "UPDATE accounts SET  amount = " + (amountOfFromAccount-transactionAmount) + " WHERE id = " +fromAccountID;
        statement.executeUpdate(queryTransactionFromAccount);
    }

    private int getAmountOfAccount(int accountID, Statement statement) throws SQLException {
        int amountOfFromAccount = getAmountOfToAccount(accountID, statement);
        return amountOfFromAccount;
    }
}
