package gotcha.service;

import gotcha.dao.TagDAO;

import java.util.List;

public class TagService {
    private final TagDAO tagDAO = new TagDAO();

    public List<String> getAllTags() {
        return tagDAO.fetchTagNames();
    }

    public boolean saveUserTags(int userId, List<Integer> tagIds) {
        return tagDAO.insertUserTags(userId, tagIds);
    }
}
