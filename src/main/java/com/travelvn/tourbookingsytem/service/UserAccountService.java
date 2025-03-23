package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.request.UserAccountRequest;
import com.travelvn.tourbookingsytem.dto.response.UserAccountResponse;
import com.travelvn.tourbookingsytem.exception.AppException;
import com.travelvn.tourbookingsytem.exception.ErrorCode;
import com.travelvn.tourbookingsytem.mapper.UserAccountMapper;
import com.travelvn.tourbookingsytem.model.UserAccount;
import com.travelvn.tourbookingsytem.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    private final UserAccountMapper userAccountMapper;

    /**
     * Lưu tài khoản nếu chưa tồn tại
     *
     * @param userAccountRequest Tài khoản đăng ký
     * @return Kết quả lưu tài khoản
     */
    public boolean addUserAccount(UserAccountRequest userAccountRequest) {
        UserAccount userAccount = userAccountMapper.toUserAccount(userAccountRequest);
        if (userAccountRepository.findById(userAccount.getUsername()).isPresent()){
            throw new AppException(ErrorCode.USERACCOUNT_EXISTED);
        }
        userAccountRepository.save(userAccount);
        return true;
    }
}
