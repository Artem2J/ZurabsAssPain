package ru.artem2j.zurabasspain;

import java.io.*;
import java.util.List;
import java.util.Properties;

public class User {
    private String name;
    private String accountAdress;
    private String resultAdress;
    private List<String> messages;

    public String getName() {
        return name;
    }

    public String getAccountAdress() {
        return accountAdress;
    }

    public String getResultAdress() {
        return resultAdress;
    }


    public List<String> getMessages() {

        return messages;
    }

    public void setMessages() {

    }



    public User(String name) throws IOException {
        this.name = name;

        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/res/target"));
        this.accountAdress = properties.getProperty(name);
        this.resultAdress = "src/main/res/" + name + "_result.txt";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(resultAdress)));
        String buff = "";
        while ((buff = reader.readLine()) != null) messages.add(buff);
    }

    public void setResultAdress(String resultAdress) {
        this.resultAdress = resultAdress;
    }
}
