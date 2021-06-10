package episen.si.ing1.pds.backend.server.indicators;

public class OccupancyByCompany {
    private final String companyName;
    private final String occupancyRate;

    public OccupancyByCompany(String companyName, String occupancyRate) {
        this.companyName = companyName;
        this.occupancyRate = occupancyRate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getOccupancyRate() {
        return occupancyRate;
    }
}
