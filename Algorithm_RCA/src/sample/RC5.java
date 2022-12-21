package sample;

import java.io.*;
import java.util.Arrays;

import static java.lang.System.in;
import static java.lang.System.out;

public class RC5 {
    private static final int   INIT_A     = 0x67452301;
    private static final int   INIT_B     = (int) 0xEFCDAB89L;
    private static final int   INIT_C     = (int) 0x98BADCFEL;
    private static final int   INIT_D     = 0x10325476;
    private static final int[] SHIFT_AMTS = { 7, 12, 17, 22, 5, 9, 14, 20, 4,
            11, 16, 23, 6, 10, 15, 21    };
    private static final int[] TABLE_T    = new int[64];
    static
    {
        for (int i = 0; i < 64; i++)
            TABLE_T[i] = (int) (long) ((1L << 32) * Math.abs(Math.sin(i + 1)));
    }

    public String path;

    byte[] buffer0;

    byte[] buffer;

    byte [] intA = new byte[16];

    int diff1;

    byte[] buffer1;

    byte[] K;

    byte [] row = new byte [16];

    public  int r = 20;

    long S[];

    long S1[];

    long A = 0, B = 0;

    long A1 = 0, B1 = 0;

    long A2 = 0, B2 = 0;

    byte[] EncCod =  new byte[16];

    byte[] EncCod1 =  new byte[16];

    byte[] DecCod =  new byte[16];

    String aString = null;

    double TimeSetKey;

    double TimeEncrypt;

    double TimeDecrypt;

    double TimeMD5;

    private static final long Pw = 0xb7e151628aed2a6bL;
    private static final long Qw = 0x9e3779b97f4a7c15L;


    RC5(){
    }

    public void setKey (byte[] K)
    {
        double startTime = System.nanoTime();

        int w = 64;
        int u = w/8;
        int b = 16;
        int c = b/u;
        // c = 1

        long[] L = new long[c];
        for (int i = 0; i != c; i++)
        {
            L[i] = 0;
            for (int j = 0; j != u; j++)
                L[i] += (K[i*u + j] & 0xffL) << (8 * j);
        }


        S = new long[2*(r + 1)];
        S[0] = Pw;
        for (int i=1; i < S.length; i++)
        {
            S[i] = (S[i-1] + Qw);
        }

        // Çì³øàþòüñÿ S i L

        int iter;
        if (L.length > S.length)
        {
            iter = 3 * L.length;
        }
        else
        {
            iter = 3 * S.length;
        }


        int i = 0, j = 0;

        for (int k = 0; k < iter; k++)
        {
            A = S[i] = landslideLeft(S[i] + A + B, 3);
            B = L[j] = landslideLeft(L[j] + A + B, A+B);
            i = (i+1) % S.length;
            j = (j+1) % L.length;
        }
        double endTime = System.nanoTime();
        System.out.println("Total execution time: " + (endTime-startTime) + "ms");
        TimeSetKey = endTime-startTime;
    }



    public void EncryptCode(){
        double startTime = System.nanoTime();
        int diff = 0;
        if (buffer0.length % 16 !=0)
            diff = 16 - buffer0.length % 16;

        for(int i= 0; i < diff; i++){
            row [i] = (byte)diff; // !!!!!!!!!!!!!!
        }



        buffer = Arrays.copyOf(buffer0, buffer0.length + diff);
        System.arraycopy(row, 0, buffer, buffer0.length, diff);

        EncCod = new byte[buffer.length];


        for (int k=0; k<buffer.length / 16; k++)
        {


            A1 = BytestoLong(buffer, k*16);
            B1 = BytestoLong(buffer, k*16+8);


            A1 = A1 + S[0];
            B1 = B1 + S[1];

            for (int i = 1; i <= r; i++)
            {
                A1 = landslideLeft(A1^B1, B1) + S[2*i];
                B1 = landslideLeft(B1^A1, A1) + S[2*i+1];
            }


            LongtoByte(A1, EncCod, k*16);
            LongtoByte(B1, EncCod, k*16+8);

        }
        double endTime = System.nanoTime();
        TimeEncrypt = endTime - startTime;
    }


    public void DecryptCode(){

        double startTime = System.nanoTime();

        DecCod = new byte[buffer.length];

        for (int k=0; k<buffer1.length / 16; k++)
        {

            A2 = BytestoLong(buffer1, k*16);
            B2 = BytestoLong(buffer1, k*16+8);

            for (int i = r; i >= 1; i--)
            {
                B2 = landslideRight(B2 - S[2*i+1],A2)^ A2;
                A2 = landslideRight(A2 - S[2*i],B2)^ B2;
            }

            A2 = A2 - S[0];
            B2 = B2 - S[1];

            LongtoByte(A2, DecCod, k*16);
            LongtoByte(B2, DecCod, k*16+8);

            System.out.println();
            for (int i = k*16; i < k*16+16; i++){
                if (DecCod[i] > 16)
                    System.out.print((char) DecCod[i]);
            }

        }
        double endTime = System.nanoTime();
        TimeDecrypt = endTime - startTime;
    }


    public long landslideLeft(long x, long y)
    {
        return ((x << (y & (64-1))) | (x >>> (64 - (y & (64-1)))));
    }


