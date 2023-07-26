package com.springboot.systems.test.models.core.setup;

import com.springboot.systems.test.models.core.auditable.Auditable;
import lombok.Setter;

import javax.persistence.*;

/**
 * It will hold all application settings details.
 * @author ttc
 */
@Entity
@Table(name = "application_settings")
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
public class ApplicationSetting extends Auditable {
    private String systemEmail, systemEmailPassword, systemEmailHost, systemEmailPort,
    systemEmailSender;

    /**
     * This is the email address meant to be filled in the "From" field whenever an email is being sent
     * @return the systemEmailSender
     */
    @Column(name = "system_email_sender")
    public String getSystemEmailSender() {
        return systemEmailSender;
    }

    /**
     * This is the email that will be used to create the email session with the gmail smtp host
     * @return the systemEmail
     */
    @Column(name = "system_email")
    public String getSystemEmail() {
        return systemEmail;
    }

    @Column(name = "system_email_password")
    public String getSystemEmailPassword() {
        return systemEmailPassword;
    }

    @Column(name = "system_email_host")
    public String getSystemEmailHost() {
        return systemEmailHost;
    }

    @Column(name = "system_email_port")
    public String getSystemEmailPort() {
        return systemEmailPort;
    }

    @Override
    public String toString() {
        return "System Email: "+this.systemEmail;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof ApplicationSetting && (getId() != null) ? getId().equals(((ApplicationSetting) object).getId()) : (object == this);
    }

    @Override
    public int hashCode() {
        return getId() != null ? this.getClass().hashCode() + getId().hashCode() : super.hashCode();
    }

}
