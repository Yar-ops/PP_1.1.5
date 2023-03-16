package jm.task.core.jdbc;

//import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

//import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Anna", "Bell", (byte) 18);
        userService.saveUser("Ivan", "Ivanov", (byte) 19);
        userService.saveUser("Den", "Brown", (byte) 20);
        userService.saveUser("Alice", "Black", (byte) 21);
        userService.getAllUsers().forEach(System.out::println);
        userService.removeUserById(2);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
