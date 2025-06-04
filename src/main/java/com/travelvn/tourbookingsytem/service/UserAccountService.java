package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.request.UserAccountRequest;
import com.travelvn.tourbookingsytem.dto.request.customer.UpdateCustomerRequest;
import com.travelvn.tourbookingsytem.dto.request.useraccount.ChangePwdRequest;
import com.travelvn.tourbookingsytem.dto.response.AuthenticationResponse;
import com.travelvn.tourbookingsytem.dto.response.UserAccountResponse;
import com.travelvn.tourbookingsytem.exception.AppException;
import com.travelvn.tourbookingsytem.exception.ErrorCode;
import com.travelvn.tourbookingsytem.mapper.UserAccountMapper;
import com.travelvn.tourbookingsytem.model.UserAccount;
import com.travelvn.tourbookingsytem.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
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
//        log.info("Service");

        UserAccount userAccount = userAccountMapper.toUserAccount(userAccountRequest);
        if (userAccountRepository.findById(userAccount.getUsername()).isPresent()){
            throw new AppException(ErrorCode.USERACCOUNT_EXISTED);
        }

//        log.info("UserAccount: {}", userAccount);
//        log.info("Customer: {}", userAccount.getC());
        userAccount.setPassword(passwordEncoder.encode(userAccountRequest.getPassword()));
        try{
            userAccountRepository.save(userAccount);
        } catch (Exception e){
            e.printStackTrace();
        }

//        log.info("AfterSave");
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
        log.info("SERVICE GETMYINFO");
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        UserAccount account = userAccountRepository.findById(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        log.info(account.toString());
        //log.info(account.getC().toString());
        log.info(userAccountMapper.toUserAccountResponse(account).toString());
        return userAccountMapper.toUserAccountResponse(account);
    }

    /**
     * Đổi mật khẩu
     * @param changePwdRequest Yêu cầu đổi mật khẩu
     * @return Kết quả đổi mật khẩu
     */
    public boolean changePwd(ChangePwdRequest changePwdRequest) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        UserAccount account = userAccountRepository.findById(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if(!passwordEncoder.matches(changePwdRequest.getOldPassword(), account.getPassword()))
            throw new AppException(ErrorCode.UNMATCHED_PWD);

        if(changePwdRequest.getOldPassword().equalsIgnoreCase(changePwdRequest.getNewPassword()))
            throw new AppException(ErrorCode.DUPLICATE_PWD);

        account.setPassword(passwordEncoder.encode(changePwdRequest.getNewPassword()));
        return userAccountRepository.save(account) != null;
    }

    /**
     * Cập nhật thông tin của mình
     * @param updateCustomerRequest Yêu cầu cập nhật thông tin
     * @return Thông tin mới
     */
    public UserAccountResponse updateMyInfo(UpdateCustomerRequest updateCustomerRequest) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        UserAccount account = userAccountRepository.findById(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userAccountMapper.updateUserAccount(updateCustomerRequest, account);
        log.info(account.toString());
        log.info(account.getC().toString());
        userAccountRepository.save(account);
        return userAccountMapper.toUserAccountResponse(account);
    }
}
