package com.example.services.serviceImpl;

import com.example.Exception.AddressException;
import com.example.Exception.AddressNotFoundException;
import com.example.Exception.UserIdNotFoundException;
import com.example.entities.Address;
import com.example.entities.User;
import com.example.repository.AddressRepository;
import com.example.repository.UserRepository;
import com.example.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public Address addAddress(Long userId, Address address) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserIdNotFoundException("User not found: " + userId));
        address.setUser(user);
        return addressRepository.save(address);
    }

    @Override
    public Address getAddressById(Long addressId) {
        return addressRepository.findById(addressId).orElseThrow(()-> new AddressNotFoundException("Address Not Found: "+addressId));
    }

    @Override
    public List<Address> getAddressesByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserIdNotFoundException("User Not Found : " + userId));
        return addressRepository.findByUser(user);
    }

    @Override
    public Address updateAddress(Long userId,Long addressId, Address address) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserIdNotFoundException("User Not Found with given id: " + userId));
        Address address1 = addressRepository.findById(addressId).orElseThrow(() -> new AddressNotFoundException("Address Not Found: " + addressId));
        // Ensure address belongs to user
        if(!address1.getUser().getUserId().equals(userId)){
            throw new AddressException("You are not allowed to change address");
        }
        address1.setStreet(address.getStreet());
        address1.setCity(address.getCity());
        address1.setState(address.getState());
        address1.setPincode(address.getPincode());
        address1.setType(address.getType());
        return addressRepository.save(address1);
    }

    @Override
    public void deleteAddress(Long addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new AddressNotFoundException("Address Not Found: " + addressId));
        addressRepository.delete(address);

    }
}
