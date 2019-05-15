package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
/**
 * Client MyCompressorOutputStream extends OutputStream
 * Compressor byte array -
 * Take each time 8 char`s and convert them to a byte number
 */
public class MyCompressorOutputStream extends OutputStream {
    private OutputStream out;


    public MyCompressorOutputStream(OutputStream ops) {
        out = ops;
    }

    public void write(byte[] b) throws IOException {
        ArrayList<Byte> listByte = new ArrayList<>();
        byte[] tempByte = new byte[8];
        int counter , i;

        //Add the details to list:
        for (i = 0; i < 8; i++) {
            listByte.add(b[i]);
        }
        //take binary number that composed of 8 bits and change to byte
        while(i < b.length) {
            counter=0;
            while (counter < 8 && i< b.length) {
                tempByte[counter] = b[i];
                counter++;
                i++;
            }
            if (counter == 8) {
                byte byteToAdd = binaryToByte(tempByte);
                listByte.add(byteToAdd);
                //the maze is not n*n,there we will check the long of the binary we will convert to byte
            } else {
                byte[] tempSmallByte = new byte[b.length % 8];
                for (int j = 0; j < tempSmallByte.length; j++)
                    tempSmallByte[j] = tempByte[j];
                byte byteToAdd = binaryToByte(tempSmallByte);
                listByte.add(byteToAdd);
            }
        }
        //Write out
        byte[] compressed = listToByte(listByte);
        for (int k = 0; k < compressed.length; k++) {
            out.write(compressed[k]);
        }
    }


    public void write(int b) {
    }

    //Array list to Byte []
    public byte[] listToByte(ArrayList al) {
        byte[] b = new byte[al.size()];
        for (int i = 0; i < al.size(); i++) {
            b[i] = (byte) al.get(i);
        }
        return b;
    }

    // take 8 and convert
    private byte binaryToByte(byte[] binaryArray) {
        int byteNum = 0;
        double power = 0;
        for (int i = binaryArray.length - 1; i >= 0; i--) {
            byteNum = byteNum + binaryArray[i] * (int) Math.pow(2, power);
            power++;
        }
        return (byte) byteNum;
    }

}
