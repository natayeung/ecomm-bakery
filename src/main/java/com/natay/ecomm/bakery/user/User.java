package com.natay.ecomm.bakery.user;

public class User {

    private final UserAccount account;
    private final UserAddress address;

    private User(UserAccount account, UserAddress address) {
        this.account = account;
        this.address = address;
    }

    public static User with(UserAccount registeredAccount, UserAddress registeredAddress) {
        return new User(registeredAccount, registeredAddress);
    }

    public UserAccount account() {
        return account;
    }

    public UserAddress address() {
        return address;
    }

    @Override
    public String toString() {
        return "User{" +
                "account=" + account +
                ", address=" + address +
                '}';
    }
}
