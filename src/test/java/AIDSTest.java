import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class AIDSTest {
    private AIDS aids;

    @Before
    public void setUp() {
        System.out.println("\nSetting up test environment...");
        aids = new AIDSImpl("aids.csv");
        System.out.println("Test environment ready.");
    }

    @Test
    public void testFindDeathsByCountry() {
        System.out.println("\nTesting findDeaths by country...");
        String country = "Uganda";
        List<AIDSDeathRecord> deaths = aids.findDeaths("country", country);
        
        System.out.println("Found " + deaths.size() + " records for " + country);
        assertFalse("Should find records for " + country, deaths.isEmpty());
        
        for (AIDSDeathRecord record : deaths) {
            System.out.println("Verifying record: " + record);
            assertEquals("Record should be for " + country, country, record.getCountry());
        }
        System.out.println("All records verified for " + country);
    }

    @Test
    public void testFindDeathsByYear() {
        System.out.println("\nTesting findDeaths by year...");
        int year = 1990;
        List<AIDSDeathRecord> deaths = aids.findDeaths("year", year);
        
        System.out.println("Found " + deaths.size() + " records for year " + year);
        assertFalse("Should find records for year " + year, deaths.isEmpty());
        
        for (AIDSDeathRecord record : deaths) {
            System.out.println("Verifying record: " + record);
            assertEquals("Record should be from year " + year, year, record.getYear());
        }
        System.out.println("All records verified for year " + year);
    }

    @Test
    public void testFindDeathsByDeathType() {
        System.out.println("\nTesting findDeaths by death type...");
        int deathCount = 100;
        List<AIDSDeathRecord> deaths = aids.findDeaths("adultdeaths", deathCount);
        
        System.out.println("Found " + deaths.size() + " records with " + deathCount + " adult deaths");
        assertFalse("Should find records with " + deathCount + " adult deaths", deaths.isEmpty());
        
        for (AIDSDeathRecord record : deaths) {
            System.out.println("Verifying record: " + record);
            assertEquals("Record should have " + deathCount + " adult deaths", 
                        deathCount, record.getAdultDeaths());
        }
        System.out.println("All records verified for death count " + deathCount);
    }

    @Test
    public void testFindDeathsInYearRange() {
        System.out.println("\nTesting findDeathsInYearRange...");
        String country = "Uganda";
        int startYear = 1990;
        int endYear = 1995;
        
        List<AIDSDeathRecord> deaths = aids.findDeathsInYearRange(country, startYear, endYear);
        System.out.println("Found " + deaths.size() + " records for " + country + 
                         " between " + startYear + " and " + endYear);
        
        assertFalse("Should find records for " + country + " between " + startYear + 
                   " and " + endYear, deaths.isEmpty());
        
        for (AIDSDeathRecord record : deaths) {
            System.out.println("Verifying record: " + record);
            assertEquals("Record should be for " + country, country, record.getCountry());
            assertTrue("Year should be between " + startYear + " and " + endYear, 
                      record.getYear() >= startYear && record.getYear() <= endYear);
        }
        System.out.println("All records verified for year range");
    }

    @Test
    public void testAverageDeathsPerYear() {
        System.out.println("\nTesting averageDeathsPerYear...");
        String country = "Uganda";
        int startYear = 1990;
        int endYear = 1995;
        
        List<AIDSDeathRecord> averages = aids.averageDeathsPerYear(country, startYear, endYear);
        System.out.println("Calculated averages for " + averages.size() + " years");
        
        assertFalse("Should calculate averages for " + country + " between " + startYear + 
                   " and " + endYear, averages.isEmpty());
        assertEquals("Should have one record per year", endYear - startYear + 1, averages.size());
        
        for (AIDSDeathRecord record : averages) {
            System.out.println("Verifying average record: " + record);
            assertEquals("Record should be for " + country, country, record.getCountry());
            assertTrue("Year should be between " + startYear + " and " + endYear, 
                      record.getYear() >= startYear && record.getYear() <= endYear);
        }
        System.out.println("All average records verified");
    }

    @Test
    public void testEdgeCases() {
        System.out.println("\nTesting edge cases...");
        
        // Test non-existent country
        String nonExistentCountry = "NonExistentCountry";
        System.out.println("Testing non-existent country: " + nonExistentCountry);
        List<AIDSDeathRecord> deaths = aids.findDeaths("country", nonExistentCountry);
        assertTrue("Should return empty list for non-existent country", deaths.isEmpty());
        System.out.println("Verified empty result for non-existent country");
        
        // Test year range with no data
        System.out.println("Testing year range with no data (1800-1850)");
        List<AIDSDeathRecord> yearRangeDeaths = aids.findDeathsInYearRange("Uganda", 1800, 1850);
        assertTrue("Should return empty list for year range with no data", yearRangeDeaths.isEmpty());
        System.out.println("Verified empty result for year range with no data");
        
        // Test invalid attribute
        System.out.println("Testing invalid attribute");
        List<AIDSDeathRecord> invalidDeaths = aids.findDeaths("invalidAttribute", 100);
        assertTrue("Should return empty list for invalid attribute", invalidDeaths.isEmpty());
        System.out.println("Verified empty result for invalid attribute");
        
        System.out.println("All edge cases verified");
    }
} 