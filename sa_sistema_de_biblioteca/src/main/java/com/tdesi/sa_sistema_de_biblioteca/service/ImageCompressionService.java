package com.tdesi.sa_sistema_de_biblioteca.service;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class ImageCompressionService {

    public String compressBase64(String base64) {
        if (base64 == null || base64.isEmpty()) return base64;

        try {
            byte[] input = Base64.getDecoder().decode(base64);

            Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
            deflater.setInput(input);
            deflater.finish();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(input.length);
            byte[] buffer = new byte[1024];

            while (!deflater.finished()) {
                int count = deflater.deflate(buffer);
                outputStream.write(buffer, 0, count);
            }

            outputStream.close();
            byte[] compressedBytes = outputStream.toByteArray();

            return Base64.getEncoder().encodeToString(compressedBytes);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao comprimir Base64", e);
        }
    }

    public String decompressBase64(String compressedBase64) {
        if (compressedBase64 == null || compressedBase64.isEmpty()) return compressedBase64;

        try {
            byte[] input = Base64.getDecoder().decode(compressedBase64);

            Inflater inflater = new Inflater();
            inflater.setInput(input);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(input.length);
            byte[] buffer = new byte[1024];

            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }

            outputStream.close();
            byte[] decompressedBytes = outputStream.toByteArray();

            return Base64.getEncoder().encodeToString(decompressedBytes);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao descomprimir Base64", e);
        }
    }
}