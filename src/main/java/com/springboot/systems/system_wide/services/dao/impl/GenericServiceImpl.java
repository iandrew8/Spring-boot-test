package com.springboot.systems.system_wide.services.dao.impl;

import com.springboot.systems.system_wide.models.core.auditable.Auditable;
import com.springboot.systems.system_wide.models.core.security.User;
import com.springboot.systems.system_wide.models.enums.RecordStatus;
import com.springboot.systems.system_wide.services.core.emailtemplate.EmailClientService;
import com.springboot.systems.system_wide.services.core.emailtemplate.EmailTemplateService;
import com.springboot.systems.system_wide.services.core.googledrive.GoogleDriveManagerService;
import com.springboot.systems.system_wide.services.core.logs.LogService;
import com.springboot.systems.system_wide.services.core.security.*;
import com.springboot.systems.system_wide.services.core.setup.*;
import com.springboot.systems.system_wide.services.dao.GenericService;
import com.springboot.systems.system_wide.services.exceptions.ContentNotFoundException;
import com.springboot.systems.system_wide.services.exceptions.OperationFailedException;
import com.googlecode.genericdao.search.Search;
import com.springboot.systems.system_wide.services.core.security.*;
import com.springboot.systems.system_wide.services.core.setup.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Transactional
@Slf4j
public abstract class GenericServiceImpl<T extends Auditable> extends BaseDAOImpl<T> implements GenericService<T> {

    @Autowired
    @Lazy
    public UserService userService;

    @Autowired
    @Lazy
    public EmailClientService emailClientService;

    @Autowired
    @Lazy
    public GoogleDriveManagerService googleDriveManagerService;

    @Autowired
    @Lazy
    public CurrencyConversionService currencyConversionService;

    @Autowired
    @Lazy
    public DataPackageService dataPackageService;

    @Autowired
    @Lazy
    public LogService logService;

    @Autowired
    @Lazy
    public ApplicationSettingService applicationSettingService;

    @Autowired
    @Lazy
    public RestTemplate restTemplate;

    @Autowired
    @Lazy
    public ServiceZoneService serviceZoneService;

    @Autowired
    @Lazy
    public UserProfileService userProfileService;

    @Autowired
    @Lazy
    public EmailTemplateService emailTemplateService;

    @Autowired
    @Lazy
    public CurrencyService currencyService;

    @Autowired
    @Lazy
    public UnitOfMeasureService unitOfMeasureService;

    @Autowired
    @Lazy
    public PermissionService permissionService;

    @Autowired
    @Lazy
    public IAuthenticationFacade iAuthenticationFacade;

    @Autowired
    @Lazy
    public RoleService roleService;

    @Autowired
    @Lazy
    public PasswordEncoder passwordEncoder;

    /**
     * This method carries out the delete action for an entity instance
     */
    @Override
    public void deleteInstance(T instance) throws OperationFailedException, ContentNotFoundException {
        User updatedBy = userService.getLoggedInUser();
        if (!isDeletable(instance))
            throw new OperationFailedException("Deletion is not yet supported for this instance");
        changeStatusToDeleted(instance, updatedBy);
    }

    /**
     * Deactivates the instance by changing its status to deleted
     *
     * @param instance, the instance to be deleted
     */
    private void changeStatusToDeleted(T instance, User user) {
        log.debug("Instance is deletable! Now setting the audit trail.");
        instance.setUpdatedBy(user);
        instance.setDateChanged(new Date());
        instance.setRecordStatus(RecordStatus.DELETED);
        super.save(instance);
        log.debug("Set record to deleted!");
    }


    /**
     * Must be implemented by all classes that extend this abstract class.
     * This method must be implemented  to specify whether instances of an entity
     * can be deleted or not.
     *
     * @param instance, the instance to be deleted
     * @return true if the instance can be deleted, false otherwise
     * @throws OperationFailedException, if an error occurs while checking if the instance is deletable
     */
    public abstract boolean isDeletable(T instance) throws OperationFailedException;


    /**
     * Retrieves items from the database based on the ID provided
     */
    @Override
    public T getInstanceByID(String instance) throws OperationFailedException, ContentNotFoundException {
        return super.searchUniqueByPropertyEqual("id", instance);
    }

    /**
     * Counts the number of occurrences of what is specified as the search
     */
    @Override
    public int countInstances(Search search) throws ContentNotFoundException {
        return super.count(search);
    }

    /**
     * Returns item being searched for from the database
     */
    @Override
    public List<T> getInstances(Search search, int offset, int limit) {
        return super.search(search.setFirstResult(offset).setMaxResults(limit));

    }

    /**
     * Returns all instances from the database with a RecordStatus of ACTIVE
     */
    @Override
    public List<T> getAllInstances() {
        Search search = new Search();
        search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        return super.search(search);
    }

}

