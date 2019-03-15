package dao.daoImpl;

import entity.Department;
import entity.Worker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DepartmentDaoTest {

    private static SessionFactory sessionFactory;
    private static Session session;
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
    public void testGetDepartmentByStatusUsingCriteria() {
        Department department1 = createDepartment(false);
        unitDepartment.save(department1);
        Department department2 = createDepartment(false);
        unitDepartment.save(department2);
        Department department3 = createDepartment(true);
        unitDepartment.save(department3);

        List<Department> actual = unitDepartment.getDepartmentByStatusUsingCriteria(true);
        assertEquals(1, actual.size());
        assertEquals(true, actual.get(0).getStatus());

    }

    @Test
    public void testGetDepartmentByStatusUsingHql() {
        Department department1 = createDepartment(false);
        unitDepartment.save(department1);
        Department department2 = createDepartment(false);
        unitDepartment.save(department2);
        Department department3 = createDepartment(true);
        unitDepartment.save(department3);

        List<Department> actual = unitDepartment.getDepartmentByStatus(true);
        assertEquals(1, actual.size());
        assertEquals(true, actual.get(0).getStatus());

    }

    @Test
    public void whenGetDepartmentByStatusUsingHqlReturnEmptyList() {
        Department department1 = createDepartment(false);
        unitDepartment.save(department1);
        Department department2 = createDepartment(false);
        unitDepartment.save(department2);
        Department department3 = createDepartment(false);
        unitDepartment.save(department3);

        List<Department> actual = unitDepartment.getDepartmentByStatus(true);
        assertTrue(actual.isEmpty());

    }

    @Test
    public void whenGetDepartmentByStatusUsingCriteriaReturnEmptyList() {
        Department department1 = createDepartment(false);
        unitDepartment.save(department1);
        Department department2 = createDepartment(false);
        unitDepartment.save(department2);
        Department department3 = createDepartment(false);
        unitDepartment.save(department3);

        List<Department> actual = unitDepartment.getDepartmentByStatusUsingCriteria(true);
        assertTrue(actual.isEmpty());

    }

    private Department createDepartment(Boolean status) {
        Department department = new Department();
        department.setName("Name");
        department.setStatus(status);
        return department;
    }

}
