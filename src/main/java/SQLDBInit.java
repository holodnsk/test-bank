import java.sql.*;
import java.util.*;
import java.util.Date;

// TODO узнать как правильно инициализировать БД и переделать
public class SQLDBInit {

    public static final int NUM_MAX_ACCOUNTS_EVER_CLIENT = 5;//11;
    public static final int NUM_CLIENTS = 3;//100;
    public static final int NUM_MAX_TRANSACTIONS_EVER_ACCOUNT = 3;//100;
    public static final int MAX_GENERATED_VALUE_OF_ACCOUNT= 1000;

    // TODO заменить Date
    private static final Date START_DATE_OF_TRANSACTIONS = new Date(2015,0,1);

    private final static String dropDB = "DROP DATABASE IF EXISTS kholodkovDB;";
    private final static String createDB = "CREATE DATABASE kholodkovDB DEFAULT CHARACTER SET 'utf8';";
    private final static String useDB = "USE kholodkovDB;";

    // запросы для создания таблиц
    //TODO все ID надо генерить непоследовательно, это должно быть случайное и уникальное число
    private final static String createTableClients =
            "CREATE TABLE `clients` (" +
                    "`id` int(8) NOT NULL AUTO_INCREMENT,  " +
                    "`name` varchar(25) NOT NULL,  " +
                    "PRIMARY KEY (`id`) " +
                    ") ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;";

    private final static String createTableAccounts =
            "CREATE TABLE `accounts` (" +
                    "`id` int(8) NOT NULL AUTO_INCREMENT,  " +
                    "`clientId` int(8) NOT NULL,  " +
                    "`amount` int(8) NOT NULL,  " +
                    "PRIMARY KEY (`id`) " +
                    ") ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;";
    private final static String createTableTransactions =
            "CREATE TABLE `transactions` (" +
                    "`id` int(8) NOT NULL AUTO_INCREMENT,  " +
                    "`fromAccountId` int(8) NOT NULL,  " +
                    "`toAccountId` int(8) NOT NULL,  " +
                    "`amount` int(8) NOT NULL,  " +
                    "`date` date NOT NULL,  " +
                    "PRIMARY KEY (`id`) " +
                    ") ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;";

    // шаблоны запросов и людские имена для наполнения таблиц тестовыми данными
    private final static String headClientsToDB = "insert into clients (name) values ('";
    private final static String tailClientsToDB = "');";
    private final static List<String> clientsNames = Arrays.asList(
            "Avgusta","Agata","Agafya","Aglaya","Agnessa","Agniya","Agrafena","Agrippina",
            "Ariadna","Beatrisa","Berta","Borislava","Bronislava","Valentina","Valeriya",
            "Galya","Ganna","Genrietta","Glafira","Gorislava","Darya","Diana","Dina",
            "Inga","Inessa","Inna","Iraida","Irina","Iya","Kazimira","Kaleriya","Kapitolina",
            "Konkordiya","Kseniya","Lada","Larisa","Leokadiya","Liana","Lidiya","Liliana",
            "Marta","Marfa","Melanya","Melitrisa","Milana","Milena","Milica","Mira","Miroslava",
            "Polina","Praskovya","Pulxeriya","Rada","Raisa","Regina","Renata","Rimma",
            "Taisiya","Tamara","Tatyana","Ulyana","Faina","Fedosya","Felicata","Flora",
            "Avksentij","Avtonom","Agap","Agafon","Aggej","Adam","Adrian","Azarij",
            "Apollon","Arefij","Aristarx","Arkadij","Arsenij","Artemij","Artem","Arxip",
            "Varfolomej","Vasilij","Vaclav","Velimir","Venedikt","Veniamin","Vikentij","Viktor",
            "Gedeon","Gennadij","Georgij","Gerasim","German","Gleb","Gordej","Gostomysl",
            "Evlampij","Evsej","Evstafij","Evstignej","Egor","Elizar","Elisej","Emelyan","Epifan",
            "Iosif","Ipat","Ipatij","Ippolit","Iraklij","Isaj","Isidor","Kazimir",
            "Kupriyan","Lavr","Lavrentij","Ladimir","Ladislav","Lazar","Lev","Leon","Leonid",
            "Mechislav","Milan","Milen","Milij","Milovan","Mina","Mir","Miron","Miroslav",
            "Nikon","Nifont","Oleg","Olimpij","Onufrij","Orest","Osip","Ostap","Ostromir",
            "Proxor","Radim","Radislav","Radovan","Ratibor","Ratmir","Rodion","Roman","Rostislav",
            "Sergej","Sigizmund","Sidor","Sila","Silantij","Silvestr","Simon","Sokrat","Solomon",
            "Trofim","Ulyan","Ustin","Fadej","Fedor","Fedosij","Fedot","Feliks","Feoktist",
            "Emmanuil","Emil","Erast","Ernest","Ernst","Yuvenalij","Yulian","Yulij","Yurij",
            "Yakov","Yan","Yakub","Yanuarij","Yaropolk","Yaroslav");

