package com.natay.ecomm.bakery.user;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static com.natay.ecomm.bakery.user.UserRegistrationDtoFactory.createUserRegistrationDtoWithAddressDetails;
import static com.natay.ecomm.bakery.user.UserRegistrationDtoFactory.createUserRegistrationDtoWithEmailAndPassword;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SoftAssertionsExtension.class)
public class UserRegistrationTests {

    private final String email = "harryp@gmail.com";
    private final String password = "harrypass";
    private final String addressLine1 = "2A Silver Count";
    private final String addressLine2 = "1 High Street";
    private final String postcode = "PO1 2ST";

    @InjectSoftAssertions
    private SoftAssertions softly;

    private UserRegistrationService userRegistrationService;
    private AccountService userAccountService;
    private AddressService userAddressService;

    @BeforeEach
    public void setUp() {
        userAccountService = new UserAccountService();
        userAddressService = new UserAddressService();
        userRegistrationService = new UserRegistrationService(userAccountService, userAddressService);
    }

    @Test
    public void shouldRegisterAccountForNewUser() {
        RegistrationDto dto = createUserRegistrationDtoWithEmailAndPassword(email, password);

        userRegistrationService.register(dto);

        Optional<UserAccount> userAccount = userAccountService.findAccountByEmail(email);
        assertThat(userAccount)
                .isNotEmpty()
                .hasValueSatisfying((acct) -> {
                    softly.assertThat(acct.email()).isEqualTo(email);
                    softly.assertThat(acct.password()).isEqualTo(password);
                });
    }

    @Test
    public void shouldRegisterAddressForNewUser() {
        RegistrationDto dto = createUserRegistrationDtoWithAddressDetails(addressLine1, addressLine2, postcode);

        userRegistrationService.register(dto);

        Optional<UserAddress> userAddress = userAddressService.findAddressByEmail(email);
        assertThat(userAddress)
                .isNotEmpty()
                .hasValueSatisfying((addr) -> {
                    softly.assertThat(addr.addressLine1()).isEqualTo(addressLine1);
                    softly.assertThat(addr.addressLine2()).isEqualTo(addressLine2);
                    softly.assertThat(addr.postcode()).isEqualTo(postcode);
                });
    }
}
