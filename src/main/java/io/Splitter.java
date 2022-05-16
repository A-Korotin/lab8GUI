package io;

import java.util.List;

public final class Splitter {
    public static List<String> splitToChunks(String source, int chunkSize) {
        // do not touch this :)
        return List.of(source.split("(?<=\\G.{" + chunkSize + "})"));
    }
}
