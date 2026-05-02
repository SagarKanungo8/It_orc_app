package com.ncs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ncs.model.Address;

public interface AddressRepo extends JpaRepository<Address, Long>{

}
