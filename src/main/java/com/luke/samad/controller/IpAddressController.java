package com.luke.samad.controller;

import com.luke.samad.dto.CidrDto;
import com.luke.samad.dto.CidrInputDto;
import com.luke.samad.service.IpAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class IpAddressController {

    @Autowired
    private IpAddressService service;

    @PostMapping(value = "/cidr")
    private String addCidr(@RequestBody CidrInputDto input) {
        service.addCidr(input.getCidr());
        return input.getCidr() + " added successfully";
    }

    @GetMapping(value = "/cidrs")
    private List<CidrDto> getAllCidrs() {
        return service.getAllCidrs();
    }

    @GetMapping(value = "/cidr")
    private CidrDto getCidr(@PathParam("cidr") String cidr) {
        return service.getCidr(cidr);
    }

    @GetMapping(value = "/acquire/{ip}")
    private String acquired(@PathVariable("ip") String ip) {
        return service.acquired(ip);
    }

    @GetMapping(value = "/release/{ip}")
    private String release(@PathVariable("ip") String ip) {
        return service.release(ip);
    }

}
