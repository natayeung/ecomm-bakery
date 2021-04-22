package com.natay.ecomm.bakery.registration;


import com.natay.ecomm.bakery.account.*;
import com.natay.ecomm.bakery.account.persistence.AccountDatabaseAdapter;
import com.natay.ecomm.bakery.account.persistence.AddressDatabaseAdapter;
import com.natay.ecomm.bakery.registration.dto.RegistrationDto;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static com.natay.ecomm.bakery.testutils.RegistrationDtoFactory.*;
import static com.natay.ecomm.bakery.testutils.RandomUtil.randomEmail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

/**
 * @author natayeung
 */
@JdbcTest()
@AutoConfigureTestDatabase(replace = NONE)
@ContextConfiguration(classes = {
        AddressDatabaseAdapter.class,
        AccountDatabaseAdapter.class,
        BCryptPasswordEncoder.class,
        UserAddressService.class,
        UserAccountService.class})
@ActiveProfiles("dev")
@EnableEncryptableProperties
@ExtendWith(SoftAssertionsExtension.class)
public class UserRegistrationTests {

    private final String password = "jdpass";
    private final String addressLine1 = "2A Silver Count";
    private final String addressLine2 = "1 High Street";
    private final String townOrCity = "London";
    private final String postcode = "E15 2GU";

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

        Optional<Account> foundUserAccount = accountService.findAccountByEmail(email);
        assertThat(foundUserAccount)
                .isNotEmpty()
                .hasValueSatisfying((acct) -> {
                    softly.assertThat(acct.email()).isEqualTo(email);
                    softly.assertThat(passwordEncoder.matches(password, acct.password())).isTrue();
                });
    }

    @Test
    public void shouldRegisterAddressForNewUser() {
        RegistrationDto dto = createRegistrationDtoWithAddressDetails(addressLine1, addressLine2, townOrCity, postcode);

        userRegistrationService.register(dto);

        Optional<Address> foundUserAddress = addressService.findAddressByEmail(dto.getEmail());
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
