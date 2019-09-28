package com.lotus.smallroutine.address.mapper;

import java.util.List;

import com.lotus.smallroutine.address.model.Address;

public interface AddressMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(Address record);

    Address selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Address record);

	List<Address> selectClientAddresss(Address address);

}