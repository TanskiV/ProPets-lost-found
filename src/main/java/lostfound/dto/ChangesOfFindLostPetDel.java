package lostfound.dto;

import lombok.*;
import lostfound.utils.Location;

import java.time.LocalDate;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ChangesOfFindLostPetDel {
    long id;
    boolean typePost;
    String type;
    Location location;
    Set<String> photos;
    Set<String> tags;
    String user;
    LocalDate datePost;
    LocalDate deleteDate;
}
