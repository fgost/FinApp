package dsw.finApp.utils.pagination;

import lombok.Getter;
import org.springframework.data.domain.Page;


@Getter
public class DefaultWrapper {

    public DefaultWrapper (Page<?> page) {
        this.success = true;
        this.data = DefaultData.builder()
                .items(page.getContent())
                .pagination(new CustomPage<>(page))
                .build();
    }

    private boolean success;
    private DefaultData data;
}
