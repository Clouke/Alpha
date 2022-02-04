package gg.clouke.alpha.check;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BaseCheck {

    String name();
    String type();
    boolean experimental() default false;

}