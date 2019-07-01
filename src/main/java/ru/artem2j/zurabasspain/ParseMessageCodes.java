package ru.artem2j.zurabasspain;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

import static ru.artem2j.zurabasspain.ChooseUser.chooseUser;

public class ParseMessageCodes {
    static void parseMessageCodes() throws IOException {
        User user = chooseUser();
        String nextUrl = user.getAccountAdress();
        String fileName = user.getResultAdress();
        OutputStreamWriter writerClean = new OutputStreamWriter(new FileOutputStream(fileName));
        writerClean.write("");
        writerClean.close();
        while (!nextUrl.equals("")){
            URL url = new URL(nextUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent","Mozilla/5.0");


            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileName, true));

            int responseCode = connection.getResponseCode();
            String buff;
            nextUrl = "";
            if(responseCode == 200){
                BufferedReader reader2 = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((buff = reader2.readLine()) != null){
                    if (buff.contains("rating r_")) writer.write((buff.split("rating r_")[1].split("\"")[0]) + "\r");
                    if (buff.contains("\"preloadpage\""))nextUrl = "https://myslo.ru" + buff.split("\"")[3];
                }
            }else System.out.println("Ответ сервера: " + responseCode);

            writer.close();
            System.out.println(nextUrl);
        }
        System.out.println("Parse complete");
    }
}
