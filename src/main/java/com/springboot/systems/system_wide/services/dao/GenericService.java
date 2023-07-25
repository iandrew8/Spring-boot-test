package com.springboot.systems.system_wide.services.dao;

import com.springboot.systems.system_wide.models.enums.Actions;
import com.springboot.systems.system_wide.services.exceptions.ContentNotFoundException;
import com.springboot.systems.system_wide.services.exceptions.OperationFailedException;
import com.springboot.systems.system_wide.services.exceptions.ValidationFailedException;
import com.googlecode.genericdao.search.Search;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Generic interface which is used my most services. It defines CRUD
 * methods for all entities that inherit/implement this interface.
 *
 * @param <T>
 * @author ttc
 */

public interface GenericService<T> {

    /**
     * Saved <T> to the database after appropriate validation rules have been passed.
     *
     * @param entityInstance, the instance of the entity to be saved
     * @return the saved instance
     * @throws ValidationFailedException, if validation rules are not passed
     */
    T saveInstance(T entityInstance) throws ValidationFailedException, OperationFailedException;

    /**
     * Queries for a list of object instances that match the specified search
     * criteria, from the specified offset and limit.
     *
     * @param search, the search criteria
     * @param offset, the offset from which to start the query
     * @param limit,  the maximum number of results to return
     * @return a list of object instances that match the specified search criteria
     */
    List<T> getInstances(Search search, int offset, int limit);

    /**
     * Returns the instance of the object represented by the specified identifier.
     *
     * @return the instance of the object represented by the specified identifier
     */
    T getInstanceByID(String id) throws OperationFailedException, ContentNotFoundException;

    /**
     * Queries for the number of object instances that match the specified search
     * criteria.
     *
     * @param search, the search criteria
     * @return the number of object instances that match the specified search criteria
     */
    int countInstances(Search search) throws ContentNotFoundException;

    /**
     * Deactivates the specified instance. Deactivated records can neither be used
     * to create new records nor can they appear in lists of queried data from the
     * database.
     * <p>
     * However, records already saved that reference the deactivated record still
     * maintain that relationship. As a result, a deactivated record will appear in
     * its relationships but only for viewing purposes.
     * <p>
     * This obviously means that we do not permanently delete records from the DB,
     * but change record statuses.
     *
     * @param instance, the instance to be deactivated
     * @throws OperationFailedException, if the operation fails
     */
    void deleteInstance(T instance) throws OperationFailedException, ContentNotFoundException;

    /**
     * Queries all contents of a specified entity
     *
     * @return a list of all instances of the specified entity
     */
    List<T> getAllInstances();

    /**
     * Deletes the record with the specified id. It makes sure that the record exists first before calling the
     * deleteInstance method. The deleteInstance method is called only if the record exists. The deleteInstance method
     * handles the actual deletion of the record.
     *
     * @param id, the id of the record to be deleted
     * @throws OperationFailedException, if the operation fails
     * @throws ContentNotFoundException, if the record with the specified id does not exist
     */
    void deleteRecord(String id) throws OperationFailedException, ContentNotFoundException;

    /**
     * Returns true if the logged-in user has a permission to perform the {@link Actions} they intend to perform
     *
     * @return {@link Boolean}
     */
    Boolean permitLoggedInUser(Actions actions);

    /**
     * Adds a new record and returns an existing on if it already exists
     *
     * @param payload, the payload to be added
     * @throws ContentNotFoundException,  if the record with the specified id does not exist
     * @throws ValidationFailedException, if validation rules are not passed
     * @throws OperationFailedException,  if the operation fails
     * @throws ParseException,            if the operation fails
     */
    <R> T addNewRecord(R payload) throws ContentNotFoundException, ValidationFailedException, OperationFailedException, ParseException;

    /**
     * Updates an existing record and returns it
     *
     * @param id,      the id of the record to be updated
     * @param payload, the payload to be updated
     * @throws ContentNotFoundException,  if the record with the specified id does not exist
     * @throws ValidationFailedException, if validation rules are not passed
     * @throws OperationFailedException,  if the operation fails
     * @throws ParseException,            if the operation fails
     */
    <R> T updateRecord(String id, R payload) throws ContentNotFoundException, ValidationFailedException,
            OperationFailedException, ParseException, IOException, InterruptedException;


}