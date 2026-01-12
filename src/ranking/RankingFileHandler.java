package ranking;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RankingFileHandler {
    // atributo

    private static final String FILEMANE = "ranking.txt";

    // métodos
    
    public void save(List<RankingEntry> ranking) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILEMANE))) {
            for (RankingEntry entry : ranking)
                writer.println(entry.getName() + ";" + entry.getScore());
        } catch (IOException e) {
            System.err.println("ERROR IN SAVING FILE: " + e.getMessage());
        }
    }

    public List<RankingEntry> load() {
        List<RankingEntry> list = new ArrayList<>();
        File file = new File(FILEMANE);
        if (!file.exists())
            return list;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILEMANE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2)
                    list.add(new RankingEntry(parts[0], Integer.parseInt(parts[1])));
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("ERROR IN LOADING FILE: " + e.getMessage());
        }
        return list;
    }
}
