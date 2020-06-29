package lostfound.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lostfound.utils.Location;

import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LostFindDto {
    String type;
    Location location;
    Set<String> photos;
    Set<String> tags;
}
