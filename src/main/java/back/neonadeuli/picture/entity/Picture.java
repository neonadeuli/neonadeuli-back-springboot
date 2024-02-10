package back.neonadeuli.picture.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Pictures")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Picture {

    public static Picture DEFAULT_PICTURE = new Picture(1L, "", "", "");

    @Id
    @Column(name = "picture_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String folderName;

    @Column
    @NotNull
    private String originName;

    @Column
    @NotNull
    private String savedName;

    public Picture(String folderName, String originName, String savedName) {
        this.folderName = folderName;
        this.originName = originName;
        this.savedName = savedName;
    }
}
