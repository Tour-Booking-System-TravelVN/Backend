package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.request.UserAccountRequest;
import com.travelvn.tourbookingsytem.dto.response.UserAccountResponse;
import com.travelvn.tourbookingsytem.exception.AppException;
import com.travelvn.tourbookingsytem.exception.ErrorCode;
import com.travelvn.tourbookingsytem.mapper.UserAccountMapper;
import com.travelvn.tourbookingsytem.model.UserAccount;
import com.travelvn.tourbookingsytem.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    private final UserAccountMapper userAccountMapper;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

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
        userAccount.setPassword(passwordEncoder.encode(userAccountRequest.getPassword()));
        userAccountRepository.save(userAccount);
        return true;
    }

    /**
     * Kiểm tra username và password có đúng không
     *
     * @param userAccountRequest Tài khoản đăng nhập
     * @return Kết quả đăng nhập
     */
    public boolean authenticate(UserAccountRequest userAccountRequest) {
        var user = userAccountRepository.findById(userAccountRequest.getUsername())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        return passwordEncoder.matches(userAccountRequest.getPassword(), user.getPassword());
    }

    /**
     * Lấy thông tin của mình bằng Security
     * username = name trong token
     *
     * @return Thông tin tài khoản
     */
    public UserAccountResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        UserAccount account = userAccountRepository.findById(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userAccountMapper.toUserAccountResponseWithoutCustomer(account);
    }
}
