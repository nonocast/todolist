package cn.nonocast.api;

import cn.nonocast.model.Comment;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController("apiCommentController")
@RequestMapping("/api")
public class CommentController {
	private List<Comment> comments = new ArrayList<>();

	@RequestMapping("comments")
	public Object index() {
		return comments;
	}

	@RequestMapping(value = "comments", method = RequestMethod.POST)
	public Object create(@ModelAttribute Comment comment) {
		comments.add(comment);
		return comment;
	}

	public CommentController() {
		comments.add(new Comment(1L, "nonocast", "hello world"));
		comments.add(new Comment(2L, "h705c", "烦不烦啊"));
		comments.add(new Comment(3L, "tony", "测试测试"));
	}
}
