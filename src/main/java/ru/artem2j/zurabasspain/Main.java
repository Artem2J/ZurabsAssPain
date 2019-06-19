package ru.artem2j.zurabasspain;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Выбор действия: \r 1 - Парсить сообщения пользователя \r 2 - Опустить рейтинг пользователю");
        String choose = "";
        while (true){
            choose = reader.readLine();
            if (choose.equals("1") || choose.equals("2"))break;
        }
        reader.close();
        if (Integer.parseInt(choose) == 1)parseMessageCodes();
        if (Integer.parseInt(choose) == 2)sendRanking();


    }

    private static void sendRanking() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/res/artem2j_result.txt")));
        String buff;
        while ((buff = reader.readLine()) != null){
            URL url = new URL("https://myslo.ru/raiting/remove?type=comment&id=" + buff + "&r=0&X-Requested-With=XMLHttpRequest");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            connection.setRequestProperty("User-Agent","Mozilla/5.0");
            connection.setRequestProperty("Referer", "https://myslo.ru/user/profile/artem2j");
            connection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
            connection.setRequestProperty("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
            connection.setRequestProperty("Cookie", "ASP.NET_SessionId=nbfx4tyoegt50vdp51refulq; rheftjdd=rheftjddVal; _ga=GA1.2.1441851303.1541766871; _ym_uid=1541766872295958505; _grf_vis=1; __gads=ID=b55a4def1eafda96:T=1541766874:S=ALNI_Mbk-8q8yHIgQvlr0a42P2k51BRdTg; __RequestVerificationToken=ZbiYEBEa6phtrowtdnQcjE1pwS1f4Ya80axLphc-0Pg0ws9s_9mPq5lKaXDEoqSblaVyXwXtOKrR0M4ZpWMQXYo3Bps1; __utmc=231353987; .ASPXAUTH=8C44A5580182030CDF71D896797668F8A13786D8C40B5DB6E567F3C8F8470E4E3212E1BDE50BF6B5108B1DED38A9FD5EB5CAF8B7CBF53246E6CB128818EA2C5035F90FA83B287C5C99A0AE372C7E60F648031208D415797D060FF631EB707AE8264E339E0EBBC480BA01EBAFDB80DF74DC6705701BA809147D8B5669B612F934D28140DF0BEA533114F7A17A1530307A217BE78856568C797E35E63B00AD76BC935C3448813268F4DCB90B2FE42C34CCB920A4AA66DBC98F4ABCD18847BC9F6C394BEFA37E50177DEF21E2E0C04B22093C0AEB60F0F32056485E8FE97A35A782C3268181F7EFE9B01874A91F59840F915736DE67AF35E96433CA7EA04522B55438E688CDBFA95571C91404314CBF6BF76BB8D3652882FAA28243F1F7FF5F77E3040FA79AF880151B482A1A80A68F8F5D9046B2694A4E0A5D7E16636C80697AC2108063BD0EA7C2383442B8380846986191FCFA35595C86BDEE1E44A4999C0A0939F51254; _ym_d=1557747847; _grf_ref=www.google.ru; __utmz=231353987.1559718089.206.4.utmcsr=yxnews|utmccn=(not%20set)|utmcmd=desktop; _gid=GA1.2.1075712240.1560747491; _ym_isad=2; __utma=231353987.1441851303.1541766871.1560752672.1560773816.249; __utmt=1; _ym_visorc_21247984=w; __utmb=231353987.6.10.1560773816");

            int respCode = connection.getResponseCode();
            System.out.println(respCode);

        }

    }

    private static void parseMessageCodes() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/res/target"));
        System.out.println("Выберете цель:");
        System.out.println(("1 - " + properties.getProperty("Rozario")));
        System.out.println(("2 - " + properties.getProperty("Zurab")));
        BufferedReader cHreader = new BufferedReader(new InputStreamReader(System.in));
        String choose = "";
        while (true){
            choose = cHreader.readLine();
            if (choose.equals("1") || choose.equals("2"))break;
        }
        cHreader.close();
        String nextUrl = "";
        if (Integer.parseInt(choose) == 1)nextUrl = properties.getProperty("Rozario");
        if (Integer.parseInt(choose) == 2)nextUrl = properties.getProperty("Zurab");

        while (!nextUrl.equals("")){
            URL url = new URL(nextUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent","Mozilla/5.0");

            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("src/main/res/" + properties.getProperty("acc").split("/")[5] +"_result.txt", true));

            int responseCode = connection.getResponseCode();
            String buff;
            nextUrl = "";
            if(responseCode == 200){
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((buff = reader.readLine()) != null){
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
