package com.springboot.systems.test.services.core.setup;

import com.springboot.systems.test.services.dao.GenericService;
import com.springboot.systems.test.services.exceptions.OperationFailedException;
import com.springboot.systems.test.services.exceptions.ValidationFailedException;
import com.springboot.systems.test.models.core.setup.ServiceZone;

public interface ServiceZoneService extends GenericService<ServiceZone> {

    /**
     * Returns a {@link ServiceZone} with a name matching what has been supplied
     *
     * @param name, the name of the {@link ServiceZone} to be returned
     * @return {@link ServiceZone}
     */
    ServiceZone getServiceZoneByName(String name) throws ValidationFailedException, OperationFailedException;
}
