package com.natay.ecomm.bakery.registration;


import com.natay.ecomm.bakery.account.*;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.natay.ecomm.bakery.registration.RegistrationDtoFactory.*;
import static com.natay.ecomm.bakery.testutils.RandomUtil.randomEmail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author natayeung
 */
@SpringBootTest
@ActiveProfiles("dev")
@Import(RegistrationTestConfig.class)
@ExtendWith(SoftAssertionsExtension.class)
public class UserRegistrationTests {

    private final String password = "harrypass";
    private final String addressLine1 = "2A Silver Count";
    private final String addressLine2 = "1 High Street";
    private final String postcode = "PO1 2ST";

    @InjectSoftAssertions
    private SoftAssertions softly;

    private UserRegistrationService userRegistrationService;

    @Autowired
    private AccountService accountService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        userRegistrationService = new UserRegistrationService(accountService, addressService);
    }

    @Test
    public void shouldRegisterAccountForNewUser() {
        final String email = randomEmail();
        RegistrationDto dto = createRegistrationDtoWithEmailAndPassword(email, password);

        userRegistrationService.register(dto);

        Optional<UserAccount> foundUserAccount = accountService.findAccountByEmail(email);
        assertThat(foundUserAccount)
                .isNotEmpty()
                .hasValueSatisfying((acct) -> {
                    softly.assertThat(acct.email()).isEqualTo(email);
                    softly.assertThat(passwordEncoder.matches(password, acct.password())).isTrue();
                });
    }

    @Test
    public void shouldRegisterAddressForNewUser() {
        RegistrationDto dto = createRegistrationDtoWithAddressDetails(addressLine1, addressLine2, postcode);

        userRegistrationService.register(dto);

        Optional<UserAddress> foundUserAddress = addressService.findAddressByEmail(dto.getEmail());
        assertThat(foundUserAddress)
                .isNotEmpty()
                .hasValueSatisfying((addr) -> {
                    softly.assertThat(addr.addressLine1()).isEqualTo(addressLine1);
                    softly.assertThat(addr.addressLine2()).isEqualTo(addressLine2);
                    softly.assertThat(addr.postcode()).isEqualTo(postcode);
                });
    }

    @Test
    public void sameEmailCannotBeUsedMoreThanOnceForRegistration() {
        final String email = randomEmail();
        RegistrationDto dto = createRegistrationDtoWithEmail(email);
        userRegistrationService.register(dto);

        assertThatThrownBy(() -> userRegistrationService.register(dto))
                .isInstanceOf(EmailAlreadyUsedException.class)
                .hasMessageContaining(email);
    }
}
