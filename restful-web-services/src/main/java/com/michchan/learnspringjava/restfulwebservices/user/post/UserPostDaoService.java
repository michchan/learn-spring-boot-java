package com.michchan.learnspringjava.restfulwebservices.user.post;

import com.michchan.learnspringjava.restfulwebservices.post.Post;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserPostDaoService {
    private static List<UserPost> posts = new ArrayList<>();
    private static int postsCount = 0;

    static {
        List<String> media = new ArrayList<>();
        media.add("https://media.istockphoto.com/photos/hand-arranging-wood-block-stacking-as-step-stair-on-paper-pink-picture-id1169974807?s=612x612");
        media.add("https://media.istockphoto.com/photos/software-developer-freelancer-working-at-home-picture-id1174690086?s=612x612");
        media.add("https://media.istockphoto.com/photos/close-up-man-hand-arranging-wood-block-stacking-as-step-stair-on-picture-id1217026295?s=612x612");

        postsCount++;
        posts.add(new UserPost(3, new Post(postsCount++, "This is the first post", "UserPost 1 description", media)));
        posts.add(new UserPost(3, new Post(postsCount++, "This is the second post", "UserPost 2 description", media)));
        posts.add(new UserPost(1, new Post(postsCount++,"This is the third post", "UserPost 3 description", media)));
        posts.add(new UserPost(1, new Post(postsCount++, "This is the fourth post", "UserPost 4 description", media)));
        posts.add(new UserPost(2, new Post(postsCount++, "This is the fifth post", "UserPost 5 description", media)));
        postsCount--;

        System.out.println("postsCount: " + postsCount);
    }

    public List<UserPost> findAllByUserId(int userId) {
        List<UserPost> userPosts = new ArrayList<>();
        for (UserPost post:posts) {
            if (post.getUserId() == userId) {
                userPosts.add(post);
            }
        }
        return userPosts;
    }

    public UserPost save(UserPost post) {
        if (post.getId() == null) {
            post.setId(++postsCount);
        }
        posts.add(post);
        return post;
    }

    public UserPost findOne(int id, int userId) {
        for (UserPost post:posts) {
            if (post.getId() == id && post.getUserId() == userId) {
                return post;
            }
        }
        return null;
    }
}
