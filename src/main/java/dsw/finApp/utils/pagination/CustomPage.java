package dsw.finApp.utils.pagination;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomPage<T> {

    private long totalDeElementos;
    private int tamanhoDaPagina;
    private int numeroDaPagina;
    private int totalPaginas;
    private int ultimaPagina;
    private boolean possuiPaginaAnterior;
    private boolean possuiPaginaSeguinte;
    private int paginaAnterior;
    private int paginaSeguinte;

    public CustomPage(Page<T> page) {
        this.totalDeElementos = page.getTotalElements();
        this.totalPaginas = page.getTotalPages();
        this.ultimaPagina = this.totalPaginas;
        this.tamanhoDaPagina = page.getSize();
        this.numeroDaPagina = page.getNumber();
        this.possuiPaginaAnterior = page.hasPrevious();
        this.possuiPaginaSeguinte = page.hasNext();
        this.paginaAnterior = page.previousOrFirstPageable().getPageNumber();
        this.paginaSeguinte = page.nextOrLastPageable().getPageNumber();
    }
}
