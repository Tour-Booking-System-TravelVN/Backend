package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.request.UserAccountAdRequest;
import com.travelvn.tourbookingsytem.dto.response.UserAccountAdResponse;
import com.travelvn.tourbookingsytem.mapper.UserAccountAdMapper;
import com.travelvn.tourbookingsytem.model.Administrator;
import com.travelvn.tourbookingsytem.model.Customer;
import com.travelvn.tourbookingsytem.model.TourGuide;
import com.travelvn.tourbookingsytem.model.TourOperator;
import com.travelvn.tourbookingsytem.model.UserAccount;
import com.travelvn.tourbookingsytem.repository.AdministratorRepository;
import com.travelvn.tourbookingsytem.repository.CustomerRepository;
import com.travelvn.tourbookingsytem.repository.TourGuideRepository;
import com.travelvn.tourbookingsytem.repository.TourOperatorRepository;
import com.travelvn.tourbookingsytem.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.Normalizer;
import java.util.regex.Pattern;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAccountAdService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private TourGuideRepository tourGuideRepository;

    @Autowired
    private TourOperatorRepository tourOperatorRepository;

    @Autowired
    private UserAccountAdMapper userAccountAdMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserAccountAdResponse> getAllUserAccounts() {
        return userAccountRepository.findAll().stream()
                .map(userAccountAdMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserAccountAdResponse getUserAccountByUsername(String username) {
        return userAccountRepository.findById(username)
                .map(userAccountAdMapper::toResponse)
                .orElse(null);
    }

    @Transactional
    public UserAccountAdResponse createUserAccount(UserAccountAdRequest request) {
        // Kiểm tra username đã tồn tại
        if (userAccountRepository.findById(request.getUsername()).isPresent()) {
            return null;
        }

        // Kiểm tra mật khẩu
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            return null;
        }

        // Kiểm tra chỉ một ID được cung cấp
        int idCount = 0;
        if (request.getCustomerId() != null) idCount++;
        if (request.getAdministratorId() != null) idCount++;
        if (request.getTourGuideId() != null) idCount++;
        if (request.getTourOperatorId() != null) idCount++;
        if (idCount != 1) {
            return null; // Phải cung cấp đúng một ID
        }

        // Kiểm tra và lấy thực thể liên quan
        Customer customer = null;
        Administrator administrator = null;
        TourGuide tourGuide = null;
        TourOperator tourOperator = null;

        if (request.getCustomerId() != null) {
            if (userAccountRepository.existsByCId(request.getCustomerId())) {
                return null; // ID đã được liên kết
            }
            customer = customerRepository.findById(request.getCustomerId()).orElse(null);
            if (customer == null) {
                return null; // ID không tồn tại
            }
        } else if (request.getAdministratorId() != null) {
            if (userAccountRepository.existsByAdministratorId(request.getAdministratorId())) {
                return null; // ID đã được liên kết
            }
            administrator = administratorRepository.findById(request.getAdministratorId()).orElse(null);
            if (administrator == null) {
                return null; // ID không tồn tại
            }
        } else if (request.getTourGuideId() != null) {
            if (userAccountRepository.existsByTourGuideId(request.getTourGuideId())) {
                return null; // ID đã được liên kết
            }
            tourGuide = tourGuideRepository.findById(request.getTourGuideId()).orElse(null);
            if (tourGuide == null) {
                return null; // ID không tồn tại
            }
        } else if (request.getTourOperatorId() != null) {
            if (userAccountRepository.existsByTourOperatorId(request.getTourOperatorId())) {
                return null; // ID đã được liên kết
            }
            tourOperator = tourOperatorRepository.findById(request.getTourOperatorId()).orElse(null);
            if (tourOperator == null) {
                return null; // ID không tồn tại
            }
        }

        // Tạo UserAccount
        UserAccount userAccount = userAccountAdMapper.toEntity(request);
        userAccount.setC(customer);
        userAccount.setAdministrator(administrator);
        userAccount.setTourGuide(tourGuide);
        userAccount.setTourOperator(tourOperator);
        userAccount.setPassword(passwordEncoder.encode(request.getPassword()));

        UserAccount savedUserAccount = userAccountRepository.save(userAccount);
        return userAccountAdMapper.toResponse(savedUserAccount);
    }

    @Transactional
    public UserAccountAdResponse updateUserAccount(String username, UserAccountAdRequest request) {
        UserAccount userAccount = userAccountRepository.findById(username).orElse(null);
        if (userAccount == null) {
            return null;
        }

        // Kiểm tra chỉ một ID được cung cấp
        int idCount = 0;
        if (request.getCustomerId() != null) idCount++;
        if (request.getAdministratorId() != null) idCount++;
        if (request.getTourGuideId() != null) idCount++;
        if (request.getTourOperatorId() != null) idCount++;
        if (idCount > 1) {
            return null; // Chỉ được cung cấp tối đa một ID
        }

        // Kiểm tra và lấy thực thể liên quan
        Customer customer = null;
        Administrator administrator = null;
        TourGuide tourGuide = null;
        TourOperator tourOperator = null;

        if (request.getCustomerId() != null) {
            if (userAccountRepository.existsByCIdAndUsernameNot(request.getCustomerId(), username)) {
                return null; // ID đã được liên kết với tài khoản khác
            }
            customer = customerRepository.findById(request.getCustomerId()).orElse(null);
            if (customer == null) {
                return null; // ID không tồn tại
            }
        } else if (request.getAdministratorId() != null) {
            if (userAccountRepository.existsByAdministratorIdAndUsernameNot(request.getAdministratorId(), username)) {
                return null; // ID đã được liên kết với tài khoản khác
            }
            administrator = administratorRepository.findById(request.getAdministratorId()).orElse(null);
            if (administrator == null) {
                return null; // ID không tồn tại
            }
        } else if (request.getTourGuideId() != null) {
            if (userAccountRepository.existsByTourGuideIdAndUsernameNot(request.getTourGuideId(), username)) {
                return null; // ID đã được liên kết với tài khoản khác
            }
            tourGuide = tourGuideRepository.findById(request.getTourGuideId()).orElse(null);
            if (tourGuide == null) {
                return null; // ID không tồn tại
            }
        } else if (request.getTourOperatorId() != null) {
            if (userAccountRepository.existsByTourOperatorIdAndUsernameNot(request.getTourOperatorId(), username)) {
                return null; // ID đã được liên kết với tài khoản khác
            }
            tourOperator = tourOperatorRepository.findById(request.getTourOperatorId()).orElse(null);
            if (tourOperator == null) {
                return null; // ID không tồn tại
            }
        }

        // Cập nhật UserAccount
        userAccountAdMapper.updateEntityFromRequest(request, userAccount);
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            userAccount.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        UserAccount updatedUserAccount = userAccountRepository.save(userAccount);

        return userAccountAdMapper.toResponse(updatedUserAccount);
    }
// loại bỏ xóa
//    @Transactional
//    public boolean deleteUserAccount(String username) {
//        if (userAccountRepository.existsById(username)) {
//            userAccountRepository.deleteById(username);
//            return true;
//        }
//        return false;
//    }
    //loa bỏ khoảng trắng và dấu truước khi tạo tài khoanr
    private String normalizeUsername(String name, Integer id) {
        String noAccent = Normalizer.normalize(name, Normalizer.Form.NFD);
        String clean = noAccent.replaceAll("\\p{M}", "");
        clean = clean.replaceAll("[^a-zA-Z0-9]", "");
        return clean + id;
    }
    public void createUserAccountForCustomer(Customer customer) {
        String username = normalizeUsername(customer.getFirstname(), customer.getId());
        if (userAccountRepository.existsById(username)) return;

        UserAccount account = UserAccount.builder()
                .username(username)
                .password(passwordEncoder.encode("matkhau"))
                .c(customer)
                .email(username + "@gmail.com")
                .status("ON")
                .build();

        userAccountRepository.save(account);
    }

}