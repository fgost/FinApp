package dsw.finApp.v1.budget.mapper;

import dsw.finApp.v1.budget.domain.BudgetEntity;
import dsw.finApp.v1.budget.model.response.BudgetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BudgetResponseMapper {
    BudgetResponseMapper INSTANCE = Mappers.getMapper(BudgetResponseMapper.class);

    BudgetResponse mapEntityToResponse(BudgetEntity entity);

    BudgetResponse mapEntityToBudgetResponse(BudgetEntity entity);
}
