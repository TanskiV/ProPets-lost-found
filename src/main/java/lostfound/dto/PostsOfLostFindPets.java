package lostfound.dto;

import lostfound.utils.Data;
import lostfound.utils.Links;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PostsOfLostFindPets {
    Links links;
    List<Data> data;
}
