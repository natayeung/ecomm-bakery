package com.natay.ecomm.bakery.account;

import com.natay.ecomm.bakery.registration.AccountDto;
import com.natay.ecomm.bakery.registration.RegistrationDto;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static com.natay.ecomm.bakery.account.AccountDtoFactory.createAccountDtoWithAddress;
import static com.natay.ecomm.bakery.registration.RegistrationDtoFactory.createRegistrationDtoWithAddressDetails;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

/**
 * @author natayeung
 */
@JdbcTest
@AutoConfigureTestDatabase(replace = NONE)
@ContextConfiguration(classes = {
        AddressDatabaseAdapter.class,
        UserAddressService.class})
@ActiveProfiles("dev")
@EnableEncryptableProperties
@ExtendWith(SoftAssertionsExtension.class)
public class AccountUpdateTests {

    private final String addressLine1 = "2A Silver Count";
    private final String addressLine2 = "12 High Street";
    private final String townOrCity = "Potters Bar";
    private final String postcode = "EN6 5AF";
    private final String newAddressLine1 = "12 High Street";
    private final String newAddressLine2 = "";
    private final String newTownOrCity = "London";
    private final String newPostCode = "E15 2GU";

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
        RegistrationDto registrationDto = createRegistrationDtoWithAddressDetails(addressLine1, addressLine2, townOrCity, postcode);
        String email = registrationDto.getEmail();
        addressService.registerAddress(registrationDto);
        AccountDto accountDto = createAccountDtoWithAddress(email, newAddressLine1, newAddressLine2, newTownOrCity, newPostCode);

        addressService.updateAddress(accountDto);

        Optional<Address> foundUserAddress = addressService.findAddressByEmail(email);
        assertThat(foundUserAddress)
                .isNotEmpty()
                .hasValueSatisfying((a) -> {
                    softly.assertThat(a.addressLine1()).isEqualTo(newAddressLine1);
                    softly.assertThat(a.addressLine2()).isEqualTo(newAddressLine2);
                    softly.assertThat(a.townOrCity()).isEqualTo(newTownOrCity);
                    softly.assertThat(a.postcode()).isEqualTo(newPostCode);
                });
    }
}
