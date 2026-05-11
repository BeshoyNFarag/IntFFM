package com.beshoy.MIntFFM.user;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.time.LocalDate;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


class UserMapperTest {

    private static UserMapper userMapper;

    @BeforeAll
    static void setup(){
        userMapper = new UserMapper();
    }

    //testing using valid data all of this data is valid and should pass
    //uniqueness of data can be checked or tested in the service layer
    @ParameterizedTest
    @CsvSource({
            "Beshoy, Farag, beshoy@example.com, StrongP@ssw0rd123, 1990-05-15",
            "John, Doe, john@example.com, Password123!, 1985-08-20",
            "Jane, Smith, jane@example.com, MyPass456$, 1995-03-10",
            "Ali, Kha, ali@example.com, SecurePass789, 1988-12-01",
            "Ali, pho, ali@example.com, SecurePass78s@9, 1988-12-01"
    })
    void toEntityTestValidData(String firstName, String lastName, String email,
                      String password, String birthdateString) {

        //Given
        LocalDate birthdate = LocalDate.parse(birthdateString);
        UserSignUpDTORequest request = new UserSignUpDTORequest(
                firstName, lastName, email, password, birthdate
        );
        //When
        UserEntity result = userMapper.toEntity(request);
        //Then
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertEquals(email, result.getEmail());
        assertEquals(password, result.getPassword());
        assertEquals(birthdate, result.getBirthdate());
    }


    @Test
    void toEntityShouldHandleNullFirstName() {
        // GIVEN
        UserSignUpDTORequest request = new UserSignUpDTORequest
                (
                        null,
                        "Farag",
                        "test@email.com",
                        "Password123",
                        LocalDate.of(1990, 5, 15)
        );

        // WHEN
        UserEntity result = userMapper.toEntity(request);

        // THEN
        assertNull(result.getFirstName());
        assertEquals("Farag", result.getLastName());
        assertEquals("test@email.com", result.getEmail());
    }


    @Test
    void toEntityShouldHandleNullEmail() {
        // GIVEN
        UserSignUpDTORequest request = new UserSignUpDTORequest(
                "Beshoy",
                "Farag",
                null,
                "Password123",
                LocalDate.of(1990, 5, 15)
        );

        // WHEN
        UserEntity result = userMapper.toEntity(request);

        // THEN
        assertNull(result.getEmail());
        assertEquals("Beshoy", result.getFirstName());
        assertEquals("Farag", result.getLastName());
    }

    @Test
    void toEntityShouldHandleNullBirthdate() {
        // GIVEN
        UserSignUpDTORequest request = new UserSignUpDTORequest(
                "Beshoy",
                "Farag",
                "test@email.com",
                "Password123",
                null
        );

        // WHEN
        UserEntity result = userMapper.toEntity(request);

        // THEN
        assertNull(result.getBirthdate());
        assertEquals("Beshoy", result.getFirstName());
    }

