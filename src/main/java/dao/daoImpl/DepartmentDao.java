package dao.daoImpl;

import dao.Dao;
import entity.Department;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

    public void setCurrentSession(Session session) {
        this.currentSession = session;
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

    //homework using hql
    public List<Department> getDepartmentByStatus(Boolean status) {
        Query query = currentSession.createQuery("from Department as department where department.status = :code");
        query.setParameter("code", status);
        return query.list();
    }

    //homework using persistence criteria
    public List<Department> getDepartmentByStatusUsingCriteria(Boolean status) {
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();

        CriteriaQuery<Department> criteria = builder.createQuery(Department.class);
        Root<Department> root = criteria.from(Department.class);

        criteria.select(root).where(builder.equal(root.get("status"), status));

        Query<Department> q = currentSession.createQuery(criteria);
        return q.getResultList();

    }
}
