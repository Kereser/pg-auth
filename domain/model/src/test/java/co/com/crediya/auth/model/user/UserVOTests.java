package co.com.crediya.auth.model.user;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import co.com.crediya.auth.model.user.exceptions.IllegalValueForArgumentException;
import co.com.crediya.auth.model.user.exceptions.MissingValueOnRequiredFieldException;
import co.com.crediya.auth.model.user.vo.*;

@ExtendWith(MockitoExtension.class)
class UserVOTests {

  @Nested
  class UserEmailTests {
    @Test
    void shouldCreateUserEmailSuccessfullyWithValidEmail() {
      String validEmail = "test@example.com";

      UserEmail userEmail = assertDoesNotThrow(() -> new UserEmail(validEmail));

      assertEquals(validEmail, userEmail.value());
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNull() {
      assertThrows(MissingValueOnRequiredFieldException.class, () -> new UserEmail(null));
    }

    @Test
    void shouldThrowExceptionWhenEmailIsEmpty() {
      assertThrows(MissingValueOnRequiredFieldException.class, () -> new UserEmail(""));
    }

    @Test
    void shouldThrowExceptionWhenEmailIsTooLong() {
      String longEmail = "a".repeat(60) + "@example.com";

      assertThrows(IllegalValueForArgumentException.class, () -> new UserEmail(longEmail));
    }

    @Test
    void shouldThrowExceptionForInvalidFormat() {
      String invalidFormatEmail = "esto-no-es-un-email";

      assertThrows(IllegalValueForArgumentException.class, () -> new UserEmail(invalidFormatEmail));
    }
  }

  @Nested
  class AddressTests {
    @Test
    void shouldCreateSuccessfullyWithValidAddress() {
      String validAddress = "Calle Falsa 123";
      Address address = assertDoesNotThrow(() -> new Address(validAddress));
      assertEquals(validAddress, address.value());
    }

    @Test
    void shouldBeValidWhenAddressIsNull() {
      assertDoesNotThrow(() -> new Address(null));
    }

    @Test
    void shouldThrowExceptionWhenAddressIsBlank() {
      assertThrows(MissingValueOnRequiredFieldException.class, () -> new Address("   "));
    }

    @Test
    void shouldThrowExceptionWhenAddressIsTooShort() {
      assertThrows(IllegalValueForArgumentException.class, () -> new Address("abc"));
    }

    @Test
    void shouldThrowExceptionWhenAddressIsTooLong() {
      String longAddress = "a".repeat(81);
      assertThrows(IllegalValueForArgumentException.class, () -> new Address(longAddress));
    }
  }

  @Nested
  class BirthDateTests {
    @Test
    void shouldCreateSuccessfullyWithValidBirthDate() {
      LocalDate validDate = LocalDate.now().minusYears(25);
      BirthDate birthDate = assertDoesNotThrow(() -> new BirthDate(validDate));
      assertEquals(validDate, birthDate.value());
    }

    @Test
    void shouldThrowExceptionWhenDateIsNull() {
      assertThrows(MissingValueOnRequiredFieldException.class, () -> new BirthDate(null));
    }

    @Test
    void shouldThrowExceptionWhenUserIsTooYoung() {
      LocalDate tooYoung = LocalDate.now().minusYears(17);
      assertThrows(IllegalValueForArgumentException.class, () -> new BirthDate(tooYoung));
    }

    @Test
    void shouldThrowExceptionWhenUserIsTooOld() {
      LocalDate tooOld = LocalDate.now().minusYears(101);
      assertThrows(IllegalValueForArgumentException.class, () -> new BirthDate(tooOld));
    }
  }

  @Nested
  class FirstNameTests {
    @Test
    void shouldCreateSuccessfullyWithValidName() {
      String validName = "Juan";
      FirstName firstName = assertDoesNotThrow(() -> new FirstName(validName));
      assertEquals(validName, firstName.value());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
      assertThrows(MissingValueOnRequiredFieldException.class, () -> new FirstName(null));
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
      assertThrows(MissingValueOnRequiredFieldException.class, () -> new FirstName("   "));
    }

    @Test
    void shouldThrowExceptionWhenNameIsTooLong() {
      String longName = "a".repeat(31);
      assertThrows(IllegalValueForArgumentException.class, () -> new FirstName(longName));
    }
  }

