package com.springboot.systems.test.services.core.setup.impl;

import com.springboot.systems.test.controllers.dtos.setup.payload.CurrencyConversionPayload;
import com.springboot.systems.test.models.core.security.User;
import com.springboot.systems.test.models.core.security.annotations.permissions.PermissionConstants;
import com.springboot.systems.test.models.core.setup.CurrencyConversion;
import com.springboot.systems.test.models.enums.Actions;
import com.springboot.systems.test.models.enums.RecordStatus;
import com.springboot.systems.test.services.core.setup.CurrencyConversionService;
import com.springboot.systems.test.services.core.setup.repo.CurrencyConversionRepository;
import com.springboot.systems.test.services.core.setup.repo.CurrencyRepository;
import com.springboot.systems.test.services.dao.impl.GenericServiceImpl;
import com.springboot.systems.test.services.exceptions.ContentNotFoundException;
import com.springboot.systems.test.services.exceptions.OperationFailedException;
import com.springboot.systems.test.services.exceptions.ValidationFailedException;
import com.googlecode.genericdao.search.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CurrencyConversionServiceImpl extends GenericServiceImpl<CurrencyConversion> implements CurrencyConversionService {

    private final CurrencyConversionRepository currencyConversionRepository;
    private final CurrencyRepository currencyRepository;

    @Override
    public CurrencyConversion saveInstance(CurrencyConversion entityInstance) throws ValidationFailedException, OperationFailedException {
        entityInstance.validate();
        return super.merge(entityInstance);
    }

    @Override
    public boolean isDeletable(CurrencyConversion instance) throws OperationFailedException {
        return true;
    }

    @Override
    public CurrencyConversion getInstanceByID(String instance) throws OperationFailedException, ContentNotFoundException {
        return currencyConversionRepository.findByIdAndRecordStatus(instance, RecordStatus.ACTIVE).orElseThrow(
                () -> new ContentNotFoundException("CurrencyConversion of Id: " + instance +
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

        if (actions.equals(Actions.VIEW) && loggedInUser.hasPermission(PermissionConstants.PERM_VIEW_CURRENCY_CONVERSION))
            return true;

        if (actions.equals(Actions.ADD) && loggedInUser.hasPermission(PermissionConstants.PERM_ADD_CURRENCY_CONVERSION))
            return true;

        if (actions.equals(Actions.UPDATE) && loggedInUser.hasPermission(PermissionConstants.PERM_UPDATE_CURRENCY_CONVERSION))
            return true;

        if (actions.equals(Actions.DELETE) && loggedInUser.hasPermission(PermissionConstants.PERM_DELETE_CURRENCY_CONVERSION))
            return true;

        return loggedInUser.hasAdministrativePrivileges();
    }

    @Override
    public <R> CurrencyConversion addNewRecord(R payload) throws ContentNotFoundException, ValidationFailedException, OperationFailedException, ParseException {
        CurrencyConversionPayload currencyConversionPayload = (CurrencyConversionPayload) payload;
        if (currencyConversionRepository.findCurrencyConversionByCurrency(currencyService.getInstanceByID(currencyConversionPayload.getCurrencyId())).isPresent())
            throw new OperationFailedException("Currency conversion for this currency already exists");

        CurrencyConversion currencyConversion = new CurrencyConversion();
        currencyConversion.setCurrency(currencyService.getInstanceByID(currencyConversionPayload.getCurrencyId()));
        currencyConversion.setOneDollarIsWorth(currencyConversionPayload.getOneDollarIsWorth());
        return saveInstance(currencyConversion);
    }

    @Override
    public <R> CurrencyConversion updateRecord(String id, R payload) throws ContentNotFoundException, ValidationFailedException, OperationFailedException, ParseException {
        CurrencyConversion currencyConversionToEdit = getInstanceByID(id);
        CurrencyConversionPayload currencyConversionPayload = (CurrencyConversionPayload) payload;
        if (currencyConversionPayload.getCurrencyId() != null && StringUtils.isNotBlank(currencyConversionPayload.getCurrencyId()))
            currencyConversionToEdit.setCurrency(currencyService.getInstanceByID(currencyConversionPayload.getCurrencyId()));
        if (currencyConversionPayload.getOneDollarIsWorth() != null && currencyConversionPayload.getOneDollarIsWorth().compareTo(BigDecimal.ZERO) > 0)
            currencyConversionToEdit.setOneDollarIsWorth(currencyConversionPayload.getOneDollarIsWorth());

        return saveInstance(currencyConversionToEdit);
    }

    @Override
    public CurrencyConversion findByCurrencyUnit(String unit) {
        Search search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        search.addFilterEqual("currency.unit", unit);
        return super.searchUnique(search);
    }

    @Override
    public void createDefaultCurrencyConversion() {
        try {
            if (currencyConversionService.findByCurrencyUnit("UGX") == null) {
                CurrencyConversion currencyConversion = new CurrencyConversion();
                currencyConversion.setCurrency(currencyRepository.findCurrencyByUnitAndRecordStatus("UGX",
                        RecordStatus.ACTIVE).orElse(null));
                currencyConversion.setOneDollarIsWorth(new BigDecimal("3650"));
                currencyConversionService.saveInstance(currencyConversion);
            }
        } catch (ValidationFailedException | OperationFailedException e) {
            log.error("Application Setting Seeder Error: {}", e.getMessage());
        }
    }
}
