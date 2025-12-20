package com.example.services;

import com.example.entities.Address;

import java.util.List;

public interface AddressService {
    // CREATE
    Address addAddress(Long userId, Address address);
    // READ
    Address getAddressById(Long addressId);
    List<Address> getAddressesByUser(Long userId);
    // UPDATE
    Address updateAddress(Long userId,Long addressId, Address address);

    // DELETE
    void deleteAddress(Long addressId);

}
