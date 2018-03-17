package pl.sda.jpatraining;

import com.google.common.collect.Lists;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.errorprone.annotations.NoAllocation;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JdbcMain {

    public static void main(String[] args) {
        showEmployees();

        statementExample("2000"); // normalne wykonanie
        statementExample("1000000 OR 1 = 1"); // SQL injection

        prepareStatementExample("2000");

        callableStatementExample(7698);
    }

    private static void callableStatementExample(int id) {
        System.out.println("callableStatementExample " + id);
        try (Connection connection = getConnection()) {
            String query = "{CALL sdajdbc.getNameById(?,?)}";
            CallableStatement callableStatement = connection.prepareCall(query);
            callableStatement.setInt(1, id);
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            callableStatement.execute();
            String employeeName = callableStatement.getString(2);
            System.out.println("Pracownik: " + employeeName);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void prepareStatementExample(String minSalary) {
        System.out.println("prepareStatementExample " + minSalary);
        try (Connection connection = getConnection()) {
            String sql = "SELECT\n" +
                    "  employee_id,\n" +
                    "  employee_name,\n" +
                    "  salary,\n" +
                    "  hiredate\n" +
                    "FROM sdajdbc.employee\n" +
                    "WHERE salary > ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 2000);
            ResultSet resultSet = preparedStatement.executeQuery();
            printOutEmployees(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void statementExample(String minSalary) {
        System.out.println("statementExample " + minSalary);
        try (Connection connection = getConnection()) {
            String sql = "SELECT\n" +
                    "  employee_id,\n" +
                    "  employee_name,\n" +
                    "  salary,\n" +
                    "  hiredate\n" +
                    "FROM sdajdbc.employee\n" +
                    "WHERE salary > " + minSalary + "  ";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            printOutEmployees(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showEmployees() {
        try (Connection connection = getConnection()) {
            String query = "SELECT employee_id, employee_name, salary, hiredate FROM sdajdbc.employee;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            printOutEmployees(resultSet);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void printOutEmployees(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            System.out.println(EmployeeDto.builder().employee_id(resultSet.getInt("employee_id"))
                    .employee_name(resultSet.getString("employee_name"))
                    .salary(resultSet.getFloat("salary"))
                    .hiredate(resultSet.getDate("hiredate")).build().toString());

//            System.out.println(
//                    Stream.of(employee_id, employee_name, salary, hiredate)
//                            .map(e -> e.toString()).collect(Collectors.joining(", ")));
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