    private final static String headAccountsToDB = "insert into accounts (clientId, amount) values ('";
    private final static String insideAccountsToDB = "', '";
    private final static String tailAccountsToDB = "');";

    private final static List<String> sampleDates = Arrays.asList(
            "2015-11-01","2015-11-02", "2015-11-03","2015-11-04","2015-11-05","2015-11-06","2015-11-07","2015-11-08",
            "2015-11-09","2015-11-10","2015-11-11","2015-11-12","2015-11-13","2015-11-14","2015-11-15","2015-11-16",
            "2015-11-17","2015-11-18","2015-11-19","2015-11-20","2015-11-21","2015-11-22","2015-11-23","2015-11-24",
            "2015-11-25","2015-11-26","2015-11-27","2015-11-28","2015-11-29","2015-11-30","2015-12-01"
    );


    public static void init() throws SQLException {


        Statement statement = Database.getStatement();

        reNewDB(statement);

        fillClientsTableRandomNames(statement);
        fillAccountsTableRandomData(statement);
        fillTransactionsTableRandomData(statement);



    }

    private static void reNewDB(Statement statement) throws SQLException {
        // прибьем старую базу
        statement.executeUpdate(dropDB);
        // создадим новую
        statement.executeUpdate(createDB);
        // и будем ее использовать
        statement.executeUpdate(useDB);
        // создадим таблицы в этой базе
        statement.executeUpdate(createTableClients);
        statement.executeUpdate(createTableAccounts);
        statement.executeUpdate(createTableTransactions);
    }

    private static List<Integer> getClientsIDs(Statement statement) throws SQLException {
        ResultSet SQLResultSetClientsIDs = statement.executeQuery("SELECT id FROM clients");
        List<Integer> clientsIDs = new ArrayList<Integer>();
        while (SQLResultSetClientsIDs.next()) {
            clientsIDs.add(SQLResultSetClientsIDs.getInt("id"));
        }
        return clientsIDs;
    }

    private static void fillAccountsTableRandomData(Statement statement) throws SQLException {

        List<Integer> clientsIDs = getClientsIDs(statement);

        for (Integer clientID : clientsIDs) {
            int numOfAccountsForClient = new Random().nextInt(NUM_MAX_ACCOUNTS_EVER_CLIENT);

            for (int i = 0; i<numOfAccountsForClient; i++){
                int transactAmount = new Random().nextInt(MAX_GENERATED_VALUE_OF_ACCOUNT);


                String sqlQuery = headAccountsToDB + clientID + insideAccountsToDB + transactAmount + tailAccountsToDB;
                statement.executeUpdate(sqlQuery);
            }
        }
    }

    private static void fillTransactionsTableRandomData(Statement statement) throws SQLException {

        List<Integer> accountsIDs = getAccountsIDs(statement);


        for (Integer fromAccountID : accountsIDs) {
            int numOfTransactionsForClient = new Random().nextInt(NUM_MAX_TRANSACTIONS_EVER_ACCOUNT);

            for (int i = 0; i<numOfTransactionsForClient; i++){
//                // "insert into users (name, age, isAdmin, createdDate) values ('Ivan', '1', true, '1990-03-20');
                int transactionAmount = new Random().nextInt(MAX_GENERATED_VALUE_OF_ACCOUNT/10);
                if (transactionAmount==0) transactionAmount=1;
                int toAccountID = accountsIDs.get(new Random().nextInt(accountsIDs.size()));

                // Если денег не хватает то пропускаем цикл
                if( isNotEnoughMoneyOnAccount(statement, transactionAmount, fromAccountID)) {
                    continue;
                }

                // TODO исправить генерацию отрицательных балансов
                String randomDate = sampleDates.get(new Random().nextInt(sampleDates.size()));

                new Transaction(fromAccountID,toAccountID,transactionAmount,statement, randomDate);
            }
        }
    }

    private static boolean isNotEnoughMoneyOnAccount(Statement statement, int transactionAmount, int fromAccountID) throws SQLException {
        // TODO наверное SQL может дать нужный ответ
        ResultSet rs = statement.executeQuery("SELECT amount FROM accounts WHERE clientId=" + fromAccountID);
        while(rs.next()) {
            int amountOfFromAccount = rs.getInt("amount");

            if (amountOfFromAccount<transactionAmount) {
                return true;
            }
        }
        return false;
    }

    private static List<Integer> getAccountsIDs(Statement statement) throws SQLException {
        // TODO разобраться почему и зачем закрывается ResultSet
        ResultSet SQLResultSetAccountsIDs = statement.executeQuery("SELECT id FROM clients");
        List<Integer> accountsIDs = new ArrayList<Integer>();
        while (SQLResultSetAccountsIDs.next()) {
            accountsIDs.add(SQLResultSetAccountsIDs.getInt("id"));
        }
        return accountsIDs;
    }

    private static void fillClientsTableRandomNames(Statement statement) throws SQLException {
        Collections.shuffle(clientsNames);
        for (String name : clientsNames.subList(0, NUM_CLIENTS)) {
            statement.executeUpdate(headClientsToDB+name+tailClientsToDB);
        }
    }
}
