package  com.lotus.smallroutine.customer.mapper;

import com.lotus.smallroutine.customer.model.Customer;

public interface CustomerMapper {
  
    int insertSelective(Customer record);

    int deleteByPrimaryKey(Long id);
    
    Customer selectByPrimaryKey(Long id);
    
    Customer selectCustomer(Customer customer);

    int updateByPrimaryKeySelective(Customer record);
    
}