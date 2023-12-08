package dsw.finApp.v1.budget.mapper;

import dsw.finApp.v1.budget.domain.BudgetEntity;
import dsw.finApp.v1.budget.model.request.BudgetRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BudgetRequestMapper {

    BudgetRequestMapper INSTANCE = Mappers.getMapper(BudgetRequestMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "budgetName")
    @Mapping(target = "budgetLimit")
    BudgetEntity mapModelToEntity(BudgetRequest request);

}
