package dao.daoImpl;

import dao.Dao;
import entity.Worker;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

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
}
