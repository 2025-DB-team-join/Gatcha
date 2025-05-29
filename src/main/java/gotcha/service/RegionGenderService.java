package gotcha.service;

import gotcha.dao.RegionGenderDAO;
import java.util.List;

public class RegionGenderService {
    private RegionGenderDAO dao = new RegionGenderDAO();

    public List<RegionGenderDAO.ClassRank> getRankedClassesWithDays(String region, String gender) {
        return dao.getRankedClassesWithDays(region, gender);
    }
}
