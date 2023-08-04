package com.example.joshuaburt_comp1011sec005_lab03;

        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.TextArea;
        import javafx.scene.control.TextField;
        import java.net.URL;
        import java.sql.*;
        import java.util.ArrayList;
        import java.util.ResourceBundle;

public class Assignment3Controller implements Initializable {
    //Format 2: The other application format is a show all table with clickable cells that execute SQL update statement
    @FXML
    private TextArea textArea;
    @FXML
    private TextField query;
    private static Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    int nLength = 10;
    int n2Length = 20;
    int n3Length = 30;
    private static final String url = "jdbc:mysql://localhost:3308/gamedatabase";
    private static final String user = "root";
    private static final String password = "";

    //SELECT, INSERT, UPDATE queries
    @FXML
    void queryData(/*ActionEvent event*/) {
        String sql = query.getText();
        try {
            System.out.println(sql);
            getConnection();
            textArea.clear();
            preparedStatement = connection.prepareStatement(sql);
            boolean isResultSet = preparedStatement.execute();
            if (isResultSet) { // if resultSet (SELECT statement)
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    //gets row 0 data
                    for (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){ //columns start at 1 if using function .getMetaData()
                        textArea.appendText(resultSet.getMetaData().getColumnName(i));
                        textArea.appendText(": ");
                        textArea.appendText(resultSet.getString(resultSet.getMetaData().getColumnName(i)));
                        if(i < resultSet.getMetaData().getColumnCount()) {
                            textArea.appendText(", ");
                        }
                    }
                    textArea.appendText("\n");
                    //gets row >= 1 data
                    String resultString = resultSetToString(resultSet); //displays data in a single line
                    textArea.appendText(resultString + "\n");
                }
            }
            textArea.appendText(preparedStatement.toString() + "\n"); // else INSERT or UPDATE query
            textArea.appendText("Database updated");
            resultSet.close();
            preparedStatement.close();
        }
        catch (SQLException e) {
            textArea.appendText("Error in SQL syntax or table does not exist.");
            e.printStackTrace();
        }
    }

    @FXML
    void instructions(/*ActionEvent event*/) { //"Blind" database data manipulation
        textArea.clear();
        textArea.appendText("Database entry format and examples: " + "\n");
        textArea.appendText("SHOW TABLES " + "\n");
        textArea.appendText("SHOW COLUMNS FROM 'x' " + "\n");
        textArea.appendText("SELECT * FROM 'x' " + "\n");
        textArea.appendText("CREATE TABLE AnotherTable (column_name INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT, column_name2 VARCHAR(20) DEFAULT NULL, creation_date DATE DEFAULT NULL) ENGINE=InnoDB AUTO_INCREMENT=24" + "\n");
        textArea.appendText("INSERT INTO Game ('x') VALUES 'x' " + "\n");
        textArea.appendText("INSERT INTO Player ('x', 'y', 'z', 'a', 'b', 'c') VALUES ('x', 'y', 'z', 'a', 'b', 'c') " + "\n");
        textArea.appendText("INSERT INTO PlayerAndGame ('x', 'y', 'z', 'a') VALUES ('x','y','z','a') " + "\n");
        textArea.appendText("UPDATE Game SET game_title = 'x' WHERE game_id = 31" + "\n");
        textArea.appendText("DELETE FROM PlayerAndGame WHERE player_game_id >= 35" + "\n");
        textArea.appendText("DROP TABLE AnotherTable");
    }

    @FXML //Prints a report of all information
    void showAll(/*ActionEvent event*/) {
        try {
            getConnection();
            String sql = "SELECT player_game_id, PlayerAndGame.game_id, game_title, PlayerAndGame.player_id, score, playing_date, first_name, last_name, address, postal_code, province, phone_number FROM PlayerAndGame JOIN Game ON PlayerAndGame.game_id = Game.game_id JOIN Player ON PlayerAndGame.player_id = Player.player_id ORDER BY player_game_id;";

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            textArea.clear();
            textArea.appendText(formatString("player_game_id", nLength) + "\t" + formatString("game_id", nLength) + "\t" + formatString("game_title", n2Length)  + "\t" + formatString("player_id", nLength) + "\t" + formatString("score", nLength) + "\t" + formatString("playing_date", n2Length) + "\t" + formatString("first_name", n2Length) + "\t" + formatString("last_name", n2Length) + "\t" + formatString("address", n3Length) + "\t" + formatString("postal_code", nLength) + "\t" + formatString("province", nLength) + "\t" + formatString("phone_number", nLength) +"\n");

            while (resultSet.next()) {
                String player_game_id = resultSet.getString("player_game_id");
                String game_id = resultSet.getString("PlayerAndGame.game_id");
                String game_title = resultSet.getString("game_title");
                String player_id = resultSet.getString("PlayerAndGame.player_id");
                String score = resultSet.getString("score");
                String playing_date = resultSet.getString("playing_date");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String address = resultSet.getString("address");
                String postal_code = resultSet.getString("postal_code");
                String province = resultSet.getString("province");
                String phone_number = resultSet.getString("phone_number");


                String gameInfo = formatString(player_game_id, n3Length) + "\t" + formatString(game_id, n2Length) + "\t" + formatString(game_title, n2Length)  + "\t" + formatString(player_id, nLength) + "\t" + formatString(score, nLength) + "\t" + formatString(playing_date, n2Length) + "\t" + formatString(first_name, n2Length) + "\t" + formatString(last_name, n2Length) + "\t" + formatString(address, n3Length) + "\t" + formatString(postal_code, nLength) + "\t" + formatString(province, nLength) + "\t" + formatString(phone_number, nLength) +"\n";
                textArea.appendText(gameInfo);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void clearView(/*ActionEvent event*/) {
        textArea.clear();
    }

    @FXML
    void clearDatabase(/*ActionEvent event*/) {
        try {
            textArea.clear();
            getConnection();
            ArrayList<String> delete = new ArrayList<>();
            delete.add("DELETE FROM PlayerAndGame");
            delete.add("DELETE FROM Game");
            delete.add("DELETE FROM Player");
            for(int i = 0; i < delete.size(); i++ ){
            preparedStatement = connection.prepareStatement(delete.get(i));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            }
            textArea.appendText("All data deleted");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String formatString(String data, int dataLength) {
        if(data == null){
            data = "NULL";
        }
        if (data.length() >= dataLength) {
            return data;
        }
        else {
            StringBuilder newLength = new StringBuilder(data);
            while (newLength.length() < dataLength) {
                newLength.append(" "); //adds blankspace up to desired length in order to format data into columns
            }
            //System.out.println(sb.length());
            return newLength.toString();
        }
    }

    public static String resultSetToString(ResultSet resultSet) throws SQLException {//converts SELECT, UPDATE, INSERT to string (skips first row of data; Error: Column Index out of range, 0 < 1.)
        StringBuilder sb = new StringBuilder();

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                if (i > 1) {
                    sb.append(", ");
                }
                String columnName = metaData.getColumnName(i);
                String columnValue = resultSet.getString(i);
                sb.append(columnName).append(": ").append(columnValue);
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public static Connection getConnection() throws SQLException {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return connection;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}

