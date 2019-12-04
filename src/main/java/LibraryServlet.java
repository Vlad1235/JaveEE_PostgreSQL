import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class LibraryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter(); //   вывести пользователю результат на страницу

        try {
            Class.forName("org.postgresql.Driver"); // загружаем драйвер PostrgesSQL в JVM. Подключение драйвера к приложению
        } catch (ClassNotFoundException e) { // выскочит если мы не загрузили библиотеку в проект. А ля проблемы с Майвеном
            e.printStackTrace();
        }

        try {
            Connection con = DriverManager.getConnection( //подключение к БД
                    "jdbc:postgresql://localhost:5432/java_ee_db",
                    "postgres",
                    "");
            Statement statement = con.createStatement(); // создаем объект для совершения запросов к на данном этапе уже подключенной БД. Для каждого пользователя будет создаваться отдельное подключение
            ResultSet resultSet = statement.executeQuery("SELECT title FROM books"); // передаем запрос клиента в базу данных. Взяли частный случай, когда запрос на названия книг в базе данных. Объект класса ResultSet хранит результат выполнения запроса, они хранятся как множество.
            while (resultSet.next()){
                    pw.println(resultSet.getString("title")); // передаем название колонки, которая нужна клиенту
            }
            statement.close(); // закрываем подключение к базе данных после того, как пользователь получил список названий книг.
        } catch (SQLException e) { // любые косяки работы(например такой базы данный не существует, сервак не запущен и тд) с базой будут тут. Никакой конкретики об ошибке
            e.printStackTrace();
        }

    }
}
