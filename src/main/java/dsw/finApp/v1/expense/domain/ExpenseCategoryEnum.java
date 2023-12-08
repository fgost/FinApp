package dsw.finApp.v1.expense.domain;

import dsw.finApp.exception.util.ExceptionUtils;
import dsw.finApp.exception.util.MessageResource;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ExpenseCategoryEnum {

    HOUSING((short) 0),
    FOOD((short) 1),
    TRANSPORTATION((short) 2),
    HEALTH((short) 3),
    LEISURE((short) 4),
    CLOTHING((short) 5),
    INVESTMENTS((short) 6),
    DEBTS((short) 7),
    TAXES((short) 8),
    INSURANCE((short) 9),
    VARIABLE((short) 10),
    PETS((short) 11),
    DONATIONS((short) 12),
    CHILD((short) 13),
    EDUCATION((short) 14);

    private final short value;

    public static ExpenseCategoryEnum fromValue(Short expenseCategoryCode) {
        var message = MessageResource.getInstance().getMessage("expense.category.invalid");
        return Arrays.stream(ExpenseCategoryEnum.values())
                .filter(it -> Short.valueOf(it.getValue()).equals(expenseCategoryCode))
                .findFirst()
                .orElseThrow(() ->  ExceptionUtils.buildBadRequestException(message));
    }
}
