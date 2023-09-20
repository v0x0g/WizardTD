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
    LESS_THAN(44, '<'),
    UNDERSCORE(45, '_'),
    GREATER_THAN(46, '>'),
    QUESTION_MARK(47, '?'),
    R_BRACKET(48, ')'),
    EXCLAMATION(49, '!'),
    AT(50, '@'),
    HASH(51, '#'),
    DOLLAR(52, '$'),
    PERCENT(53, '%'),
    CARAT(54, '^'),
    AMPERSAND(55, '&'),
    ASTERISK(56, '*'),
    L_BRACKET(57, '('),
    COLON(59, ':'),
    PLUS(61, '+'),
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
    L_BRACE(91, '{'),
    PIPE(92, '|'),
    R_BRACE(93, '}'),
    NUM_0(96, '0'),
    NUM_1(97, '1'),
    NUM_2(98, '2'),
    NUM_3(99, '3'),
    NUM_4(100, '4'),
    NUM_5(101, '5'),
    NUM_6(102, '6'),
    NUM_7(103, '7'),
    NUM_8(104, '8'),
    NUM_9(105, '9'),
    ASTERISK_NUMPAD(106, '*'),
    PLUS_NUMPAD(107, '+'),
    MINUS_NUMPAD(109, '-'),
    PERIOD_NUMPAD(110, '.'),
    SLASH_NUMPAD(111, '/'),
    DEL(127, ''),
    QUOUTE(222, '"'),
    ;
    
    public final int  code;
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
