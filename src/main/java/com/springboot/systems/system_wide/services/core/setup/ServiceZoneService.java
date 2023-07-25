package com.springboot.systems.system_wide.services.core.setup;

import com.springboot.systems.system_wide.services.dao.GenericService;
import com.springboot.systems.system_wide.services.exceptions.OperationFailedException;
import com.springboot.systems.system_wide.services.exceptions.ValidationFailedException;
import com.springboot.systems.system_wide.models.core.setup.ServiceZone;

public interface ServiceZoneService extends GenericService<ServiceZone> {

    /**
     * Returns a {@link ServiceZone} with a name matching what has been supplied
     *
     * @param name, the name of the {@link ServiceZone} to be returned
     * @return {@link ServiceZone}
     */
    ServiceZone getServiceZoneByName(String name) throws ValidationFailedException, OperationFailedException;
}
