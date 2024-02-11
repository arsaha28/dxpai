package com.dxpai;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DxpAI {

    public static void main(String[] args) {
        System.setProperty("GOOGLE_APPLICATION_CREDENTIALS","/users/arnabsaha/secret/wishai-17ff11913377.json");
        SpringApplication.run(DxpAI.class, args);
    }
}
