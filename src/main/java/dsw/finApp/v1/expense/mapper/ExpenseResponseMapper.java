package dsw.finApp.v1.expense.mapper;

import dsw.finApp.v1.expense.model.response.ExpenseResponse;
import dsw.finApp.v1.expense.domain.ExpenseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExpenseResponseMapper {
    ExpenseResponseMapper INSTANCE = Mappers.getMapper(ExpenseResponseMapper.class);

    ExpenseResponse mapEntityToResponse(ExpenseEntity entity);

    @Mapping(target = "expenseName")
    @Mapping(target = "expensePrice")
    @Mapping(target = "expenseType")
    ExpenseResponse mapEntityToExpenseResponse(ExpenseEntity entity);
}
