package com.gmail.woosay333.onlinebookstore.validation;

import com.gmail.woosay333.onlinebookstore.annotation.UniqueIsbn;
import com.gmail.woosay333.onlinebookstore.service.BookService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueIsbnValidator implements ConstraintValidator<UniqueIsbn, String> {
    private final BookService bookService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !bookService.isIsbnAlreadyInUse(value);
    }
}
