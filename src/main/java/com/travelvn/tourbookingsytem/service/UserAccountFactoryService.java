package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.model.TourGuide;
import com.travelvn.tourbookingsytem.model.TourOperator;
import com.travelvn.tourbookingsytem.model.UserAccount;
import com.travelvn.tourbookingsytem.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.Normalizer;

@Service
public class UserAccountFactoryService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createForTourGuide(TourGuide guide) {
        if (guide == null || guide.getFirstname() == null || guide.getId() == null) return;

        String username = normalizeUsername(guide.getFirstname(), guide.getId());
        if (userAccountRepository.existsById(username)) return;

        UserAccount account = UserAccount.builder()
                .username(username)
                .password(passwordEncoder.encode("matkhau"))
                .tourGuide(guide)
                .email(username + "@gmail.com")
                .status("ON")
                .build();

        userAccountRepository.save(account);
    }

    public void createForTourOperator(TourOperator operator) {
        if (operator == null || operator.getFirstname() == null || operator.getId() == null) return;

        String username = normalizeUsername(operator.getFirstname(), operator.getId());
        if (userAccountRepository.existsById(username)) return;

        UserAccount account = UserAccount.builder()
                .username(username)
                .password(passwordEncoder.encode("matkhau"))
                .tourOperator(operator)
                .email(username + "@gmail.com")
                .status("ON")
                .build();

        userAccountRepository.save(account);
    }

    private String normalizeUsername(String name, Integer id) {
        String noAccent = Normalizer.normalize(name, Normalizer.Form.NFD);
        String clean = noAccent.replaceAll("\\p{M}", "");
        clean = clean.replaceAll("[^a-zA-Z0-9]", "");
        return clean.toLowerCase() + id;
    }
}
