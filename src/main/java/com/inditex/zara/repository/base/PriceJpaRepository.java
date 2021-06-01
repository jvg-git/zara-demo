package com.inditex.zara.repository.base;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.NoRepositoryBean;

import com.inditex.zara.entity.PriceCompositeKey;

/**
 * Interface defining custom method(s) added to all the Spring Data JPA repositories in the application.
 *
 * @param <T> Entity type.
 */
@NoRepositoryBean
public interface PriceJpaRepository<T> extends JpaRepository<T, PriceCompositeKey> {
    
	/**
     * Persists the supplied entity.
     * If the entity has an id and previously has been persisted, it will be merged to the
     * persistence context otherwise it will be inserted into the persistence context.
     *
     * @param inEntity Entity to persist.
     * @return Persisted entity.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    T persist(final T inEntity);
    
    
//    Optional<T> deleteById(Long id);

}

