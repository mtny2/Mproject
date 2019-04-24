package IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MyDecompressorInputStream extends InputStream {

    private InputStream in;

    public MyDecompressorInputStream(InputStream IPS) {
        in = IPS;
    }

    public int read(byte[] b) {
        ArrayList<Byte> fromFile = new ArrayList<>();
        try {
            while (in.available() > 0)
                fromFile.add((byte) in.read());//reading from the compress file
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 8; i++)
            b[i] = fromFile.remove(0);
        byte[] byteArrayFinal = new byte[b.length];
        int beginFrom = 8;
        while (fromFile.size() != 0) {
            byte toBinary = fromFile.remove(0);
            byte[] binaryValues = ByteToBinary(toBinary);
            for (int j = 0; j < 8; beginFrom++) {
                byteArrayFinal[beginFrom] = binaryValues[j];
                j++;
            }
            if (fromFile.size() == 1) {
                if (byteArrayFinal.length % 8 != 0) {//the maze is not n*n
                    toBinary = fromFile.remove(0);
                    byte[] lastBinaryValues = ByteToBinary(toBinary);
                    for (int j = 8 - byteArrayFinal.length % 8; j < 8; beginFrom++) {
                        byteArrayFinal[beginFrom] = lastBinaryValues[j];
                        j++;
                    }
                }
            }
        }
        for (int f = 8; f < byteArrayFinal.length; f++)
            b[f] = byteArrayFinal[f];//copy to original array
        return 0;
    }


    public int read() {
        return 0;
    }

    // gets byte bet it in a binary vector
    private byte[] ByteToBinary(int b) {
        int Byte = b;
        if (Byte < 0)
            Byte += 256;
        byte[] binaryVec = new byte[8];
        for (int i = 7; i >= 0; i--) {
            binaryVec[i] = (byte) (Byte % 2);
            Byte = Byte / 2;
        }
        return binaryVec;
    }
}
