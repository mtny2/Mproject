package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class MyCompressorOutputStream extends OutputStream {
    private OutputStream out;


    public MyCompressorOutputStream(OutputStream OPS) {

        out = OPS;
    }


    public void write(int b) {
    }


    private byte BinaryToDecimal(byte[] ArryToConvert) {
        int intNum = 0;
        double power
                = 0;
        for (int i = ArryToConvert.length - 1; i >= 0; i--) {
            intNum = intNum + ArryToConvert[i] * (int) Math.pow(2, power);
            power++;
        }
        return (byte) intNum;
    }

//        ArrayList<Byte> temp = new ArrayList<>();
//
//        int j = 8;//where the maze's values start
//        byte[] bitSend = new byte[8];
//        while (j < b.length) {
//            int count = 0;
//            int tempSize;
//            while (count < 8 && j < b.length) { //send first 8 details
//                bitSend[count] = b[j];
//                j++;
//                count++;
//            }
//            if (count == 8)
//                temp.add(BinaryToDecimal(bitSend));
//            else
//            {//when last 8 bytes are less then 8
//                tempSize = b.length % 8;
//                byte[] bitSend2 = new byte[tempSize];
//                for (int i = 0; i < bitSend2.length; i++)
//                    bitSend2[i] = bitSend[i];
//                temp.add(BinaryToDecimal(bitSend2));
//            }
//        }
//        byte[] compressedMaze = new byte[8 + temp.size()];
//        int copy = 0;
//        for (; copy < 8; copy++)
//            compressedMaze[copy] = b[copy];//8 cells for maze's info
//        while (temp.size() != 0) {
//            compressedMaze[copy] = temp.remove(0);
//            copy++;
//        }
//        try {
//            for (int i = 0; i < compressedMaze.length; i++)
//                out.write(compressedMaze[i]);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
public void write(byte[] b) {

    ArrayList<Byte> temp = new ArrayList<>();

    byte [] aryToConvert = new byte8];
    int counter = 0;
    for (int i = 8; i < b.length; i++) {
        if (counter < 8) {
            aryToConvert[counter] = b[i];
            counter++;
        } else if (counter == 8) {
            int decimalNum = BinaryToDecimal(aryToConvert);
            temp.add(decimalNum);
            counter = 0;
            aryToConvert[counter] = b[i];
        } else {
            int tempSize = b.length % 8;
            byte[] aryToConvertLast = new byte[tempSize];
            for (int k = 0; k < bitSend2.length; k++)
                aryToConvertLast[i] = (byte) aryToConvert[i];
            temp.add(BinaryToDecimal(aryToConvertLast));
        }

    }
}}








