package lostfound.model;

import lombok.*;
import lostfound.utils.Location;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Set;
@Data
@Document("ProPetsLostFound")
@Builder
public class LostFoundPet {
    @Id
    long postId;
    String type;
    Location location;
    Set<String> photos;
    Set<String> tags;
    String user;
    LocalDate datePost;
    //true = found false = lost
    boolean status;
    double[] position;
}
