package com.springboot.systems.test.models.core.setup;

import com.springboot.systems.test.models.core.auditable.Auditable;
import com.springboot.systems.test.models.enums.DiscountStatus;
import lombok.Setter;

import javax.persistence.*;

/**
 * It will hold all various regions served and indicate whether they have discounts applied to them.
 * @author ttc
 */
@Entity
@Table(name = "service_zones")
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
public class ServiceZone extends Auditable {
    private String name;
    // This will specify whether a service zone has an NRC discount applied to it or not.
    private DiscountStatus NRCDiscountStatus;

    public ServiceZone() {
        this.NRCDiscountStatus = DiscountStatus.APPLIED;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "nrc_discount_status")
    @Enumerated(EnumType.STRING)
    public DiscountStatus getNRCDiscountStatus() {
        return NRCDiscountStatus;
    }

    @Override
    public String toString() {
        return this.name + " - " + this.NRCDiscountStatus.getName();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof ServiceZone && (getId() != null) ? getId().equals(((ServiceZone) object).getId()) : (object == this);
    }

    @Override
    public int hashCode() {
        return getId() != null ? this.getClass().hashCode() + getId().hashCode() : super.hashCode();
    }

}
