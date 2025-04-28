public class AIDSDeathRecord {
    private String country;
    private int year;
    private int aidsOrphans;
    private int adultDeaths;
    private int allAgesDeaths;
    private int childrenDeaths;
    private int femaleAdultDeaths;
    private int maleAdultDeaths;

    public AIDSDeathRecord(String country, int year, int aidsOrphans, int adultDeaths, 
                          int allAgesDeaths, int childrenDeaths, int femaleAdultDeaths, 
                          int maleAdultDeaths) {
        this.country = country;
        this.year = year;
        this.aidsOrphans = aidsOrphans;
        this.adultDeaths = adultDeaths;
        this.allAgesDeaths = allAgesDeaths;
        this.childrenDeaths = childrenDeaths;
        this.femaleAdultDeaths = femaleAdultDeaths;
        this.maleAdultDeaths = maleAdultDeaths;
    }

    // Getters
    public String getCountry() { return country; }
    public int getYear() { return year; }
    public int getAidsOrphans() { return aidsOrphans; }
    public int getAdultDeaths() { return adultDeaths; }
    public int getAllAgesDeaths() { return allAgesDeaths; }
    public int getChildrenDeaths() { return childrenDeaths; }
    public int getFemaleAdultDeaths() { return femaleAdultDeaths; }
    public int getMaleAdultDeaths() { return maleAdultDeaths; }

    @Override
    public String toString() {
        return "AIDSDeathRecord{" +
                "country='" + country + '\'' +
                ", year=" + year +
                ", aidsOrphans=" + aidsOrphans +
                ", adultDeaths=" + adultDeaths +
                ", allAgesDeaths=" + allAgesDeaths +
                ", childrenDeaths=" + childrenDeaths +
                ", femaleAdultDeaths=" + femaleAdultDeaths +
                ", maleAdultDeaths=" + maleAdultDeaths +
                '}';
    }
} 