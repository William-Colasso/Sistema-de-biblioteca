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

        public ObjectFileRepository(Class<T> typeClass, String idFieldName) {
            this.filePath = typeClass.getSimpleName() + ".txt";
            this.gson = new Gson();
            this.typeClass = typeClass;
            this.idFieldName = idFieldName;
    
            try {
                File file = new File(filePath);
                if (!file.exists()) {
                    file.createNewFile();
                }
            } catch (IOException e) {
                // converte checked em unchecked para não forçar classes filhas / Spring a lidarem com checked
                throw new UncheckedIOException("Não foi possível criar/abrir o arquivo: " + filePath, e);
            }
        }
    

        public T save(T entity) throws IOException {
            List<T> all = findAll();
            TypeId idValue = getIdValue(entity);
        
            if (idValue == null || (idValue instanceof Number && ((Number) idValue).longValue() == 0)) {
                // Atribuir novo ID apenas se não houver ID definido
                TypeId nextId = generateNextId(all);
                setIdValue(entity, nextId);
            } else {
                // Se já existir, atualizar
                all.removeIf(e -> Objects.equals(getIdValue(e), idValue));
            }
        
            all.add(entity);
            writeAll(all);
            return entity;
        }
        
        // ---------- Gerar próximo ID ----------
        // Assume que TypeId é algum tipo numérico como Integer ou Long
        private TypeId generateNextId(List<T> list) {
            long maxId = 0;
            for (T e : list) {
                if (e == null) continue; // Ignorar elementos null
                TypeId currentId = getIdValue(e);
                if (currentId != null) {
                    long val = ((Number) currentId).longValue();
                    if (val > maxId) {
                        maxId = val;
                    }
                }
            }
            long next = maxId + 1;
            if (Number.class.isAssignableFrom(getIdValueClass())) {
                if (getIdValueClass() == Integer.class) return (TypeId) Integer.valueOf((int) next);
                if (getIdValueClass() == Long.class) return (TypeId) Long.valueOf(next);
            }
            throw new RuntimeException("Tipo de ID não suportado para auto-incremento: " + getIdValueClass());
        }
        
        // Método auxiliar para definir ID via reflexão
        private void setIdValue(T entity, TypeId id) {
            try {
                Field field = typeClass.getDeclaredField(idFieldName);
                field.setAccessible(true);
                field.set(entity, id);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao definir campo ID", e);
            }
        }
        
        // Descobrir a classe do campo ID
        private Class<?> getIdValueClass() {
            try {
                Field field = typeClass.getDeclaredField(idFieldName);
                return field.getType();
            } catch (NoSuchFieldException e) {
                throw new RuntimeException("Campo ID não encontrado", e);
            }
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

        public boolean existsById(TypeId id) throws IOException {
            List<T> list = findAll();
            System.out.println(list);
            // Ordenar lista usando QuickSort
            quickSort(list, 0, list.size() - 1);
            System.out.println(list);
            System.out.println(binarySearch(list, id));
            // Buscar usando Binary Search
            return binarySearch(list, id);
        }

        // ---------- Algoritmo de Ordenação (QuickSort) ----------
        private void quickSort(List<T> list, int low, int high) {
            if (low < high) {
                int pi = partition(list, low, high);
                quickSort(list, low, pi - 1);
                quickSort(list, pi + 1, high);
            }
        }

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

        // ---------- Algoritmo de Busca (Binary Search) ----------
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
            return false;
        }
    }