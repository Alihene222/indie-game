package com.alihene.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Util {
    public static float differenceBetween(float f1, float f2) {
        float result = 0;

        if(f1 <= f2){
            result = f2 - f1;
        } else {
            result = f1 - f2;
        }

        return result;
    }

    public static String readFile(String file) {
        StringBuilder text = new StringBuilder();

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = reader.readLine()) != null) {
                text.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return text.toString();
    }

    public boolean randomChanceOneIn(int range) {
        return ThreadLocalRandom.current().nextInt(range) == 0;
    }
}
