package com.crawl.service.impl;

import com.crawl.dao.CategoryDao;
import com.crawl.dao.CrawlQueueDao;
import com.crawl.dao.ProductDao;
import com.crawl.dao.ShopDao;
import com.crawl.enums.Status;
import com.crawl.model.*;
import com.crawl.service.CrawlService;
import com.crawl.service.MapperService;
import com.crawl.utils.HttpUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Service(ShopeeCrawlService.CODE)
@Log
public class ShopeeCrawlService implements CrawlService {

    public static final String CODE = "SHOPEE";
    private static final String CDN = "https://cf.shopee.vn/file/";
    private static final String GET_ALL_CATEGORY = "https://shopee.vn/api/v4/pages/get_category_tree";
    private static final String GET_PRODUCT_BY_CAT = "https://shopee.vn/api/v4/search/search_items";
    private static final String GET_PRODUCT_DETAIL = "https://shopee.vn/api/v4/item/get";
    private static final String GET_SHOP_INFO = "https://shopee.vn/api/v4/product/get_shop_info";
    private static final String GET_SHOP_DETAIL = "https://shopee.vn/api/v4/shop/get_shop_detail";
    @Autowired
    private MapperService mapperService;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private CrawlQueueDao crawlQueueDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ShopDao shopDao;

    @Override
    public void doCrawJob(CrawlQueue queue) {
        log.log(Level.INFO, "ShopeeCrawlService >> Start Job System");
        crawlQueueDao.updateByStatus(queue, Status.IN_PROGRESS.getCode());
        var response = HttpUtil.get(GET_ALL_CATEGORY);
        var listCategory = mapperService.readValue(response, ShopeeCategoryCrawler.class);
        insertDB(listCategory.getData().getCategoryLists());
        var request = mapperService.readValue(queue.getRequest(), CrawlRequest.class);
        var rootCats = request.getCatIds() == null || request.getCatIds().isEmpty() ? categoryDao.getCategoryByParentId(0L) : categoryDao.getCategoryById(request.getCatIds());
        rootCats.forEach(i -> handleDb(i, request));
        log.log(Level.INFO, "ShopeeCrawlService >> Job Done. Have Fun :)");
        crawlQueueDao.updateByStatus(queue, Status.DONE.getCode());
    }

    private void insertDB(List<ShopeeCategoryCrawler.CategoryItem> categoryItems) {
        categoryItems.forEach(item -> {
            log.log(Level.INFO, "ShopeeCrawlService >> Start Sync Category >> {0}", item.getCatId());
            var dto = new CategoryDto();
            dto.setProvider(CODE);
            dto.setCatId(item.getCatId());
            dto.setCode(item.getName());
            dto.setName(item.getDisplayName());
            dto.setImage(getCdnForImage(item.getImage()));
            dto.setParentId(item.getParentCatId());
            categoryDao.add(dto);
            if (item.getChildren() != null) {
                insertDB(item.getChildren());
            }
        });
    }

    private void handleDb(CategoryDto category, CrawlRequest request) {
        var limit = 60;
        var newest = (Integer.parseInt(request.getPageFrom()) - 1) * limit;
        var totalItem = Integer.parseInt(request.getPageTo()) * limit;
        while (newest != totalItem) {
            var param = Map.of("match_id", category.getCatId().toString(), "by", "ctime", "order", "DESC", "limit", String.valueOf(limit), "newest", String.valueOf(newest));
            upsertData(param);
            newest = newest + limit;
        }
    }

