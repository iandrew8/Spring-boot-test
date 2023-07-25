package com.springboot.systems.system_wide.services.core.logs.impl;

import com.springboot.systems.system_wide.models.enums.Actions;
import com.springboot.systems.system_wide.services.dao.impl.GenericServiceImpl;
import com.springboot.systems.system_wide.models.core.security.annotations.permissions.PermissionConstants;
import com.springboot.systems.system_wide.models.enums.RecordStatus;
import com.springboot.systems.system_wide.services.exceptions.ContentNotFoundException;
import com.springboot.systems.system_wide.services.exceptions.OperationFailedException;
import com.springboot.systems.system_wide.services.exceptions.ValidationFailedException;
import com.springboot.systems.system_wide.models.core.logs.Log;
import com.springboot.systems.system_wide.models.core.security.User;
import com.springboot.systems.system_wide.services.core.logs.repo.LogRepository;
import com.springboot.systems.system_wide.services.core.logs.LogService;
import com.springboot.systems.system_wide.services.utils.Validate;
import com.googlecode.genericdao.search.Search;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LogServiceImpl extends GenericServiceImpl<Log> implements LogService {

    private final LogRepository logRepository;

    @Override
    public Log saveInstance(Log entityInstance) throws ValidationFailedException, OperationFailedException {
        Validate.notNull(entityInstance.getLog(), "Log is required");
        return super.merge(entityInstance);
    }

    @Override
    public void deleteRecord(String id) throws OperationFailedException, ContentNotFoundException {
        deleteInstance(getInstanceByID(id));
    }

    @Override
    public boolean isDeletable(Log instance) throws OperationFailedException {
        return true;
    }

    @Override
    public List<Log> getLogs(String log) {
        Search search = new Search().addFilterEqual("recordStatus", RecordStatus.ACTIVE);
        search.addFilterEqual("log", log);
        return super.search(search);
    }

    @Override
    public Boolean permitLoggedInUser(Actions actions) {
        User loggedInUser = userService.getLoggedInUser();

        if (actions.equals(Actions.VIEW) && loggedInUser.hasPermission(PermissionConstants.PERM_VIEW_LOG))
            return true;

        if (actions.equals(Actions.ADD) && loggedInUser.hasPermission(PermissionConstants.PERM_ADD_LOG))
            return true;

        if (actions.equals(Actions.UPDATE) && loggedInUser.hasPermission(PermissionConstants.PERM_UPDATE_LOG))
            return true;

        if (actions.equals(Actions.DELETE) && loggedInUser.hasPermission(PermissionConstants.PERM_DELETE_LOG))
            return true;

        return loggedInUser.hasAdministrativePrivileges();
    }

    @Override
    public <R> Log addNewRecord(R payload) throws ContentNotFoundException, ValidationFailedException, OperationFailedException, ParseException {
        Log log = (Log) payload;
        Log log1 = new Log();
        log1.setLog(log.getLog());
        return saveInstance(log1);
    }

    @Override
    public <R> Log updateRecord(String id, R payload) throws ContentNotFoundException, ValidationFailedException, OperationFailedException, ParseException {
        Log log1 = getInstanceByID(id);
        Log log = (Log) payload;

        if (log.getLog() != null)
            log1.setLog(log.getLog());

        return saveInstance(log1);
    }

    @Override
    public Log getInstanceByID(String instance) throws OperationFailedException, ContentNotFoundException {
        return logRepository.findByIdAndRecordStatus(instance, RecordStatus.ACTIVE).orElseThrow(
                () -> new ContentNotFoundException("Log of Id: " + instance +
                        " was not found")
        );
    }

    @Override
    public Log createLog(String log) throws ValidationFailedException, OperationFailedException {
        Log newLog = new Log();
        newLog.setLog(log);
        return saveInstance(newLog);
    }
}
