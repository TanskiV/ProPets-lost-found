package lostfound.dto;

import lostfound.utils.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FindPetDto {
    String type;
    Location location;
    int radiusSearching;
    Set<String> photos;
    Set<String> tags;
}
