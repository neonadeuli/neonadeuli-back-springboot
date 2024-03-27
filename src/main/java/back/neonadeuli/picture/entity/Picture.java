package back.neonadeuli.picture.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "pictures")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Picture {

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

    @Transient
    private MultipartFile multipartFile;

    public Picture(String folderName, String originName, String extension, MultipartFile multipartFile) {
        this.folderName = folderName;
        this.originName = originName;
        this.extension = extension;
        this.multipartFile = multipartFile;
    }

    public String getSavePath() {
        return folderName + "/" + savedName + "." + extension;
    }
}
