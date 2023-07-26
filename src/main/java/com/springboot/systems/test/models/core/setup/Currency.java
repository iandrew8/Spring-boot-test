package com.springboot.systems.test.models.core.setup;

import com.springboot.systems.test.models.core.auditable.Auditable;
import com.springboot.systems.test.services.exceptions.ValidationFailedException;
import com.springboot.systems.test.services.utils.Validate;
import lombok.Setter;

import javax.persistence.*;

/**
 * It will hold all currencies used to bill different ISPs.
 * @author ttc
 */
@Entity
@Table(name = "currencies")
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
public class Currency extends Auditable {
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
        return object instanceof Currency && (getId() != null) ? getId().equals(((Currency) object).getId()) : (object == this);
    }

    @Override
    public int hashCode() {
        return getId() != null ? this.getClass().hashCode() + getId().hashCode() : super.hashCode();
    }

}
