import dao.daoImpl.DepartmentDao;
import dao.daoImpl.WorkerDao;
import entity.Department;
import entity.Worker;
import entity.WorkerAvailability;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DepartmentDao departmentDao = new DepartmentDao();
        departmentDao.openSessionTransaction();
        List<Department> departmentsByStatus = departmentDao.getDepartmentByStatusUsingCriteria(true);
        departmentDao.getCurrentSession().close();
        System.out.println(Arrays.toString(departmentsByStatus.toArray()));
        WorkerDao workerDao = new WorkerDao();
        workerDao.openSessionTransaction();
        List<Worker> workersByDepartmentIdAndAvailability = workerDao.getWorkersByDepartmentIdAndAvailability(1, WorkerAvailability.PART_TIME);
        System.out.println(Arrays.toString(workersByDepartmentIdAndAvailability.toArray()));
        workerDao.getCurrentSession().close();

    }
}
