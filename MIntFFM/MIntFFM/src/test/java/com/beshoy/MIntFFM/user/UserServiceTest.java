package com.beshoy.MIntFFM.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.any;
import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @CsvSource({
            "Beshoy, Farag, beshoy@example.com, StrongP@ssw0rd123, 1990-05-15",
            "John, Doe, john@example.com, Password123!, 1985-08-20"
    })
    void SignUpMockitoTest(String firstName, String lastName, String email,
                           String password, String birthdateString) {

        LocalDate birthdate = LocalDate.parse(birthdateString);

        UserSignUpDTORequest signUpRequest = new UserSignUpDTORequest(
                firstName, lastName, email, password, birthdate
        );

        UserEntity user = new UserEntity();
        UserEntity savedUser = new UserEntity();
        UserResponseDTO expectedResponse = new UserResponseDTO(1L, firstName, lastName, email, birthdate, LocalDate.now());

        when(userMapper.toEntity(signUpRequest)).thenReturn(user);
        when(userRepo.save(user)).thenReturn(savedUser);
        when(userMapper.toResponseDTO(savedUser)).thenReturn(expectedResponse);

        // Call service method
        UserResponseDTO actualResponse = userService.signUp(signUpRequest);

        // Assert
        assertEquals(expectedResponse, actualResponse);
    }

    @ParameterizedTest
    @CsvSource({
            "Beshoy, Farag, beshoy@example.com, StrongP@ssw0rd123, 1990-05-15",
            "John, Doe, john@example.com, Password123!, 1985-08-20",
            "Jane, Smith, jane@example.com, MyPass456$, 1995-03-10"
    })
    void signUp_ShouldSaveUserAndReturnResponse_WhenValidRequest(
            String firstName, String lastName, String email,
            String password, String birthdateString) {

        // GIVEN
        LocalDate birthdate = LocalDate.parse(birthdateString);
        UserSignUpDTORequest request = new UserSignUpDTORequest(
                firstName, lastName, email, password, birthdate
        );

        UserEntity user = new UserEntity();
        UserEntity savedUser = new UserEntity();
        savedUser.setUserId(1L);
        UserResponseDTO expectedResponse = new UserResponseDTO(
                1L, firstName, lastName, email, birthdate, LocalDate.now()
        );

        when(userRepo.existsByEmail(email)).thenReturn(false);
        when(userMapper.toEntity(request)).thenReturn(user);
        when(userRepo.save(user)).thenReturn(savedUser);
        when(userMapper.toResponseDTO(savedUser)).thenReturn(expectedResponse);

        // WHEN
        UserResponseDTO actualResponse = userService.signUp(request);

        // THEN
        assertEquals(expectedResponse, actualResponse);
        verify(userRepo).existsByEmail(email);
        verify(userMapper).toEntity(request);
        verify(userRepo).save(user);
        verify(userMapper).toResponseDTO(savedUser);
    }



    @Test
    void signUp_ShouldThrowException_WhenRequestIsNull() {
        // WHEN & THEN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.signUp(null));

        assertEquals("SignUp request cannot be null", exception.getMessage());
        verify(userRepo, never()).existsByEmail(any());
        verify(userMapper, never()).toEntity(any());
        verify(userRepo, never()).save(any());
    }



    @Test
    void signUp_ShouldThrowException_WhenFirstNameIsNull() {
        // GIVEN
        UserSignUpDTORequest request = new UserSignUpDTORequest(
                null, "Farag", "test@example.com", "StrongP@ssw0rd123", LocalDate.of(1990, 5, 15)
        );

        // WHEN & THEN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.signUp(request));

        assertEquals("First name is required", exception.getMessage());
        verify(userRepo, never()).existsByEmail(any());
        verify(userMapper, never()).toEntity(any());
    }

    @Test
    void signUp_ShouldThrowException_WhenFirstNameIsBlank() {
        // GIVEN
        UserSignUpDTORequest request = new UserSignUpDTORequest(
                "", "Farag", "test@example.com", "StrongP@ssw0rd123", LocalDate.of(1990, 5, 15)
        );

        // WHEN & THEN
        assertThrows(IllegalArgumentException.class,
                () -> userService.signUp(request));
    }

    @Test
    void signUp_ShouldThrowException_WhenFirstNameTooShort() {
        // GIVEN
        UserSignUpDTORequest request = new UserSignUpDTORequest(
                "A", "Farag", "test@example.com", "StrongP@ssw0rd123", LocalDate.of(1990, 5, 15)
        );

        // WHEN & THEN
        assertThrows(IllegalArgumentException.class,
                () -> userService.signUp(request));
    }

    @Test
    void signUp_ShouldThrowException_WhenLastNameIsNull() {
        // GIVEN
        UserSignUpDTORequest request = new UserSignUpDTORequest(
                "Beshoy", null, "test@example.com", "StrongP@ssw0rd123", LocalDate.of(1990, 5, 15)
        );

        // WHEN & THEN
        assertThrows(IllegalArgumentException.class,
                () -> userService.signUp(request));
    }

    @Test
    void signUp_ShouldThrowException_WhenEmailIsNull() {
        // GIVEN
        UserSignUpDTORequest request = new UserSignUpDTORequest(
                "Beshoy", "Farag", null, "StrongP@ssw0rd123", LocalDate.of(1990, 5, 15)
        );

        // WHEN & THEN
        assertThrows(IllegalArgumentException.class,
                () -> userService.signUp(request));
    }

    @Test
    void signUp_ShouldThrowException_WhenEmailInvalid() {
        // GIVEN
        UserSignUpDTORequest request = new UserSignUpDTORequest(
                "Beshoy", "Farag", "invalid-email", "StrongP@ssw0rd123", LocalDate.of(1990, 5, 15)
        );

        // WHEN & THEN
        assertThrows(IllegalArgumentException.class,
                () -> userService.signUp(request));
    }

    @Test
    void signUp_ShouldThrowException_WhenPasswordTooShort() {
        // GIVEN
        UserSignUpDTORequest request = new UserSignUpDTORequest(
                "Beshoy", "Farag", "test@example.com", "Weak1", LocalDate.of(1990, 5, 15)
        );

        // WHEN & THEN
        assertThrows(IllegalArgumentException.class,
                () -> userService.signUp(request));
    }

    @Test
    void signUp_ShouldThrowException_WhenPasswordNoUppercase() {
        // GIVEN
        UserSignUpDTORequest request = new UserSignUpDTORequest(
                "Beshoy", "Farag", "test@example.com", "weakpass123", LocalDate.of(1990, 5, 15)
        );

        // WHEN & THEN
        assertThrows(IllegalArgumentException.class,
                () -> userService.signUp(request));
    }

    @Test
    void signUp_ShouldThrowException_WhenBirthdateInFuture() {
        // GIVEN
        UserSignUpDTORequest request = new UserSignUpDTORequest(
                "Beshoy", "Farag", "test@example.com", "StrongP@ssw0rd123", LocalDate.now().plusDays(1)
        );

        // WHEN & THEN
        assertThrows(IllegalArgumentException.class,
                () -> userService.signUp(request));
    }

    @Test
    void signUp_ShouldThrowException_WhenUserUnder13() {
        // GIVEN
        UserSignUpDTORequest request = new UserSignUpDTORequest(
                "Beshoy", "Farag", "test@example.com", "StrongP@ssw0rd123", LocalDate.now().minusYears(10)
        );

        // WHEN & THEN
        assertThrows(IllegalArgumentException.class,
                () -> userService.signUp(request));
    }

    // ==================== DUPLICATE EMAIL TEST ====================

    @Test
    void signUp_ShouldThrowException_WhenEmailAlreadyExists() {
        // GIVEN
        UserSignUpDTORequest request = new UserSignUpDTORequest(
                "Beshoy", "Farag", "existing@example.com", "StrongP@ssw0rd123", LocalDate.of(1990, 5, 15)
        );

        when(userRepo.existsByEmail("existing@example.com")).thenReturn(true);

        // WHEN & THEN
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.signUp(request));

        assertEquals("Email already exists: existing@example.com", exception.getMessage());
        verify(userRepo).existsByEmail("existing@example.com");
        verify(userMapper, never()).toEntity(any());
        verify(userRepo, never()).save(any());
    }
}