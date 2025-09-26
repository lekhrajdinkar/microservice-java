package microservice.basicWebApp.jewelleryApp.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class ApiVersionController
{

    //=====================================
    // 4. API Versioning
    // header ::  Accept="application/v1/2+json" // 👈🏻
    //=====================================
    @GetMapping(
            value="/sample-get",
            produces="application/v1+json" // 👈🏻 produces
    )
    private Integer getId() { return 20; }

    @GetMapping(
            value="/sample-get",
            produces="application/v2+json" // 👈🏻
    )
    private String getName() { return "20String / v2"; }
}
