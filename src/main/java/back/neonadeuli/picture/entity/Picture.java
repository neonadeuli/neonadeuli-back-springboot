package back.neonadeuli.picture.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "Pictures")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Picture {

    public static final UUID DEFAULT_UUID = UUID.fromString("222f1dcb-80fa-46fe-a055-1214fe176561");

    @Id
    @Column
    @UuidGenerator
    private UUID savedName;

    @Column
    @NotNull
    private String folderName;

    @Column
    @NotNull
    private String originName;

    @Column
    @NotNull
    private String extension;

    public Picture(String folderName, String originName, String extension) {
        this.folderName = folderName;
        this.originName = originName;
        this.extension = extension;
    }

    public String getSavePath() {
        return folderName + "/" + savedName + "." + extension;
    }
}
