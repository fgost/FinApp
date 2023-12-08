package dsw.finApp.v1.expense.domain;

import dsw.finApp.exception.util.ExceptionUtils;
import dsw.finApp.exception.util.MessageResource;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ExpenseTypeEnum {

    FIXED((short) 0),
    VARIABLE((short) 1);

    private final short value;

    public static ExpenseTypeEnum fromValue(Short expenseTypeCode) {
        var message = MessageResource.getInstance().getMessage("expense.type.invalid");
        return Arrays.stream(ExpenseTypeEnum.values())
                .filter(it -> Short.valueOf(it.getValue()).equals(expenseTypeCode))
                .findFirst()
                .orElseThrow(() ->  ExceptionUtils.buildBadRequestException(message));
    }
}
