package com.shopping.dessert.controller;

import com.shopping.dessert.dto.CartDto;
import com.shopping.dessert.dto.ProductDto;
import com.shopping.dessert.entity.ProductEntity;
import com.shopping.dessert.service.FileService;
import com.shopping.dessert.service.ProductService;
import lombok.RequiredArgsConstructor;
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
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final FileService fileService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/add")
    public String addProduct(Model model){
        model.addAttribute("productAddForm",new ProductDto.Request.ProductAddForm());
        return "product/add";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public String addProduct(@Valid ProductDto.Request.ProductAddForm productAddForm, BindingResult result, Model model, RedirectAttributes re){

        Optional<ProductEntity> productEntity = productService.getProductByName(productAddForm.getName());

        if (productEntity.isPresent()){
            result.rejectValue("name","sameName","같은 이름의 상품이 존재합니다.");
        }

        if(result.hasErrors()){
            model.addAttribute("productAddForm", productAddForm);
            return "product/add";
        }

        ProductEntity product = productService.addProduct(productAddForm);
        fileService.uploadFile(productAddForm.getMultiParts(), product);

        re.addAttribute("productId",product.getProductId());
        return "redirect:/products/{productId}"; // detail 페이지로 넘기기
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/update/{productId}")
    public String updateProduct(@PathVariable Long productId, Model model){
        ProductDto.ProductDetail productDetail = productService.getProductDetail(productId);

        model.addAttribute("productDetail", productDetail);
        return "product/update";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/update/{productId}")
    public String updateProduct(@PathVariable Long productId, @Valid @RequestBody ProductDto.ProductDetail productDetail, BindingResult result, Model model, RedirectAttributes re ){
        Optional<ProductEntity> productEntity = productService.getProductByName(productDetail.getName());

        if (productEntity.isPresent() && !productEntity.get().getProductId().equals(productId)){
            result.rejectValue("name","sameName","같은 이름의 상품이 존재합니다.");
        }

        if (result.hasErrors()){
            model.addAttribute("productDetail", productDetail);
            return "product/update";
        }

        productService.updateProduct(productDetail);
        re.addAttribute("productId",productId);
        return "redirect:/products/{productId}"; // productDetail 페이지로 넘기기
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
        return "redirect:/";
    }


    @GetMapping("/list")
    public String getProductList(Model model, Pageable pageable){
        Page<ProductDto.ProductDetail> productDetails = productService.getProductList(pageable);
        System.out.println(productDetails.getContent().get(0).getFileDtoList().get(0).getSavedName());
        model.addAttribute("productDetails",productDetails);
        return "product/list";

    }

    @GetMapping("/{productId}")
    public String getProductDetail(@PathVariable Long productId, Model model){
        model.addAttribute("productDetail", productService.getProductDetail(productId));
        model.addAttribute("cartAddForm",new CartDto.Response.CartAddForm());
        return "product/detail";
    }


}
