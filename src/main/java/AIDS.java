import java.util.List;

public interface AIDS {

    /**
    * Returns all records that exactly match the specified
    criteria.
    * @param attribute The attribute/field to query on
    * @param value The exact value to match
    * @return A collection of records matching the criteria
    */
    List<AIDSDeathRecord> findDeaths(String attribute, Object value);

    /**
    * Returns all records where the specified attribute falls
    within the given range.
    * @param attribute The attribute/field to query on
    * @param lowerBound The lower bound of the range (inclusive)
    * @param upperBound The upper bound of the range (inclusive)
    * @return A collection of records matching the criteria
    */
    List<AIDSDeathRecord> findDeathsInYearRange(String country, int lowerBound, int upperBound);

    /**
    * Returns the average value of the specified attribute during
    a given time frame
    * @param attribute The attribute/field
    * @param startTime The start time of the period (inclusive)
    * @param endTime The end time of the period (inclusive)
    * @return The statistic calculated */
    List<AIDSDeathRecord> averageDeathsPerYear(String country, int startYear, int endYear);
}
