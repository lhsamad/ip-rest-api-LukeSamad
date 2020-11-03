package com.luke.samad.service;

import com.luke.samad.domain.Address;
import com.luke.samad.domain.Cidr;
import com.luke.samad.dto.CidrDto;
import com.luke.samad.repository.AddressRepository;
import com.luke.samad.repository.CidrRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CidrServiceTest {

    @InjectMocks
    CidrService cidrService;

    @Mock
    AddressRepository addressRepository;

    @Mock
    CidrRepository cidrRepository;

    @Before
    public void init(){
        doReturn(getCidrDto()).when(cidrRepository).findByCidr(anyString());
        doReturn(getCidrDtos()).when(cidrRepository).findAll();
    }

    @Test
    public void addCidr(){
        assertEquals("10.0.0.1/24 added successfully", cidrService.addCidr("10.0.0.1/24"));
    }

    @Test
    public void getCidr() {
        CidrDto cidrDto = cidrService.getCidr("10.0.0.1/24");
        assertNotNull(cidrDto);
    }

    @Test
    public void getCidrs() {
        List<CidrDto> cidrs = cidrService.getAllCidrs();
        assertNotNull(cidrs);
    }

    @Test
    public void acquired() {
        Address address = Address.builder().id(1l).ip("10.0.0.227").status("available").build();
        doReturn(address).when(addressRepository).findByIp(anyString());
        address.setStatus("acquired");
        doReturn(address).when(addressRepository).save(any());
        assertEquals(cidrService.acquired("10.0.0.227"), "10.0.0.227 acquired successfully");
        assertEquals(address.getStatus(), "acquired");

    }

    @Test
    public void release() {
        Address address = Address.builder().id(1l).ip("10.0.0.227").status("acquired").build();
        doReturn(address).when(addressRepository).findByIp(anyString());
        address.setStatus("available");
        doReturn(address).when(addressRepository).save(any());
        assertEquals(cidrService.release("10.0.0.227"), "10.0.0.227 released successfully");
        assertEquals(address.getStatus(), "available");
    }

    private Iterable<Cidr> getCidrDtos(){
        List<Cidr> cidrs = new ArrayList<>();
        cidrs.add(getCidrDto());
        cidrs.add(getCidrDto());
        return cidrs;
    }

    private Cidr getCidrDto(){
        Cidr cidr = Cidr.builder().id(1l).cidr("10.0.0.1/24").build();
        Set<Address> addresses = new HashSet<>();
        addresses.add(new Address(1L, "10.0.0.221", "available", cidr));
        addresses.add(new Address(2L, "10.0.0.222", "available", cidr));
        addresses.add(new Address(3L, "10.0.0.223", "available", cidr));
        cidr.setAddresses(addresses);
        return cidr;
    }
}
