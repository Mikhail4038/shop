package com.keiko.productservice.repository.specs;

import com.keiko.productservice.entity.Product;
import com.keiko.productservice.entity.Product_;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.time.LocalDate;

public class ProductExpirationSpecs {
    public static Specification<Product> isExpired () {
        return (root, query, builder) -> {
            Timestamp now = Timestamp.valueOf (LocalDate.now ().atStartOfDay ());
            return builder.lessThan (root.get (Product_.EXPIRATION_DATE), now);
        };
    }

    public static Specification<Product> isExpirationDateSoon (byte daysForPromo) {
        return (root, query, builder) -> {
            LocalDate deadline = LocalDate.now ().plusDays (daysForPromo);
            return builder.lessThan (root.get (Product_.EXPIRATION_DATE),
                    Timestamp.valueOf (deadline.atStartOfDay ()));
        };
    }
}
