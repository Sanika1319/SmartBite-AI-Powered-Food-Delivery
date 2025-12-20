package com.example.controllers;

import com.example.entities.Address;
import com.example.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @PostMapping("/add/{userId}")
    public ResponseEntity<Address> addAddress(@PathVariable Long userId, @RequestBody Address address){
        return new ResponseEntity<>(addressService.addAddress(userId,address), HttpStatus.CREATED);
    }

    @GetMapping("/getAddressById/{addressId}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long addressId){
        return new ResponseEntity<>(addressService.getAddressById(addressId),HttpStatus.OK);
    }

    @GetMapping("/getAddressesByUser/{userId}")
    public ResponseEntity<List<Address>> getAddressesByUser(@PathVariable Long userId){
        return new ResponseEntity<>(addressService.getAddressesByUser(userId),HttpStatus.OK);
    }

    @PutMapping("/updateAddress/{userId}/{addressId}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long userId,
                                                 @PathVariable Long addressId,
                                                 @RequestBody Address address){
        return new ResponseEntity<>(addressService.updateAddress(userId,addressId,address),HttpStatus.OK);
    }

    @DeleteMapping("/deleteAddress/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long addressId){
        addressService.deleteAddress(addressId);
        return ResponseEntity.ok("Address Deleted Successfully");
    }
}
