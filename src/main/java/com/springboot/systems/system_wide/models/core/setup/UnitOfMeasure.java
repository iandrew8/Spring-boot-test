package com.springboot.systems.system_wide.models.core.setup;

import com.springboot.systems.system_wide.models.core.auditable.Auditable;
import com.springboot.systems.system_wide.services.exceptions.ValidationFailedException;
import com.springboot.systems.system_wide.services.utils.Validate;
import lombok.Setter;

import javax.persistence.*;

/**
 * It will hold all the units used to measure the Internet speeds provided to
 * the clients
 * @author ttc
 */
@Entity
@Table(name = "units_of_measure")
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
public class UnitOfMeasure extends Auditable {
    private String name, unit;

    public void validate() throws ValidationFailedException {
        Validate.notNull(this.getName(), "Name is required");
        Validate.notNull(this.getUnit(), "Unit is required");
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "unit")
    public String getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof UnitOfMeasure && (getId() != null) ? getId().equals(((UnitOfMeasure) object).getId()) : (object == this);
    }

    @Override
    public int hashCode() {
        return getId() != null ? this.getClass().hashCode() + getId().hashCode() : super.hashCode();
    }

}
