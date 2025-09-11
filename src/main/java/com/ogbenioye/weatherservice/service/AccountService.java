package com.ogbenioye.weatherservice.service;

import com.ogbenioye.weatherservice.dto.ApiResponse;
import com.ogbenioye.weatherservice.dto.LoginResponse;
import com.ogbenioye.weatherservice.dto.UserDto;
import com.ogbenioye.weatherservice.model.ApplicationUser;
import com.ogbenioye.weatherservice.repository.ApplicationUserRepository;
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

    public AccountService(ApplicationUserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
    }

    public ApiResponse<ApplicationUser> registerUser(UserDto userDto) {
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

        userRepository.save(user);

        //TODO: Generate api key

        return new ApiResponse<ApplicationUser>(
                true,
                "Registration Complete",
                HttpStatus.CREATED,
                user
        );
    }

    public ApiResponse<LoginResponse> login(UserDto userDto) {
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

}
