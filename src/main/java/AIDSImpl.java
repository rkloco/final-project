import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AIDSImpl implements AIDS {
    private List<AIDSDeathRecord> records;
    private Map<String, List<AIDSDeathRecord>> countryIndex;
    private Map<Integer, List<AIDSDeathRecord>> yearIndex;

    public AIDSImpl(String csvFilePath) {
        records = new ArrayList<>();
        countryIndex = new HashMap<>();
        yearIndex = new HashMap<>();
        loadData(csvFilePath);
    }

    private void loadData(String csvFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            // Skip header
            br.readLine();
            
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 8) {
                    AIDSDeathRecord record = new AIDSDeathRecord(
                        values[0], // country
                        Integer.parseInt(values[1]), // year
                        Integer.parseInt(values[2]), // aidsOrphans
                        Integer.parseInt(values[3]), // adultDeaths
                        Integer.parseInt(values[4]), // allAgesDeaths
                        Integer.parseInt(values[5]), // childrenDeaths
                        Integer.parseInt(values[6]), // femaleAdultDeaths
                        Integer.parseInt(values[7])  // maleAdultDeaths
                    );
                    
                    records.add(record);
                    
                    // Index by country
                    countryIndex.computeIfAbsent(record.getCountry(), k -> new ArrayList<>()).add(record);
                    
                    // Index by year
                    yearIndex.computeIfAbsent(record.getYear(), k -> new ArrayList<>()).add(record);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading AIDS data: " + e.getMessage(), e);
        }
    }

    @Override
    public List<AIDSDeathRecord> findDeaths(String attribute, Object value) {
        List<AIDSDeathRecord> results = new ArrayList<>();
        
        switch (attribute.toLowerCase()) {
            case "country":
                results.addAll(countryIndex.getOrDefault(value.toString(), new ArrayList<>()));
                break;
            case "year":
                results.addAll(yearIndex.getOrDefault((Integer)value, new ArrayList<>()));
                break;
            default:
                // For other attributes, we need to scan the records
                for (AIDSDeathRecord record : records) {
                    if (matchesAttribute(record, attribute, value)) {
                        results.add(record);
                    }
                }
        }
        
        return results;
    }

    private boolean matchesAttribute(AIDSDeathRecord record, String attribute, Object value) {
        switch (attribute.toLowerCase()) {
            case "aidsorphans":
                return record.getAidsOrphans() == (Integer)value;
            case "adultdeaths":
                return record.getAdultDeaths() == (Integer)value;
            case "allagesdeaths":
                return record.getAllAgesDeaths() == (Integer)value;
            case "childrendeaths":
                return record.getChildrenDeaths() == (Integer)value;
            case "femaleadultdeaths":
                return record.getFemaleAdultDeaths() == (Integer)value;
            case "maleadultdeaths":
                return record.getMaleAdultDeaths() == (Integer)value;
            default:
                return false;
        }
    }

    @Override
    public List<AIDSDeathRecord> findDeathsInYearRange(String country, int lowerBound, int upperBound) {
        List<AIDSDeathRecord> results = new ArrayList<>();
        List<AIDSDeathRecord> countryRecords = countryIndex.getOrDefault(country, new ArrayList<>());
        
        for (AIDSDeathRecord record : countryRecords) {
            if (record.getYear() >= lowerBound && record.getYear() <= upperBound) {
                results.add(record);
            }
        }
        
        return results;
    }

    @Override
    public List<AIDSDeathRecord> averageDeathsPerYear(String country, int startYear, int endYear) {
        List<AIDSDeathRecord> results = new ArrayList<>();
        List<AIDSDeathRecord> countryRecords = countryIndex.getOrDefault(country, new ArrayList<>());
        
        // Group records by year
        Map<Integer, List<AIDSDeathRecord>> yearGroups = new HashMap<>();
        for (AIDSDeathRecord record : countryRecords) {
            if (record.getYear() >= startYear && record.getYear() <= endYear) {
                yearGroups.computeIfAbsent(record.getYear(), k -> new ArrayList<>()).add(record);
            }
        }
        
        // Calculate averages for each year
        for (int year = startYear; year <= endYear; year++) {
            List<AIDSDeathRecord> yearRecords = yearGroups.getOrDefault(year, new ArrayList<>());
            if (!yearRecords.isEmpty()) {
                // Calculate averages
                int totalAidsOrphans = 0;
                int totalAdultDeaths = 0;
                int totalAllAgesDeaths = 0;
                int totalChildrenDeaths = 0;
                int totalFemaleAdultDeaths = 0;
                int totalMaleAdultDeaths = 0;
                
                for (AIDSDeathRecord record : yearRecords) {
                    totalAidsOrphans += record.getAidsOrphans();
                    totalAdultDeaths += record.getAdultDeaths();
                    totalAllAgesDeaths += record.getAllAgesDeaths();
                    totalChildrenDeaths += record.getChildrenDeaths();
                    totalFemaleAdultDeaths += record.getFemaleAdultDeaths();
                    totalMaleAdultDeaths += record.getMaleAdultDeaths();
                }
                
                int count = yearRecords.size();
                results.add(new AIDSDeathRecord(
                    country,
                    year,
                    totalAidsOrphans / count,
                    totalAdultDeaths / count,
                    totalAllAgesDeaths / count,
                    totalChildrenDeaths / count,
                    totalFemaleAdultDeaths / count,
                    totalMaleAdultDeaths / count
                ));
            }
        }
        
        return results;
    }
} 