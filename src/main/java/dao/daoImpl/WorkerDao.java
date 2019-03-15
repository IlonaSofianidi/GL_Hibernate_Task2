package dao.daoImpl;

import dao.Dao;
import entity.Worker;
import entity.WorkerAvailability;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class WorkerDao implements Dao<Worker> {
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
    public Optional<Worker> get(int id) {
        return Optional.ofNullable(getCurrentSession().get(Worker.class, id));
    }

    @Override
    public List<Worker> getAll() {
        return (List<Worker>) getCurrentSession().createQuery("from Worker").list();
    }

    @Override
    public void save(Worker worker) {
        getCurrentSession().saveOrUpdate(worker);
    }

    @Override
    public void update(Worker worker) {
        getCurrentSession().update(worker);
    }

    @Override
    public void delete(Worker worker) {
        getCurrentSession().delete(worker);
    }

    @Override
    public void deleteAll() {
        List<Worker> allWorkers = getAll();
        for (Worker worker : allWorkers) {
            getCurrentSession().delete(worker);
        }
    }

    public void setCurrentSession(Session session) {
        this.currentSession = session;
    }

    //homework using hql
    public List<Worker> getWorkersByDepartmentIdAndAvailability(int departmentId, WorkerAvailability availability) {
        Query query = currentSession.createQuery("from Worker as worker where worker.department.id = :code AND worker.availability = :code2");
        query.setParameter("code", departmentId);
        query.setParameter("code2", availability);
        return query.list();


    }

    //homework using persistence criteria
    public List<Worker> getWorkersByDepartmentIdAndAvailabilityUsingCriteria(int departmentId, WorkerAvailability availability) {
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();

        CriteriaQuery<Worker> criteria = builder.createQuery(Worker.class);
        Root<Worker> root = criteria.from(Worker.class);

        criteria.select(root).where(builder.and(builder.equal(root.get("department").get("id"), departmentId)),
                (builder.equal(root.get("availability"), availability)));

        Query<Worker> q = currentSession.createQuery(criteria);

        return q.getResultList();

    }
}
