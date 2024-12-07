package com.delivery_man.service.impl;

import com.delivery_man.Exception.ApiException;
import com.delivery_man.config.PasswordEncoder;
import com.delivery_man.constant.UserErrorCode;
import com.delivery_man.dto.auth.Authentication;
import com.delivery_man.dto.user.*;
import com.delivery_man.entity.Point;
import com.delivery_man.entity.User;
import com.delivery_man.repository.PointRepository;
import com.delivery_man.repository.UserRepository;
import com.delivery_man.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PointRepository pointRepository;

    /**
     * 회원 가입 로직
     *
     * @param userSignUpRequestDto 회원 가입 시 요청 정보
     * @return
     */
    @Transactional
    @Override
    public UserSignUpResponseDto signUpUser(UserSignUpRequestDto userSignUpRequestDto) {

        //이메일로 유저 조회
        Optional<User> findUser = userRepository.findByEmail(userSignUpRequestDto.getEmail());

        //이메일이 겹칠 경우
        if (findUser.isPresent()) {
            throw new ApiException(UserErrorCode.INVALID_INPUT_EMAIL);
        }

        //repository 에 저장
        User savedUser = userRepository.save(userSignUpRequestDto.toEntity());

        return new UserSignUpResponseDto(savedUser);
    }

    /**
     * 로그인 로직
     *
     * @param userLoginRequestDto 로그인 요청 정보
     * @return
     */
    @Override
    public Authentication login(UserLoginRequestDto userLoginRequestDto) {

        //이메일로 변수 생성
        String email = userLoginRequestDto.getEmail();

        //이메일로 유저 조회
        Optional<User> findUser = userRepository.findByEmail(email);

        //유저가 없을 경우
        if (findUser.isEmpty()) {
            throw new ApiException(UserErrorCode.USER_NOT_FOUND);
        }

        return new Authentication(findUser.get().getId(), findUser.get().getEmail(), findUser.get().getGrade());
    }

    /**
     * 회원 조회 로직
     *
     * @param userId 유저 식별자
     * @return 회원 정보와 포인트 합
     */
    @Override
    public UserResponseDto findUser(Long userId, Long sessionId) {

        //유저 식별자로 조회
        User findUser = userRepository.findById(userId).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        //조회하는 유저와 일치하지 않는 경우
        if (!(Objects.equals(sessionId, findUser.getId()))) {
            throw new ApiException(UserErrorCode.INVALID_SESSION_ID);
        }

        //포인트 조회
        List<Point> findAllPoint = pointRepository.findByUserId(userId);

        BigDecimal totalPoints = BigDecimal.ZERO;

        //포인트 총합 계산
        for (Point point : findAllPoint) {
            totalPoints = totalPoints.add(point.getPoint());
        }
        return new UserResponseDto(findUser, totalPoints);
    }

    /**
     * 회원 탈퇴 로직
     *
     * @param userId              탈퇴할 유저 식별자
     * @param userLeaveRequestDto 탈퇴시 요청 정보
     * @param request
     * @param sessionId           현재 로그인 한 유저 식별자
     */
    @Transactional
    @Override
    public void leaveUser(Long userId, UserLeaveRequestDto userLeaveRequestDto, HttpServletRequest request, Long sessionId) {

        //유저 정보가 일치하지 않는 경우
        if (!userId.equals(sessionId)) {
            throw new ApiException(UserErrorCode.INVALID_SESSION_ID);
        }

        //유저 식별자로 유저 조회
        User findUser = userRepository.findById(userId).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        //비밀번호 검증
        if (!PasswordEncoder.matches(userLeaveRequestDto.getPassword(), findUser.getPassword())) {
            throw new ApiException(UserErrorCode.INVALID_PASSWORD);
        }

        //유저 상태 변경
        findUser.updateStatus("leave");

        userRepository.save(findUser);
    }
}
