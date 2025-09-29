package br.ufpb.dsc.cinema_api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DuracaoValidator implements ConstraintValidator<DuracaoValida, Integer> {
    @Override
    public boolean isValid(Integer duracao, ConstraintValidatorContext context) {
        if (duracao == null) return false;
        return duracao >= 30 && duracao <= 300;
    }
}
