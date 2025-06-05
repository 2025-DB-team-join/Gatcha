package gotcha.service;

import gotcha.dao.GroupDAO;
import gotcha.dao.HostedClassDAO;
import gotcha.dao.HostedClassDAO.HostedClass;

import java.util.List;
import java.util.Vector;

public class ManageGroupService {
	private final HostedClassDAO hdao = new HostedClassDAO();
    private final GroupDAO gdao = new GroupDAO();

    public List<HostedClass> getMyGroups(int hostId) {
        return hdao.getMyHostedClasses(hostId);
    }
    
    public HostedClass getGroupDetail(int classId) {
        return hdao.getHostedClassById(classId); // 추가 필요
    }

    public boolean softDeleteGroup(int classId) {
        return gdao.markGroupAsDeleted(classId);
    }

}
