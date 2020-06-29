package lostfound.utils;

import lostfound.dto.LostFindDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Data {
    LostFindDto data;
    String user;
    LocalDate datePost;
}
