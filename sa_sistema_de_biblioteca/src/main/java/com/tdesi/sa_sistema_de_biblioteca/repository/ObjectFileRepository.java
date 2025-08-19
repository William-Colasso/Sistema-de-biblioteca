package com.tdesi.sa_sistema_de_biblioteca.repository;

import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public class ObjectFileRepository<TypeId, T> {

    
    private final String filePath; // Caminho do arquivo onde serão armazenados os objetos em formato JSON
    private final Gson gson; // Biblioteca para conversão de objetos <-> JSON
    private final Class<T> typeClass; // Classe do tipo armazenado
    private final String idFieldName; // Nome do campo que é usado como ID na entidade

    // Construtor: recebe a classe e o nome do campo que serve como ID
    public ObjectFileRepository(Class<T> typeClass, String idFieldName) {
        this.filePath = typeClass.getSimpleName() + ".txt"; // Nome do arquivo baseado no nome da classe
        this.gson = new Gson();
        this.typeClass = typeClass;
        this.idFieldName = idFieldName;

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile(); // Cria o arquivo se ele não existir
            }
        } catch (IOException e) {
            // Converte a exceção checked em unchecked para simplificar o uso no Spring
            throw new UncheckedIOException("Não foi possível criar/abrir o arquivo: " + filePath, e);
        }
    }

    // Salva ou atualiza um objeto no arquivo
    public T save(T entity) throws IOException {
        List<T> all = findAll(); // Lê todos os registros atuais
        TypeId idValue = getIdValue(entity); // Obtém o valor do ID do objeto

        // Se não houver ID ou ele for 0 gera um novo
        if (idValue == null || (idValue instanceof Number && ((Number) idValue).longValue() == 0)) {
            TypeId nextId = generateNextId(all);
            setIdValue(entity, nextId);
        } else {
            // Se o ID já existe, remove o objeto antigo para atualizar
            all.removeIf(e -> Objects.equals(getIdValue(e), idValue));
        }

        all.add(entity); // Adiciona o novo/atualizado
        writeAll(all);   // Salva toda a lista no arquivo
        return entity;
    }

    // Gera o próximo ID disponível para IDs numéricos
    private TypeId generateNextId(List<T> list) {
        long maxId = 0;
        for (T e : list) {
            if (e == null) continue;
            TypeId currentId = getIdValue(e);
            if (currentId != null) {
                long val = ((Number) currentId).longValue();
                if (val > maxId) {
                    maxId = val;
                }
            }
        }
        long next = maxId + 1; // Próximo ID

        // Retorna o tipo correto (Integer ou Long)
        if (Number.class.isAssignableFrom(getIdValueClass())) {
            if (getIdValueClass() == Integer.class)
                return (TypeId) Integer.valueOf((int) next);
            if (getIdValueClass() == Long.class)
                return (TypeId) Long.valueOf(next);
        }
        throw new RuntimeException("Tipo de ID não suportado para auto-incremento: " + getIdValueClass());
    }

    // Define o valor do ID no objeto usando reflexão
    private void setIdValue(T entity, TypeId id) {
        try {
            Field field = typeClass.getDeclaredField(idFieldName);
            field.setAccessible(true);
            field.set(entity, id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao definir campo ID", e);
        }
    }

    // Obtém a classe do campo ID (ex: Integer.class, Long.class)
    private Class<?> getIdValueClass() {
        try {
            Field field = typeClass.getDeclaredField(idFieldName);
            return field.getType();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Campo ID não encontrado", e);
        }
    }

    // Busca um objeto pelo ID
    public Optional<T> findById(TypeId id) throws IOException {
        return findAll().stream()
                .filter(e -> Objects.equals(getIdValue(e), id))
                .findFirst();
    }

    // Retorna todos os objetos salvos no arquivo
    public List<T> findAll() throws IOException {
        List<T> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Lê cada linha do arquivo e converte para objeto
            while ((line = br.readLine()) != null) {
                list.add(gson.fromJson(line, typeClass));
            }
        }
        return list;
    }

    // Exclui um objeto pelo ID
    public void deleteById(TypeId id) throws IOException {
        List<T> all = findAll();
        all.removeIf(e -> Objects.equals(getIdValue(e), id)); // Remove o item com o ID correspondente
        writeAll(all); // Reescreve o arquivo com a lista atualizada
    }

    // Sobrescreve o arquivo com a lista completa de objetos
    private void writeAll(List<T> entities) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false))) {
            for (T entity : entities) {
                bw.write(gson.toJson(entity)); // Converte objeto para JSON
                bw.newLine(); // Nova linha para cada objeto
            }
        }
    }

    // Obtém o valor do ID de um objeto usando reflexão
    private TypeId getIdValue(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("O objeto a ser salvo não pode ser null");
        }
        try {
            Field field = typeClass.getDeclaredField(idFieldName);
            field.setAccessible(true);
            return (TypeId) field.get(entity);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao acessar campo ID", e);
        }
    }

    // Verifica se existe um objeto com o ID informado usando QuickSort + Binary Search
    public boolean existsById(TypeId id) throws IOException {
        List<T> list = findAll();
        quickSort(list, 0, list.size() - 1); // Ordena a lista
        return binarySearch(list, id); // Busca binária no array ordenado
    }

    // Algoritmo de ordenação QuickSort (ordena pelo ID)
    private void quickSort(List<T> list, int low, int high) {
        if (low < high) {
            int pi = partition(list, low, high);
            quickSort(list, low, pi - 1);
            quickSort(list, pi + 1, high);
        }
    }

    // Função de partição do QuickSort
    private int partition(List<T> list, int low, int high) {
        TypeId pivot = getIdValue(list.get(high));
        int i = low - 1;
        for (int j = low; j < high; j++) {
            Comparable<TypeId> currentId = (Comparable<TypeId>) getIdValue(list.get(j));
            if (currentId.compareTo(pivot) <= 0) {
                i++;
                Collections.swap(list, i, j);
            }
        }
        Collections.swap(list, i + 1, high);
        return i + 1;
    }

    // Algoritmo de busca binária (Binary Search) para encontrar um ID
    private boolean binarySearch(List<T> list, TypeId id) {
        int left = 0, right = list.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            Comparable<TypeId> midId = (Comparable<TypeId>) getIdValue(list.get(mid));
            int cmp = midId.compareTo(id);

            if (cmp == 0)
                return true;
            if (cmp < 0)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return false; // Não encontrado
    }
}
