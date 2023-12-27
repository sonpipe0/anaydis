package anaydis.compression;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RunLengthEncoding implements Compressor{
    @Override
    public void encode(@NotNull InputStream input, @NotNull OutputStream output) throws IOException {
        int currentByte = -1;
        int count = 1;
        currentByte = input.read();
        while (currentByte != -1){
            int nextByte;
            while ((nextByte = input.read()) != -1) {
                if (nextByte == currentByte) count++;
                else break;
            }
            output.write(0xFF);
            output.write(currentByte);
            if(count!=1)output.write(count);
            currentByte = nextByte;
            count = 1;
        }

    }

    @Override
    public void decode(@NotNull InputStream input, @NotNull OutputStream output) throws IOException {
        int count;
        int divider = input.read();
        while (divider != -1){
            int character = input.read();
            count = input.read();
            if(count != 0xFF && count != -1) {
                for (int i = 0; i < count; i++) {
                    output.write(character);
                }
                divider = input.read();
            }
            else{
                output.write(character);
                divider = count;
            }
        }
    }
}
