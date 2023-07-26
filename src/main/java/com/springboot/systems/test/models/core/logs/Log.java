package com.springboot.systems.test.models.core.logs;

import com.springboot.systems.test.models.core.auditable.Auditable;
import lombok.Setter;

import javax.persistence.*;

/**
 * It will be used to store logs that need to be stored by the system.
 * @author ttc
 */
@Entity
@Table(name = "logs")
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
public class Log extends Auditable {
    private String log;

    @Column(name = "log", columnDefinition = "text")
    public String getLog() {
        return log;
    }

    @Override
    public String toString() {
        return this.log;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Log && (getId() != null) ? getId().equals(((Log) object).getId()) : (object == this);
    }

    @Override
    public int hashCode() {
        return getId() != null ? this.getClass().hashCode() + getId().hashCode() : super.hashCode();
    }

}
