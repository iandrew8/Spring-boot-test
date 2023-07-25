package com.springboot.systems.system_wide.services.core.logs;

import com.springboot.systems.system_wide.services.dao.GenericService;
import com.springboot.systems.system_wide.services.exceptions.OperationFailedException;
import com.springboot.systems.system_wide.services.exceptions.ValidationFailedException;
import com.springboot.systems.system_wide.models.core.logs.Log;

import java.util.List;

public interface LogService extends GenericService<Log> {

    /**
     * Returns the {@link Log}s with content similar to what has been supplied
     *
     * @param log, the {@link Log} content to be searched for
     * @return {@link List<Log>}
     */
    List<Log> getLogs(String log);

    /**
     * This method will be used to create logs of the system
     * @param log, the {@link Log} content to be created
     * @return the created {@link Log}
     */
    Log createLog(String log) throws ValidationFailedException, OperationFailedException;
}
