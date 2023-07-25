package com.springboot.systems.system_wide.services.core.setup.impl;

import com.springboot.systems.system_wide.controllers.dtos.setup.payload.UnitOfMeasurePayload;
import com.springboot.systems.system_wide.models.core.security.User;
import com.springboot.systems.system_wide.models.core.security.annotations.permissions.PermissionConstants;
import com.springboot.systems.system_wide.models.core.setup.UnitOfMeasure;
import com.springboot.systems.system_wide.models.enums.Actions;
import com.springboot.systems.system_wide.models.enums.RecordStatus;
import com.springboot.systems.system_wide.services.core.setup.UnitOfMeasureService;
import com.springboot.systems.system_wide.services.core.setup.repo.UnitOfMeasureRepository;
import com.springboot.systems.system_wide.services.dao.impl.GenericServiceImpl;
import com.springboot.systems.system_wide.services.exceptions.ContentNotFoundException;
import com.springboot.systems.system_wide.services.exceptions.OperationFailedException;
import com.springboot.systems.system_wide.services.exceptions.ValidationFailedException;
import com.googlecode.genericdao.search.Search;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

@Service
@Transactional
@RequiredArgsConstructor
public class UnitOfMeasureServiceImpl extends GenericServiceImpl<UnitOfMeasure> implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Override
    public UnitOfMeasure saveInstance(UnitOfMeasure entityInstance) throws ValidationFailedException, OperationFailedException {
        entityInstance.validate();
        return super.merge(entityInstance);
    }

    @Override
    public boolean isDeletable(UnitOfMeasure instance) throws OperationFailedException {
        return true;
    }

    @Override
    public UnitOfMeasure getInstanceByID(String instance) throws OperationFailedException, ContentNotFoundException {
        return unitOfMeasureRepository.findByIdAndRecordStatus(instance, RecordStatus.ACTIVE).orElseThrow(
                () -> new ContentNotFoundException("Unit of measure of Id: " + instance +
                        " was not found")
        );
    }

    @Override
    public void deleteRecord(String id) throws OperationFailedException, ContentNotFoundException {
        deleteInstance(getInstanceByID(id));
    }

    @Override
    public UnitOfMeasure getUnitOfMeasureByUnit(String unit) {
        Search search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        search.addFilterEqual("unit", unit);
        return super.searchUnique(search);
    }

    @Override
    public UnitOfMeasure getUnitOfMeasureByUnitAndName(String name, String unit) {
        Search search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        search.addFilterEqual("unit", unit);
        search.addFilterEqual("name", name);
        return super.searchUnique(search);
    }

    @Override
    public Boolean permitLoggedInUser(Actions actions) {
        User loggedInUser = userService.getLoggedInUser();

        if (actions.equals(Actions.VIEW) && loggedInUser.hasPermission(PermissionConstants.PERM_VIEW_UNIT_OF_MEASURE))
            return true;

        if (actions.equals(Actions.ADD) && loggedInUser.hasPermission(PermissionConstants.PERM_ADD_UNIT_OF_MEASURE))
            return true;

        if (actions.equals(Actions.UPDATE) && loggedInUser.hasPermission(PermissionConstants.PERM_UPDATE_UNIT_OF_MEASURE))
            return true;

        if (actions.equals(Actions.DELETE) && loggedInUser.hasPermission(PermissionConstants.PERM_DELETE_UNIT_OF_MEASURE))
            return true;

        return loggedInUser.hasAdministrativePrivileges();
    }

    @Override
    public <R> UnitOfMeasure addNewRecord(R payload) throws ContentNotFoundException, ValidationFailedException, OperationFailedException, ParseException {
        /*-----Check if record exists, return it if it does, and create a new one if it does not-----*/
        UnitOfMeasurePayload unitOfMeasurePayload = (UnitOfMeasurePayload) payload;
        UnitOfMeasure unitOfMeasure = getUnitOfMeasureByUnitAndName(unitOfMeasurePayload.getName(), unitOfMeasurePayload.getUnit());

        if (unitOfMeasure != null && unitOfMeasure.getId() != null)
            return unitOfMeasure;

        unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setName(unitOfMeasurePayload.getName());
        unitOfMeasure.setUnit(unitOfMeasurePayload.getUnit());
        return saveInstance(unitOfMeasure);
    }

    @Override
    public <R> UnitOfMeasure updateRecord(String id, R payload) throws ContentNotFoundException, ValidationFailedException, OperationFailedException, ParseException {
        UnitOfMeasure unitOfMeasureToEdit = getInstanceByID(id);
        UnitOfMeasurePayload unitOfMeasurePayload = (UnitOfMeasurePayload) payload;

        if (unitOfMeasurePayload.getName() != null)
            unitOfMeasureToEdit.setName(unitOfMeasurePayload.getName());
        if (unitOfMeasurePayload.getUnit() != null)
            unitOfMeasureToEdit.setUnit(unitOfMeasurePayload.getUnit());

        return saveInstance(unitOfMeasureToEdit);
    }

    @Override
    public void createDefaultUnitOfMeasure() {
        String unit = "Mbps";
        if (unitOfMeasureRepository.findUnitOfMeasureByUnit(unit).isEmpty()) {
            UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
            unitOfMeasure.setName("Mega Bytes Per Second");
            unitOfMeasure.setUnit(unit);
            unitOfMeasureRepository.save(unitOfMeasure);
        }
        dataPackageService.createDefaultDataPackage();
    }
}
