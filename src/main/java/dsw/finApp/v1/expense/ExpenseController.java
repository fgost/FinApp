package dsw.finApp.v1.expense;

import dsw.finApp.utils.pagination.DefaultWrapper;
import dsw.finApp.utils.pagination.PaginationRequest;
import dsw.finApp.v1.expense.model.request.ExpenseRequest;
import dsw.finApp.v1.expense.model.response.ExpenseResponse;
import dsw.finApp.v1.users.domain.Expense;
import dsw.finApp.v1.users.domain.Preference;
import dsw.finApp.v1.users.model.response.UserResponseExpense;
import dsw.finApp.v1.users.model.response.UserResponsePreference;
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
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/finApp/v1/expense")
@Validated
public class ExpenseController {
    private ExpenseFacade expenseFacade;

    @GetMapping
    public ResponseEntity<DefaultWrapper> findAll(
            @RequestParam(name = "expenseName", defaultValue = "") String expenseName,
            @RequestParam(name = "price", defaultValue = "") String price,
            PaginationRequest paginationRequest) {
        Double priceValue = null;
        if (!price.isEmpty())
            priceValue = Double.parseDouble(price);
         var response = expenseFacade.findAll(paginationRequest, expenseName, priceValue);
         return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> findByCode(@PathVariable(name = "id") String code) {
        var response = expenseFacade.findByCode(code);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ExpenseResponse> insert(
            @Valid @RequestBody ExpenseRequest expenseRequest,
            HttpServletResponse response) {
        var dto = expenseFacade.insert(expenseRequest);
        URI uri = getURIFor(dto.getCode());
        response.addHeader(HttpHeaders.LOCATION, uri.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> update(@PathVariable(name = "id") String code,
                                                  @Valid @RequestBody ExpenseRequest expenseRequest) {
        var response = expenseFacade.update(code, expenseRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByCode(@PathVariable(name = "id") String code) {
        expenseFacade.deleteByCode(code);
        return ResponseEntity.noContent().build();
    }

    private URI getURIFor(String code) {
        return ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{code}")
                .buildAndExpand(code)
                .toUri();
    }
}
