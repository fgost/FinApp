package dsw.finApp.v1.budget;

import dsw.finApp.utils.pagination.DefaultWrapper;
import dsw.finApp.utils.pagination.PaginationRequest;
import dsw.finApp.utils.pagination.PaginationUtils;
import dsw.finApp.v1.budget.mapper.BudgetRequestMapper;
import dsw.finApp.v1.budget.mapper.BudgetResponseMapper;
import dsw.finApp.v1.budget.model.request.BudgetRequest;
import dsw.finApp.v1.budget.model.response.BudgetResponse;
import dsw.finApp.v1.budget.service.BudgetService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BudgetFacade {
    private final BudgetService budgetService;
    private final PaginationUtils paginationUtils;

    public BudgetFacade(BudgetService budgetService, PaginationUtils paginationUtils) {
        this.budgetService = budgetService;
        this.paginationUtils = paginationUtils;
    }

    public DefaultWrapper findAll(PaginationRequest paginationRequest, String budgetName, Double budgetLimit) {
        Pageable pageable = paginationUtils.getPageableWithUserPreferencesSupport(paginationRequest);
        var entities = budgetService.findAll(pageable, budgetName, budgetLimit);
        var dtos = entities.getContent().stream().map(BudgetResponseMapper.INSTANCE::mapEntityToResponse).toList();
        var page = PaginationUtils.pageOf(List.copyOf(dtos), pageable, entities.getTotalElements());
        return new DefaultWrapper(page);
    }

    public BudgetResponse findByCode(String code) {
        var entity = budgetService.findByCode(code);
        return BudgetResponseMapper.INSTANCE.mapEntityToBudgetResponse(entity);
    }

    public BudgetResponse insert(BudgetRequest input) {
        var entity = BudgetRequestMapper.INSTANCE.mapModelToEntity(input);
        var savedEntity = budgetService.insert(entity);
        return BudgetResponseMapper.INSTANCE.mapEntityToResponse(savedEntity);
    }

    public BudgetResponse update(String code, BudgetRequest request) {
        var entity = BudgetRequestMapper.INSTANCE.mapModelToEntity(request);
        var savedEntity = budgetService.update(code, entity);
        return BudgetResponseMapper.INSTANCE.mapEntityToResponse(savedEntity);
    }

    public void deleteByCode(String code) {
        budgetService.deleteByCode(code);
    }
}
