package com.Session;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.faces.validator.ValidatorException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return (T) getEntityManager().find(this.entityClass, id);
    }

    public List<T> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select((Selection) cq.from(this.entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select((Selection) cq.from(this.entityClass));
        TypedQuery typedQuery = getEntityManager().createQuery(cq);
        typedQuery.setMaxResults(range[1] - range[0] + 1);
        typedQuery.setFirstResult(range[0]);
        return typedQuery.getResultList();
    }

    public int count() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(this.entityClass);
        cq.select((Selection) getEntityManager().getCriteriaBuilder().count((Expression) rt));
        TypedQuery typedQuery = getEntityManager().createQuery(cq);
        return ((Long) typedQuery.getSingleResult()).intValue();
    }

    public void createWithValidator(T entity) throws ValidatorException, TransactionRequiredException, PersistenceException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity, new Class[0]);
        if (constraintViolations.size() > 0) {
            Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<T> cv = iterator.next();
                System.out.println("******" + cv.getConstraintDescriptor());
                System.err.println(cv.getRootBeanClass().getName() + "." + cv.getPropertyPath() + " " + cv.getMessage());
            }
            getEntityManager().persist(entity);
        } else {
            getEntityManager().persist(entity);
        }
    }

    public void editWithValidator(T entity) throws ValidatorException, TransactionRequiredException, PersistenceException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity, new Class[0]);
        if (constraintViolations.size() > 0) {
            Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<T> cv = iterator.next();
                System.out.println("******" + cv.getConstraintDescriptor());
                System.err.println(cv.getRootBeanClass().getName() + "." + cv.getPropertyPath() + " " + cv.getMessage());
            }
            getEntityManager().merge(entity);
        } else {
            getEntityManager().merge(entity);
        }
    }

    public void flush() {
        getEntityManager().flush();
    }

    public void limpiarCache() {
        getEntityManager().getEntityManagerFactory().getCache().evictAll();
    }
}
