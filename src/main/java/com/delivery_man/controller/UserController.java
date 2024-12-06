package com.delivery_man.controller;

import com.delivery_man.config.Const;
import com.delivery_man.dto.*;
import com.delivery_man.entity.User;
import com.delivery_man.service.PictureService;
import com.delivery_man.service.S3Service;
import com.delivery_man.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PictureService pictureService;
    private final S3Service s3Service;

    /**
     * 회원 가입 메서드
     *
     * @param userSignUpRequestDto 회원 가입 시 요청 정보
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponseDto> creatUser(
            @RequestPart("request") UserSignUpRequestDto userSignUpRequestDto,
            @RequestPart(value = "images", required = false) MultipartFile image
            ) throws IOException {

        //회원 가입 한 유저 객체
        User savedUser = userService.signUpUser(userSignUpRequestDto);

        //picture 테이블에 담을 변수 생성
        String category = savedUser.getClass().getSimpleName();
        Long idNumber = savedUser.getId();
        //업로드 된 이미지 파일 주소
        String publicUrl = s3Service.uploadImage(image, savedUser);

        //picture 테이블에 저장
        pictureService.savePicture(publicUrl, category, idNumber);

        return ResponseEntity.ok().body(new UserSignUpResponseDto(savedUser, publicUrl));
    }

    /**
     * 회원 탈퇴 메서드
     *
     * @param userId              탈퇴 할 유저 식별자
     * @param userLeaveRequestDto 탈퇴 시 요청 정보
     * @param request
     * @param authentication      session 정보
     * @return
     */
    @PatchMapping("/{userId}")
    public ResponseEntity<String> leaveUser(@PathVariable Long userId,
                                            @RequestBody UserLeaveRequestDto userLeaveRequestDto,
                                            HttpServletRequest request,
                                            @SessionAttribute(name = Const.SESSION_KEY) Authentication authentication
    ) {
        Long sessionId = authentication.getId();
        userService.leaveUser(userId, userLeaveRequestDto, request, sessionId);

        return ResponseEntity.ok().body("정상적으로 삭제되었습니다.");
    }

    /**
     * 회원 조회 메서드
     *
     * @param userId 유저 식별자
     * @return 회원 정보와 포인트
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> findUser(@PathVariable Long userId,
                                                    HttpServletRequest request,
                                                    @SessionAttribute(name = Const.SESSION_KEY) Authentication session) {

        Long sessionId = session.getId();
        return ResponseEntity.ok().body(userService.findUser(userId, sessionId));
    }
}