    private void upsertData(Map<String, String> requestParam) {
        var response = HttpUtil.get(GET_PRODUCT_BY_CAT, requestParam, null);
        var listProductCrawler = mapperService.readValue(response, ShopeeItemCrawler.class).getItems();
        for (ShopeeItemCrawler.Product item : listProductCrawler) {
            log.log(Level.INFO, "ShopeeCrawlService >> Start Sync Product >> {0}", item.getProductId());
            var product = mapperService.readValue(HttpUtil.get(GET_PRODUCT_DETAIL, Map.of("itemid", item.getProductId().toString(), "shopid", item.getShopId().toString()), null), ShopeeItemDetailCrawler.class);
            var dto = new ProductDto();
            dto.setProvider(CODE);
            dto.setProdId(item.getProductId());
            dto.setShopId(item.getShopId());
            dto.setName(product.getData().getName());
            dto.setCurrency(item.getItemBasic().getCurrency());
            dto.setImage(getCdnForImage(product.getData().getImage()));
            dto.setImages(String.join(",", product.getData().getImages().stream().map(this::getCdnForImage).collect(Collectors.toList())));
            dto.setPrice(product.getData().getPrice());
            dto.setMaxPrice(product.getData().getMaxPrice());
            dto.setMinPrice(product.getData().getMinPrice());
            dto.setPriceBeforeDiscount(product.getData().getPriceBeforeDiscount());
            dto.setPriceMaxBeforeDiscount(product.getData().getPriceMaxBeforeDiscount());
            dto.setPriceMinBeforeDiscount(product.getData().getPriceMinBeforeDiscount());
            dto.setShopLocation(product.getData().getShopLocation());
            dto.setDescription(product.getData().getDescription());
            dto.setStock(product.getData().getStock());
            dto.setDiscount(product.getData().getDiscount());
            dto.setCtime(convertProviderCreatedDate(product.getData().getCtime()));
            dto.setStatus(product.getData().getStatus());
            dto.setLikeCount(product.getData().getLikeCount());
            dto.setRating(mapperService.writeValueAsString(product.getData().getItemRating()));
            dto.setSku(mapperService.writeValueAsString(product.getData().getSku()));
            dto.setAttributes(mapperService.writeValueAsString(product.getData().getAttributes()));
            product.getData().getCategories().forEach(cat -> productDao.mapToCategory(cat.getCatId(), dto.getProdId()));
            productDao.add(dto);
            log.log(Level.INFO, "ShopeeCrawlService >> Start Sync Shop >> {0}", item.getShopId());
            var shopInfo = mapperService.readValue(HttpUtil.get(GET_SHOP_INFO, Map.of("shopId", String.valueOf(item.getShopId())), null), ShopeeShopInfo.class);
            var shopDetail = mapperService.readValue(HttpUtil.get(GET_SHOP_DETAIL, Map.of("sort_sold_out", "0", "username", shopInfo.getData().getUsername()), null), ShopeeShopDetail.class);
            var shopDto = new ShopDto();
            if (shopDetail.getData() != null) {
                shopDto.setShopId(shopDetail.getData().getShopId());
                shopDto.setUserId(shopDetail.getData().getAccount().getUserId());
                shopDto.setName(shopDetail.getData().getName());
                shopDto.setUsername(shopDetail.getData().getAccount().getUsername());
                shopDto.setAvatar(getCdnForImage(shopDetail.getData().getAccount().getAvatar()));
                shopDto.setBackground(getCdnForImage(shopDetail.getData().getBackground()));
                shopDto.setShopLocation(shopDetail.getData().getShopLocation());
                shopDto.setRatingStar(shopDetail.getData().getRatingStar());
                shopDto.setRatingGood(shopDetail.getData().getRatingGood());
                shopDto.setRatingNormal(shopDetail.getData().getRatingNormal());
                shopDto.setRatingBad(shopDetail.getData().getRatingBad());
                shopDto.setTotalProduct(shopDetail.getData().getTotalProduct());
                shopDto.setFollower(shopDetail.getData().getFollower());
                shopDto.setDescription(shopDetail.getData().getDescription());
                shopDto.setCountry(shopDetail.getData().getCountry());
                shopDto.setStatus(shopDetail.getData().getStatus());
                shopDto.setProvider(CODE);
                shopDao.add(shopDto);
            }
        }
    }

    private String getCdnForImage(String image) {
        return CDN + image;
    }

    private Date convertProviderCreatedDate(String ctime) {
        var date = ctime + "000";
        return new Date(Long.parseLong(date));
    }
}
