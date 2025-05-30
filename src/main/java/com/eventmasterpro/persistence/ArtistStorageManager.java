package com.eventmasterpro.persistence;
import com.eventmasterpro.model.Artist;

import java.io.*;
import java.util.*;

public class ArtistStorageManager {
    //Saves a list of Artist objects to a .txt file
    public static void saveArtistList(List<Artist> artists, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Artist artist : artists) {
                writer.write(artist.toFileString());
                writer.newLine();
            }
        }
    }
    //Loads a list of Artist objects from a .txt file
    public static List<Artist> loadArtistList(String filePath) throws IOException {
        List<Artist> artists = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Artist artist = Artist.fromFileString(line);
                artists.add(artist);
            }
        }
        return artists;
    }
}
