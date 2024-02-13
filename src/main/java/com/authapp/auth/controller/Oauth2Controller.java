package com.authapp.auth.controller;

import com.authapp.auth.entity.Settings;
import com.authapp.auth.model.ResponseCode;
import com.authapp.auth.oauth.Autorization;
import com.authapp.auth.oauth.Token;
import com.authapp.auth.oauth.TokenHolder;
import com.authapp.auth.repository.SettingsRepository;
import com.authapp.auth.settings.Application;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class Oauth2Controller {

    final SettingsRepository settingsRepository;
    final TokenHolder tokenHolder;

    public Oauth2Controller(SettingsRepository settingsRepository, TokenHolder tokenHolder) {
        this.settingsRepository = settingsRepository;
        this.tokenHolder = tokenHolder;
    }

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping(value = "/settings")
    public String getSettingsPage() {
        return "settings.html";
    }

    @PostMapping("/saveSettings")
    public ResponseEntity<Settings> saveApplicationSettings(@RequestBody Settings settings) {
        settingsRepository.save(settings);
        return new ResponseEntity<>(settings, HttpStatus.OK);
    }

    @GetMapping("/getAuth")
    public String getAuthCode(Model model) {
        Settings settings = settingsRepository.getReferenceById(1L);
        String authUrl = Application.buildAuthUrl(settings);
        if (authUrl != null) {
            model.addAttribute("googleUrl", authUrl);
            return "redirect:" + authUrl;
        } else {
            return "redirect:/auth";
        }
    }

    @PostMapping("/getToken")
    public ResponseEntity<ResponseCode> getToken(@RequestBody String json) {
        Settings settings = settingsRepository.getReferenceById(1L);
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseCode code = null;
        try {
            code = objectMapper.readValue(json, ResponseCode.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String tokenUrl = Application.buildTokenUrl(settings, code.getValue());
        Token token = Autorization.getToken(tokenUrl);
        if(token.getAccess_token() !=null) {
            tokenHolder.setToken(token);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/success")
    public ResponseEntity<Token> successedUser(Model model) {
        model.addAttribute("token", tokenHolder.getToken().getAccess_token());
        return new ResponseEntity<>(tokenHolder.getToken(), HttpStatus.OK);
    }
}
