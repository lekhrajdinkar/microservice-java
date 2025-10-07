package basicWebApp.jewelleryApp.controller;

import basicWebApp.jewelleryApp.custom.enums.StatusEnum;
import basicWebApp.jewelleryApp.dto.JewelleryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/CRUD")
@Tag(name = "Jewellery API for My-Store", description = "Custom API for demonstrating Jewellery APIs")
@Validated
public class CrudController {

    //====================
    // 1. SWAGGER API Doc
    //====================
    @GetMapping(value={"/get-random-one"})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Operation(
            summary = "Get random hardcoded jewellery",
            description = "Demonstrate complete swagger doc",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    schema = @Schema(implementation = JewelleryDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Item not found")
            }
    )
    /*
        method arg : @Parameter(
                name =  "firstName",
                description  = "First Name of the user",
                example = "Vatsal",
                required = true)
    */
    JewelleryDTO get_one() throws Exception
    {
        JewelleryDTO dto =  JewelleryDTO.builder()
                .name("Jewellery-1")
                .id(1)
                .price(20000)
                .createTime(LocalDateTime.now())
                .build();
        return dto;
    }



    //====================
    // 2. BINDING
    //====================
    @Operation(description = "Demonstrate Binding data - header,body, pathParam, requestParam")
    @GetMapping(value = {
             "/binding/{pathVar_1}"
            ,"/binding/{pathVar_1}/{pathVar_2}"
    })
    ResponseEntity<JewelleryDTO> getJewelleryRE(
            HttpServletResponse response,

            @RequestParam(value = "ReqParam1", defaultValue = "defaultValue", required = false) String reqParam1,
            @RequestParam Map<String,String> allRequestParams, // <<<< map

            @PathVariable("pathVar_1") String pathVariable1,
            @PathVariable(value="pathVar_2", required = false) String pathVariable2_optional,
            @PathVariable Map<String,String> allPathVariables, // <<<< map

            @RequestHeader("h1") String h1,
            @RequestHeader(value="h2",required = false, defaultValue = "v2-default") String h2_Optional,
            @RequestHeader Map<String,String> allHeader, // <<<< map
            @RequestHeader HttpHeaders httpHeaders

            , @RequestBody JewelleryDTO dto_input
    ) throws Exception
    {
        JewelleryDTO dto =  JewelleryDTO.builder()
                .name("Jewellery-2")
                .id(2)
                .price(2000)
                .createTime(LocalDateTime.now())
                .build();

        log.info(" \n\n >>> pathVariable1|2 : {}|{}, header-1|2: {}|{} \n", pathVariable1,pathVariable2_optional, h1,h2_Optional);
        allHeader.forEach((k,v)-> System.out.println(k+":"+v));
        allPathVariables.forEach((k,v)-> System.out.println(k+"::"+v));
        allRequestParams.forEach((k,v)-> System.out.println(k+":::"+v));

        // Way-1
        // response.setHeader("h1","v1");
        // response.setStatus(200);

        // way-2
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.add("h1","v1");
        ResponseEntity<JewelleryDTO> httpResponse1 = new ResponseEntity<JewelleryDTO>(dto,responseHeader,HttpStatus.OK);

        // WAY-3 ResponseEntity BUILDER
        ResponseEntity<JewelleryDTO> httpResponse2 = ResponseEntity
                .status(200)
                .header("h1", "v1")
                .header("h2", "v2")
                .body(dto);

        return httpResponse2;
    }

    //=====================================
    // 3. Validator - Request and response
    // cannot valid ResponseEntity.
    //=====================================
    @Operation(description = "Demonstrate data validation Binding request data + on response Data")
    @Valid
    @PostMapping("/validation")
    // JSON --> dtoInput
    JewelleryDTO getJewellery3(@Valid @RequestBody JewelleryDTO dtoInput) throws Exception
    {
        log.info("dtoInput : {}", dtoInput);

        /*JewelleryDTO dto =  JewelleryDTO.builder()
                .name("Jewellery-3")
                .id(dtoInput.getId())
                .price(300) // change to fail
                .createTime(LocalDateTime.now())
                .build();
        return dto;*/

        /*ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dtoInput);
        JewelleryDTO dto2 = mapper.readValue(json, JewelleryDTO.class);*/

         dtoInput.setStockStatus(StatusEnum.IN_STOCK); // <<<
         return dtoInput;
    }

}
