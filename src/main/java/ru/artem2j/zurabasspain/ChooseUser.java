package ru.artem2j.zurabasspain;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ChooseUser {
    public static User chooseUser() throws IOException {
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
