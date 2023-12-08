package dsw.finApp.v1.budget;

import dsw.finApp.utils.pagination.DefaultWrapper;
import dsw.finApp.utils.pagination.PaginationRequest;
import dsw.finApp.v1.budget.model.request.BudgetRequest;
import dsw.finApp.v1.budget.model.response.BudgetResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("/finApp/v1/budget")
@Validated
public class BudgetController {
    private BudgetFacade budgetFacade;

    @GetMapping
    public ResponseEntity<DefaultWrapper> findAll(
            @RequestParam(name = "budgetName", defaultValue = "") String budgetName,
            @RequestParam(name = "budgetLimit", defaultValue = "") String budgetLimit,
            PaginationRequest paginationRequest) {
        Double budgetLimitValue = null;
        if (!budgetLimit.isEmpty())
                budgetLimitValue = Double.parseDouble(budgetLimit);
        var response = budgetFacade.findAll(paginationRequest, budgetName, budgetLimitValue);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetResponse> findByCode(@PathVariable(name = "id") String code) {
        var response = budgetFacade.findByCode(code);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByCode(@PathVariable(name = "id") String code) {
        budgetFacade.deleteByCode(code);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<BudgetResponse> insert(
            @Valid @RequestBody BudgetRequest budgetRequest,
            HttpServletResponse response) {
        var dto = budgetFacade.insert(budgetRequest);
        URI uri = getURIFor(dto.getCode());
        response.addHeader(HttpHeaders.LOCATION, uri.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetResponse> update(@PathVariable(name = "id") String code,
                                                  @Valid @RequestBody BudgetRequest budgetRequest) {
        var response = budgetFacade.update(code, budgetRequest);
        return ResponseEntity.ok(response);
    }

    private URI getURIFor(String code) {
        return ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{code}")
                .buildAndExpand(code)
                .toUri();
    }
}
