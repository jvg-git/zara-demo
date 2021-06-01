package com.inditex.zara.services.async;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.inditex.zara.entity.Price;
import com.inditex.zara.entity.PriceCompositeKey;
import com.inditex.zara.repository.base.PriceJpaRepository;

import io.reactivex.Observable;

/**
 * Abstract base class for services that performs CRUD operations for entities
 * The implementation uses RxJava.
 * @param <E> -  Entity type.
 */
@Transactional
public abstract class AbstractAsyncBaseService<E extends Price> {

	protected PriceJpaRepository<E> zaraRepository;

	private static Logger logger = LoggerFactory.getLogger(AbstractAsyncBaseService.class);
	
	
	/**
	 * 	Creates a  new Service instance that will use the supplied repository for
	 * entity persistence.
	 * @param repository - repository for the service {@link PriceJpaRepository}
	 */
	public AbstractAsyncBaseService(final PriceJpaRepository<E> repository) {
		zaraRepository = repository;
	}

	/**
	 * Saves the supplied entity.
	 * @param inEntity - Entity to save.
	 * @return Observable that will receive the saved entity, or exception if error occurs.
	 */
	public Observable<E> save(final E inEntity) {
		return Observable.create(inSource -> {
			try {
				final E theSavedEntity = zaraRepository.save(inEntity);
				inSource.onNext(theSavedEntity);
				inSource.onComplete();
			} catch (final Throwable theException) {
				logger.error("save error, entity[{}]: {}", inEntity, theException);
				inSource.onError(theException);
			}
		});
	}

	/**
	 * Updates the supplied entity.
	 * @param inEntity - Entity to update.
	 * @return Observable that will receive the updated entity, or exception if error occurs.
	 */
	public Observable<E> update(final E inEntity) {
		return Observable.create(inSource -> {
			try {
				final E theUpdatedEntity = zaraRepository.persist(inEntity);
				inSource.onNext(theUpdatedEntity);
				inSource.onComplete();
			} catch (final Exception theException) {
				logger.error("update error, entity[{}]: {}", inEntity, theException);
				inSource.onError(theException);
				
			}
		});
	}

	/**
	 * Finds the entity having supplied PriceCompositeKey.
	 * @param inEntityId - PriceCompositeKey of entity to retrieve.
	 * @return - Observable that will receive the found entity, or exception if error occurs 
	 * or no entity is found.
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
	public Observable<E> find(final PriceCompositeKey inEntityId) {
		return Observable.create(inSource -> {
			try {
				final E theEntity = zaraRepository.getById(inEntityId);
				if (theEntity != null) {
					inSource.onNext(theEntity);
					inSource.onComplete();
				} else {
					inSource.onError(new Error("Cannot find entity with PriceCompositeKey " + inEntityId));
				}
			} catch (final Exception theException) {
				inSource.onError(theException);
			}
		});
	}

	/**
	 * Finds all the entities.
	 * @return Observable that will receive a list of entities, or exception if error occurs.
	 */
	@Transactional(readOnly = true)
	public Observable<List<E>> findAll() {
		return Observable.create(inSource -> {
			try {
				final List<E> theEntitiesList = StreamSupport.stream(zaraRepository.findAll().spliterator(), false)
						.collect(Collectors.toList());
				inSource.onNext(theEntitiesList);
				inSource.onComplete();
			} catch (final Exception theException) {
				inSource.onError(theException);
			}
		});
	}

	/**
	 * Deletes the entity having supplied id.
	 * @param inId - Id of entity to delete.
	 * @return Observable that will receive completion, or exception if error  occurs.
	 */
	@SuppressWarnings("rawtypes")
	public Observable delete(final PriceCompositeKey inId) {
		return Observable.create(inSource -> {
			try {
				final E theEntity = zaraRepository.getById(inId);
				zaraRepository.delete(theEntity);
				inSource.onComplete();
			} catch (final Exception theException) {
				inSource.onError(theException);
			}
		});
	}

	/**
	 * Deletes all entities.
	 * @return Observable that will receive completion, or exception if error occurs.
	 */
	@SuppressWarnings("rawtypes")
	public Observable deleteAll() {
		return Observable.create(inSource -> {
			try {
				zaraRepository.deleteAll();
				inSource.onComplete();
			} catch (final Exception theException) {
				inSource.onError(theException);
			}
		});
	}
		
}
