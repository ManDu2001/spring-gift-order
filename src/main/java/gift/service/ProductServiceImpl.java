package gift.service;

import gift.domain.Product;
import gift.dto.ProductAdminRequestDto;
import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.exception.ProductNotFoundException;
import gift.repository.ProductRepository;
import gift.validation.ProductNameValidator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
    ProductNameValidator.validate(productRequestDto.name(), false);
    Product product = new Product(productRequestDto.name(), productRequestDto.price(),
        productRequestDto.imageUrl());
    Product saved = productRepository.save(product);
    return new ProductResponseDto(saved);
  }

  public ProductResponseDto createAdminProduct(ProductAdminRequestDto productAdminRequestDto) {
    ProductNameValidator.validate(productAdminRequestDto.name(), productAdminRequestDto.kakaoConfirmed());
    Product product = new Product(productAdminRequestDto.name(), productAdminRequestDto.price(),
        productAdminRequestDto.imageUrl());
    Product saved = productRepository.save(product);
    return new ProductResponseDto(saved);

  }

  public Page<ProductResponseDto> searchAllProducts(Pageable pageable) {
    return productRepository.findAll(pageable)
        .map(ProductResponseDto::new);
  }

  public ProductResponseDto searchProductById(Long id) {
    Optional<Product> optionalProduct = productRepository.findById(id);

    Product product = optionalProduct.orElseThrow(() ->
        new ProductNotFoundException(id)
    );

    return new ProductResponseDto(product);
  }

  public ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto) {
    ProductNameValidator.validate(productRequestDto.name(), false);
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException(id));

    product.update(productRequestDto.name(), productRequestDto.price(), productRequestDto.imageUrl());

    return new ProductResponseDto(product);
  }

  public ProductResponseDto updateAdminProduct(Long id, ProductAdminRequestDto productAdminRequestDto) {
    ProductNameValidator.validate(productAdminRequestDto.name(), productAdminRequestDto.kakaoConfirmed());
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException(id));

    product.update(productAdminRequestDto.name(), productAdminRequestDto.price(), productAdminRequestDto.imageUrl());

    return new ProductResponseDto(product);
  }

  public void deleteProduct(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException(id));
    productRepository.delete(product);
  }
}
