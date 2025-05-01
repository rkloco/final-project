import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Initialize the AIDS implementation
        AIDS aidsData = new AIDSImpl();
        
        // Load data from CSV file
        aidsData.loadData("aids.csv");

        // Example 1: Find deaths for a specific country (indexed query)
        System.out.println("Deaths in Georgia:");
        List<AIDSDeathRecord> georgiaDeaths = aidsData.findDeaths("country", "Georgia");
        georgiaDeaths.forEach(System.out::println);

        // Example 2: Find deaths in a year range for a country
        System.out.println("\nDeaths in Libya between 2010 and 2015:");
        List<AIDSDeathRecord> libyaDeaths = aidsData.findDeathsInYearRange("Libya", 2010, 2015);
        libyaDeaths.forEach(System.out::println);

        // Example 3: Calculate average deaths per year for a country
        System.out.println("\nAverage deaths per year in Armenia from 2018 to 2020:");
        List<AIDSDeathRecord> armeniaAvg = aidsData.averageDeathsPerYear("Armenia", 2018, 2020);
        armeniaAvg.forEach(System.out::println);

        // Example 4: Find records with specific adult deaths (non-indexed query)
        System.out.println("\nRecords with 100 adult deaths in 2020:");
        List<AIDSDeathRecord> adultDeaths = aidsData.findDeaths("adultdeaths", 100);
        adultDeaths.stream()
                  .filter(record -> record.getYear() == 2020)
                  .forEach(System.out::println);

        // Example 5: Find records with specific children deaths (non-indexed query)
        System.out.println("\nRecords with 100 children deaths in Libya:");
        List<AIDSDeathRecord> childrenDeaths = aidsData.findDeaths("childrendeaths", 100);
        childrenDeaths.stream()
                     .filter(record -> record.getCountry().equals("Libya"))
                     .forEach(System.out::println);

        // Example 6: Find records with specific female adult deaths (non-indexed query)
        System.out.println("\nRecords with 100 female adult deaths and 1000+ AIDS orphans:");
        List<AIDSDeathRecord> femaleDeaths = aidsData.findDeaths("femaleadultdeaths", 100);
        femaleDeaths.stream()
                   .filter(record -> record.getAidsOrphans() >= 1000)
                   .forEach(System.out::println);
    }
} 