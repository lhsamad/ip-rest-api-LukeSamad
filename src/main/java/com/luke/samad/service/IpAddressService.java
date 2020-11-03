package com.luke.samad.service;

import com.luke.samad.domain.Address;
import com.luke.samad.domain.Cidr;
import com.luke.samad.dto.CidrDto;
import com.luke.samad.dto.IpDto;
import com.luke.samad.repository.AddressRepository;
import com.luke.samad.repository.CidrRepository;
import org.apache.commons.net.util.SubnetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Transactional
@Service
public class IpAddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CidrRepository cidrRepository;

    public String addCidr(String cidr) {
        Cidr address = Cidr.builder().cidr(cidr).build();
        address.setAddresses(createAddresses(cidr, address));
        cidrRepository.save(address);
        return cidr + " added successfully";
    }

    public CidrDto getCidr(String cidr) {
        return toCidrDto(cidrRepository.findByCidr(cidr));
    }

    public List<CidrDto> getAllCidrs() {
        List<CidrDto> result = new ArrayList<>();
        Iterable<Cidr> iterable = cidrRepository.findAll();
        if (iterable != null) {
            Set<Cidr> cidrs = StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toSet());
            for(Cidr cidr : cidrs) {
                result.add(toCidrDto(cidr));
            }
        }
        return result;
    }

    private CidrDto toCidrDto(Cidr c) {
        CidrDto dto = CidrDto.builder().cidr(c.getCidr()).build();
        Set<IpDto> ips = new HashSet<>();
        for (Address a : c.getAddresses()) {
            ips.add(IpDto.builder().ip(a.getIp()).status(a.getStatus()).build());
        }
        dto.setIps(ips);
        return dto;
    }

    public String acquired(String ip) {
        updateStatus(ip, "acquired");
        return ip + " acquired successfully`";
    }

    public String release(String ip) {
        updateStatus(ip, "available");
        return ip + " release successfully`";
    }

    private void updateStatus(String ip, String status) {
        Address address = addressRepository.findByIp(ip);
        address.setStatus(status);
        addressRepository.save(address);
    }

    private Set<Address> createAddresses(String cidr, Cidr address) {
        List<String> cidrRange = this.calculateRange(cidr);
        Set<Address> addresses = new HashSet(cidrRange.size());
        for (String ip : cidrRange) {
            addresses.add(Address.builder().status("available").ip(ip).address(address).build());
        }
        return addresses;
    }

    private List<String> calculateRange(String subnet) {
        List<String> lstAvailableIps = new ArrayList<>();
        SubnetUtils utils = new SubnetUtils(subnet);
        SubnetUtils.SubnetInfo subnetInfo = utils.getInfo();
        String[] arrIpAddresses = subnetInfo.getAllAddresses();
        for (String ipAddress : arrIpAddresses) {
            lstAvailableIps.add(ipAddress);
        }
        return lstAvailableIps;
    }

}
