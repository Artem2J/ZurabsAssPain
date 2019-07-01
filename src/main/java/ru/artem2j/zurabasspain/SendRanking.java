package ru.artem2j.zurabasspain;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SendRanking implements Runnable{

    Trend trend;
    User targetUser;

    public SendRanking(User targetUser, Trend trend) {

        this.targetUser = targetUser;
        this.trend = trend;
    }

    public static void sendRanking(String editRait) throws IOException {






    }

    @Override
    public void run() {
        System.out.println("Доступно " + targetUser.getMessages().size() + " комментариев для оценки");
        System.out.println("Введите число оцениваемых комментариев");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int maxCount = 0;
        try {
            maxCount = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int count = 0;
        for (String messageCode : targetUser.getMessages()) {
            count++;
            URL url = null;
            try {
                url = new URL("https://myslo.ru/raiting/" + trend + "?type=comment&id=" + messageCode + "&r=0&X-Requested-With=XMLHttpRequest");
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                connection.setRequestProperty("Referer", targetUser.getAccountAdress());
                connection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
                connection.setRequestProperty("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
                connection.setRequestProperty("Cookie", "ASP.NET_SessionId=qqdms53egj0bcflrosz1qk2k; rheftjdd=rheftjddVal; _ga=GA1.2.2071530158.1547180792; _ym_uid=1547180792673566375; _ym_d=1547180792; __utmc=231353987; _grf_vis=1; __RequestVerificationToken=X1azfOXHscoxH8QS8Q7x2iPUlTXg8n0DJfasw_owsF8yiFYTl69j3JKpnHPd0xWftYGzCyK_sZb8I9xlyOLAA4L4Pzs1; __utmz=231353987.1557464952.7.2.utmcsr=yxnews|utmccn=(not%20set)|utmcmd=desktop; _gid=GA1.2.383040985.1560482317; _ym_isad=2; .ASPXAUTH=19D523A5DC98A7571CBC477C9B13491B933D3B68AD9C374489FF44FC44F41F96F3124449BA1E89D91E28A82DA5D822952DEC410C70D99AED42251D2B708B328F51E3F3909BDFCC249D49ED4E92E4953D79567CEB256F54C13F678B2C3CD8E0D86520AE1DE779701872469930F84660B9B115B5F4D13008913E559A3937F567E506B1F980B319BCF8A7334E117F0CC15056C01648F3122B076D13DB746B9145F1F4E6664D228157AF353665BD9FB0DFDCE738BAF6CABD8AED1F3D9E90A5CD54C438D4972BE508CE143C038A9DCE7570A9678083F60AC00E2D46C0FB8328E3B197B42D3D6212F8F21395FD0D440B42DDDBA6655F4B5BCC40AE212ACACC230DE5EA8061D46AD98D0DBBF69A236CE84F49AAB19EF8B3B82FF35EDD4931CA0A2506DDE156253927D877244B5E47EC93A62A845F7AEF807C86C544506D5584DB063940A7DC1AD076C62C22980F12620B40BAC6EF275877BB7FC88B4F5B40B440B010B882475E60; __utma=231353987.2071530158.1547180792.1561355341.1561358318.88; _ym_visorc_21247984=w; __utmt=1; _gat_UA-38715892-1=1; _gat=1; __utmb=231353987.34.10.1561358318");
                int respCode = connection.getResponseCode();
                System.out.println("Ответ сервера: " + respCode + "  - Комментарий " + count + "/" + targetUser.getMessages().size());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (count >= maxCount) break;
        }
    }
}