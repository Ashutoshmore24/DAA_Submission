import java.io.*;
import java.util.*;

class Movie {
    String title;
    double rating;
    int year;
    long popularity;

    Movie(String title, double rating, int year, long popularity) {
        this.title = title;
        this.rating = rating;
        this.year = year;
        this.popularity = popularity;
    }
}

public class QuickSortMovies {

    // Read CSV file
    static List<Movie> readCSV(String file) throws Exception {
        List<Movie> movies = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;
        br.readLine(); // Skip header

        while ((line = br.readLine()) != null) {
            String[] d = line.split(",");

            if (d.length >= 4) {
                movies.add(new Movie(
                    d[0].trim(),
                    Double.parseDouble(d[1].trim()),
                    Integer.parseInt(d[2].trim()),
                    Long.parseLong(d[3].trim())
                ));
            }
        }

        br.close();
        return movies;
    }

    // Compare movies: Rating DESC, Year ASC, Popularity ASC
    static boolean before(Movie a, Movie b) {
        if (a.rating != b.rating)
            return a.rating > b.rating;

        if (a.year != b.year)
            return a.year < b.year;

        return a.popularity < b.popularity;
    }

    // Quick Sort
    static void quickSort(List<Movie> m, int low, int high) {
        if (low < high) {
            int p = partition(m, low, high);
            quickSort(m, low, p - 1);
            quickSort(m, p + 1, high);
        }
    }

    // Middle element as pivot
    static int partition(List<Movie> m, int low, int high) {

        int mid = low + (high - low) / 2;

        // Move middle element to end
        Collections.swap(m, mid, high);

        Movie pivot = m.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (before(m.get(j), pivot)) {
                i++;
                Collections.swap(m, i, j);
            }
        }

        Collections.swap(m, i + 1, high);
        return i + 1;
    }

    // Display output
    static void display(List<Movie> movies) {

        System.out.println("Quick Sort Assignment No.2 Output");
        System.out.println("SORTED MOVIES");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-30s %-13s %-20s %-15s%n",
                "Title", "Rating", "Release Year", "Popularity");
        System.out.println("--------------------------------------------------------------------------------");

        for (int i = 0; i < Math.min(10, movies.size()); i++) {
            Movie m = movies.get(i);

            System.out.printf("%-30s %-13s %-20d %-15d%n",
                    m.title,
                    m.rating == (int)m.rating ? (int)m.rating : m.rating,
                    m.year,
                    m.popularity);
        }

        System.out.println("--------------------------------------------------------------------------------");
    }

    public static void main(String[] args) {

        try {
            List<Movie> movies = readCSV("movies.csv");

            quickSort(movies, 0, movies.size() - 1);

            display(movies);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
