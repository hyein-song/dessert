package com.shopping.dessert.controller;

import com.shopping.dessert.dto.ProductDto;
import com.shopping.dessert.entity.ProductEntity;
import com.shopping.dessert.service.ProductService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/add")
    public String addProduct(Model model){
        model.addAttribute("addForm",new ProductDto.Request.AddForm());
        return "product/add";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public String addProduct(@Valid ProductDto.Request.AddForm addForm, BindingResult result, Model model, RedirectAttributes re){

        Optional<ProductEntity> productEntity = productService.getProductByName(addForm.getName());

        if (productEntity.isPresent()){
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/update/{productId}")
    public String updateProduct(@PathVariable Long productId, Model model){
        ProductDto.Detail detail = productService.getProductDetail(productId);

        model.addAttribute("detail",detail);
        return "product/update";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/update/{productId}")
    public String updateProduct(@PathVariable Long productId, @Valid ProductDto.Detail detail, BindingResult result, Model model, RedirectAttributes re ){
        Optional<ProductEntity> productEntity = productService.getProductByName(detail.getName());

        if (productEntity.isPresent() && !productEntity.get().getProductId().equals(productId)){
            result.rejectValue("name","sameName","같은 이름의 상품이 존재합니다.");
        }

        if (result.hasErrors()){
            model.addAttribute("detail", detail);
            return "product/update";
        }

        productService.updateProduct(detail);
        re.addAttribute("productId",productId);
        return "redirect:/products/{productId}"; // detail 페이지로 넘기기
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
        return "redirect:/";
    }


//    //TODO: page 적용
//    @GetMapping("/list")
//    public String getProductList(Model model){
//
//        List<ProductDto.Detail> products = productService.getProductList();
//        model.addAttribute("products",products);
//
//        return "product/list";
//
//    }

    @GetMapping("/list")
    public String getProductList(Model model, Pageable pageable){
        Page<ProductDto.Detail> products = productService.getProductList(pageable);
        model.addAttribute("products",products);
        return "product/list";

    }

    @GetMapping("/{productId}")
    public String getProductDetail(@PathVariable Long productId, Model model){
        model.addAttribute("detail", productService.getProductDetail(productId));
        return "product/detail";
    }


}
