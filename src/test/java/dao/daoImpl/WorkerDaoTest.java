package dao.daoImpl;

import entity.Department;
import entity.Worker;
import entity.WorkerAvailability;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WorkerDaoTest {

    private static SessionFactory sessionFactory;
    private static Session session = null;
    private WorkerDao unitWorker;
    private DepartmentDao unitDepartment;

    @BeforeClass
    public static void setUp() {
        // setup the session factory
        Configuration config = new Configuration().configure("hibernate.cfg.test.xml");
        config.addAnnotatedClass(Worker.class);
        config.addAnnotatedClass(Department.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        sessionFactory = config.buildSessionFactory(builder.build());
    }

    @Before
    public void init() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        unitWorker = new WorkerDao();
        unitDepartment = new DepartmentDao();
        unitWorker.setCurrentSession(session);
        unitDepartment.setCurrentSession(session);
    }

    @AfterClass
    public static void tearDown() {
        sessionFactory.close();
    }

    @After
    public void clearDB() {
        unitWorker.deleteAll();
        unitDepartment.deleteAll();
        if (session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }

        session.close();
    }

    @Test
    public void testGetWorkersByDepartmentIdAndAvailabilityUsingHql() {
        Department department1 = createDepartment();
        unitDepartment.save(department1);
        Department department2 = createDepartment();
        unitDepartment.save(department2);
        Worker worker = createWorker(WorkerAvailability.PART_TIME, department1);
        unitWorker.save(worker);
        Worker worker1 = createWorker(WorkerAvailability.FULLTIME, department1);
        unitWorker.save(worker1);
        Worker worker2 = createWorker(WorkerAvailability.PART_TIME, department2);
        unitWorker.save(worker2);

        int idForGetQuery = department1.getId();
        List<Worker> workersByDepartmentIdAndAvailability = unitWorker.getWorkersByDepartmentIdAndAvailability(idForGetQuery, WorkerAvailability.PART_TIME);

        assertEquals(1, workersByDepartmentIdAndAvailability.size());
        assertEquals(WorkerAvailability.PART_TIME, workersByDepartmentIdAndAvailability.get(0).getAvailability());
        assertEquals(idForGetQuery, workersByDepartmentIdAndAvailability.get(0).getDepartment().getId());


    }

    @Test
    public void testGetWorkersByDepartmentIdAndAvailabilityUsingCriteria() {
        Department department1 = createDepartment();
        unitDepartment.save(department1);
        Department department2 = createDepartment();
        unitDepartment.save(department2);
        Worker worker = createWorker(WorkerAvailability.PART_TIME, department1);
        unitWorker.save(worker);
        Worker worker1 = createWorker(WorkerAvailability.FULLTIME, department1);
        unitWorker.save(worker1);
        Worker worker2 = createWorker(WorkerAvailability.PART_TIME, department2);
        unitWorker.save(worker2);

        int idForGetQuery = department1.getId();
        List<Worker> workersByDepartmentIdAndAvailability = unitWorker.getWorkersByDepartmentIdAndAvailabilityUsingCriteria(idForGetQuery, WorkerAvailability.PART_TIME);

        assertEquals(1, workersByDepartmentIdAndAvailability.size());
        assertEquals(WorkerAvailability.PART_TIME, workersByDepartmentIdAndAvailability.get(0).getAvailability());
        assertEquals(idForGetQuery, workersByDepartmentIdAndAvailability.get(0).getDepartment().getId());

    }

    @Test
    public void whenGetWorkersByDepartmentIdAndAvailabilityUsingHqlReturnEmptyList() {
        Department department1 = createDepartment();
        unitDepartment.save(department1);
        Department department2 = createDepartment();
        unitDepartment.save(department2);
        Worker worker = createWorker(WorkerAvailability.PART_TIME, department1);
        unitWorker.save(worker);
        Worker worker1 = createWorker(WorkerAvailability.PART_TIME, department1);
        unitWorker.save(worker1);
        Worker worker2 = createWorker(WorkerAvailability.PART_TIME, department2);
        unitWorker.save(worker2);

        int idForGetQuery = department1.getId();
        List<Worker> workersByDepartmentIdAndAvailability = unitWorker.getWorkersByDepartmentIdAndAvailability(idForGetQuery, WorkerAvailability.FULLTIME);

        assertTrue(workersByDepartmentIdAndAvailability.isEmpty());

    }

    @Test
    public void whenGetWorkersByDepartmentIdAndAvailabilityUsingCriteriaReturnEmptyList() {
        Department department1 = createDepartment();
        unitDepartment.save(department1);
        Department department2 = createDepartment();
        unitDepartment.save(department2);
        Worker worker = createWorker(WorkerAvailability.PART_TIME, department1);
        unitWorker.save(worker);
        Worker worker1 = createWorker(WorkerAvailability.PART_TIME, department1);
        unitWorker.save(worker1);
        Worker worker2 = createWorker(WorkerAvailability.PART_TIME, department2);
        unitWorker.save(worker2);

        int idForGetQuery = department1.getId();
        List<Worker> workersByDepartmentIdAndAvailability = unitWorker.getWorkersByDepartmentIdAndAvailabilityUsingCriteria(idForGetQuery, WorkerAvailability.FULLTIME);

        assertTrue(workersByDepartmentIdAndAvailability.isEmpty());

    }

    private Department createDepartment() {
        Department department = new Department();
        department.setName("Name");
        department.setStatus(false);
        return department;
    }

    private Worker createWorker(WorkerAvailability availability, Department department) {
        Worker worker = new Worker();
        worker.setFullName("Full Name");
        worker.setAvailability(availability);
        worker.setAge(35);//default
        worker.setDepartment(department);
        return worker;
    }
}