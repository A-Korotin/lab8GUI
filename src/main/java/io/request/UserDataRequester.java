package io.request;

import commands.dependencies.Instances;
import exceptions.ProgramExitException;
import io.OutPutter;
import net.auth.User;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public final class UserDataRequester {

    private static final Scanner scanner = new Scanner(System.in);


    public static User requestUser(Instances instances, OutPutter outPutter) {
        try {
            outPutter.output("Введите логин");
            String login = scanner.nextLine().trim();
            outPutter.output("Введите пароль");
            String pass;
            while(!reliablePassword((pass = scanner.nextLine().trim())))
                outPutter.output("Пароль ненадежный) Придумай новый");
            return new User(login, pass);

        } catch (NoSuchElementException e) {
            throw new ProgramExitException("Завершение работы");
        }
    }

    private static boolean reliablePassword(String password){
        int count = 0;
        boolean reliable = true;
        for(int i = 1; i < password.length(); i++){
            if (password.charAt(i) == password.charAt(i - 1)){
                count++;
            }
        }
        if (count == password.length() - 1){
            reliable = false;
        }
        return reliable;
    }
}