    //this test should handle normal cases of input, no null, no extreme values what so ever
    @ParameterizedTest
    @CsvSource({
            "1, Beshoy, Farag, beshoy@example.com, 1990-05-15, 2024-01-01",
            "2, John, Doe, john@example.com, 1985-08-20, 2024-01-02",
            "3, Jane, Smith, jane@example.com, 1995-03-10, 2024-01-03",
            "100, Ali, Khan, ali@example.com, 1988-12-01, 2024-01-04",
            "999, VeryLongFirstNameHere, VeryLongLastNameHere, verylongemail@example.com, 2000-01-01, 2024-01-05"
    })
    void toResponseDTO_ShouldMapAllFieldsCorrectly(
            Long userId, String firstName, String lastName, String email,
            String birthdateString, String createdAtString) {

        // GIVEN
        LocalDate birthdate = LocalDate.parse(birthdateString);
        LocalDate createdAt = LocalDate.parse(createdAtString);

        UserEntity user = new UserEntity();
        user.setUserId(userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setBirthdate(birthdate);
        user.setCreatedAt(createdAt);

        // WHEN
        UserResponseDTO response = userMapper.toResponseDTO(user);

        // THEN
        assertAll("All fields mapped correctly",
                () -> assertEquals(userId, response.id()),
                () -> assertEquals(firstName, response.firstName()),
                () -> assertEquals(lastName, response.lastName()),
                () -> assertEquals(email, response.email()),
                () -> assertEquals(birthdate, response.birthdate()),
                () -> assertEquals(createdAt, response.createdAt())
        );
    }



    @Test
    void toResponseDTO_ShouldThrowNullPointerException_WhenUserIsNull() {
        // WHEN & THEN
        assertThrows(IllegalArgumentException.class,
                () -> userMapper.toResponseDTO(null));
    }

    @ParameterizedTest
    @NullSource
    void toResponseDTO_ShouldHandleNullUserId(Long nullUserId) {
        // GIVEN
        UserEntity user = new UserEntity();
        user.setUserId(nullUserId);
        user.setFirstName("Beshoy");
        user.setLastName("Farag");
        user.setEmail("test@example.com");
        user.setBirthdate(LocalDate.of(1990, 5, 15));
        user.setCreatedAt(LocalDate.now());

        // WHEN
        UserResponseDTO response = userMapper.toResponseDTO(user);

        // THEN
        assertNull(response.id());
        assertEquals("Beshoy", response.firstName());
    }

    @ParameterizedTest
    @NullSource
    void toResponseDTO_ShouldHandleNullFirstName(String nullFirstName) {
        // GIVEN
        UserEntity user = new UserEntity();
        user.setUserId(1L);
        user.setFirstName(nullFirstName);
        user.setLastName("Farag");
        user.setEmail("test@example.com");
        user.setBirthdate(LocalDate.of(1990, 5, 15));
        user.setCreatedAt(LocalDate.now());

        // WHEN
        UserResponseDTO response = userMapper.toResponseDTO(user);

        // THEN
        assertNull(response.firstName());
        assertEquals("Farag", response.lastName());
    }

    @ParameterizedTest
    @NullSource
    void toResponseDTO_ShouldHandleNullLastName(String nullLastName) {
        // GIVEN
        UserEntity user = new UserEntity();
        user.setUserId(1L);
        user.setFirstName("Beshoy");
        user.setLastName(nullLastName);
        user.setEmail("test@example.com");
        user.setBirthdate(LocalDate.of(1990, 5, 15));
        user.setCreatedAt(LocalDate.now());

        // WHEN
        UserResponseDTO response = userMapper.toResponseDTO(user);

        // THEN
        assertNull(response.lastName());
        assertEquals("Beshoy", response.firstName());
    }

    @ParameterizedTest
    @NullSource
    void toResponseDTO_ShouldHandleNullEmail(String nullEmail) {
        // GIVEN
        UserEntity user = new UserEntity();
        user.setUserId(1L);
        user.setFirstName("Beshoy");
        user.setLastName("Farag");
        user.setEmail(nullEmail);
        user.setBirthdate(LocalDate.of(1990, 5, 15));
        user.setCreatedAt(LocalDate.now());

        // WHEN
        UserResponseDTO response = userMapper.toResponseDTO(user);

        // THEN
        assertNull(response.email());
    }

    @ParameterizedTest
    @NullSource
    void toResponseDTO_ShouldHandleNullBirthdate(LocalDate nullBirthdate) {
        // GIVEN
        UserEntity user = new UserEntity();
        user.setUserId(1L);
        user.setFirstName("Beshoy");
        user.setLastName("Farag");
        user.setEmail("test@example.com");
        user.setBirthdate(nullBirthdate);
        user.setCreatedAt(LocalDate.now());

        // WHEN
        UserResponseDTO response = userMapper.toResponseDTO(user);

        // THEN
        assertNull(response.birthdate());
    }

    @ParameterizedTest
    @NullSource
    void toResponseDTO_ShouldHandleNullCreatedAt(LocalDate nullCreatedAt) {
        // GIVEN
        UserEntity user = new UserEntity();
        user.setUserId(1L);
        user.setFirstName("Beshoy");
        user.setLastName("Farag");
        user.setEmail("test@example.com");
        user.setBirthdate(LocalDate.of(1990, 5, 15));
        user.setCreatedAt(nullCreatedAt);

        // WHEN
        UserResponseDTO response = userMapper.toResponseDTO(user);

        // THEN
        assertNull(response.createdAt());
    }

    // ==================== EXTREME DATA TESTS ====================

    @ParameterizedTest
    @MethodSource("provideExtremeData")
    void toResponseDTO_ShouldHandleExtremeData(
            Long userId, String firstName, String lastName, String email,
            LocalDate birthdate, LocalDate createdAt) {

        // GIVEN
        UserEntity user = new UserEntity();
        user.setUserId(userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setBirthdate(birthdate);
        user.setCreatedAt(createdAt);

        // WHEN
        UserResponseDTO response = userMapper.toResponseDTO(user);

        // THEN
        assertEquals(userId, response.id());
        assertEquals(firstName, response.firstName());
        assertEquals(lastName, response.lastName());
        assertEquals(email, response.email());
        assertEquals(birthdate, response.birthdate());
        assertEquals(createdAt, response.createdAt());
    }

    private static Stream<Arguments> provideExtremeData() {
        return Stream.of(
                // Maximum values
                Arguments.of(
                        Long.MAX_VALUE,
                        "A".repeat(255),  // 255 characters
                        "B".repeat(255),
                        "extremely-long-email-address-that-might-exceed-normal-limits@example.com",
                        LocalDate.of(9999, 12, 31),
                        LocalDate.MAX
                ),
                // Minimum values
                Arguments.of(
                        0L,
                        "A",  // Single character
                        "B",
                        "a@b.c",  // Minimal valid email
                        LocalDate.of(1900, 1, 1),
                        LocalDate.MIN
                ),
                // Special characters
                Arguments.of(
                        -1L,
                        "Béshoy-Ph'nglui",
                        "Farág O'Connor",
                        "test+special@example.com",
                        LocalDate.of(1990, 5, 15),
                        LocalDate.now()
                ),
                // Unicode/Emoji
                Arguments.of(
                        123L,
                        "Beshoy😊",
                        "Farag🎉",
                        "emoji@example.com",
                        LocalDate.of(1990, 5, 15),
                        LocalDate.now()
                ),
                // Whitespace
                Arguments.of(
                        999L,
                        "  Beshoy  ",
                        "  Farag  ",
                        "  spaces@example.com  ",
                        LocalDate.of(1990, 5, 15),
                        LocalDate.now()
                )
        );
    }

    // ==================== MULTIPLE NULL FIELDS COMBINATIONS ====================

    @ParameterizedTest
    @MethodSource("provideMultipleNullFields")
    void toResponseDTO_ShouldHandleMultipleNullFields(
            Long userId, String firstName, String lastName, String email,
            LocalDate birthdate, LocalDate createdAt) {

        // GIVEN
        UserEntity user = new UserEntity();
        user.setUserId(userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setBirthdate(birthdate);
        user.setCreatedAt(createdAt);

        // WHEN
        UserResponseDTO response = userMapper.toResponseDTO(user);

        // THEN
        assertEquals(userId, response.id());
        assertEquals(firstName, response.firstName());
        assertEquals(lastName, response.lastName());
        assertEquals(email, response.email());
        assertEquals(birthdate, response.birthdate());
        assertEquals(createdAt, response.createdAt());
    }

    @org.jetbrains.annotations.NotNull
    private static Stream<Arguments> provideMultipleNullFields() {
        return Stream.of(
                // All fields null
                Arguments.of(null, null, null, null, null, null),
                // Only ID present
                Arguments.of(1L, null, null, null, null, null),
                // Only name present
                Arguments.of(null, "Beshoy", "Farag", null, null, null),
                // Only date fields present
                Arguments.of(null, null, null, null, LocalDate.now(), LocalDate.now()),
                // Missing email and createdAt
                Arguments.of(1L, "Beshoy", "Farag", null, LocalDate.now(), null),
                // Missing ID and birthdate
                Arguments.of(null, "Beshoy", "Farag", "test@email.com", null, LocalDate.now())
        );
    }

}