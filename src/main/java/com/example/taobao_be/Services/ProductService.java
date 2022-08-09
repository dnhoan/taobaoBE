package com.example.taobao_be.Services;
import com.example.taobao_be.DTO.PageDTO;
import com.example.taobao_be.DTO.ProductDTO;
import com.example.taobao_be.models.Category;
import com.example.taobao_be.models.Product;
import com.example.taobao_be.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ServletContext servletContext;

    @Value("${file.upload-dir}")
    String FILE_DIRECTORY;

    public PageDTO<ProductDTO> getProductsByCateId(int offset, int limit, Category category) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Product> page = this.productRepository.findProductsByCategory(category, pageable);
        return getProductDTOPageDTO(page);
    }

    public PageDTO<ProductDTO> getAll(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Product> page = this.productRepository.findAll(pageable);
        return getProductDTOPageDTO(page);
    }

    private PageDTO<ProductDTO> getProductDTOPageDTO(Page<Product> page) {
        List<ProductDTO> productDTOList = page.stream().map(u -> this.modelMapper.map(u, ProductDTO.class)).collect(Collectors.toList());
        return new PageDTO<ProductDTO>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                productDTOList,
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
    }

    public ProductDTO create(Product product) throws Exception {
        try {
            this.productRepository.saveAndFlush(product);
            return this.modelMapper.map(product, ProductDTO.class);
        } catch (Exception exception) {
            throw new Exception("Create User false");
        }
    }

    public ProductDTO update(Product product) {
        Product u = this.productRepository.save(product);
        return this.modelMapper.map(u, ProductDTO.class);
    }

    public ProductDTO delete(Product product) {
        product.setStatus("0");
        this.productRepository.save(product);
        return this.modelMapper.map(product, ProductDTO.class);
    }

    public ProductDTO getById(Long id) throws Exception {
        Optional<Product> product = this.productRepository.findById(id);
        if (product.isPresent()) {
            return this.modelMapper.map(product.get(), ProductDTO.class);
        }
        throw new Exception("Not found category by id");
    }

    public Product getProductById(Long id) throws Exception {
        Optional<Product> productOptional = this.productRepository.findById(id);
        if (productOptional.isPresent()) {
            return productOptional.get();
        }
        throw new Exception("Not found category by id");
    }

    public String uploadFile(MultipartFile attach) throws IllegalStateException, IOException {
        if (!attach.isEmpty()) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String img = timestamp.getTime()+"_"+attach.getOriginalFilename();
            File myFile = new File(FILE_DIRECTORY + img);
            myFile.createNewFile();
            FileOutputStream fos =new FileOutputStream(myFile);
            fos.write(attach.getBytes());
            fos.close();
            return img;
        }
        return "";
    }
}
