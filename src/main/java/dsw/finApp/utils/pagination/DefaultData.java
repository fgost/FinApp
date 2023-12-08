package dsw.finApp.utils.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DefaultData {

    @JsonProperty(value = "itens")
    private List<?> items;

    @JsonProperty(value = "paginacao")
    private CustomPage<?> pagination;
}
