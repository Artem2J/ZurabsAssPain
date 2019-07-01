package ru.artem2j.zurabasspain;

import jdk.jfr.StackTrace;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.text.Format;
import java.util.*;

import static ru.artem2j.zurabasspain.ChooseUser.chooseUser;
import static ru.artem2j.zurabasspain.ParseMessageCodes.parseMessageCodes;
import static ru.artem2j.zurabasspain.SendRanking.sendRanking;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("");
        System.out.println("Выбор действия: \r\n 1 - Парсить сообщения пользователя \r\n 2 - Опустить рейтинг пользователю \r\n" +
                " 3 - Поднять рейтинг пользователю");

        int choose = 0;
        try {
            choose = new Scanner(System.in).nextInt();
        }catch (Exception e){
            System.out.println(e.getStackTrace() + "oops");
        }

        if (choose == 1)parseMessageCodes();
        if (choose == 2)sendRanking("Down");
        if (choose == 3)sendRanking("Up");

        reader.close();
    }
}





