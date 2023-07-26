package com.springboot.systems.test.services.core.setup.impl;

import com.springboot.systems.test.models.enums.Actions;
import com.springboot.systems.test.services.dao.impl.GenericServiceImpl;
import com.springboot.systems.test.controllers.dtos.setup.payload.DataPackagePayload;
import com.springboot.systems.test.models.core.security.annotations.permissions.PermissionConstants;
import com.springboot.systems.test.models.enums.RecordStatus;
import com.springboot.systems.test.models.core.security.annotations.roles.RoleConstants;
import com.springboot.systems.test.services.exceptions.ContentNotFoundException;
import com.springboot.systems.test.services.exceptions.OperationFailedException;
import com.springboot.systems.test.services.exceptions.ValidationFailedException;
import com.springboot.systems.test.models.core.security.User;
import com.springboot.systems.test.models.core.setup.DataPackage;
import com.springboot.systems.test.models.core.setup.UnitOfMeasure;
import com.springboot.systems.test.services.core.setup.repo.DataPackageRepository;
import com.springboot.systems.test.services.core.setup.DataPackageService;
import com.springboot.systems.test.services.utils.Validate;
import com.googlecode.genericdao.search.Search;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DataPackageServiceImpl extends GenericServiceImpl<DataPackage> implements DataPackageService {

    private final DataPackageRepository dataPackageRepository;

    @Override
    public DataPackage saveInstance(DataPackage entityInstance) throws ValidationFailedException, OperationFailedException {
        Validate.notNull(entityInstance.getQuantity(), "Quantity is required");
        Validate.notNull(entityInstance.getUnitOfMeasure(), "Unit of measure is required");
        return super.merge(entityInstance);
    }

    @Override
    public void deleteRecord(String id) throws OperationFailedException, ContentNotFoundException {
        deleteInstance(getInstanceByID(id));
    }

    @Override
    public boolean isDeletable(DataPackage instance) throws OperationFailedException {
        return true;
    }

    @Override
    public List<DataPackage> getDataPackagesByUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        Search search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        search.addFilterEqual("unitOfMeasure", unitOfMeasure);
        return super.search(search);
    }

    @Override
    public List<DataPackage> getDataPackageByQuantity(double quantity) throws ValidationFailedException, OperationFailedException {
        Search search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        search.addFilterEqual("quantity", quantity);
        return super.search(search);
    }

    @Override
    public DataPackage getDataPackageByQuantityAndUnitOfMeasure(double quantity, UnitOfMeasure unitOfMeasure) {
        Search search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        search.addFilterEqual("quantity", quantity);
        search.addFilterEqual("unitOfMeasure", unitOfMeasure);

        if (super.searchUnique(search) != null)
            return super.searchUnique(search);

        return new DataPackage();
    }

    @Override
    public Boolean permitLoggedInUser(Actions actions) {
        User loggedInUser = userService.getLoggedInUser();

        if (actions.equals(Actions.VIEW) && loggedInUser.hasPermission(PermissionConstants.PERM_VIEW_DATA_PACKAGE))
            return true;

        if (actions.equals(Actions.VIEW) && loggedInUser.hasRole(RoleConstants.ROLE_ISP_ADMIN))
            return true;

        if (actions.equals(Actions.ADD) && loggedInUser.hasPermission(PermissionConstants.PERM_ADD_DATA_PACKAGE))
            return true;

        if (actions.equals(Actions.UPDATE) && loggedInUser.hasPermission(PermissionConstants.PERM_UPDATE_DATA_PACKAGE))
            return true;

        if (actions.equals(Actions.DELETE) && loggedInUser.hasPermission(PermissionConstants.PERM_DELETE_DATA_PACKAGE))
            return true;

        return loggedInUser.hasAdministrativePrivileges();
    }

    @Override
    public <R> DataPackage addNewRecord(R payload) throws ContentNotFoundException, ValidationFailedException, OperationFailedException, ParseException {
        /*-----Check if the DataPackage exists, if it does then return it, otherwise create a new one-----*/
        DataPackagePayload dataPackagePayload = (DataPackagePayload) payload;
        DataPackage dataPackage = getDataPackageByQuantityAndUnitOfMeasure(dataPackagePayload.getQuantity(), unitOfMeasureService
                .getInstanceByID(dataPackagePayload.getUnitOfMeasureId()));

        if (dataPackage != null && dataPackage.getId() != null)
            throw new OperationFailedException("DataPackage with the same quantity and unit of measure already exists");

        dataPackage = new DataPackage();
        dataPackage.setQuantity(dataPackagePayload.getQuantity());
        dataPackage.setUnitOfMeasure(unitOfMeasureService.getInstanceByID(dataPackagePayload.getUnitOfMeasureId()));

        return saveInstance(dataPackage);
    }

    @Override
    public <R> DataPackage updateRecord(String id, R payload) throws ContentNotFoundException, ValidationFailedException, OperationFailedException, ParseException {
        DataPackage dataPackageToEdit = getInstanceByID(id);
        DataPackagePayload dataPackagePayload = (DataPackagePayload) payload;
        if (dataPackagePayload.getQuantity() >= 0)
            dataPackageToEdit.setQuantity(dataPackagePayload.getQuantity());
        if (dataPackagePayload.getUnitOfMeasureId() != null)
            dataPackageToEdit.setUnitOfMeasure(unitOfMeasureService.getInstanceByID(dataPackagePayload.getUnitOfMeasureId()));

        return saveInstance(dataPackageToEdit);
    }

    @Override
    public DataPackage getInstanceByID(String instance) throws OperationFailedException, ContentNotFoundException {
        return dataPackageRepository.findByIdAndRecordStatus(instance, RecordStatus.ACTIVE).orElseThrow(
                () -> new ContentNotFoundException("DataPackage of Id: " + instance +
                        " was not found")
        );
    }

    @Override
    public void createDefaultDataPackage() {
        List<Long> quantities = Arrays.asList(10L, 20L, 30L, 40L, 50L, 100L);
        quantities.forEach(quantity -> {
            if (dataPackageRepository.findDataPackageByQuantity(quantity).isEmpty()) {
                DataPackage aDataPackage = new DataPackage();
                aDataPackage.setQuantity(quantity);
                aDataPackage.setUnitOfMeasure(unitOfMeasureService.getUnitOfMeasureByUnit("Mbps"));
                dataPackageRepository.save(aDataPackage);
            }
        });
    }
}