    private long landslideRight(long x, long y)
    {
        return ((x >>> (y & (64-1))) | (x << (64 - (y & (64-1)))));
    }

    private long BytestoLong(byte[]  src, int  srcOff)
    {
        return (src[srcOff] & 0xffL) | ((src[srcOff + 1] & 0xffL) << 8)
                | ((src[srcOff + 2] & 0xffL) << 16) | ((src[srcOff + 3] & 0xffL) << 24) |
                ((src[srcOff + 4] & 0xffL) << 32) | ((src[srcOff + 5] & 0xffL) << 40) |
                ((src[srcOff + 6] & 0xffL) << 48) | ((src[srcOff + 7] & 0xffL) << 56);
    }

    private void LongtoByte(  long    word,
                              byte[]  dst,
                              int     dstOff)
    {
        dst[dstOff] = (byte)word;
        dst[dstOff + 1] = (byte)(word >> 8);
        dst[dstOff + 2] = (byte)(word >> 16);
        dst[dstOff + 3] = (byte)(word >> 24);
        dst[dstOff + 4] = (byte)(word >> 32);
        dst[dstOff + 5] = (byte)(word >> 40);
        dst[dstOff + 6] = (byte)(word >> 48);
        dst[dstOff + 7] = (byte)(word >> 56);
    }


    public byte[] computeMD5(byte[] message)
    {
        double startTime = System.nanoTime();
        int messageLenBytes = message.length;
        int numBlocks = ((messageLenBytes + 8) >>> 6) + 1;
        int totalLen = numBlocks << 6;
        byte[] paddingBytes = new byte[totalLen - messageLenBytes];
        paddingBytes[0] = (byte) 0x80;
        long messageLenBits = (long) messageLenBytes << 3;
        for (int i = 0; i < 8; i++)
        {
            paddingBytes[paddingBytes.length - 8 + i] = (byte) messageLenBits;
            messageLenBits >>>= 8;
        }
        int a = INIT_A;
        int b = INIT_B;
        int c = INIT_C;
        int d = INIT_D;
        int[] buffer = new int[16];
        for (int i = 0; i < numBlocks; i++)
        {
            int index = i << 6;
            for (int j = 0; j < 64; j++, index++)
                buffer[j >>> 2] = ((int) ((index < messageLenBytes) ? message[index]
                        : paddingBytes[index - messageLenBytes]) << 24)
                        | (buffer[j >>> 2] >>> 8);
            int originalA = a;
            int originalB = b;
            int originalC = c;
            int originalD = d;
            for (int j = 0; j < 64; j++)
            {
                int div16 = j >>> 4;
                int f = 0;
                int bufferIndex = j;
                switch (div16)
                {
                    case 0:
                        f = (b & c) | (~b & d);
                        break;
                    case 1:
                        f = (b & d) | (c & ~d);
                        bufferIndex = (bufferIndex * 5 + 1) & 0x0F;
                        break;
                    case 2:
                        f = b ^ c ^ d;
                        bufferIndex = (bufferIndex * 3 + 5) & 0x0F;
                        break;
                    case 3:
                        f = c ^ (b | ~d);
                        bufferIndex = (bufferIndex * 7) & 0x0F;
                        break;
                }
                int temp = b
                        + Integer.rotateLeft(a + f + buffer[bufferIndex]
                                + TABLE_T[j],
                        SHIFT_AMTS[(div16 << 2) | (j & 3)]);
                a = d;
                d = c;
                c = b;
                b = temp;
            }
            a += originalA;
            b += originalB;
            c += originalC;
            d += originalD;
        }
        byte[] md5 = new byte[16];
        int count = 0;
        for (int i = 0; i < 4; i++)
        {
            int n = (i == 0) ? a : ((i == 1) ? b : ((i == 2) ? c : d));
            for (int j = 0; j < 4; j++)
            {
                md5[count++] = (byte) n;
                n >>>= 8;
            }
        }
        double endTime = System.nanoTime();
        TimeMD5 = endTime - startTime;
        return md5;

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
        out.println(buffer0.length);
        for (int i = 0; i<buffer0.length; i++){
            out.print(buffer0[i] + " ");
        }
    }

    public void ReadFileDec(String path){
        try {
            try (FileInputStream fin = new FileInputStream(path)) {
                String p;
                System.out.println(path);
                buffer1 = new byte[fin.available()];
                fin.read(buffer1, 0, buffer1.length);
                fin.close();
            }


        } catch (IOException e)
        {
            e.printStackTrace();
            out.println("Error: " + e);
        }
        out.println(buffer1.length);
        for (int i = 0; i<buffer1.length; i++){
            out.print(buffer1[i] + " ");
        }
    }





    public void WriteFileEnc() throws IOException {
        OutputStream os = new FileOutputStream("EncryptRC5.txt");
        os.write(EncCod);
        os.close();
    }

    public void WriteFileDec() throws IOException {
        OutputStream os = new FileOutputStream("DecryptRC5.txt");
        os.write(DecCod);
        os.close();
    }

    double TimeEncryptCode(){
        return TimeSetKey + TimeEncrypt + TimeMD5;
    }

    double TimeDecryptCode(){
        return TimeSetKey + TimeDecrypt + TimeMD5;
    }

}
