package WizardTD.Ext;

import lombok.*;

import java.util.*;

@ToString
@AllArgsConstructor
public enum MouseCode {
    LMB(37), RMB(39), MMB(3),
    ;

    final int code;

    public static Optional<MouseCode> fromInt(final int code) {
        return Arrays.stream(MouseCode.values()).filter((v) -> v.code == code).findFirst();
    }
}
