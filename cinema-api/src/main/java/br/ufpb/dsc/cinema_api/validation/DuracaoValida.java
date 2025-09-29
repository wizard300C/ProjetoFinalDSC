package br.ufpb.dsc.cinema_api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DuracaoValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DuracaoValida {
    String message() default "Duração inválida do filme";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
