package io;

import java.util.*;
import java.io.*;
/**Абстрактный класс чтения введённой или записанной в файле информации*/
public abstract class InputReader {
    protected boolean askForInput;

    public boolean getAskForInput() {
        return askForInput;
    }

    protected List<String> additionalProperties = new ArrayList<>();

    public abstract List<String> getInput();

    public abstract String getRawInput();

    public void addProperties(String... prop) {
        additionalProperties.clear();
        additionalProperties.addAll(List.of(prop));
    }
    }
