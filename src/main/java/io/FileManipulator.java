package io;

import collection.DAO;
import collection.Describable;
import com.fasterxml.jackson.databind.JsonNode;
import collection.DragonDAO;
import exceptions.SavedToTmpFileException;
import json.Json;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**Класс, реализующий методы сохранения данных в файл и получение данных из файла для дальнейшего взаимодействия с ними*/
@Deprecated
public final class FileManipulator {
    private static final String directory = System.getenv().get("DAO_COLLECTION_FILEPATH");

    /**
     * Метод сохранения коллекции в файл
     * @param dao - коллекция
     * */
    public static void save(Describable dao) {
        try {
            save(dao, "dragons.json");
        }  catch (IOException e) {
            String name = saveToTmp(dao);
            throw new SavedToTmpFileException("%s Коллекция была сохранена во временный файл %s".formatted(e.getMessage(), name));
        }
    }
    /**
     * Метод возвращения коллекции, считанной из файла
     * @return new collection.DragonDAO() - коллекция, считанная из файла
     * */
    public static DAO get() {
        try {
            return get("dragons.json");
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("Файл не найден\\отказано в доступе (%s). Была инициализирована пустая коллекция".formatted(e.getMessage()));
        }
        catch (IOException | RuntimeException e) {
            throw new RuntimeException("Значения файла JSON были изменены вручную, что привело к ошибке. Была инициализирована пустая коллекция.");
        }
    }


    private static void save(Describable dao, String fileName) throws IOException{
        String filepath = directory + fileName;

        try (FileOutputStream stream = new FileOutputStream(filepath); OutputStreamWriter writer = new OutputStreamWriter(stream)) {
            String description = dao.description();
            writer.write(description);
        }
    }

    private static String saveToTmp(Describable dao) {
        List<File> tmpFiles = getTmpFiles();

        String tmpToSave = null;

        for (File f: tmpFiles) {
            if (f.canWrite()) {
                tmpToSave = f.getName();
                break;
            }
        }

        if (tmpToSave == null)
            tmpToSave = createTmpFile();
        try {
            save(dao, tmpToSave);
        } catch (IOException e){
            //..
        }
        return tmpToSave;
    }

    private static DAO get(String fileName) throws IOException{
        String filepath = directory + fileName;

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             FileInputStream fileInputStream = new FileInputStream(filepath);
             BufferedInputStream inputStream = new BufferedInputStream(fileInputStream)) {

            int nextByte;
            while ((nextByte = inputStream.read()) != -1)
                bos.write((char) nextByte);

            String input = bos.toString();
            JsonNode node = Json.parse(input);
            DragonDAO out = Json.fromJson(node, DragonDAO.class);
            return out;
        }
    }


    public static List<File> getTmpFiles() {
        File dir = new File(directory);
        return Arrays.stream(dir.listFiles()).filter(File::isFile).filter(f -> f.getName().contains(".tmp")).toList();
    }

    public static String createTmpFile() {
        String name = generateTmpFileName();
        File dir = new File(directory, name);
        try {
            dir.createNewFile();
            return name;
        } catch (IOException e) {
            //..
        }
        return null;
    }

    private static String generateTmpFileName() {
        Random r = new Random(System.currentTimeMillis());
        List<File> tmpFiles = getTmpFiles();
        while (true) {
            String fileName = "%d.tmp".formatted(Math.abs(r.nextLong()));
            if (tmpFiles.stream().noneMatch(f -> f.getName().contains(fileName)))
                return fileName;
        }
    }
}
