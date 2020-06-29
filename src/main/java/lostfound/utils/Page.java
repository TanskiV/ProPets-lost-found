package lostfound.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Page {
    @JsonProperty("current-page")
    int currentPage;
    @JsonProperty("per-page")
    int perPage;
    int from;
    int to;
    int total;
    @JsonProperty("last-page")
    int lastPage;
}
