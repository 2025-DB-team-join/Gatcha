package gotcha.dto;

public class PublicGroup {
    private int classId;
    private String title;
    private String category;
    private String context;
    private String region;
    private String hostNickname;
    private String days;
    private int userCount;
    private int max;
    private String status;

    // 전체 필드를 받는 생성자
    public PublicGroup(int classId, String title, String category, String context,
                       String region, String hostNickname, String days,
                       int userCount, int max, String status) {
        this.classId = classId;
        this.title = title;
        this.category = category;
        this.context = context;
        this.region = region;
        this.hostNickname = hostNickname;
        this.days = days;
        this.userCount = userCount;
        this.max = max;
        this.status = status;
    }

    public int getClassId() { return classId; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getContext() { return context; }
    public String getRegion() { return region; }
    public String getHostNickname() { return hostNickname; }
    public String getDays() { return days; }
    public int getUserCount() { return userCount; }
    public int getMax() { return max; }
    public String getStatus() { return status; }
}
