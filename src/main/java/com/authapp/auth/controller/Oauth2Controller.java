package com.authapp.auth.controller;

import com.authapp.auth.entity.AppSettings;
import com.authapp.auth.entity.User;
import com.authapp.auth.model.ResponseCode;
import com.authapp.auth.model.user.Person;
import com.authapp.auth.oauth.Authorization;
import com.authapp.auth.oauth.Token;
import com.authapp.auth.holder.TokenHolder;
import com.authapp.auth.repository.SettingsRepository;
import com.authapp.auth.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.authapp.auth.oauth.Authorization.getAuthCode;
import static com.authapp.auth.oauth.Authorization.refreshToken;

@Controller
public class Oauth2Controller {

    final SettingsRepository settingsRepository;
    final TokenHolder tokenHolder;
    final UserRepository userRepository;

    public Oauth2Controller(SettingsRepository settingsRepository, TokenHolder tokenHolder, UserRepository userRepository) {
        this.settingsRepository = settingsRepository;
        this.tokenHolder = tokenHolder;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String index() {
        return "/index.html";
    }

    @GetMapping(value = "/settings")
    public String getSettingsPage() {
        return "settings.html";
    }

    @PostMapping("/saveSettings")
    public ResponseEntity<AppSettings> saveApplicationSettings(@RequestBody AppSettings settings) {
        settingsRepository.save(settings);
        return new ResponseEntity<>(settings, HttpStatus.OK);
    }

    @GetMapping("/getAuth")
    public String authCode(Model model) {
        try {
            AppSettings settings = settingsRepository.getReferenceById(1L);
            String authUrl = getAuthCode(settings).toUriString();
            model.addAttribute("googleUrl", authUrl);
            return "redirect:" + authUrl;
        } catch (EntityNotFoundException e) {
            return "/conferror";
        }
    }

    @PostMapping("/getToken")
    public ResponseEntity<ResponseCode> getToken(@RequestBody String json) {
        AppSettings settings = settingsRepository.getReferenceById(1L);
        if (settings.getAppId() == null) {
            ResponseCode responseCode = new ResponseCode();
            responseCode.setValue("No setting for this app");
            return new ResponseEntity<>(responseCode, HttpStatus.OK);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseCode code = null;
        try {
            code = objectMapper.readValue(json, ResponseCode.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Token token = Authorization.getToken(settings, code.getValue());
        if (token.getAccess_token() == null) {
            token = refreshToken(token, settings);
        }
        tokenHolder.setToken(token);
        Person googlePerson = Authorization.getUserPersonalInfo(token);
        String googleId = googlePerson
                .getEmailAddresses()
                .get(0)
                .getMetadata()
                .getSource()
                .getId();
        User user = userRepository.findUserByGoogleId(googleId);
        if (user == null) {
            user = new User();
            user.setGoogleId(googleId);
            user.setType(googlePerson
                    .getEmailAddresses()
                    .get(0)
                    .getMetadata()
                    .getSource()
                    .getType());
            user.setEmail(googlePerson.getEmailAddresses().get(0).getValue());
            userRepository.save(user);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/success")
    public String successedUser(Model model) {
        if (tokenHolder.getToken() != null) {
            model.addAttribute("token", userRepository.findUserByEmail("formoneyout@gmail.com").getEmail());
            return "/success.html";
        } else {
            return "redirect:index.html";
        }
    }

    @GetMapping("/revokeToken")
    public String logout(Model model) {
        Token token = tokenHolder.getToken();
        if (token.getAccess_token() !=null) {
            Authorization.revokeToken(token);
            return "/index";
        }
        return "/errorMessage";
    }
}