  @Nested
  class IdNumberTests {
    @Test
    void shouldCreateSuccessfullyWithValidIdNumber() {
      String validId = "123456789";
      IdNumber idNumber = assertDoesNotThrow(() -> new IdNumber(validId));
      assertEquals(validId, idNumber.value());
    }

    @Test
    void shouldThrowExceptionWhenIdNumberIsNull() {
      assertThrows(MissingValueOnRequiredFieldException.class, () -> new IdNumber(null));
    }

    @Test
    void shouldThrowExceptionWhenIdNumberIsBlank() {
      assertThrows(MissingValueOnRequiredFieldException.class, () -> new IdNumber("  "));
    }

    @Test
    void shouldThrowExceptionWhenIdNumberIsTooShort() {
      assertThrows(IllegalValueForArgumentException.class, () -> new IdNumber("1234"));
    }

    @Test
    void shouldThrowExceptionWhenIdNumberIsTooLong() {
      String longId = "1".repeat(21);
      assertThrows(IllegalValueForArgumentException.class, () -> new IdNumber(longId));
    }
  }

  @Nested
  class IdTypeTests {
    @ParameterizedTest
    @ValueSource(strings = {"CC", "PASSPORT", "cc", "passport"})
    void shouldCreateSuccessfullyFromValidCode(String code) {
      IdType idType = assertDoesNotThrow(() -> IdType.fromCode(code));
      assertNotNull(idType);
    }

    @Test
    void shouldThrowExceptionWhenCodeIsNull() {
      assertThrows(MissingValueOnRequiredFieldException.class, () -> IdType.fromCode(null));
    }

    @Test
    void shouldThrowExceptionForInvalidCode() {
      assertThrows(IllegalValueForArgumentException.class, () -> IdType.fromCode("INVALID_CODE"));
    }
  }

  @Nested
  class LastNameTests {
    @Test
    void shouldCreateSuccessfullyWithValidName() {
      String validName = "Perez";
      LastName lastName = assertDoesNotThrow(() -> new LastName(validName));
      assertEquals(validName, lastName.value());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
      assertThrows(MissingValueOnRequiredFieldException.class, () -> new LastName(null));
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
      assertThrows(MissingValueOnRequiredFieldException.class, () -> new LastName("   "));
    }

    @Test
    void shouldThrowExceptionWhenNameIsTooLong() {
      String longName = "a".repeat(31);
      assertThrows(IllegalValueForArgumentException.class, () -> new LastName(longName));
    }
  }

  @Nested
  class PhoneNumberTests {
    @Test
    void shouldCreateSuccessfullyWithValidNumber() {
      String validNumber = "3101234567";
      PhoneNumber phoneNumber = assertDoesNotThrow(() -> new PhoneNumber(validNumber));
      assertEquals(validNumber, phoneNumber.value());
    }

    @Test
    void shouldBeValidWhenPhoneNumberIsNull() {
      assertDoesNotThrow(() -> new PhoneNumber(null));
    }

    @Test
    void shouldThrowExceptionWhenNumberIsTooShort() {
      assertThrows(IllegalValueForArgumentException.class, () -> new PhoneNumber("123"));
    }

    @Test
    void shouldThrowExceptionWhenNumberIsTooLong() {
      assertThrows(IllegalValueForArgumentException.class, () -> new PhoneNumber("12345678901234"));
    }
  }

  @Nested
  class UserSalaryTests {
    @Test
    void shouldCreateSuccessfullyWithValidSalary() {
      BigDecimal validSalary = new BigDecimal(2_500_000);
      UserSalary userSalary = assertDoesNotThrow(() -> new UserSalary(validSalary));
      assertEquals(0, validSalary.compareTo(userSalary.value()));
    }

    @Test
    void shouldThrowExceptionWhenSalaryIsNull() {
      assertThrows(MissingValueOnRequiredFieldException.class, () -> new UserSalary(null));
    }

    @Test
    void shouldThrowExceptionWhenSalaryIsNegative() {
      BigDecimal negativeSalary = new BigDecimal("-100");
      assertThrows(
          IllegalValueForArgumentException.class, () -> new UserSalary(negativeSalary));
    }

    @Test
    void shouldThrowExceptionWhenSalaryIsTooHigh() {
      BigDecimal highSalary = new BigDecimal("15000001");
      assertThrows(IllegalValueForArgumentException.class, () -> new UserSalary(highSalary));
    }
  }
}
