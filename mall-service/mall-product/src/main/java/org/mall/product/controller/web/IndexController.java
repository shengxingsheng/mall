package org.mall.product.controller.web;

import org.mall.product.dto.Catalog2DTO;
import org.mall.product.entity.Category;
import org.mall.product.service.CategoryService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author sxs
 * @since 2023/1/31
 */
@Controller
public class IndexController {

    private final CategoryService categoryService;
    private final RedissonClient redissonClient;
    public IndexController(CategoryService categoryService, RedissonClient redissonClient) {
        this.categoryService = categoryService;
        this.redissonClient = redissonClient;
    }

    @GetMapping({"/", "/index.html"})
    public String indexPage(Model model) {

        List<Category> categoryList= categoryService.getLevel1();
        model.addAttribute("categorys", categoryList);
        return "index";
    }

    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<Catalog2DTO>> getCatalog() {
        return categoryService.getCatalog();
    }
    @ResponseBody
    @GetMapping("/hello")
    public String hello() {
        RLock lock = redissonClient.getLock("my-lock");
        //加锁-阻塞式
        lock.lock();
        try{
            System.out.println("加锁成功，执行业务..."+Thread.currentThread().toString());
            Thread.sleep(30000L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("释放锁"+Thread.currentThread().toString());
            //解锁
            lock.unlock();
        }
        return "hello";
    }

}
