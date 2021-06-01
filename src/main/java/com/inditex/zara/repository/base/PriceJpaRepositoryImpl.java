package com.inditex.zara.repository.base;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.inditex.zara.entity.Price;
import com.inditex.zara.entity.PriceCompositeKey;

public class PriceJpaRepositoryImpl<T> extends SimpleJpaRepository<T, PriceCompositeKey> implements PriceJpaRepository<T> {

    protected EntityManager entityManager;
    
    /**
     * Creates a new repository instance for the entity specified by the supplied entity
     * information that uses the supplied entity manager.
     * Constructor from superclass.
     *
     * @param entityInformation Entity information.
     * @param inEntityManager Entity manager.
     */
    public PriceJpaRepositoryImpl(final JpaEntityInformation<T, ?> entityInformation,
        final EntityManager inEntityManager) {
        super(entityInformation, inEntityManager);
        entityManager = inEntityManager;
    }
    
    /**
     * Creates a repository instance for the supplied entity that uses the
     * supplied entity manager.
     * Constructor from superclass.
     * @param entityClass Entity type.
     * @param em Entity manager.
     */
	public PriceJpaRepositoryImpl(final Class<T> entityClass, EntityManager em) {
		super(entityClass, em);
		entityManager = em;
	}

	/**
	 * Persist the entity to the DB
	 */
    @Transactional
    @Override
	public T persist(T inEntity) {
    	 T savedEntity = inEntity;
         final PriceCompositeKey entityId = ((Price) savedEntity).getPriceCompositeKey();
        	 if ((entityId != null) && existsById(entityId)) {
        	 savedEntity = entityManager.merge(inEntity);
         } else {
             entityManager.persist(inEntity);
         }
         entityManager.flush();
         return savedEntity;
	}   

}
