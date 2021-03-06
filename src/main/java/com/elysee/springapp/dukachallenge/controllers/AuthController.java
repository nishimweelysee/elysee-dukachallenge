package com.elysee.springapp.dukachallenge.controllers;

import com.elysee.springapp.dukachallenge.domain.TaskOwner;
import com.elysee.springapp.dukachallenge.payloads.GeneralResponsePayload;
import com.elysee.springapp.dukachallenge.payloads.JwtAccessTokenResponsePayload;
import com.elysee.springapp.dukachallenge.payloads.request.LoginPayload;
import com.elysee.springapp.dukachallenge.payloads.request.SignupPayload;
import com.elysee.springapp.dukachallenge.services.TaskOwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    private final TaskOwnerService ownerService;
    public AuthController(TaskOwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<GeneralResponsePayload> registerUser(@RequestBody @Valid SignupPayload signupPayload){
        TaskOwner user = ownerService.register(signupPayload);
        log.info("user "+signupPayload+" is ");
        return new ResponseEntity<GeneralResponsePayload>(GeneralResponsePayload.builder()
            .statusCode(HttpStatus.CREATED.toString())
                .data(user)
                .message("Your registration was completed successfully").build(), HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAccessTokenResponsePayload> loginUser(@RequestBody @Valid LoginPayload loginPayload){
        log.info("User "+loginPayload.getUsername()+"  is Logging in");
        JwtAccessTokenResponsePayload loginResponse = ownerService.ownerLogin(loginPayload);
        return new ResponseEntity<JwtAccessTokenResponsePayload>(loginResponse, HttpStatus.OK);
    }
}
