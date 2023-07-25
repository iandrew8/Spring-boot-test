package com.springboot.systems.system_wide.models.core.setup;

import com.springboot.systems.system_wide.models.core.auditable.Auditable;
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
    private String clickUpPrivateKey, smartOLTAPIKey, systemEmail, systemEmailPassword, systemEmailHost, systemEmailPort,
    systemEmailSender, googleDriveFolderId;
    private Currency systemCurrency;

    /**
     * This is the email address meant to be filled in the "From" field whenever an email is being sent
     * @return the systemEmailSender
     */
    @Column(name = "system_email_sender")
    public String getSystemEmailSender() {
        return systemEmailSender;
    }

    /**
     * This is the folder id of the google drive folder where all the files will be uploaded to
     * @return
     */
    @Column(name = "google_drive_folder_id")
    public String getGoogleDriveFolderId() {
        return googleDriveFolderId;
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

    @Column(name = "click_up_private_key")
    public String getClickUpPrivateKey() {
        return clickUpPrivateKey;
    }

    @Column(name = "smart_olt_api_key")
    public String getSmartOLTAPIKey() {
        return smartOLTAPIKey;
    }

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "system_currency_id", nullable = false)
    public Currency getSystemCurrency() {
        return systemCurrency;
    }

    @Override
    public String toString() {
        return "ClickUp Private Key: "+this.clickUpPrivateKey;
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
