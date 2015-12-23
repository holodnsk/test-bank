import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Transaction {

    int fromAccountID;
    int toAccountID;
    int transactionAmount;
    String date;
    Statement statement;
    // TODO заменить Date
    // TODO записывать неудавшиеся транзакции
    // TODO работать с наличкой
    // TODO транзакционность
    public Transaction(int fromAccountID, int toAccountID, int transactionAmount, Statement statement, String date) throws SQLException {
        this.fromAccountID = fromAccountID;
        this.toAccountID = toAccountID;
        this.transactionAmount=transactionAmount;
        this.date=date;
        this.statement=statement;

        int amountOfFromAccount = getAmountOfAccount(fromAccountID);
        getQueryTransactionFromAccount(fromAccountID, transactionAmount, statement, amountOfFromAccount);

        int amountOfToAccount = getAmountOfAccount(toAccountID);
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

    private int getAmountOfAccount(int accountID) throws SQLException {
        int amountOfToAccount=0;
        ResultSet rsTo = statement.executeQuery("SELECT amount FROM accounts WHERE clientId=" + accountID);
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
}
