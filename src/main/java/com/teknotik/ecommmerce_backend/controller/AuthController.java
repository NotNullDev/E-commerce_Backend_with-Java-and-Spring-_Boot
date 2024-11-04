package com.teknotik.ecommmerce_backend.controller;


import com.teknotik.ecommmerce_backend.dto.RegisterResponse;
import com.teknotik.ecommmerce_backend.dto.RegistrationStore;
import com.teknotik.ecommmerce_backend.dto.RegistrationUser;
import com.teknotik.ecommmerce_backend.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationService authenticationService;



    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }



    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegistrationUser registrationUser) {
        switch (registrationUser.roleId()) {
            case 1:
                authenticationService.adminRegister(registrationUser.name(), registrationUser.email(), registrationUser.password());
                return new RegisterResponse(registrationUser.email(), "Registration happened successfully");
            case 3:
                authenticationService.customerRegister(registrationUser.name(), registrationUser.email(), registrationUser.password());
                return new RegisterResponse(registrationUser.email(), "Registration happened successfully");
        }
        throw new RuntimeException();
    }

    @PostMapping("/storeRegister")
    public RegisterResponse storeRegister(@RequestBody RegistrationStore registrationStore){
        authenticationService.storeRegister(registrationStore.name(), registrationStore.email(),
                registrationStore.password(), registrationStore.storeName(),
                registrationStore.storePhone(), registrationStore.storeTaxId(), registrationStore.storeBankAccount());
        return new RegisterResponse(registrationStore.email(), "Registration happened successfully");
    }


}