package edu.cmu.cs.cs214.hw4.core;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * The json config reader.
 */
public class JSONConfigReader {
    /**
     * The json item represents the segment of different directions of a tile.
     */
    public static class JSONItem {
        private String id;
        private SegmentType up;
        private SegmentType down;
        private SegmentType left;
        private SegmentType right;
        private SegmentType center;
        private int quantity;
    }

    /**
     * An array of JSONItem.
     */
    public static class Tiles {
        private JSONItem[] tiles;
    }

    /**
     * Parses the configuration file and initializes a tile object for each tile.
     * @param configFile the configuration file path.
     * @return a list of tiles generates from the configuration file.
     */
    public static List<Tile> parse (String configFile) {
        List<Tile> tileList = new ArrayList<>();
        Gson gson = new Gson();
        try {
            Reader reader = new FileReader(configFile);
            Tiles res = gson.fromJson(reader, Tiles.class);
            for (JSONItem t : res.tiles) {
                for (int i = 0; i < t.quantity; ++i) {
                    tileList.add(new Tile(t.id, t.up, t.down, t.left, t.right, t.center));
                }
            }
            reader.close();
            return tileList;
        } catch (IOException e) {
            throw new IllegalArgumentException("Error when reading file: " + configFile, e);
        }
    }
}
