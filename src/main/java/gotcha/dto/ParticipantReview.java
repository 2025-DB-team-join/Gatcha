package gotcha.dto;

public class ParticipantReview {
    private int reviewId;
    private float rating;
    private String content;

    // 생성자
    public ParticipantReview(int reviewId, float rating, String content) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.content = content;
    }

    // Getter
    public int getReviewId() {
        return reviewId;
    }

    public float getRating() {
        return rating;
    }

    public String getContent() {
        return content;
    }
}

