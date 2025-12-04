package com.example.shoppingapp.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {

    String message()default "(Parol guclu deyil! 8 simvol, böyük/kiçik hərf, rəqəm və xüsusi simvol olmali)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};



}
