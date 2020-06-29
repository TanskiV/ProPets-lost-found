package lostfound.dto;

import lombok.*;
import lostfound.utils.Location;

import java.time.LocalDate;
import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PostByIdDto {
    long id;
    String typePost;
    boolean status;
    Location location;
    Set<String> photos;
    Set<String> tags;
    String user;
    LocalDate datePost;
}
