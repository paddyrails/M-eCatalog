//package com.pp.cs.sales.catalog.api;
//
//
//import io.swagger.annotations.*;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestPart;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.validation.Valid;
//import javax.validation.constraints.*;
//import java.util.List;
//@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2026-03-14T11:07:35.828-04:00")
//
//@Validated
//@Api(value = "products", description = "the products API")
//@RequestMapping(value = "/api/sales/catalog/v1")
//public interface ProductsApi {
//
//    @ApiOperation(value = "Get products by country", nickname = "getProducts", notes = "Returns a list of products filtered by country. Response includes Name, Code, Description, and Price.", response = Product.class, responseContainer = "List", tags={ "catalog", })
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successful operation", response = Product.class, responseContainer = "List"),
//            @ApiResponse(code = 400, message = "Invalid request (e.g. missing country)"),
//            @ApiResponse(code = 200, message = "Unexpected error") })
//    @RequestMapping(value = "/products",
//            method = RequestMethod.GET)
//    ResponseEntity<List<Product>> getProducts(@NotNull @ApiParam(value = "Country code to filter products. Allowed values are us, can, mex.", required = true, allowableValues = "us, can, mex") @Valid @RequestParam(value = "country", required = true) String country);
//
//}
//
