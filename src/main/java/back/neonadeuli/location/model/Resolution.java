package back.neonadeuli.location.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Resolution {
    RES_6(6),
    RES_4(4);

    private final int level;
}
