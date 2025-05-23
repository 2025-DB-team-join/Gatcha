package gotcha.service;

import gotcha.dao.HostedClassDAO;
import java.util.List;


public class HostedClassService {
    private HostedClassDAO dao = new HostedClassDAO();

    public List<HostedClassDAO.HostedClass> getMyHostedClasses(int hostId) {
        return dao.getMyHostedClasses(hostId);
    }
}
