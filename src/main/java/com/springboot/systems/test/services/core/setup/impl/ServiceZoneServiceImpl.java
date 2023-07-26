package com.springboot.systems.test.services.core.setup.impl;

import com.springboot.systems.test.models.enums.Actions;
import com.springboot.systems.test.models.enums.DiscountStatus;
import com.springboot.systems.test.services.dao.impl.GenericServiceImpl;
import com.springboot.systems.test.controllers.dtos.setup.payload.ServiceZonePayload;
import com.springboot.systems.test.models.core.security.annotations.permissions.PermissionConstants;
import com.springboot.systems.test.models.enums.RecordStatus;
import com.springboot.systems.test.services.exceptions.ContentNotFoundException;
import com.springboot.systems.test.services.exceptions.OperationFailedException;
import com.springboot.systems.test.services.exceptions.ValidationFailedException;
import com.springboot.systems.test.models.core.security.User;
import com.springboot.systems.test.models.core.setup.ServiceZone;
import com.springboot.systems.test.services.core.setup.repo.ServiceZoneRepository;
import com.springboot.systems.test.services.core.setup.ServiceZoneService;
import com.springboot.systems.test.services.utils.Validate;
import com.googlecode.genericdao.search.Search;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ServiceZoneServiceImpl extends GenericServiceImpl<ServiceZone> implements ServiceZoneService {

    private final ServiceZoneRepository serviceZoneRepository;

    @Override
    public ServiceZone saveInstance(ServiceZone entityInstance) throws ValidationFailedException, OperationFailedException {
        Validate.notNull(entityInstance.getName(), "Name is required");
        return super.merge(entityInstance);
    }

    @Override
    public boolean isDeletable(ServiceZone instance) throws OperationFailedException {
        return true;
    }

    @Override
    public ServiceZone getInstanceByID(String instance) throws OperationFailedException, ContentNotFoundException {
        return serviceZoneRepository.findByIdAndRecordStatus(instance, RecordStatus.ACTIVE).orElseThrow(
                () -> new ContentNotFoundException("ServiceZone of Id: " + instance +
                        " was not found")
        );
    }

    @Override
    public void deleteRecord(String id) throws OperationFailedException, ContentNotFoundException {
        deleteInstance(getInstanceByID(id));
    }

    @Override
    public ServiceZone getServiceZoneByName(String name) throws ValidationFailedException, OperationFailedException {
        Search search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        search.addFilterEqual("name", name);

        if (super.searchUnique(search) != null)
            return super.searchUnique(search);

        ServiceZone serviceZone = new ServiceZone();
        serviceZone.setName(name);
        return saveInstance(serviceZone);
    }

    @Override
    public Boolean permitLoggedInUser(Actions actions) {
        if (Objects.isNull(iAuthenticationFacade.getAuthentication()) && actions.equals(Actions.VIEW))
            return true;

        User loggedInUser = userService.getLoggedInUser();

        if (actions.equals(Actions.VIEW) && loggedInUser.hasPermission(PermissionConstants.PERM_VIEW_REGION))
            return true;

        if (actions.equals(Actions.ADD) && loggedInUser.hasPermission(PermissionConstants.PERM_ADD_REGION))
            return true;

        if (actions.equals(Actions.UPDATE) && loggedInUser.hasPermission(PermissionConstants.PERM_UPDATE_REGION))
            return true;

        if (actions.equals(Actions.DELETE) && loggedInUser.hasPermission(PermissionConstants.PERM_DELETE_REGION))
            return true;

        return loggedInUser.hasAdministrativePrivileges();
    }

    @Override
    public <R> ServiceZone addNewRecord(R payload) throws ContentNotFoundException, ValidationFailedException, OperationFailedException, ParseException {
        /*-----Check if service zone exists, return it if it does and create a new one if it does not-----*/
        ServiceZonePayload serviceZonePayload = (ServiceZonePayload) payload;
        return getServiceZoneByName(serviceZonePayload.getName());
    }

    @Override
    public <R> ServiceZone updateRecord(String id, R payload) throws ContentNotFoundException, ValidationFailedException, OperationFailedException, ParseException {
        ServiceZone serviceZoneToEdit = getInstanceByID(id);
        ServiceZonePayload serviceZonePayload = (ServiceZonePayload) payload;

        if (serviceZonePayload.getName() != null)
            serviceZoneToEdit.setName(serviceZonePayload.getName());

        if (serviceZonePayload.getNrcDiscountStatus() != null)
            serviceZoneToEdit.setNRCDiscountStatus(DiscountStatus.getEnumObject(serviceZonePayload.getNrcDiscountStatus()));

        return saveInstance(serviceZoneToEdit);
    }
}
