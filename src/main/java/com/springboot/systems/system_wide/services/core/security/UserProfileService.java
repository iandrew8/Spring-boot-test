package com.springboot.systems.system_wide.services.core.security;

import com.springboot.systems.system_wide.services.dao.GenericService;
import com.springboot.systems.system_wide.models.core.security.UserProfile;
import com.springboot.systems.system_wide.models.core.security.User;

public interface UserProfileService extends GenericService<UserProfile> {

    /**
     * @param user, the {@link User} of the {@link UserProfile} to be retrieved
     * @return {@link UserProfile}
     */
    UserProfile getUserProfileByUser(User user);

    /**
     * This method will be used to send out mails to the newly created user
     * @param userProfile, the {@link UserProfile} of the {@link User} to be sent the email
     */
    void sendUserCreationEmail(UserProfile userProfile) throws Exception;
}
