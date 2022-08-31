package com.shopping.dessert.controller;

import com.shopping.dessert.dto.ProductDto;
import com.shopping.dessert.service.ProductService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/add")
    public String addProduct(Model model){
        model.addAttribute("addForm", new ProductDto.Request.AddForm());
        return "product/add";
    }

    @PostMapping("/add")
    public String addProduct(ProductDto.Request.AddForm addForm, BindingResult result, Model model, RedirectAttributes re){

        if (productService.isExistedName(addForm.getName())) {
            result.rejectValue("name","sameName","같은 이름의 상품이 존재합니다.");
        }

        if(result.hasErrors()){
            model.addAttribute("addForm", addForm);
            return "product/add";
        }

        Long productId = productService.addProduct(addForm);
        re.addAttribute("productId",productId);
        return "redirect:/products/{productId}"; // detail 페이지로 넘기기
    }

    //    @PutMapping("/{productId}")
//    public String updateProduct(@PathVariable Long productId){
//
//    }

    //상품 삭제


    //TODO: page 적용
    @GetMapping("/list")
    public String getProductList(Model model){

        List<ProductDto.Response.Detail> products = productService.getProductList();
        model.addAttribute("products",products);

        return "product/list";

    }

    @GetMapping("/{productId}")
    public String getProductDetail(@PathVariable Long productId, Model model){
        model.addAttribute("detail", productService.getProductDetail(productId));
        return "product/detail";
    }


}
