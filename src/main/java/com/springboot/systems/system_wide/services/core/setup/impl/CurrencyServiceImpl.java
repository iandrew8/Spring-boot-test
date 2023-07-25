package com.springboot.systems.system_wide.services.core.setup.impl;

import com.springboot.systems.system_wide.controllers.dtos.setup.payload.CurrencyPayload;
import com.springboot.systems.system_wide.models.core.security.User;
import com.springboot.systems.system_wide.models.core.security.annotations.permissions.PermissionConstants;
import com.springboot.systems.system_wide.models.core.setup.Currency;
import com.springboot.systems.system_wide.models.enums.Actions;
import com.springboot.systems.system_wide.models.enums.RecordStatus;
import com.springboot.systems.system_wide.services.core.setup.CurrencyService;
import com.springboot.systems.system_wide.services.core.setup.repo.CurrencyRepository;
import com.springboot.systems.system_wide.services.dao.impl.GenericServiceImpl;
import com.springboot.systems.system_wide.services.exceptions.ContentNotFoundException;
import com.springboot.systems.system_wide.services.exceptions.OperationFailedException;
import com.springboot.systems.system_wide.services.exceptions.ValidationFailedException;
import com.googlecode.genericdao.search.Search;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class CurrencyServiceImpl extends GenericServiceImpl<Currency> implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Override
    public Currency saveInstance(Currency entityInstance) throws ValidationFailedException, OperationFailedException {
        entityInstance.validate();
        return super.merge(entityInstance);
    }

    @Override
    public boolean isDeletable(Currency instance) throws OperationFailedException {
        return true;
    }

    @Override
    public Currency getCurrencyByNameAndUnit(String name, String unit) {
        Search search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        search.addFilterEqual("name", name);
        search.addFilterEqual("unit", unit);
        return super.searchUnique(search);
    }

    @Override
    public Currency getInstanceByID(String instance) throws OperationFailedException, ContentNotFoundException {
        return currencyRepository.findByIdAndRecordStatus(instance, RecordStatus.ACTIVE).orElseThrow(
                () -> new ContentNotFoundException("Currency of Id: " + instance +
                        " was not found")
        );
    }

    @Override
    public void deleteRecord(String id) throws ContentNotFoundException, OperationFailedException {
        deleteInstance(getInstanceByID(id));
    }

    @Override
    public Boolean permitLoggedInUser(Actions actions) {
        User loggedInUser = userService.getLoggedInUser();

        if (actions.equals(Actions.VIEW) && loggedInUser.hasPermission(PermissionConstants.PERM_VIEW_CURRENCY))
            return true;

        if (actions.equals(Actions.ADD) && loggedInUser.hasPermission(PermissionConstants.PERM_ADD_CURRENCY))
            return true;

        if (actions.equals(Actions.UPDATE) && loggedInUser.hasPermission(PermissionConstants.PERM_UPDATE_CURRENCY))
            return true;

        if (actions.equals(Actions.DELETE) && loggedInUser.hasPermission(PermissionConstants.PERM_DELETE_CURRENCY))
            return true;

        return loggedInUser.hasAdministrativePrivileges();
    }

    @Override
    public <R> Currency addNewRecord(R payload) throws ContentNotFoundException, ValidationFailedException, OperationFailedException, ParseException {
        CurrencyPayload currencyPayload = (CurrencyPayload) payload;
        Currency currency = getCurrencyByNameAndUnit(currencyPayload.getName(), currencyPayload.getUnit());

        if (currency != null && currency.getId() != null)
            throw new OperationFailedException("Currency with name: " + currencyPayload.getName() + " and " +
                    "unit: " + currencyPayload.getUnit() + " already exists");

        currency = new Currency();
        currency.setName(currencyPayload.getName());
        currency.setUnit(currencyPayload.getUnit());
        return saveInstance(currency);
    }

    @Override
    public <R> Currency updateRecord(String id, R payload) throws ContentNotFoundException, ValidationFailedException, OperationFailedException, ParseException {
        Currency currencyToEdit = getInstanceByID(id);
        CurrencyPayload currencyPayload = (CurrencyPayload) payload;

        if (currencyPayload.getName() != null)
            currencyToEdit.setName(currencyPayload.getName());
        if (currencyPayload.getUnit() != null)
            currencyToEdit.setUnit(currencyPayload.getUnit());

        return saveInstance(currencyToEdit);
    }

    @Override
    public void createDefaultCurrency() {
        Map<String, String> currencies = new HashMap<>();
        currencies.put("UGX", "Uganda Shilling");
        currencies.put("USD", "United States Dollar");

        currencies.forEach((key, value) -> {
            if (currencyRepository.findCurrencyByUnitAndRecordStatus(key, RecordStatus.ACTIVE).isEmpty()) {
                Currency currency = new Currency();
                currency.setName(value);
                currency.setUnit(key);
                currencyRepository.save(currency);
            }
        });
    }
}
