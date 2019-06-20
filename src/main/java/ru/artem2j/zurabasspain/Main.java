package ru.artem2j.zurabasspain;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("");
        System.out.println("Выбор действия: \r\n 1 - Парсить сообщения пользователя \r\n 2 - Опустить рейтинг пользователю \r\n" +
                " 3 - Поднять рейтинг пользователю");

        String choose = "";
        while (true){
            choose = reader.readLine();
            if (choose.equals("1") || choose.equals("2") || choose.equals("3"))break;
        }

        if (Integer.parseInt(choose) == 1)parseMessageCodes();
        if (Integer.parseInt(choose) == 2)sendRanking("Down");
        if (Integer.parseInt(choose) == 3)sendRanking("Up");

        reader.close();
    }

    private static void sendRanking(String editRait) throws IOException {
        System.out.println("Выберете жертву:");
        User user = chooseUser();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(user.getResultAdress())));
        String buff;
        String eR= "";
        if (editRait.equals("Down"))eR = "remove";
        else if (editRait.equals("Up")) eR = "add";
        List<String> lIst = new ArrayList<String>();
        while ((buff = reader.readLine()) != null) {
            lIst.add(buff);
        }
        System.out.println("Доступно " + lIst.size() + " комментариев для оценки");
        System.out.println("Введите число оцениваемых комментариев");
        BufferedReader reader3 = new BufferedReader(new InputStreamReader(System.in));
        int maxCount = Integer.parseInt(reader3.readLine());
        reader3.close();
        int count = 0;
        for (String messageCode: lIst) {
            count++;
            URL url = new URL("https://myslo.ru/raiting/" + eR + "?type=comment&id=" + buff + "&r=0&X-Requested-With=XMLHttpRequest");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            connection.setRequestProperty("User-Agent","Mozilla/5.0");
            connection.setRequestProperty("Referer", user.getAccountAdress());
            connection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
            connection.setRequestProperty("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
            connection.setRequestProperty("Cookie", "ASP.NET_SessionId=qqdms53egj0bcflrosz1qk2k; rheftjdd=rheftjddVal; _ga=GA1.2.2071530158.1547180792; _ym_uid=1547180792673566375; _ym_d=1547180792; __utmc=231353987; _grf_vis=1; __RequestVerificationToken=X1azfOXHscoxH8QS8Q7x2iPUlTXg8n0DJfasw_owsF8yiFYTl69j3JKpnHPd0xWftYGzCyK_sZb8I9xlyOLAA4L4Pzs1; __utmz=231353987.1557464952.7.2.utmcsr=yxnews|utmccn=(not%20set)|utmcmd=desktop; _gid=GA1.2.383040985.1560482317; _ym_isad=2; __utma=231353987.2071530158.1547180792.1561024226.1561031877.64; __utmt=1; _ym_visorc_21247984=w; .ASPXAUTH=564F8C776C23DF6E7CC5D0129B9282FF491524E55B08607125F20281567865DD2913E3F19BF6E735051871C4F080AE0A79CE4D505C9E458E75D94D9F91B37E2E73B4E01E4A1A7C96B2B370BF571E159F105B69D634608C3536E32C176CCBA34BCA20E8F6DB2B32A41C1041AD416755C340E38838F15012C612EBBCC1E805F675D6B23EBC1BE2FA557D00DC471DE7B498B8E6E0E7C9E0A457D3DCE41876558668190BBDE114F4F19827C9FC14278FD1777E7C30C70F9FBA193DAF507092BE5F6DC8C39B245E5EDE45F9FB85CA935CD9E2C8600B326905DC8F28162DB3C89F45D218665A3110511AC8BCFFE43EACE62AE5C82A2451D67916535CF88F43D9EBE57BB53C9A204D953E13428EA8931D1ADD21C36E80BCE7B804F0A45A6AF8FD9F07E0151923D40EAB3B40B46EFD4865CBF48464922AC2A1DF6FC5357E2A2192079D917B53823D4FF27AECB9967FD4E454E5CF9BD3E6390ACE35FE1D5D0F57BFEAEAFA866FCAE587D71C9BC4F238DB8FD22BBA125D267C; __utmb=231353987.6.10.1561031877");

            int respCode = connection.getResponseCode();
            System.out.println("Ответ сервера: " + respCode + "  - Комментарий " + count + "/" + lIst.size());

        }

    }

    private static void parseMessageCodes() throws IOException {
        User user = chooseUser();
        String nextUrl = user.getAccountAdress();

        while (!nextUrl.equals("")){
            URL url = new URL(nextUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent","Mozilla/5.0");
            String fileName = user.getResultAdress();
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

    private static User chooseUser() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/res/target"));
        System.out.println("Выберете цель:");
        Map<String, String> accs = new HashMap<>();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            accs.put((String) entry.getKey(), (String) entry.getValue());
        }
        Set<String> accKeys = accs.keySet();
        for (String key: accKeys) {
            System.out.println(key);
        }
        String choose2;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            choose2 = reader.readLine();
            if (accKeys.contains(choose2)){
                System.out.println("Ok");
                break;
            }
            else System.out.println("Нет такого аккаунта. Повторите ввод:");
        }
        reader.close();
        return new User(choose2);

    }
}
