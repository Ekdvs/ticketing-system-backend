package com.company.ticket_booking_backend.controller;

import com.company.ticket_booking_backend.model.Category;
import com.company.ticket_booking_backend.model.SubCategory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/meta")
public class SubcategoriesController {

    @GetMapping("/categories")
    public Map<String, List<String>> getCategories() {

        Map<String, List<String>> result = new LinkedHashMap<>();

        for (Category category : Category.values()) {

            List<String> subCategories = Arrays.stream(SubCategory.values())
                    .filter(sc -> sc.getCategory() == category)
                    .map(Enum::name)
                    .collect(Collectors.toList());

            result.put(category.name(), subCategories);
        }

        return result;
    }
}