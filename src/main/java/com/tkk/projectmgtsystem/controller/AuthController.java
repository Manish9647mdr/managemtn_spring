package com.tkk.projectmgtsystem.controller;

import com.tkk.projectmgtsystem.config.JwtProvider;
import com.tkk.projectmgtsystem.modal.User;
import com.tkk.projectmgtsystem.repository.UserRepository;
import com.tkk.projectmgtsystem.request.LoginRequest;
import com.tkk.projectmgtsystem.response.AuthResponse;
import com.tkk.projectmgtsystem.service.CustomUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsImpl customUserDetails;

    @PostMapping("/signup")
    // ユーザーを作成するためのハンドラー
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
        // ユーザーが既に存在するかを確認する変数
        User isUserExit = userRepository.findByEmail(user.getEmail());

        // ユーザーが存在する場合、エラーメッセージをスローする
        if (isUserExit != null) {
            throw new Exception("Email is already exist with another account");
        }

        // 新しいユーザーを作成する
        User createUser = new User();
        // パスワードをエンコードして設定
        createUser.setPassword(passwordEncoder.encode(user.getPassword()));
        // メールアドレスを設定
        createUser.setEmail(user.getEmail());
        // フルネームを設定
        createUser.setFullName(user.getFullName());

        // ユーザーを保存
        User savedUser = userRepository.save(createUser);

        //  new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        Authentication authentication = authenticate(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse res = new AuthResponse();
        res.setMessage("Signup success");
        res.setJwt(jwt);

        // 作成したユーザーとステータスコードを返す
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> signing(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse res = new AuthResponse();
        res.setMessage("Signing success");
        res.setJwt(jwt);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
