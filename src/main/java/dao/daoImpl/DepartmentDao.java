package dao.daoImpl;

import dao.Dao;
import entity.Department;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class DepartmentDao implements Dao<Department> {
    private Session currentSession;
    private Transaction currentTransaction;

    public Session openSessionTransaction() {
        currentSession = HibernateUtil.getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;

    }

    public Session getCurrentSession() {
        return currentSession;
    }

    @Override
    public Optional<Department> get(int id) {
        return Optional.ofNullable(getCurrentSession().get(Department.class, id));
    }

    @Override
    public List<Department> getAll() {
        return (List<Department>) getCurrentSession().createQuery("from Department").list();
    }

    @Override
    public void save(Department department) {
        getCurrentSession().saveOrUpdate(department);
    }

    @Override
    public void update(Department department) {
        getCurrentSession().update(department);
    }


    @Override
    public void delete(Department department) {
        getCurrentSession().delete(department);
    }

    @Override
    public void deleteAll() {
        List<Department> departments = getAll();
        for (Department department : departments) {
            getCurrentSession().delete(department);
        }
    }
}
