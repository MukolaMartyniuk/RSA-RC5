package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Controller {

    @FXML
    private TextField TF_Path_RC5;

    @FXML
    private TextField TF_Key_RC5;

    @FXML
    private TextField TF_Path_RCA;

    @FXML
    private Button btn_Decrypt_RC5;

    @FXML
    private Button btn_Encrypt_RC5;

    @FXML
    private Button btn_Search_RC5;

    @FXML
    private Button btn_Search_RCA;

    @FXML
    private Button btn_Decrypt_RCA;

    @FXML
    private Button btn_Encrypt_RCA;

    @FXML
    private Button btn_DecryptPriv_RCA;

    @FXML
    private Button btn_EncryptPriv_RCA;

    @FXML
    private TextArea TA_Time;

    public String path;

    public String path1;

    byte[] K;

    RCA rca = new RCA();

    RC5 rc5 = new RC5();

    @FXML
    void initialize() {



        //Діалогове вікно файлу RC5
        btn_Search_RC5.setOnAction(actionEvent -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("My File Chooser");
            File f = fc.showOpenDialog(null);

            if (f != null)
            {
                TF_Path_RC5.setText(f.getAbsolutePath());
                path = f.getAbsolutePath();
            }
        });



        //Діалогове вікно файлу RCA
        btn_Search_RCA.setOnAction(actionEvent -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("My File Chooser");
            File f = fc.showOpenDialog(null);

            if (f != null)
            {
                TF_Path_RCA.setText(f.getAbsolutePath());
                path1 = f.getAbsolutePath();
            }
        });

        //Шифрування за допомогою RC5
        btn_Encrypt_RC5.setOnAction(actionEvent -> {


            //Хешування ключа
            String s = TF_Key_RC5.getText();
            K = rc5.computeMD5(s.getBytes(StandardCharsets.UTF_8));
            rc5.setKey(K);
            //Зчитування з файлу
            rc5.ReadFile(path);
            //Шифрування файлу
            rc5.EncryptCode();
            //Запис в шифрований файл
            try {
                rc5.WriteFileEnc();
            } catch (IOException e) {
                e.printStackTrace();
            }
            TA_Time.appendText("RC5 Encrypt \n");
            TA_Time.appendText(rc5.TimeEncryptCode() + "ns\n");
            TA_Time.appendText("\n");

        });

        btn_Decrypt_RC5.setOnAction(actionEvent -> {
            RC5 rc51 = new RC5();
            String s = TF_Key_RC5.getText();
            K = rc5.computeMD5(s.getBytes(StandardCharsets.UTF_8));
            rc51.setKey(K);
            //Зчитування з файлу
            rc5.ReadFileDec(path);
            //Розшифрування файлу
            rc5.DecryptCode();
            //Запис в шифрований файл
            try {
                rc5.WriteFileDec();
            } catch (IOException e) {
                e.printStackTrace();
            }
            TA_Time.appendText("RC5 Decrypt \n");
            TA_Time.appendText(rc5.TimeDecryptCode() + "ns\n");
            TA_Time.appendText("\n");
        });

        btn_Encrypt_RCA.setOnAction(actionEvent -> {
            rca.ReadFile(path1);

            try {
                rca.EncryptRCAPublic();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            TA_Time.appendText("RCA Public Encrypt \n");
            TA_Time.appendText(rca.TimePubEncrypt + "ms\n");
            TA_Time.appendText("\n");


        });

        btn_Decrypt_RCA.setOnAction(actionEvent -> {
            rca.ReadFile(path1);

            try {
                rca.DecryptRCAPublic();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            TA_Time.appendText("RCA Public Decrypt \n");
            TA_Time.appendText(rca.TimePubDecrypt + "ms\n");
            TA_Time.appendText("\n");
        });

        btn_DecryptPriv_RCA.setOnAction(actionEvent -> {
            rca.ReadFile(path1);

            try {
                rca.DecryptRCAPrivat();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            TA_Time.appendText("RCA Privat Decrypt \n");
            TA_Time.appendText(rca.TimePrivDecrypt + "ms\n");
            TA_Time.appendText("\n");
        });

        btn_EncryptPriv_RCA.setOnAction(actionEvent -> {
            rca.ReadFile(path1);

            try {
                rca.EncryptRCAPrivat();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            TA_Time.appendText("RCA Privat Encrypt \n");
            TA_Time.appendText(rca.TimePrivEncrypt + "ms\n");
            TA_Time.appendText("\n");
        });


    }


}
