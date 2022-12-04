/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.springboot.praktikum10;

import java.util.HashMap;
import java.util.Map;
import model.Product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author raiha
 */
@RestController
public class ProductServiceController {
    
    //tempat untuk meletakkan produk
    private static Map<String, Product> productRepo = new HashMap<>();
    static {
        //untuk menambahkan produk ke dalam daftar secara  manual
        Product honey = new Product();
        honey.setId("1");
        honey.setName("Honey");
        productRepo.put(honey.getId(), honey);
        
        Product almond = new Product();
        almond.setId("2");
        almond.setName("Almond");
        productRepo.put(almond.getId(), almond);
        
    }
    
    //menambahkan produk melalui postman
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<Object> createProduct(@RequestBody Product product) {
        productRepo.put(product.getId(), product);
        return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
    }
    
    //menghapus produk
    @RequestMapping(value = "/products/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") String id){
          productRepo.remove(id);
          return new ResponseEntity<>("Product is deleted successsfully", HttpStatus.OK);
    }
    
    //mengedit produk yang sudah ada
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateProduct(@PathVariable("id") String id, @RequestBody Product product){  
        //jika id yang dimasukkan tidak ada dalam daftar produk
        if(!productRepo.containsKey(id))
                {
                    return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
                }
        else 
        {
            productRepo.remove(id);
            product.setId(id);
            productRepo.put(id, product);
            return new ResponseEntity<>("Product is update successsfully", HttpStatus.OK);
        }
        
    }
    
    //menampilkan produk
    @RequestMapping(value = "/products")
    public ResponseEntity<Object> getProduct(@RequestParam(value = "name", required = false, defaultValue = "honey") String name) {
        
        return new ResponseEntity<>(productRepo.values(), HttpStatus.OK);
    }
    
        
}


    