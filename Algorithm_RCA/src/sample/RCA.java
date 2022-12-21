package sample;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Scanner;
import java.util.Arrays;

import static java.lang.System.out;
import static java.util.Arrays.copyOfRange;

public class RCA {

    public byte[] buffer0;

    KeyPair pair;

    byte[] cipherText;

    double TimePubEncrypt;

    double TimePrivEncrypt;

    double TimePubDecrypt;

    double TimePrivDecrypt;

    public RCA(){
    }

    public void EncryptRCAPublic() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        double startTime = System.currentTimeMillis();
        KeyPairGenerator key = KeyPairGenerator.getInstance("RSA");
        key.initialize(1024);
        pair = key.generateKeyPair();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, pair.getPublic());
        out.println(buffer0.length);
        OutputStream os = new FileOutputStream("EncrtyptRCA.txt");
        for (int i = 0; i < buffer0.length/117 + 1; i++){
        byte[] buffer1;
        if (i<buffer0.length/117){
            buffer1 = copyOfRange(buffer0, i*117, (i+1)*117);
        }else {
            buffer1 = copyOfRange(buffer0, i*117, buffer0.length);
        }
        cipher.update(buffer1);
        cipherText = cipher.doFinal();
        System.out.println("Encrypted Cipher Text Message : " + new String(cipherText, "UTF-8"));
        os.write(cipherText);
        System.out.println("Encryption is implemented successfully by Tech- Ranch...");
        }
        os.close();
        double endTime = System.currentTimeMillis();
        TimePubEncrypt = endTime - startTime;

    }

    public void DecryptRCAPrivat() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        double startTime = System.currentTimeMillis();
        out.println(pair);
        //KeyPairGenerator key = KeyPairGenerator.getInstance("RSA");
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());
        OutputStream os = new FileOutputStream("DecrtyptRCA.txt");

        for (int i = 0; i < buffer0.length/128; i++) {
            byte[] buffer1;
           /* if (i<buffer0.length/128){
                buffer1 = copyOfRange(buffer0, i*128, (i*1)*128);
            }else {
                buffer1 = copyOfRange(buffer0, i*117, buffer0.length);
            }*/
            buffer1 = copyOfRange(buffer0, i*128, (i+1)*128);
            byte[] decipheredText = cipher.doFinal(buffer1);
            System.out.println("Encrypted Cipher Text Message : " + new String(decipheredText, "UTF-8"));
            os.write(decipheredText);
            System.out.println("Encryption is implemented successfully by Tech- Ranch...");
        }
        os.close();
        double endTime = System.currentTimeMillis();
        TimePrivDecrypt = endTime - startTime;
    }

    public void EncryptRCAPrivat() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        double startTime = System.currentTimeMillis();
        KeyPairGenerator key = KeyPairGenerator.getInstance("RSA");
        key.initialize(1024);
        pair = key.generateKeyPair();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, pair.getPrivate());
        out.println(buffer0.length);
        OutputStream os = new FileOutputStream("EncrtyptRCA.txt");
        for (int i = 0; i < buffer0.length/117 + 1; i++){
            byte[] buffer1;
            if (i<buffer0.length/117){
                buffer1 = copyOfRange(buffer0, i*117, (i+1)*117);
            }else {
                buffer1 = copyOfRange(buffer0, i*117, buffer0.length);
            }
            cipher.update(buffer1);
            cipherText = cipher.doFinal();
            System.out.println("Encrypted Cipher Text Message : " + new String(cipherText, "UTF-8"));
            os.write(cipherText);
            System.out.println("Encryption is implemented successfully by Tech- Ranch...");
        }
        os.close();
        double endTime = System.currentTimeMillis();
        TimePrivEncrypt = endTime - startTime;
    }

    public void DecryptRCAPublic() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        double startTime = System.currentTimeMillis();
        out.println(pair);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, pair.getPublic());
        OutputStream os = new FileOutputStream("DecrtyptRCA.txt");

        for (int i = 0; i < buffer0.length/128; i++) {
            byte[] buffer1;
           /* if (i<buffer0.length/128){
                buffer1 = copyOfRange(buffer0, i*128, (i*1)*128);
            }else {
                buffer1 = copyOfRange(buffer0, i*117, buffer0.length);
            }*/
            buffer1 = copyOfRange(buffer0, i*128, (i+1)*128);
            byte[] decipheredText = cipher.doFinal(buffer1);
            System.out.println("Encrypted Cipher Text Message : " + new String(decipheredText, "UTF-8"));
            os.write(decipheredText);
            System.out.println("Encryption is implemented successfully by Tech- Ranch...");
        }
        os.close();
        double endTime = System.currentTimeMillis();
        TimePubDecrypt = endTime - startTime;
    }

    public void ReadFile(String path){
        try {
            try (FileInputStream fin = new FileInputStream(path)) {
                String p;
                System.out.println(path);
                buffer0 = new byte[fin.available()];
                fin.read(buffer0, 0, buffer0.length);
                fin.close();
            }


        } catch (IOException e)
        {
            e.printStackTrace();
            out.println("Error: " + e);
        }
    }


}
