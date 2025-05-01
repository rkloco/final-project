# AIDS Data Analysis Project

This project implements a system for analyzing AIDS-related death records across different countries and years. The implementation provides efficient querying capabilities for both indexed and non-indexed searches.

## Data Structure Design

## Core Classes

1. `AIDSDeathRecord` - Immutable data class that represents a single record with:
   - Country name
   - Year
   - Number of AIDS orphan
   - Adult deaths (total, male, female)
   - Children deaths
   - All ages deaths

2. `AIDS` - Interface defining the core query operations:
   - Finding deaths by country
   - Finding deaths in year range
   - Calculating average deaths per year

3. `AIDSImpl` - Implementation class using a hybrid approach:
   - Primary storage: ArrayList<AIDSDeathRecord>
   - Secondary indexes: 
     - HashMap<String, List<AIDSDeathRecord>> for country-based lookups
     - HashMap<Integer, List<AIDSDeathRecord>> for year-based lookups

## Performance Analysis

### Time Complexity

1. Indexed Operations:
   - Finding deaths by country: O(1) for index lookup + O(k) for processing matches where k is the number of records for that country
   - Finding deaths in year range: O(y) where y is the number of years in the range
   - Average deaths calculation: O(k) where k is the number of matching records

2. Non-indexed Operations:
   - Finding deaths by death count: O(n) where n is the total number of records
   - Complex queries (multiple conditions): O(n) but with higher constant factors due to multiple condition checking

### Space Complexity

- Primary storage: O(n) where n is the number of records
- Country index: O(n) as each record is referenced once
- Year index: O(n) as each record is referenced once
- Total: O(n) with a constant factor of approximately 3

## Implementation Choices

1. **Immutable Records** (`AIDSDeathRecord`):
   - Ensures thread safety in concurrent environments
   - Prevents accidental data modification
   - Simplifies caching and sharing of records

2. **Primary Storage** (`ArrayList<AIDSDeathRecord>`):
   - O(1) random access for record retrieval
   - Better cache locality than LinkedList
   - More memory efficient for storing fixed-size records
   - Efficient iteration for full dataset scans

3. **Secondary Indexes** (`HashMap` for country and year):
   - O(1) average case lookup time for indexed queries
   - Country index: Enables fast country-specific analysis
   - Year index: Optimizes time-series queries and range searches
   - Tradeoff: Additional O(n) memory for faster query performance

4. **Query Strategy**:
   - Indexed fields (country, year): Use HashMap lookups for O(1) access
   - Non-indexed fields (death counts): Full scan with O(n) complexity
   - Stream API for flexible filtering and post-processing

## Limitations and Potential Improvements

1. **Memory Usage**:
   - Current implementation keeps all data in memory
   - Could be improved with disk-based storage for larger datasets
   - Potential for memory-mapped files

2. **Query Flexibility**:
   - Limited to predefined query patterns
   - Could be enhanced with a more flexible query DSL
   - Potential for SQL-like query interface

3. **Performance Optimizations**:
   - Additional indexes could be added for frequently accessed fields
   - Parallel processing for large datasets
   - Caching frequently accessed results

4. **Data Validation**:
   - Basic validation of numeric values
   - Could add more sophisticated data quality checks
   - Support for handling missing or incorrect data

## Usage Examples

```java
// Create an instance
AIDS aids = new AIDSImpl();

// Load data
aids.loadData("aids_data.csv");

// Indexed queries
List<AIDSDeathRecord> georgiaRecords = aids.findDeaths("country", "Georgia");
List<AIDSDeathRecord> records2010_2015 = aids.findDeathsInYearRange("Libya", 2010, 2015);
double avgDeaths = aids.averageDeathsPerYear("Armenia", 2018, 2020);

// Non-indexed queries
List<AIDSDeathRecord> adultDeaths2020 = aids.findDeaths("adultDeaths", "100")
    .stream()
    .filter(r -> r.getYear() == 2020)
    .collect(Collectors.toList());
```

## Example Outputs

The project includes two example output files:

1. `example_output.txt`: Contains the complete output from running the `Main` class, demonstrating:
   - AIDS death records for Georgia across all years
   - Records for Libya between 2010 and 2015
   - Average deaths per year in Armenia from 2018 to 2020
   - Non-indexed queries showing records with specific death counts

2. `JUnitOutput.txt`: Contains the output from running the Maven build and JUnit tests, showing:
   - Successful compilation of all source files
   - Test execution results
   - Verification of AIDS death records
   - Build statistics and timing information

## Testing

The implementation includes comprehensive unit tests covering:
- Data loading and validation
- Indexed query operations
- Non-indexed query operations
- Edge cases and error conditions
- Performance benchmarks for large datasets

The test results are documented in `JUnitOutput.txt`, which shows successful execution of all test cases and verification of the data processing functionality.
