package anaydis.compression;

import org.junit.Assert;
import org.junit.Test;


import java.io.*;
import java.security.SecureRandom;


import static anaydis.bit.BitsStringifier.bytesToString;
import static anaydis.compression.Compressor.MEGA;
import static org.assertj.core.api.Assertions.assertThat;

public class CompressionTest {

    @Test
    public void testRunLengthEncoding() throws IOException {
        String inputString = "AAAABBBCCDAA";
        byte[] inputBytes = inputString.getBytes();
        byte[] compressedBytes = compressInput(inputBytes);
        System.out.println(new String(compressedBytes));
        byte[] decompressedBytes = decompressInput(compressedBytes);
        String originalText = new String(inputBytes);
        String decompressedText = new String(decompressedBytes);

        System.out.println("Original Text: " + originalText);
        System.out.println("Decompressed Text: " + decompressedText);

        Assert.assertEquals(originalText, decompressedText);

    }

    private byte[] compressInput(byte[] input) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        RunLengthEncoding compressor = new RunLengthEncoding();
        compressor.encode(inputStream, outputStream);
        return outputStream.toByteArray();
    }

    private byte[] decompressInput(byte[] input) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        RunLengthEncoding compressor = new RunLengthEncoding();
        compressor.decode(inputStream, outputStream);
        return outputStream.toByteArray();
    }

    @Test
    public void testHEncoding() throws IOException {
        byte[] inputBytes = {97, 97, 97, 97, 97, 97, 97, 97, 97, 32, 97, 109, 97, 115, 97, 32, 97, 109, 97, 115, 97, 32, 97, 109, 97, 115, 97};
        System.out.println("Uncompressed text: "+ bytesToString(inputBytes));
        byte[] compressedBytes = compressInputH(inputBytes);
        System.out.println("Compressed text: "+ bytesToString(compressedBytes));
        System.out.println(new String(compressedBytes));
        byte[] decompressedBytes = decompressInputH(compressedBytes);
        String originalText = new String(inputBytes);
        String decompressedText = new String(decompressedBytes);

        System.out.println("Original Text: " + originalText);
        System.out.println("Decompressed Text: " + decompressedText);

        Assert.assertEquals(originalText, decompressedText);

    }

    private byte[] compressInputH(byte[] input) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Huffman compressor = new Huffman();
        compressor.encode(inputStream, outputStream);
        return outputStream.toByteArray();
    }

    private byte[] decompressInputH(byte[] input) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Huffman compressor = new Huffman();
        compressor.decode(inputStream, outputStream);
        return outputStream.toByteArray();
    }
    public void testCompressorWithSmallText(Compressor compressor) throws IOException {
        String small_text = generateRandomString(125);
        final byte[] inputBytes = small_text.getBytes();
        System.out.println("About to test " + compressor.getClass().getSimpleName() + " with small text.");
        System.out.println("Input small text with bytes: " + bytesToString(inputBytes));
        final ByteArrayOutputStream encoded = new ByteArrayOutputStream(MEGA);
        compressor.encode(new ByteArrayInputStream(inputBytes), encoded);
        System.out.println(encoded);
        final byte[] encodedBytes = encoded.toByteArray();
        System.out.println("Encoded small text with bytes: " + bytesToString(encodedBytes));
        final ByteArrayOutputStream output = new ByteArrayOutputStream(MEGA);
        compressor.decode(new ByteArrayInputStream(encodedBytes), output);
        final byte[] outputBytes = output.toByteArray();
        System.out.println("Output small text with bytes: " + bytesToString(outputBytes));
        System.out.println(output);
        assertThat(outputBytes).isEqualTo(inputBytes);
    }

    @Test
    public void testBurrows() throws IOException{
        for (int i=0;i<45;i++){
        testCompressorWithSmallText(new Huffman());}
    }


    public static String generateRandomString(int length) {
        SecureRandom random = new SecureRandom();
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }
}
