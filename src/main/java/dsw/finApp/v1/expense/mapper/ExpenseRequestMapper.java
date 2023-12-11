package dsw.finApp.v1.expense.mapper;

import dsw.finApp.v1.expense.domain.ExpenseEntity;
import dsw.finApp.v1.expense.model.request.ExpenseRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExpenseRequestMapper {
    ExpenseRequestMapper INSTANCE = Mappers.getMapper(ExpenseRequestMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "expenseName")
    @Mapping(target = "expensePrice")
    @Mapping(target = "expenseType")
    ExpenseEntity mapModelToEntity(ExpenseRequest request);
}
