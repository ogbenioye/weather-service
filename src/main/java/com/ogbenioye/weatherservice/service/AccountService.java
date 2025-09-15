package com.ogbenioye.weatherservice.service;

import com.ogbenioye.weatherservice.dto.ApiResponse;
import com.ogbenioye.weatherservice.dto.LoginResponse;
import com.ogbenioye.weatherservice.dto.UserDto;
import com.ogbenioye.weatherservice.model.ApplicationUser;
import com.ogbenioye.weatherservice.repository.ApiKeyRepository;
import com.ogbenioye.weatherservice.repository.ApplicationUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final ApplicationUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final ApiKeyService keyService;
    private final HttpServletRequest request;
    private final ApiKeyRepository apiKeyRepository;

    public AccountService(
            ApplicationUserRepository userRepository, PasswordEncoder passwordEncoder,
            AuthenticationManager authManager, ApiKeyService keyService, HttpServletRequest request, ApiKeyRepository apiKeyRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
        this.keyService = keyService;
        this.request = request;
        this.apiKeyRepository = apiKeyRepository;
    }

    public ApiResponse<ApplicationUser> registerUser(UserDto userDto) {
        try {
            var exists =  userRepository.findByEmail(userDto.getEmail()).isPresent();
            if(exists) {
                return new ApiResponse<ApplicationUser>(
                        false,
                        "Email already taken",
                        HttpStatus.CONFLICT,
                        null
                );
            }

            var user = new ApplicationUser(
                    userDto.getEmail(),
                    userDto.getEmail(),
                    userDto.getFirstName(),
                    userDto.getLastName(),
                    passwordEncoder.encode(userDto.getPassword())
            );

            var keyResponse = keyService.generateNewKey(user.getId());
            if (!keyResponse.isSuccess()) {
                return new ApiResponse<ApplicationUser>(
                        false,
                        keyResponse.getMessage(),
                        keyResponse.getStatusCode(),
                        null
                );
            }

            user.getApiKeys().add(keyResponse.getData());

            userRepository.save(user);

            return new ApiResponse<ApplicationUser>(
                    true,
                    "Registration Complete",
                    HttpStatus.CREATED,
                    user
            );
        }
        catch (Exception ex) {
            return new ApiResponse<ApplicationUser>(
                    false,
                    "An error occurred!",
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }

    public ApiResponse<LoginResponse> login(UserDto userDto) {
        try {
            var user =  userRepository.findByEmail(userDto.getEmail());
            if(user.isEmpty()) {
                return new ApiResponse<LoginResponse>(
                        false,
                        "User not found",
                        HttpStatus.NOT_FOUND,
                        null
                );
            }

            var appUser = user.get();
            authManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getEmail(), appUser.getPassword()));

            var response = new LoginResponse(
                    appUser.getEmail(),
                    appUser.getFirstName(),
                    appUser.getLastName(),
                    appUser.getApiKeys()
            );

            return new ApiResponse<LoginResponse>(
                    true,
                    "Login successful",
                    HttpStatus.OK,
                    response
            );
        }
        catch (Exception ex) {
            return new ApiResponse<LoginResponse>(
                    false,
                    "An error occurred!",
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }

    public ApiResponse<Boolean> configureWebhook(String url) {
        try{
            //TODO: Abstract
            var key = request.getHeader("X-API-KEY");

            var apiKey = apiKeyRepository.findByApiKey(key);
            if(apiKey.isEmpty()) {
                return new ApiResponse<Boolean>(
                        false,
                        "Configuration failed",
                        HttpStatus.BAD_REQUEST,
                        null
                );
            }

            var owner = apiKey.get().getOwner();
            if (owner == null) {
                return new ApiResponse<Boolean>(
                        false,
                        "Unassigned api key",
                        HttpStatus.BAD_REQUEST,
                        null
                );
            }

            owner.setWebhook(url);
            userRepository.save(owner);

            return new ApiResponse<Boolean>(
                    true,
                    "Configuration saved",
                    HttpStatus.OK,
                    true
            );
        }
        catch (Exception ex) {
            return new ApiResponse<Boolean>(
                    false,
                    "An error occurred!",
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }

}
