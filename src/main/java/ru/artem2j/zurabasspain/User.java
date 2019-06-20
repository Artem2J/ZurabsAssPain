package ru.artem2j.zurabasspain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class User {
    private String name;

    public String getName() {
        return name;
    }

    public String getAccountAdress() {
        return accountAdress;
    }

    public String getResultAdress() {
        return resultAdress;
    }

    private String accountAdress;
    private String resultAdress;

    public User(String name) throws IOException {
        this.name = name;

        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/res/target"));
        this.accountAdress = properties.getProperty(name);
        this.resultAdress = "src/main/res/" + name + "_result.txt";
    }

    public void setResultAdress(String resultAdress) {
        this.resultAdress = resultAdress;
    }
}
