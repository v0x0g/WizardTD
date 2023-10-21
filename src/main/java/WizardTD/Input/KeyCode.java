package WizardTD.Input;

import lombok.*;
import lombok.experimental.*;
import org.checkerframework.checker.nullness.qual.*;

import java.util.*;

@SuppressWarnings("unused")
@ToString
@AllArgsConstructor
@ExtensionMethod(Arrays.class)
public enum KeyCode {
    ENTER(10, '\n'),
    COMMA(44, ','),
    MINUS(45, '-'),
    PERIOD(46, '.'),
    SLASH(47, '/'),
    NUM_1(49, '1'),
    NUM_2(50, '2'),
    NUM_3(51, '3'),
    NUM_4(52, '4'),
    NUM_5(53, '5'),
    NUM_6(54, '6'),
    NUM_7(55, '7'),
    NUM_8(56, '8'),
    NUM_9(57, '9'),
    NUM_0(58, '0'),
    A(65, 'A'),
    B(66, 'B'),
    C(67, 'C'),
    D(68, 'D'),
    E(69, 'E'),
    F(70, 'F'),
    G(71, 'G'),
    H(72, 'H'),
    I(73, 'I'),
    J(74, 'J'),
    K(75, 'K'),
    L(76, 'L'),
    M(77, 'M'),
    N(78, 'N'),
    O(79, 'O'),
    P(80, 'P'),
    Q(81, 'Q'),
    R(82, 'R'),
    S(83, 'S'),
    T(84, 'T'),
    U(85, 'U'),
    V(86, 'V'),
    W(87, 'W'),
    X(88, 'X'),
    Y(89, 'Y'),
    Z(90, 'Z'),
    NUMPAD_0(96, '0'),
    NUMPAD_1(97, '1'),
    NUMPAD_2(98, '2'),
    NUMPAD_3(99, '3'),
    NUMPAD_4(100, '4'),
    NUMPAD_5(101, '5'),
    NUMPAD_6(102, '6'),
    NUMPAD_7(103, '7'),
    NUMPAD_8(104, '8'),
    NUMPAD_9(105, '9'),
    ASTERISK_NUMPAD(106, '*'),
    PLUS_NUMPAD(107, '+'),
    MINUS_NUMPAD(109, '-'),
    PERIOD_NUMPAD(110, '.'),
    SLASH_NUMPAD(111, '/'),
    FUNCTION_1(112, '\0'),
    FUNCTION_2(113, '\0'),
    FUNCTION_3(114, '\0'),
    FUNCTION_4(115, '\0'),
    FUNCTION_5(116, '\0'),
    FUNCTION_6(117, '\0'),
    FUNCTION_7(118, '\0'),
    FUNCTION_8(119, '\0'),
    FUNCTION_9(120, '\0'),
    FUNCTION_10(121, '\0'),
    FUNCTION_11(122, '\0'),
    FUNCTION_12(123, '\0'),
    ;

    public final int code;
    public final char char_;

    public static @Nullable KeyCode fromInt(final int code) {
        return KeyCode
                .values()
                .stream()
                .filter((v) -> v.code == code)
                .findFirst()
                .orElse(null);
    }

    public static @Nullable KeyCode fromChar(final char char_) {
        return KeyCode
                .values()
                .stream()
                .filter((v) -> v.char_ == char_)
                .findFirst()
                .orElse(null);
    }
}
