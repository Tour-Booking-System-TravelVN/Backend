package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.response.UserAccountResponse;
import com.travelvn.tourbookingsytem.mapper.UserAccountMapper;
import com.travelvn.tourbookingsytem.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    private final UserAccountMapper userAccountMapper;

}
