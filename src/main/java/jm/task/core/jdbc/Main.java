package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.getAllUsers().forEach(System.out::println);

        userService.saveUser("Тинки-Винки", "Телепузик", (byte) 12);
        userService.saveUser("Дипси", "Телепузик", (byte) 12);
        userService.saveUser("Ляля", "Телепузик", (byte) 12);
        userService.saveUser("По", "Телепузик", (byte) 12);

        userService.getAllUsers().forEach(System.out::println);

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
