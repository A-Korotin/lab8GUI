package io;

import com.fasterxml.jackson.core.JsonProcessingException;
import dragon.Dragon;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
@Deprecated
public class ServerOutput implements OutPutter{
    private final List<String> list = new ArrayList<>();

    @Override
    public void output(Dragon dragon) {
        try {
            list.add(dragon.description());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T extends Number> void output(T t) {
        list.add(String.valueOf(t));
    }

    @Override
    public void output(String msg) {
        list.add(msg);
    }


    public static int getMemoryLength(Object object) throws java.io.IOException
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(stream);

        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
        objectOutputStream.close();
        stream.close();

        return stream.toByteArray().length;
    }

    @Override
    public List<String> compound() {
        List<String> listOfString = new ArrayList<>();
        String result = "";
        String bigString = "";
        for (String element : list){
            bigString = bigString + element;
        }
        char[] arrayString = bigString.toCharArray();
        for(int i = 0; i < arrayString.length; i++){
            result = result + arrayString[i];
            if (i % 10000 == 0 || i == arrayString.length - 1){
                listOfString.add(result);
                result = "";
            }
        }

        list.clear();
        return listOfString;
    }
}
