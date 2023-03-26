package org.mall.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author sxs
 * @since 2023/1/21
 */
public class ListValueConstraintValidator implements ConstraintValidator<ListValue,Byte> {
    private Set<Byte> set = new HashSet<>();
    @Override
    public void initialize(ListValue constraintAnnotation) {
        byte[] vals=constraintAnnotation.vals();
        for (byte val : vals) {
            set.add(val);
        }
    }

    @Override
    public boolean isValid(Byte value, ConstraintValidatorContext context) {
        if (Objects.isNull(value) ||set.contains(value)) {
            return true;
        }
        return false;
    }
}
