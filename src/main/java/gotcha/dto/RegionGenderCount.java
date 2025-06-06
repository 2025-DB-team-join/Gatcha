package gotcha.dto;

public class RegionGenderCount {
    private final String region;
    private final int maleCount;
    private final int femaleCount;
    private final int otherCount; // 기타 성별 인원수

    public RegionGenderCount(String region, int maleCount, int femaleCount, int otherCount) {
        this.region = region;
        this.maleCount = maleCount;
        this.femaleCount = femaleCount;
        this.otherCount = otherCount;
    }

    public String getRegion() { return region; }
    public int getMaleCount() { return maleCount; }
    public int getFemaleCount() { return femaleCount; }
    public int getOtherCount() { return otherCount; }
}
