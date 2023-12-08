package dsw.finApp.utils.pagination;

import dsw.finApp.v1.authentication.services.AuthenticationService;
import dsw.finApp.v1.users.domain.RequiredPreferences;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Component
public class PaginationUtils {

    private static final int FIRST_PAGE = 0;
    private static final long TOTAL_ELEMENTS_SIZE = 0;

    private AuthenticationService authenticationService;

    public Pageable getPageableWithUserPreferencesSupport(PaginationRequest paginationRequest) {
        Integer numberOfRecords = paginationRequest.getTamanhoDaPagina();
        Integer pageNumber = paginationRequest.getNumeroDaPagina();

        if (numberOfRecords == null) {
            Map<String, String> userPreferences = authenticationService.getCurrentUser().getPreferencesAsMap();
            String recordsPerPage = userPreferences.get(RequiredPreferences.RECORDS_PER_PAGE.getName());
            numberOfRecords = Integer.valueOf(recordsPerPage);
        }

        if (pageNumber == null)
            pageNumber = FIRST_PAGE;

        Pageable page = PageRequest.of(pageNumber, numberOfRecords);
        return page;
    }

    public static Pageable firstResultPageable() {
        String defaultRecordsPerPage = RequiredPreferences.RECORDS_PER_PAGE.getDefaultValue();
        return PageRequest.of(FIRST_PAGE, Integer.valueOf(defaultRecordsPerPage));
    }

    public static <T> Page<T> emptyPage() {
        return new PageImpl<T>(Collections.emptyList(), firstResultPageable(), TOTAL_ELEMENTS_SIZE);
    }

    public static <T> Page<T> emptyPage(Pageable pageable) {
        return new PageImpl<T>(Collections.emptyList(), pageable, TOTAL_ELEMENTS_SIZE);
    }

    public static <T> Page<T> emptyPage(Pageable pageable, long totalElements) {
        return new PageImpl<T>(Collections.emptyList(), pageable, totalElements);
    }

    public static <T> Page<T> pageOf(List<T> content, Pageable pageable) {
        return new PageImpl<T>(content, pageable, content.size());
    }

    public static <T> Page<T> pageOf(List<T> content, Pageable pageable, long totalElements) {
        return new PageImpl<T>(content, pageable, totalElements);
    }

    public static <T> Page<T> pageOf(List<T> content) {
        return new PageImpl<T>(content, firstResultPageable(), content.size());
    }
}
