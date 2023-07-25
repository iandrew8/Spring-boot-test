package com.springboot.systems.system_wide.models.core.setup;

import com.springboot.systems.system_wide.models.core.auditable.Auditable;
import lombok.Setter;

import javax.persistence.*;

/**
 * It will hold the various packages that can be provided to the clients.
 * @author ttc
 */
@Entity
@Table(name = "data_packages")
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
public class DataPackage extends Auditable {
    private double quantity;
    private UnitOfMeasure unitOfMeasure;

    @Column(name = "quantity")
    public double getQuantity() {
        return quantity;
    }

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "unit_of_measure_id", nullable = false)
    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    @Override
    public String toString() {
        return this.quantity+" "+this.unitOfMeasure.getUnit();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof DataPackage && (getId() != null) ? getId().equals(((DataPackage) object).getId()) : (object == this);
    }

    @Override
    public int hashCode() {
        return getId() != null ? this.getClass().hashCode() + getId().hashCode() : super.hashCode();
    }

}
