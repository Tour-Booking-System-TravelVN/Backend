package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.UserAccountAdRequest;
import com.travelvn.tourbookingsytem.dto.response.UserAccountAdResponse;
import com.travelvn.tourbookingsytem.service.UserAccountAdService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-accounts")
public class UserAccountAdController {

    @Autowired
    private UserAccountAdService userAccountAdService;

    @GetMapping
    public ResponseEntity<List<UserAccountAdResponse>> getAllUserAccounts() {
        return ResponseEntity.ok(userAccountAdService.getAllUserAccounts());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserAccountAdResponse> getUserAccountByUsername(@PathVariable String username) {
        UserAccountAdResponse userAccount = userAccountAdService.getUserAccountByUsername(username);
        return userAccount != null ? ResponseEntity.ok(userAccount) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<UserAccountAdResponse> createUserAccount(@Valid @RequestBody UserAccountAdRequest userAccountAdRequest) {
        return ResponseEntity.ok(userAccountAdService.createUserAccount(userAccountAdRequest));
    }

    @PutMapping("/{username}")
    public ResponseEntity<UserAccountAdResponse> updateUserAccount(@PathVariable String username, @Valid @RequestBody UserAccountAdRequest userAccountAdRequest) {
        UserAccountAdResponse updatedUserAccount = userAccountAdService.updateUserAccount(username, userAccountAdRequest);
        return updatedUserAccount != null ? ResponseEntity.ok(updatedUserAccount) : ResponseEntity.notFound().build();
    }

//    @DeleteMapping("/{username}")
//    public ResponseEntity<Void> deleteUserAccount(@PathVariable String username) {
//        userAccountAdService.deleteUserAccount(username);
//        return ResponseEntity.noContent().build();
//    }
}