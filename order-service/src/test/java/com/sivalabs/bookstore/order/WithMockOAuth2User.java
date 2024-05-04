package com.sivalabs.bookstore.order;

import java.lang.annotation.*;
import org.springframework.security.test.context.support.WithSecurityContext;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@WithSecurityContext(factory = MockOAuth2UserContextFactory.class)
public @interface WithMockOAuth2User {

    long id() default -1;

    String value() default "user";

    String username() default "";

    String[] roles() default {"USER"};
}
