package com.natay.ecomm.bakery.account;

import com.natay.ecomm.bakery.registration.AddressDto;
import com.natay.ecomm.bakery.registration.RegistrationDto;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.natay.ecomm.bakery.registration.RegistrationDtoFactory.createRegistrationDtoWithAddressDetails;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author natayeung
 */
@SpringBootTest
@ActiveProfiles("dev")
@Import(AccountTestConfig.class)
@ExtendWith(SoftAssertionsExtension.class)
public class AccountUpdateTests {

    private final String addressLine1 = "2A Silver Count";
    private final String addressLine2 = "1 High Street";
    private final String postcode = "PO1 2ST";
    private final String newAddressLine1 = "1B Golden Court";
    private final String newAddressLine2 = "1 Middle Street";
    private final String newPostCode = "PO3 1ST";

    @InjectSoftAssertions
    private SoftAssertions softly;

    @Autowired
    private AddressPersistencePort addressPersistencePort;

    private UserAddressService addressService;

    @BeforeEach
    public void setUp() {
        addressService = new UserAddressService(addressPersistencePort);
    }

    @Test
    public void shouldUpdateUserAddress() {
        RegistrationDto registrationDto = createRegistrationDtoWithAddressDetails(addressLine1, addressLine2, postcode);
        String email = registrationDto.getEmail();
        addressService.registerAddress(registrationDto);
        AddressDto addressDto = AddressDtoFactory.createAddressDto(newAddressLine1, newAddressLine2, newPostCode);

        addressService.updateAddress(email, addressDto);

        Optional<UserAddress> foundUserAddress = addressService.findAddressByEmail(email);

        assertThat(foundUserAddress)
                .isNotEmpty()
                .hasValueSatisfying((addr) -> {
                    softly.assertThat(addr.addressLine1()).isEqualTo(newAddressLine1);
                    softly.assertThat(addr.addressLine2()).isEqualTo(newAddressLine2);
                    softly.assertThat(addr.postcode()).isEqualTo(newPostCode);
                });
    }
}
