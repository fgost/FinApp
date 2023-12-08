package dsw.finApp.utils.pagination;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PaginationRequest {

    private Integer tamanhoDaPagina;
    private Integer numeroDaPagina;
}
