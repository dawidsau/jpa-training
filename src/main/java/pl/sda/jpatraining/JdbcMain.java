package pl.sda.jpatraining;

import com.google.common.collect.Lists;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcMain {

    public static void main(String[] args) {
        showEmployees();

        getConnection();
    }

    private static void showEmployees() {
        Connection connection = getConnection();

        try {
            String query = "SELECT employee_id, employee_name, salary FROM sdajdbc.employee;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
//            Lists.new
            List<String> people = Lists.newArrayList("sdfsdf","sdfsdf");

//            List<String> people = new ArrayList<>();
            while (resultSet.next()){
                resultSet.getInt("employee_id");
                resultSet.getString("employee_name");
                resultSet.getFloat("salary");

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sdajdbc?serverTimezone=UTC&useSSL=false",
                    "user", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
