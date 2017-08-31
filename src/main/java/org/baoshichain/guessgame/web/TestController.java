package org.baoshichain.guessgame.web;

import org.baoshichain.guessgame.entity.User;
import org.baoshichain.guessgame.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by hisen on 17-4-24.
 */
@Controller
@RequestMapping("/test")
public class TestController {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private UserService userService;

  @RequestMapping(value = "/{ucid}", method = RequestMethod.GET)
  private String detail(@PathVariable("ucid") Integer ucId, Model model) {
    User user = userService.selectByPrimaryKey(ucId);
    //System.out.println(user.getName());
    model.addAttribute("user", user);
    return "detail";
  }
/*
  @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
  @ResponseBody
  private String add(Book book) {
    Book hasBook = bookService.getById(book.getBookId());
    int i = -2;
    if (hasBook == null) {
      i = bookService.addBook(book);
    }
    return i > 0 ? "success" : "error";
  }

  @RequestMapping(value = "/del/{bookId}", method = RequestMethod.GET)
  @ResponseBody
  private String deleteBookById(@PathVariable("bookId") Long id) {
    int i = bookService.deleteBookById(id);
    return i > 0 ? "success" : "error";
  }*/
}
