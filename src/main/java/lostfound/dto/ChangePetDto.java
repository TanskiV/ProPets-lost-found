package lostfound.dto;

import lostfound.utils.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ChangePetDto {
    String type;
    Location location;
    Set<String> photos;
    Set<String> tags;
}
