package lostfound.dto;

import lostfound.utils.Data;
import lostfound.utils.Links;
import lostfound.utils.Meta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FindLostPetsResponse {
    Meta meta;
    Links links;
    Set<Data> data;
}
