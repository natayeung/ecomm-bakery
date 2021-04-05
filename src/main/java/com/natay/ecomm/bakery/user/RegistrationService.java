package com.natay.ecomm.bakery.user;

import com.natay.ecomm.bakery.user.dto.RegistrationDto;

/**
 * @author natayeung
 */
public interface RegistrationService {

    User register(RegistrationDto dto);
}
