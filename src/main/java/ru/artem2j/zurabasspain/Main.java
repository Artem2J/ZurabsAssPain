package ru.artem2j.zurabasspain;

import jdk.jfr.StackTrace;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.text.Format;
import java.util.*;

import static ru.artem2j.zurabasspain.ChooseUser.chooseUser;
import static ru.artem2j.zurabasspain.ParseMessageCodes.parseMessageCodes;

public class Main {

    public static void main(String[] args) throws IOException {


        while (true){
            System.out.println("");
            System.out.println("Выбор действия: \r\n " +
                    "1 - Парсить сообщения пользователя \r\n " +
                    "2 - Опустить рейтинг пользователю \r\n" +
                    "3 - Поднять рейтинг пользователю \r\n" +
                    "4 - Выход");

            int choose = 0;
            try {
                choose = new Scanner(System.in).nextInt();

            }catch (Exception e){
                System.out.println(e.getStackTrace() + "oops");
            }

            if (choose == 1)parseMessageCodes();
            if (choose == 2){
                System.out.println("Выберете жертву:");
                User user = chooseUser();
                new SendRanking(user, Trend.remove).run();
            }
            if (choose == 3){
                System.out.println("Выберете цель:");
                User user = chooseUser();
                new SendRanking(user, Trend.add).run();
            }
            if (choose == 4)break;


        }
    }
}





