package IO;

import algorithms.mazeGenerators.Maze;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {

    Maze m;
    OutputStream out;
    public MyCompressorOutputStream(FileOutputStream fileOutputStream) {
    out=fileOutputStream;

    }

    @Override
    public void write(int b) throws IOException {

    }

    @Override
    public void write(byte[] b) {

    }
}
