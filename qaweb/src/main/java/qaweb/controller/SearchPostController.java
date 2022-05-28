package qaweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import qaweb.model.Post;
import qaweb.model.User;
import qaweb.repository.PostRepository;


import java.util.List;

@Controller
@SessionAttributes({ "user", "users" })
public class SearchPostController {

    private final PostRepository postRepo;

    @Autowired
    public SearchPostController(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    @PostMapping("/search")
    public String searchProcess(@ModelAttribute User user,
                                @RequestParam("key") String key,Model model){
//        System.out.print(key);
        List<Post> posts = postRepo.findByTitleOrContent(key);
//        System.out.println(posts.size());
        model.addAttribute("posts", posts);
        model.addAttribute("key", key);
        return "index";
    }

    @GetMapping("/category/{category}")
    public String categoryProcess(@ModelAttribute User user,
                                  @PathVariable("category") String category, Model model){
//        System.out.print(category);
        List<Post> posts = postRepo.findPostByCategory(category);
//        System.out.println(posts.size());
        model.addAttribute("posts", posts);
        return "index";
    }
}

