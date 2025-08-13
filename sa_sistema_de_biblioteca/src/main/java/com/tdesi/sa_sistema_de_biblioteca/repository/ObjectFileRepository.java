package com.tdesi.sa_sistema_de_biblioteca.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ObjectFileRepository<TypeId, T> {

    private final String filePath;
    private final Gson gson;
    private final Class<T> typeClass;
    private final String idFieldName;

    public ObjectFileRepository(Class<T> typeClass, String idFieldName) throws IOException {
        this.filePath = typeClass.getSimpleName() + ".txt";
        this.gson = new Gson();
        this.typeClass = typeClass;
        this.idFieldName = idFieldName;

        // Garante que o arquivo exista
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    public void save(T entity) throws IOException {
        List<T> all = findAll();
        Optional<T> existing = findById(getIdValue(entity));

        if (existing.isPresent()) {
            // Atualizar
            all.removeIf(e -> Objects.equals(getIdValue(e), getIdValue(entity)));
        }
        all.add(entity);
        writeAll(all);
    }

    public Optional<T> findById(TypeId id) throws IOException {
        return findAll().stream()
                .filter(e -> Objects.equals(getIdValue(e), id))
                .findFirst();
    }

    public List<T> findAll() throws IOException {
        List<T> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(gson.fromJson(line, typeClass));
            }
        }
        return list;
    }

    public void deleteById(TypeId id) throws IOException {
        List<T> all = findAll();
        all.removeIf(e -> Objects.equals(getIdValue(e), id));
        writeAll(all);
    }

    private void writeAll(List<T> entities) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false))) {
            for (T entity : entities) {
                bw.write(gson.toJson(entity));
                bw.newLine();
            }
        }
    }

    private TypeId getIdValue(T entity) {
        try {
            Field field = typeClass.getDeclaredField(idFieldName);
            field.setAccessible(true);
            return (TypeId) field.get(entity);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao acessar campo ID", e);
        }
    }
}